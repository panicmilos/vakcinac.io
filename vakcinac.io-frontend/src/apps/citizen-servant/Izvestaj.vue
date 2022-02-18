<template>
  <div>
    <Form :data="data" :schema="schema" @submit="onSubmit" />
  </div>
</template>

<script>
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
      axios
        .get(`${API_URL}/izvestaji`, {
          params: {
            startDate: data.od,
            endDate: data.do,
          }
        })
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
