<template>
  <thead class="text-primary">
    <th class="text-uppercase text-xxs font-weight-bolder opacity-7 th-special" v-for="(columnName, i) in columnNames" :key="i">{{ columnName }}</th>
  </thead>
</template>

<script>
export default {
    props: {
    columnNames: Array
}
}
</script>

<style>
  th {
    text-align: center;
  }
</style>