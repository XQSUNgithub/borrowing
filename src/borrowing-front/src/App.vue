<template>
  <router-view />
</template>

<script setup>

</script>

<style>
html,body{
  overflow: hidden;
  width: 100%;
  height: 100%;
  margin: 0;
  box-sizing: border-box;
}

#app {
  width: 100%;
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.shadow{
  box-shadow: 3px 3px 10px rgb(230, 228, 228);
}

.scale:hover{
  transform: scale(1.1,1.1);
  color: #2D8CF0;
  transition-duration: 100ms;
}

.auto{
  margin-left: auto;
}
</style>
