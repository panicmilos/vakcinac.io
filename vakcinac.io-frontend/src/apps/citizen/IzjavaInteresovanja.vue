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
    "drzavljanstvo": {
      type: 'string',
      oneOf: [
        {
          const: "0",
          title: "Drzavljanin RS"
        },
        {
          const: "1",
          title: "Strani Drzavljanjin sa boravkom u RS"
        },
        {
          const: "2",
          title: "Strani Drzavljanjin bez boravkom u RS"
        },
      ]
    },
    "podnosilac": {
      type: 'string'
    },
    "broj-mobilnog-telefona": {
      type: 'string'
    },
    "broj-fiksnog-telefona": {
      type: 'string'
    },
    "opstina": {
      type: 'string'
    },
    "dobrovoljan-davalac-krvi": {
      type: 'boolean'
    },
    "zeljene-vakcine": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "proizvodjac": {
            "type": "string",
            oneOf: [
              {
                const: "0",
                title: "Pfizer-BioNTech"
              },
              {
                const: "1",
                title: "Sputnik V (Gamaleya истраживачки центар)"
              },
              {
                const: "2",
                title: "Sinopharm"
              },
              {
                const: "3",
                title: "AstraZeneca"
              },
              {
                const: "4",
                title: "Moderna"
              },
              {
                const: "5",
                title: "Било која"
              },
            ]
          }
        }
      }
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
        podnosilac: getGradjaninId()
      },
      schema,
    };
  },
  methods: {
    onSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "izjava-interesovanja-za-vakcinisanje" });
      const obj = builder.buildObject({
        ...data,
        "zeljene-vakcine": {
          proizvodjac: data["zeljene-vakcine"].map(zv => zv.proizvodjac)
        }
      });
      console.log(obj);
      axios.post(
          `${API_URL}/izjave`,
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
