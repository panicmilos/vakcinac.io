<template>
  <div>
    <h2>Accept</h2>
    <Form :data="data" :schema="schemaAccept" @submit="onAcceptSubmit" />

    <DocumentsTable :documents="documents" selector="url" />

    <h2>Decline</h2>
    <Form :data="dataDecline" :schema="schemaDecline" @submit="onDeclineSubmit" />

  </div>
</template>

<script>
import x from "xml2js";
import { defineComponent } from "@vue/composition-api";
import Form from "../../components/Form.vue";
import DocumentsTable from "../../components/Documents/DocumentsTable.vue";
import axios from "axios";
import { API_URL } from "../../cfg";
import moment from 'moment'

const schemaAccept = {
  properties: {
    "gradjanin-id": {
      "type": "string"
    },
    "zahtev": {
      "type": "string"
    },
    "vrsta-identifikacije": {
      type: "integer",
      oneOf: [
        {
          const: 0,
          title: "Drzavljanin RS"
        },
        {
          const: 1,
          title: "Strani Drzavljanjin sa boravkom u RS"
        },
        {
          const: 2,
          title: "Strani Drzavljanjin bez boravkom u RS"
        },
      ]
    },
    "testovi": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "ime": {
            "type": "string"
          },
          "vrsta-uzorka": {
            "type": "string"
          },
          "proizvodjac": {
            "type": "string"
          },
          "datum-uzorka": {
            "type": "string",
            "format": "date"
          },
          "datum-izdavanja": {
            "type": "string",
            "format": "date"
          },
          "rezultat": {
            "type": "string"
          },
          "labaratorija": {
            "type": "string"
          }
        }
      }
    },
  }
};

const schemaDecline = {
  properties: {
    "zahtev": {
      "type": "string"
    },
    "razlog": {
      "type": "string"
    }
  }
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
      dataDecline: {},
      documents: {},
      schemaAccept,
      schemaDecline
    };
  },
  methods: {
    onAcceptSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "digitalni-sertifikat" });
      const dataCopy = {...data};
      dataCopy['broj-testova'] = data['testovi']?.length ?? 0;

      dataCopy.testovi = dataCopy?.testovi?.map((test, i) => ({ test: { ...test, 'datum-uzorka': moment(test['datum-uzorka']).format('dDD.MM.yyyy.'), 'datum-izdavanja': moment(test['datum-izdavanja']).format('DD.MM.yyyy.'), $: { 'broj': i + 1} } }) ) ?? [];

      console.log(dataCopy);
      console.log(builder.buildObject(dataCopy));
      axios.post(
          `${API_URL}/sertifikati/approve`,
          builder.buildObject(dataCopy)
        )
        .then((r) => {
          this.fetchZahtevi();
          console.log(r)
        })
        .catch((e) => console.log(e));
    },

    onDeclineSubmit(data) {
      const builder = new x.Builder({ headless: true, rootName: "decline-zahtev" });

      axios.post(
          `${API_URL}/sertifikati/decline`,
          builder.buildObject(data)
        )
        .then((r) => {
          this.fetchZahtevi();
          console.log(r)
        })
        .catch((e) => console.log(e));    },

    fetchZahtevi() {
      axios.get(`${API_URL}/zahtevi/query/not-processed`,)
        .then((r) => {
          const parser = new x.Parser();
          parser.parseString(r.data, (err, res) => {
            this.documents = res['documents']['document'];
          });
        })
        .catch((e) => console.log(e));
    }
  },

  mounted() {
    this.fetchZahtevi();
  }
});
</script>

<style scoped>
h2 {
  margin-top: 20px;
  margin-left: 20px;
}
</style>
