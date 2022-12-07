<template>
    <dataViewVue v-show="show"
        :title="title"
        :data="tabledata"
        :action="action"
        :bar="bar"
        :label="label"
    />
</template>

<script setup>
import dataViewVue from '@/components/data-view.vue';
import { useStore } from 'vuex';
import {ref,computed,onMounted} from 'vue';
import { ID,error,success } from '@/api/util';
import {query,insert,remove} from '@/api/net';

const tableName = "Collection";
const key = "cid";
const act = "1";

const page = "回收站";

const store = useStore();
const show = computed(()=>store.state.menuSelected===page);

const tabledata = ref([]);

const title = ref(page);

const editShow = ref(false);

const bar = ref([
    {
        label:"刷新",
        call:()=>{refresh();}
    },
    {
        label:"下载",
        call:()=>{console.log(2345)}
    }
]);

const label = ref([
    {
        prop:"cid",
        label:"编号",
        width:"auto",
        fixed:true,
        fold:false
    },{
        prop:"provider",
        label:"标本提供者",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"gatherer",
        label:"采集者",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"donor",
        label:"捐赠者",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"recorder",
        label:"录入者",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"providetime",
        label:"提供时间",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"num",
        label:"标本数量",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"source",
        label:"产地",
        width:"200",
        fixed:false,
        fold:false
    },{
        prop:"purpose",
        label:"用途",
        width:"200",
        fixed:false,
        fold:false
    },{
        prop:"location",
        label:"库存位置号",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"access",
        label:"获取途径",
        width:"auto",
        fixed:false,
        fold:false
    }
]);


const edit = ref({
    uuid:"",
    realname:"",
    password:"",
    state:"0"
});

const addBuf = ref({
    realname:"曾柏滔",
    password:"228333",
    state:"0"
});


const action = ref([
    // {
    //     label:"编辑",
    //     call:v=>{
    //         Object.assign(edit.value,v.row);
    //         row = v.row;
    //         editShow.value = true;
    //     }
    // },
    {
        label:"还原",
        type:"success",
        call:v=>{
            const row = v.row;
            const {uuid} = row;
            window.confirm(`是否还原${row.uuid}?`,"提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
            }).then(()=>{
                remove(tableName,key,row,act).then(v=>{
                    const {data} = v;
                    if(data.data){
                        tabledata.value = tabledata.value.filter(v=>v.uuid!==uuid);
                        success("删除成功");
                    }else{
                        error("删除失败");
                    }
                }).catch(err=>{
                    console.log(err);
                });
            }).catch(()=>{

            });
        }
    }
]);


function refresh(){
    query(tableName,null,act).then(v=>{
        const {data:{data}} = v;
        tabledata.value.length&&success("刷新成功");
        tabledata.value = data;
    }).catch(err=>{
        console.log(err);
    });
}

function init(){
    refresh();
}


onMounted(()=>{
    init();
});


</script>