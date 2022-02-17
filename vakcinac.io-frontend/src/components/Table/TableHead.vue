<template>
  <thead>
    <th class="" v-for="(columnName, i) in columnNames" :key="i">{{ columnName }}</th>
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