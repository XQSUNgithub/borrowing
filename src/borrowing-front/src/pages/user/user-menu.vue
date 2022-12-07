<template>
    <menuVue :menu="menu" title="录入系统"/>
</template>

<script setup>
import {ref} from "vue";
import menuVue from '@/components/menu.vue';

const menu = ref([
        {
            label:"信息查阅",
            icon:"Monitor",
            children:[
                {
                    label:"矿物查阅"
                },{
                    label:"借出查阅"
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
                }
            ]
        },
        {
            label:"设置",
            icon:"Setting"
        }
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