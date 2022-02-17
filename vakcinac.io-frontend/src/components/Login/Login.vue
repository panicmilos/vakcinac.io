<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-card class="elevation-12">
          <v-toolbar dark color="primary">
            <v-toolbar-title>Login form</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <Form
              :data="data"
              :schema="schema"
              :uischema="uischema"
              @submit="handleLogin"
            />
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import Form from "../Form.vue";
import x from "xml2js";
import axios from "axios";
const builder = new x.Builder({ headless: true, rootName: "authenticate" });

const schema = {
  properties: {
    username: {
      type: "string",
    },
    password: {
      type: "string",
    },
  },
  submit: true,
};

const uischema = {
  type: "VerticalLayout",
  elements: [
    {
      type: "Control",
      scope: "#/properties/username",
    },
    {
      type: "Control",
      scope: "#/properties/password",
      options: {
        format: "password",
      },
    },
  ],
};

export default {
  components: { Form },
  data: () => ({
    data: {
      username: "",
      password: "",
    },
    schema,
    uischema,
  }),
  methods: {
    handleLogin(data) {
      axios
        .post(
          "http://localhost:8880/authentication",
          builder.buildObject({
            "korisnicko-ime": data.username,
            lozinka: data.password,
          })
        )
        .then((r) => {
          x.parseString(r.data, { mergeAttrs: true, explicitArray: false }, (err, result) => {
            if (err) {
              throw err;
            }
            console.log(result);
            localStorage.setItem('jwt', result[Object.keys(result)[0]].jwt)
          });
        })
        .catch((e) => console.log(e));
    },
  },
};
</script>

<style>
</style>