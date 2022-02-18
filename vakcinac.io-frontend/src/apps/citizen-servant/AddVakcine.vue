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

const schema = {
  properties: {
    proizvodjac: {
      type: "string",
      oneOf: [
        {
          const: "0",
          title: "Pfizer-BioNTech",
        },
        {
          const: "1",
          title: "Sputnik V (Gamaleya истраживачки центар)",
        },
        {
          const: "2",
          title: "Sinopharm",
        },
        {
          const: "3",
          title: "AstraZeneca",
        },
        {
          const: "4",
          title: "Moderna",
        },
        {
          const: "5",
          title: "Било која",
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
        })
        .catch((e) => console.log(e));
    },
  },
});
</script>

<style>
</style>
