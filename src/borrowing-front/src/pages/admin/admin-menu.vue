<template>
    <menuVue :menu="menu" title="管理系统"/>
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
                    label:"矿物管理"
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
            label:"审批管理",
            icon:"Histogram",
            children:[
                {
                    label:"审批记录"
                },
                {
                    label:"出库申请"
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