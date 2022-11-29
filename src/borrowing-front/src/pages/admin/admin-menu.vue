<template>
    <menuVue :menu="menu"/>
</template>

<script setup>
import {ref} from "vue";
import menuVue from '@/components/menu.vue';

const menu = ref([
        {
            label:"信息录入",
            icon:"Monitor",
            children:[
                {
                    label:"登录管理"
                },{
                    label:"类别录入"
                },{
                    label:"设备录入"
                }
            ]
        },
        {
            label:"设备状态",
            icon:"ElementPlus"
        },
        {
            label:"审批记录",
            icon:"Histogram",
            children:[
                {
                    label:"出库申请"
                }
            ]
        },
        {
            label:"审批日志",
            icon:"BellFilled",
            children:[
                {
                    label:"领用记录"
                }
            ]
        }
    ]);

</script>

<style scoped>



</style>