<template>
  <div>
  </div>
</template>

<script>
import download from "downloadjs";
import { defineComponent } from "@vue/composition-api";
import axios from "axios";
import { API_URL } from "../../cfg";

export default defineComponent({
  components: {
  },

  data() {
    return {
      data: {}
    };
  },
  
  methods: {
    fetchDocument() {
      const { path1, path2, path3 } = this.$route.params;
      const type = this.$route.query.type;

      const documentPaths = {
          'digitalni-sertifikat': 'sertifikati',
          'izjava': 'izjave',
          'saglasnost': 'saglasnosti',
          'zahtev': 'zahtevi',
          'potvrda': 'potvrde',
          'izvestaj': 'izvestaji'
        };

      axios.get(`${API_URL}/${documentPaths[path1]}/query/${path2}${path3 ? '/' + path3 : ''}?type=${type}`, { responseType: 'blob'})
          .then((r) => {
            download(r.data, path2 + (path3 ? '/' + path3 : '') + `.${type}`);
          })
          .catch((e) => console.log(e));
    
    }
  },

  mounted() {
    this.fetchDocument();
  }
});
</script>

<style>
button {
  margin-right: 20px;
}

</style>
