<template>
  <div>
    <Form :data="data" :schema="schema" @submit="onSubmit" />
  </div>
</template>

<script>
import x from "xml2js";
import { defineComponent } from "@vue/composition-api";
import Form from "../../components/Form.vue";
import axios from "axios";
import { API_URL } from "../../cfg";

const schema = {
  properties: {
    jmbg: {
      type: "string",
    },
  },
};

export default defineComponent({
  name: "App",
  components: {
    Form,
  },
  data() {
    return {
      data: {},
      schema,
    };
  },
  methods: {
    onSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "vaccinate-request" });
      const obj = builder.buildObject(data.jmbg);
      console.log(obj, data);
      axios
        .post(`${API_URL}/potvrde`, obj)
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
