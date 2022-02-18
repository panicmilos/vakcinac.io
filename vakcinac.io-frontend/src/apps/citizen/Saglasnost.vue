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

import { errorHandle } from '../../utils/errorHandle';
import { getGradjaninId } from "../../utils/auth";

const schema = {
  properties: {
    "pacijent-jmbg": {
      type: "string"
    },
    "naziv-drzavljanstva": {
      type: "string"
    },
    "broj-pasosa": {
      type: "string"
    },
    "broj-mobilnog": {
      type: "string"
    },
    "broj-fiksnog": {
      type: "string"
    },
    "radni-status": {
      type: "string",
      oneOf: [
        {
          const: "0",
          title: "запослен"
        },
        {
          const: "1",
          title: "незапослен"
        },
        {
          const: "2",
          title: "пензионер"
        },
        {
          const: "3",
          title: "ученик"
        },
        {
          const: "4",
          title: "студент"
        },
        {
          const: "5",
          title: "дете"
        },
      ]
    },
    "zanimanje": {
      type: "string",
      oneOf: [
        {
          const: "0",
          title: "здравствена заштита"
        },
        {
          const: "1",
          title: "социјална заштита"
        },
        {
          const: "2",
          title: "просвета"
        },
        {
          const: "3",
          title: "МУП"
        },
        {
          const: "4",
          title: "Војска РС"
        },
        {
          const: "5",
          title: "друго"
        },
      ]
    },
    "socijalna-zastita": {
      type: "boolean"
    },
    "opstina-sedista": {
      type: "string"
    },
    "izjava": {
      type: "boolean"
    },
    "imunoloski-lek": {
      type: "string"
    },
    "mesto-stanovanje": {
      type: "string"
    },
    "opstina-stanovanje": {
      type: "string"
    },
    "adresa-stanovanje": {
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
      data: {
        'pacijent-jmbg': getGradjaninId()
      },
      schema,
    };
  },
  methods: {
    onSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "create-saglasnost-request" });
      const obj = builder.buildObject(data);
      console.log(obj);
      axios.post(
          `${API_URL}/saglasnosti`,
          obj
        )
        .then((r) => {
          console.log(r)
          alert("Uspešna akcija!");
        })
        .catch(errorHandle);
    }
  },
});
</script>

<style>
</style>
