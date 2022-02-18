<template>
  <div>
    <Form
      :data="data"
      :schema="schema"
      @submit="onSubmit"
    />

    <DocumentsTable :documents="documents" selector="url" />
  </div>
</template>

<script>
import x from "xml2js";
import { defineComponent } from "@vue/composition-api";
import Form from "../../components/Form.vue";
import DocumentsTable from "../../components/Documents/DocumentsTable.vue";
import axios from "axios";
import { API_URL } from "../../cfg";

import { errorHandle } from '../../utils/errorHandle';

const schema = {
  properties: {
    "query": {
      type: "string"
    }
  }
};

export default defineComponent({
  components: {
    Form,
    DocumentsTable,
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
      const { query } = data;

      axios.get(`${API_URL}/documents/search?query=${query}`,)
        .then((r) => {
          const parser = new x.Parser();
          parser.parseString(r.data, (err, res) => {
            this.documents = res['documents']['document'];
          });
        })
        .catch(errorHandle);
    }
  }
});
</script>

<style>
button {
  margin-right: 20px;
}

</style>
