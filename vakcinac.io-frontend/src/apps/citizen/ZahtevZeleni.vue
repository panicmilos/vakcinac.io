<template>
  <div>
    <Form
      :data="data"
      :schema="schema"
      @submit="onSubmit"
    />
     <vue-editor v-model="content" :editorToolbar="customToolbar"></vue-editor>
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
import { VueEditor } from 'vue2-quill-editor'

import { errorHandle } from '../../utils/errorHandle';
import { getGradjaninId } from "../../utils/auth";

const schema = {
  properties: {
    "podnosilac": {
      type: "string"
    },
    "pasos": {
      type: "string"
    },
    "razlog": {
      type: "string"
    },
    "mesto": {
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
    TableRow,
    VueEditor
  },
  data() {
    return {
      data: {
        podnosilac: getGradjaninId()
      },
      schema,
      content: null,
      customToolbar: [
            ['bold', 'italic', 'underline'],
          ]
    };
  },
  methods: {
    onSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "zahtev-za-izdavanje-zelenog-sertifikata" });
      data.razlog = this.content;
      const obj = builder.buildObject(data);
      console.log(obj);
      axios.post(
          `${API_URL}/zahtevi`,
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
