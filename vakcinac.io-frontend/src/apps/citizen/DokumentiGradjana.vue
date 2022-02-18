<template>
  <div>
    <Form
      :data="data"
      :schema="schema"
      @submit="onSubmit"
    />

    <DocumentsTable :documents="documents" selector="link" />

  </div>
</template>

<script>
import x from "xml2js";
import { defineComponent } from "@vue/composition-api";
import Form from "../../components/Form.vue";
import DocumentsTable from "../../components/Documents/DocumentsTable.vue";
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
    DocumentsTable
  },

  data() {
    return {
      data: {},
      documents: {},
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
          });
        })
        .catch((e) => console.log(e));
    }
  }
});
</script>

<style>
button {
  margin-right: 20px;
}

</style>
