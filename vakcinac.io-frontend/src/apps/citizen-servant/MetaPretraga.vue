<template>
  <div>
    <Form
      :data="data"
      :schema="schema"
      :uischema="uischema"
      @submit="onSubmit"
    />

    <documents-table :documents="documents" selector="id" />

  </div>
</template>

<script>
import x from "xml2js";
import { defineComponent } from "@vue/composition-api";
import Form from "../../components/Form.vue";
import axios from "axios";
import { API_URL } from "../../cfg";
import DocumentsTable from "../../components/Documents/DocumentsTable.vue";

const schema = {
  properties: {
    graph: {
      type: "string",
      enum: [
        "interesovanje",
        "saglasnost",
        "potvrda",
        "zahtevi",
        "sertifikat",
        "izvestaj",
      ]
    },
    expression: {
      type: "string",
    }
  },
};

const uischema = {
  "type": "VerticalLayout",
  "elements": [
    {
      "type": "Control",
      "scope": "#/properties/graph",
    },
    {
      "type": "Control",
      "scope": "#/properties/expression",
      "options": {
        "multi": true
      }
    },
  ]
};

export default defineComponent({
  name: "App",
  components: {
    Form,
    DocumentsTable,
  },
  data() {
    return {
      data: {},
      documents: {},
      schema,
      uischema,
    };
  },
  methods: {
    onSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "rdf-search-request" });
      x.parseString(data.expression, {trim: true}, (err, result) => { 
        const sendData = {...data}
        sendData.expression = result;
            axios.post(
                `${API_URL}/meta-search`,
                builder.buildObject(sendData)
              )
              .then((r) => {
                const parser = new x.Parser();
                parser.parseString(r.data, (err, res) => {
                  console.log(err)
                  console.log(res)
                  this.documents = res['documents']['document'];
                });
              })
              .catch((e) => console.log(e));  
        }
      );
    }
  },
});
</script>

<style>
</style>
