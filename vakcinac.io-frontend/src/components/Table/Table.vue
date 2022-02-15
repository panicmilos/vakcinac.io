
<template>
  <div class="table-responsive p-0 scrollable hide-scroll table-wrapper-scroll-y my-custom-scrollbar">
    <table :style="`min-width: ${minWidth}px`" class="table align-items-center mb-0 text-center fixed_header">
      <slot />
    </table>
  </div>
</template>

<script>
export default {
  props: {
    minWidth: {
      type: Number,
      default: 875
    }
  }
}
</script>


<style>
.table-responsive {
  overflow-x: auto;
}

.my-custom-scrollbar {
  position: relative;
  max-height: 400px;
  overflow: auto;
}
.table-wrapper-scroll-y {
  display: block; 
}

.fixed_header{
    /* width: 400px; */
    table-layout: fixed;
    border-collapse: collapse;
}

.fixed_header thead{
  position: sticky;
  top: -1px;
  background-color: #fff;
}

/* width */
::-webkit-scrollbar {
  width: 5px;
}

/* Track */
::-webkit-scrollbar-track {
  background: #f1f1f1; 
}
 
/* Handle */
::-webkit-scrollbar-thumb {
  background: #404E67; 
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #01a9acc7; 
}
</style>