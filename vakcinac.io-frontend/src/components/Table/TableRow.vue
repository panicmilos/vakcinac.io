<template>
  <tr>
    <td class="" v-for="(value, index) in values" :key="index">
      <component :is="tableCellComponent(value)" :props="value" />
    </td>
    <td v-if="renderActions" class="other-left">
      <slot />
    </td>
  </tr>
</template>

<script>
import StyledTextCellVue from './TableCells/StyledTextCell.vue';
import TextCellVue from './TableCells/TextCell.vue'

const cellTypeComponentRegistry = {
  'styledText': StyledTextCellVue
};

export default {
  components: {

  },
  props: {
    values: {
      type: Array
    },
    renderActions: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    tableCellComponent(value) {
      if(typeof value === 'string' || value instanceof String) {
        return TextCellVue;
      } else {
        return cellTypeComponentRegistry[value.type];
      }
    }
  }
}
</script>

<style>
td {
  white-space: normal !important;
  overflow-wrap: break-word;
}
.other-left {
  text-align: right
}
</style>