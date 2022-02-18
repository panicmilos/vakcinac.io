<template>
  <div>
    <Form :data="data" :schema="schema" @submit="onSubmit" />

    <Table v-if="vakcine">
      <TableHead :columnNames="tableHead"></TableHead>
      <TableBody>
        <TableRow
          v-for="(vakcina, i) in vakcine" 
          :key="i" 
          :values="formTableRow(vakcina)"
        >
        </TableRow>
      </TableBody>
    </Table>
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
import { proizvodjaci } from "./AddVakcine.vue"

const schema = {
  properties: {
    vakcina: {
      type: "string",
      oneOf: [
        {
          const: "",
          title: ""
        }
      ],
    },
    kolicina: {
      type: "string",
    },
  },
};

export default defineComponent({
  name: "App",
  components: {
    Form,
    Table,
    TableHead,
    TableBody,
    TableRow,
  },
  data() {
    return {
      data: {},
      schema,
      tableHead: ["Proizvodjac", "Serija", "Kolicina"],
      vakcine: [],
      stocks: {}
    };
  },
  mounted() {
    this.fetchVakcine();
    this.fetchStocks();
  },
  methods: {
    fetchVakcine() {
      axios.get(`${API_URL}/vakcine/all`)
      .then((r) => {
        x.parseString(r.data, { mergeAttrs: true, explicitArray: false }, (err, result) => {
          if (err) {
            throw err;
          }
          this.vakcine = Array.isArray(result.vakcine.vakcina) ? result.vakcine.vakcina : [result.vakcine.vakcina];
          this.schema.properties.vakcina.oneOf = this.vakcine.map(v => ({
            const: v['ns2:serija'],
            title: v['ns2:serija']
          }))
        });
      })
      .catch((e) => console.log(e));
    },
    fetchStocks() {
      axios.get(`${API_URL}/vakcine/stock`)
      .then((r) => {
        x.parseString(r.data, { mergeAttrs: true, explicitArray: false }, (err, result) => {
          if (err) {
            throw err;
          }
          const stanja = Array.isArray(result.stanje_vakcina.stanje_vakcine) ? result.stanje_vakcina.stanje_vakcine : [result.stanje_vakcina.stanje_vakcine];
          stanja.forEach(s => {
            this.stocks[s.vakcina] = s.dostupno;
          });
          this.fetchVakcine();
        });
      })
      .catch((e) => console.log(e));
    },
    formTableRow(vakcina) {
      return [proizvodjaci[vakcina['ns2:proizvodjac']], vakcina['ns2:serija'], this.stocks[vakcina['ns2:serija']] ?? "0"];
    },
    onSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "stock" });
      const obj = builder.buildObject({
        amount: data.kolicina
      });
      axios
        .put(`${API_URL}/vakcine/${data.vakcina}/stock`, obj)
        .then(() => {
          this.fetchStocks();
        })
        .catch((e) => console.log(e));
    },
  },
});
</script>

<style>
</style>
