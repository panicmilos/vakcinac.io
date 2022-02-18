<template>
  <div>

    <Table>
      <TableHead :columnNames="tableHead"></TableHead>
      <TableBody>
        <TableRow
          v-for="(link, i) in links['referencing'][0]" 
          :key="i" 
          :values="[link[0], 'Referencira']"
        />

        <TableRow
          v-for="(link, i) in links['referenced-by'][0]" 
          :key="i" 
          :values="[link[0], 'Referenciran']"
        />
       
      </TableBody>
    </Table>
   
    <div id="document" />

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
      tableHead: ['Link', 'Tip']
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
    }
    
  },

  mounted() {
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
          console.log(r);
          document.getElementById("document").innerHTML = r.data;
        })
        .catch((e) => console.log(e));

    axios.get(`${API_URL}/${documentPaths[path1]}/query/${path2}${path3 ? '/' + path3 : ''}/links`,)
        .then((r) => {
          console.log(r);
          const parser = new x.Parser();
          parser.parseString(r.data, (err, res) => {
            console.log(res);
            this.links = res['document-links'];
            console.log(this.links);

          })
        })
        .catch((e) => console.log(e));
    
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
</style>
