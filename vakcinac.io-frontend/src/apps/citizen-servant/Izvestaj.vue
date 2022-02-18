<template>
  <div>
    <Form :data="data" :schema="schema" @submit="onSubmit" />

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
    od: {
      type: "string",
      format: "date",
    },
    do: {
      type: "string",
      format: "date",
    },
  },
};

export default defineComponent({
  name: "App",
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
      axios
        .get(`${API_URL}/izvestaji`, {
          params: {
            startDate: data.od,
            endDate: data.do,
          }
        })
        .then((r) => {
          console.log(r);
          this.fetchIzvestaji();
        })
        .catch(errorHandle);
    },

    fetchIzvestaji() {
      axios.get(`${API_URL}/izvestaji/query`,)
        .then((r) => {
          const parser = new x.Parser();
          parser.parseString(r.data, (err, res) => {
            this.documents = res['documents']['document'];
          });
        })
        .catch(errorHandle);
    }
  },

  mounted() {
    this.fetchIzvestaji();
  }
});
</script>

<style>
</style>
