<template>
  <div>
    <Table v-if="documents !== {}">
      <TableHead :columnNames="tableHead"></TableHead>
      <TableBody>
        <TableRow
          v-for="(document, i) in documents" 
          :key="i" 
          :values="formTableRow(document)"
        >
        <div>
          <button @click="showDocument(document)">Prikaz</button>
          <button @click="downloadPdf(document)">PDF</button>
          <button @click="downloadXhtml(document)">HTML</button>
          <button @click="downloadNTRIPLES(document)">NTRIPES</button>
          <button @click="downloadJSON(document)">JSON</button>

        </div>
        </TableRow>
      </TableBody>
    </Table>
  </div>
</template>

<script>
import download from "downloadjs";
import { defineComponent } from "@vue/composition-api";
import Table from "../../components/Table/Table.vue";
import TableBody from "../../components/Table/TableBody.vue";
import TableHead from "../../components/Table/TableHead.vue";
import TableRow from "../../components/Table/TableRow.vue";
import axios from 'axios'
import { API_URL } from "../../cfg";

export default defineComponent({
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow
  },

  props: ['documents', 'selector'],

  data() {
    return {
      tableHead: ['Izdat', 'Id', 'Tip', 'Link', 'Akcija'],
    };
  },
  
  methods: {

    formTableRow(document) {
      return [document['created-at'][0].split("^^")[0], document['id'][0], document['type'][0], document[this.selector][0]];
    },

    findUrlParts(document) {
      const documentParts = this.formTableRow(document);
      const documentPrefixes = {
        'Digitalni Sertifikat': 'sertifikati',
        'Potvrda': 'potvrde',
        'Saglasnost': 'saglasnosti',
        'Izjava': 'izjave',
        'Zahtev': 'zahtevi',
        'Izvestaj': 'izvestaji'
      };

      const pathPrefix = documentPrefixes[documentParts[2]];
      const id = documentParts[1];

      return { pathPrefix, id };
    },

    showDocument(document) {
      const { pathPrefix, id } = this.findUrlParts(document);
      const documentPrefixes = {
        'sertifikati': 'digitalni-sertifikat',
        'izjave': 'izjava',
        'saglasnosti': 'saglasnost',
        'zahtevi': 'zahtev',
        'potvrde': 'potvrda',
        'izvestaji': 'izvestaj'
      };

      this.$router.push(`/dokumenti/${documentPrefixes[pathPrefix]}/${id}`);
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

    downloadNTRIPLES(document) {
      this.downloadRDF(document, "NTRIPLES");
    },

    downloadJSON(document) {
      this.downloadRDF(document, "JSON");
    },

    downloadRDF(document, type) {
      const { pathPrefix, id } = this.findUrlParts(document);
      axios.get(`${API_URL}/${pathPrefix}/query/${id}/rdf?type=${type}`)
        .then((r) => {
          if (type === 'JSON') {
            download(JSON.stringify(r.data, null, 4), id + `.${type.toLowerCase()}`, 'application/json');
          } else {
            download(r.data, id + `.${type.toLowerCase()}`);
          }
        })
        .catch((e) => console.log(e));
    }
    
  },
});
</script>

<style>
button {
  margin-right: 20px;
}

</style>
