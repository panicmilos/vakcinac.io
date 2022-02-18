<template>
  <div>
    <Form :data="data" :schema="schema" @submit="onSubmit" />
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

import { errorHandle } from '../../utils/errorHandle';

export const proizvodjaci = [
  "Pfizer-BioNTech",
  "Sputnik V (Gamaleya истраживачки центар)",
  "Sinopharm",
  "AstraZeneca",
  "Moderna",
  "Било која",
];

const schema = {
  properties: {
    proizvodjac: {
      type: "string",
      oneOf: [
        {
          const: "0",
          title: proizvodjaci[0],
        },
        {
          const: "1",
          title: proizvodjaci[1],
        },
        {
          const: "2",
          title: proizvodjaci[2],
        },
        {
          const: "3",
          title: proizvodjaci[3],
        },
        {
          const: "4",
          title: proizvodjaci[4],
        },
        {
          const: "5",
          title: proizvodjaci[5],
        },
      ],
    },
    serija: {
      type: "string",
    },
    "period-cekanja": {
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
    };
  },
  methods: {
    onSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "vakcina" });
      const obj = builder.buildObject(data);
      console.log(obj);
      axios
        .post(`${API_URL}/vakcine`, obj)
        .then((r) => {
          console.log(r);
          alert("Uspešna akcija!");
        })
        .catch(errorHandle);
    },
  },
});
</script>

<style>
</style>
