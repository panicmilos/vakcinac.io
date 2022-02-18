<template>
  <div>
    <Form
      :data="data"
      :schema="schema"
      @submit="onSubmit"
    />

    <Table>
      <TableHead :columnNames="tableHead"></TableHead>
      <TableBody>
        <TableRow
          v-for="(document, i) in documents" 
          :key="i" 
          :values="formTableRow(document)"
        >
        <div>
          <button @click="downloadPdf(document)">PDF</button>
          <button @click="downloadXhtml(document)">XHTML</button>
        </div>
        </TableRow>
      </TableBody>
    </Table>
  </div>
</template>

<script>
import x from "xml2js";
import download from "downloadjs";
import { defineComponent } from "@vue/composition-api";
import Form from "../../components/Form.vue";
import Table from "../../components/Table/Table.vue";
import TableBody from "../../components/Table/TableBody.vue";
import TableHead from "../../components/Table/TableHead.vue";
import TableRow from "../../components/Table/TableRow.vue";
import axios from "axios";
import { API_URL } from "../../cfg";

const schema = {
  properties: {
    "jmbg": {
      type: "string"
    }
  }
};

export default defineComponent({
  components: {
    Form,
    Table,
    TableHead,
    TableBody,
    TableRow
  },

  data() {
    return {
      data: {},
      documents: {},
      tableHead: ['Izdat', 'Id', 'Tip', 'Link'],
      schema,
    };
  },
  
  methods: {
    onSubmit(data) {
      const { jmbg } = data;

      axios.get(`${API_URL}/gradjani/${jmbg}/documents/all`,)
        .then((r) => {
          const parser = new x.Parser();
          parser.parseString(r.data, (err, res) => {
            this.documents = res['citizen-documents-result']['citizen-document'];
          })
        })
        .catch((e) => console.log(e));
    },

    formTableRow(document) {
      return [document['created-at'][0].split("^^")[0], document['id'][0], document['type'][0], document['link'][0]];
    },

    findUrlParts(document) {
      const documentParts = this.formTableRow(document);
      const documentPrefixes = {
        'Digitalni Sertifikat': 'sertifikati',
        'Potvrda': 'potvrde',
        'Saglasnost': 'saglasnosti',
        'Izjava': 'izjave',
        'Zahtev': 'zahtevi'
      };

      const pathPrefix = documentPrefixes[documentParts[2]];
      const id = documentParts[1];

      return { pathPrefix, id };
    },

    downloadPdf(document) {
      const { pathPrefix, id } = this.findUrlParts(document);

      axios.get(`${API_URL}/${pathPrefix}/query/${id}?type=pdf`, { responseType: 'blob' })
        .then((r) => {
          download(r.data, id + ".pdf");
        })
        .catch((e) => console.log(e));
    },

    downloadXhtml(document) {
      const { pathPrefix, id } = this.findUrlParts(document);
      
      axios.get(`${API_URL}/${pathPrefix}/query/${id}?type=xhtml`, { responseType: 'blob' })
        .then((r) => {
          download(r.data, id + ".html");
        })
        .catch((e) => console.log(e));
    },
    
  },
});
</script>

<style>
button {
  margin-right: 20px;
}
</style>
