<template>
  <button @click="incrementCounter">
    Vue counter: {{counter}}
  </button>
</template>

<script>
import useCounter from '../compositions/useCounter'
export default {
  setup() {
    return useCounter();
  }
}
</script>

<style>

</style>