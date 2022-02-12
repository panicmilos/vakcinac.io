<template>
  <div data-app>
    <img class="logo" alt="" src="./assets/logo.png" />
    <json-forms
      v-bind:data="data"
      v-bind:renderers="renderers"
      v-bind:schema="schema"
      v-bind:uischema="uischema"
      @change="onChange"
    />
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import { JsonForms } from "@jsonforms/vue2";
import { vuetifyRenderers } from "@jsonforms/vue2-vuetify";

const renderers = [
  ...vuetifyRenderers
  // here you can add custom renderers
];

const schema = {
  properties: {
    name: {
      type: "string",
      minLength: 1,
      description: "The task's name"
    },
    description: {
      title: "Long Description",
      type: "string"
    },
    done: {
      type: "boolean"
    },
    dueDate: {
      type: "string",
      format: "date",
      description: "The task's due date"
    },
    rating: {
      type: "integer",
      maximum: 5
    },
    recurrence: {
      type: "string",
      enum: ["Never", "Daily", "Weekly", "Monthly"]
    },
    recurrenceInterval: {
      type: "integer",
      description: "Amount of days until recurrence"
    }
  }
};

const uischema = {
  type: "HorizontalLayout",
  elements: [
    {
      type: "VerticalLayout",
      elements: [
        {
          type: "Control",
          scope: "#/properties/name"
        },
        {
          type: "Control",
          scope: "#/properties/description",
          options: {
            multi: true
          }
        },
        {
          type: "Control",
          scope: "#/properties/done"
        }
      ]
    },
    {
      type: "VerticalLayout",
      elements: [
        {
          type: "Control",
          scope: "#/properties/dueDate"
        },
        {
          type: "Control",
          scope: "#/properties/rating",
        },
        {
          type: "Control",
          scope: "#/properties/recurrence"
        },
        {
          type: "Control",
          scope: "#/properties/recurrenceInterval"
        }
      ]
    }
  ]
};

export default defineComponent({
  name: "App",
  components: {
    JsonForms
  },
  data() {
    return {
      // freeze renderers for performance gains
      renderers: Object.freeze(renderers),
      data: {
        name: "Send email to Adrian",
        description: "Confirm if you have passed the subject\nHereby ...",
        done: true,
        recurrence: "Daily",
        rating: 3
      },
      schema,
      uischema
    };
  },
  methods: {
    onChange(event) {
      this.data = event.data;
    }
  },
});
</script>

<style>
.logo {
  flex: 0 1 auto;
}
</style>
