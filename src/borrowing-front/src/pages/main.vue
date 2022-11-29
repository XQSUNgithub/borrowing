<template>
    <div id="main">
        <div class="left"><router-view name="menu"/></div>
        <div class="right">
            <headerVue/>
            <router-view name="view"/>
        </div>
    </div>
</template>

<script setup>
import headerVue from '@/components/header.vue';

</script>

<style scoped>

#main{
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    position: relative;
}

#main>.left{
    height: 100%;
    background-color: #515A6E;
}
#main>.right{
    position: relative;
    background-color: #F5F7F9;
    height: 100%;
    display: flex;
    flex: 1;
    width:calc(100% - 200px);
    flex-direction: column;
    justify-content: flex-start;
    align-items: stretch;
}
</style>