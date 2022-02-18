<template>
  <div>

    <Table>
      <TableHead :columnNames="tableHead"></TableHead>
      <TableBody>
        <TableRow
          v-for="(link, i) in links['referencing'][0]" 
          :key="i" 
          :values="formTableRow(link[0], 'Referencira')"
        >
          <button @click="showDocument(link[0])">Prikaz</button>
        </TableRow>

        <TableRow
          v-for="(link, i) in links['referenced-by'][0]" 
          :key="i" 
          :values="formTableRow(link[0], 'Referenciran')"
        >
          <button @click="showDocument(link[0])">Prikaz</button>
        </TableRow>
       
      </TableBody>
    </Table>
   
    <iframe id="document" src="about:blank"></iframe>

  </div>
</template>

<script>
import x from "xml2js";
import { defineComponent } from "@vue/composition-api";
import Form from "../../components/Form.vue";
import Table from "../../components/Table/Table.vue";
import TableBody from "../../components/Table/TableBody.vue";
import TableHead from "../../components/Table/TableHead.vue";
import TableRow from "../../components/Table/TableRow.vue";
import axios from "axios";
import { API_URL } from "../../cfg";

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
      links: {},
      tableHead: ['Izdat', 'Id', 'Tip Dokumenta', 'Tip Reference', 'Link', 'Akcija'],
    };
  },
  
  methods: {

    formTableRow(document, reference) {
      return [document['created-at'][0].split("^^")[0], document['id'][0], document['type'][0], reference, document['url'][0]];
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

    showDocument(document) {
      const { pathPrefix, id } = this.findUrlParts(document);
      const documentPrefixes = {
        'sertifikati': 'digitalni-sertifikat',
        'izjave': 'izjava',
        'saglasnosti': 'saglasnost',
        'zahtevi': 'zahtev',
        'potvrde': 'potvrda'
      };

      this.$router.push(`/dokumenti/${documentPrefixes[pathPrefix]}/${id}`);
      this.fetchDocument();
    },

    fetchDocument() {
      const { path1, path2, path3 } = this.$route.params;

      const documentPaths = {
          'digitalni-sertifikat': 'sertifikati',
          'izjava': 'izjave',
          'saglasnost': 'saglasnosti',
          'zahtev': 'zahtevi',
          'potvrda': 'potvrde'
        };

      axios.get(`${API_URL}/${documentPaths[path1]}/query/${path2}${path3 ? '/' + path3 : ''}?type=xhtml`,)
          .then((r) => {
            const doc = document.getElementById('document').contentWindow.document;
            doc.open();
            doc.write(r.data);
            doc.close();

          })
          .catch((e) => console.log(e));

      axios.get(`${API_URL}/${documentPaths[path1]}/query/${path2}${path3 ? '/' + path3 : ''}/links`,)
          .then((r) => {
            const parser = new x.Parser();
            parser.parseString(r.data, (err, res) => {
              this.links = res['document-links'];

            })
          })
          .catch((e) => console.log(e));
    
    }

    
  },

  mounted() {
    this.fetchDocument();
  }
});
</script>

<style>
button {
  margin-right: 20px;
}

table {
  margin-bottom: 50px;
}

iframe {
    margin: auto;
  width: 100%;
  height: 2000px;
}
</style>
