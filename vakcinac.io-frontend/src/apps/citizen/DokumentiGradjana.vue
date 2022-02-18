<template>
  <div>
    <DocumentsTable :documents="documents" selector="link" />
  </div>
</template>

<script>
import x from "xml2js";
import { defineComponent } from "@vue/composition-api";
import DocumentsTable from "../../components/Documents/DocumentsTable.vue";
import axios from "axios";
import { API_URL } from "../../cfg";

import { errorHandle } from '../../utils/errorHandle';
import { getGradjaninId } from "../../utils/auth";

export default defineComponent({
  components: {
    DocumentsTable
  },

  data() {
    return {
      documents: {},
    };
  },
  mounted() {
    axios.get(`${API_URL}/gradjani/${getGradjaninId()}/documents/all`,)
      .then((r) => {
        const parser = new x.Parser();
        parser.parseString(r.data, (err, res) => {
          this.documents = res['citizen-documents-result']['citizen-document'];
        });
      })
      .catch(errorHandle);
  }
});
</script>

<style>
button {
  margin-right: 20px;
}

</style>
