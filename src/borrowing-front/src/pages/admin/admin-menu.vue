<template>
    <menuVue :menu="menu" title="管理系统"/>
</template>

<script setup>
import {ref,inject} from "vue";
import menuVue from '@/components/menu.vue';

const menu = ref([
        {
            label:"信息管理",
            icon:"Monitor",
            children:[
                {
                    label:"账户管理"
                },{
                    label:"矿物管理"
                },{
                    label:"归还记录"
                },{
                    label:"回收站"
                }
            ]
        },
        {
            label:"矿物录入",
            icon:"ElementPlus"
        },
        {
            label:"审批模块",
            icon:"Histogram",
            children:[
                {
                    label:"借出审批"
                }
            ]
        },
        // {
        //     label:"审批日志",
        //     icon:"BellFilled",
        //     children:[
        //         {
        //             label:"领用记录"
        //         }
        //     ]
        // }
    ]);

</script>

<style scoped>



</style>