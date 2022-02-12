<template>
  <json-forms
    v-bind:data="data"
    v-bind:renderers="renderers"
    v-bind:schema="schema"
    v-bind:uischema="uischema"
    @change="onChange"
  />
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
    };
  },
  methods: {
    onChange(event) {
      this.$emit('submit', event);
    }
  },
});
</script>

<style></style>
