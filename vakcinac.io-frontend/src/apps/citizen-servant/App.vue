<template>
  <div data-app>
    <img class="logo" alt="" src="@/assets/logo.png" />
    <Form
      :data="data"
      :schema="schema"
      :uischema="uischema"
      @submit="onSubmit"
    />
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import Form from "../../components/Form.vue";

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
    Form
  },
  data() {
    return {
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
    onSubmit(event) {
      console.log(event);
    }
  },
});
</script>

<style>
.logo {
  flex: 0 1 auto;
}
</style>
