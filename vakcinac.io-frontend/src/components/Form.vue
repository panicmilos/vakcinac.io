<template>
<div>
  <json-forms
    :data="data"
    :renderers="renderers"
    :schema="schema"
    :uischema="uischema"
    @change="onChange"
  />
  <v-card-actions>
    <v-spacer></v-spacer>
    <v-btn color="primary" @click.stop="onSubmit">Submit</v-btn>
  </v-card-actions>
</div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import { JsonForms } from "@jsonforms/vue2";
import { vuetifyRenderers } from "@jsonforms/vue2-vuetify";

const renderers = [
  ...vuetifyRenderers
];

export default defineComponent({
  name: "App",
  props: ['schema', 'uischema', 'data'],
  components: {
    JsonForms
  },
  data() {
    return {
      // freeze renderers for performance gains
      renderers: Object.freeze(renderers),
      localData: {}
    };
  },
  mounted() {
    this.localData = this.data
  },
  methods: {
    onChange(event) {
      this.$emit('change', event);
      this.localData = event.data;
    },
    onSubmit() {
      this.$emit('submit', this.localData);
    }
  },
});
</script>

<style></style>
