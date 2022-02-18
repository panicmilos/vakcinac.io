<template>
  <div>
    <Form
      :data="data"
      :schema="schema"
      @submit="onSubmit"
    />
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
    "ime": {
      type: "string"
    },
    "prezime": {
      type: "string"
    },
    "korisnicko-ime": {
      type: "string"
    },
    "lozinka": {
      type: "string"
    },
    "datum-rodjenja": {
      type: "string",
      format: "date",
    },
    "pol": {
      type: "string",
      oneOf: [
        {
          const: "0",
          title: "Male"
        },
        {
          const: "1",
          title: "Female"
        },
      ]
    },
    "email": {
      type: "string"
    },
    "jmbg": {
      type: "string"
    },
  }
};

export default defineComponent({
  name: "App",
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
      schema,
    };
  },
  methods: {
    onSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "sluzbenik" });
      console.log(builder.buildObject(data));
      axios.post(
          `${API_URL}/zaposleni/sluzbenik`,
          builder.buildObject(data)
        )
        .then((r) => {
          console.log(r)
        })
        .catch((e) => console.log(e));
    }
  },
});
</script>

<style>
</style>
