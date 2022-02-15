<template>
  <span>
  <i v-if="!!props.icon" :class="`feather icon-${props.icon} f-16`"></i>
  <span :class="`text-${props.color || 'secondary'} text-xs font-weight-bold`">{{props.label}}</span>
  </span>
</template>

<script>
export default {
  props: {
    props: {}
  }
}
</script>

<style>

</style>