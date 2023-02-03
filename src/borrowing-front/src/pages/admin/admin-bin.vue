<template>
    <dataViewVue v-show="show"
        :title="title"
        :data="tabledata"
        :action="action"
        :bar="bar"
        :label="label"
    />
    <!-- <dataEditVue
        v-model:show="editShow"
        v-show="show"
        :form="from"
        v-model:data="edit"
        :rules="rules"
        :action="exitBar"
        title="用户编辑"
    /> -->
    <!-- <dataEditVue
        :show="true"
        v-show="show"
        :form="addform"
        v-model:data="addBuf"
        :rules="rules"
        :action="addBar"
        title="用户创建"
        :adder="true"
        :close="false"
    /> -->
</template>

<script setup>
import dataViewVue from '@/components/data-view.vue';
import dataEditVue from '@/components/data-edit.vue';
import { useStore } from 'vuex';
import {ref,computed,onMounted} from 'vue';
import { ID,error,success,sure } from '@/api/util';
import {query,insert,update,remove} from '@/api/net';

const tableName = "Collection module:collected";
const tableNameDel = "Collection module:realDelete";
const key = "cid";
const act = "1";

const page = "回收站";

const store = useStore();
const show = computed(()=>store.state.menuSelected===page);

const tabledata = ref([]);

const title = ref(page);

const editShow = ref(false);

var row = null;

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

const codes = [
    {
        type:"input",
        prop:"provider",
        label:"标本提供者",
        disabled:false
    },{
        type:"input",
        prop:"gatherer",
        label:"采集者",
        disabled:false
    },{
        type:"input",
        prop:"donor",
        label:"捐赠者",
        disabled:false
    },{
        type:"input",
        prop:"recorder",
        label:"录入者",
        disabled:false
    },{
        type:"input",
        prop:"providetime",
        label:"提供时间",
        disabled:false
    },{
        type:"input",
        prop:"num",
        label:"标本数量",
        disabled:false
    },{
        type:"input",
        prop:"source",
        label:"产地",
        disabled:false
    },{
        type:"input",
        prop:"purpose",
        label:"用途",
        disabled:false
    },{
        type:"input",
        prop:"location",
        label:"库存位置号",
        disabled:false
    },{
        type:"input",
        prop:"access",
        label:"获取途径",
        disabled:false
    }
];

const from = ref([{    
        type:"input",
        prop:"cid",
        label:"编号",
        disabled:false
    },...codes]);

const addform = ref(codes);

const edit = ref({
    uuid:"",
    realname:"",
    password:"",
    state:"0"
});

const addBuf = ref({
    destroyed:"no"
});

const exitBar = ref([
    {
        label:"修改",
        call:(v)=>{
            if(v){
                update(tableName,key,edit.value,act).then(v=>{
                    const {data} = v;
                    if(data&&data.data){
                        console.log(data.data);
                        Object.assign(row,data.data);
                        success("修改成功");
                    }else{
                        error("修改失败");
                    }
                }).catch(err=>{
                    console.log(err);
                });
            }
        }
    }
]);

const addBar = ref([
    {
        label:"创建",
        call:(v)=>{
            if(v){
                sure(`是否创建记录?`)(()=>{
                    insert(tableName,key,addBuf.value,act).then(v=>{
                        const {data} = v;
                        if(data&&data.data){
                            const pack = {
                                ...addBuf.value
                            }
                            pack[key] = data.data||"";
                            tabledata.value.push(pack);
                            success("创建成功");
                        }else{
                            error("创建成功");
                        }
                    }).catch(err=>{
                        
                    });
                });
            }
        }
    }
]);

const action = ref([
    {
        label:"恢复",
        type:"success",
        call:v=>{
            const row = v.row;
            const {cid} = row;
            row.destroyed = "no";
            sure(`是否恢复记录[${row.cid}]?`)(()=>{
                update(tableName,key,row,act).then(v=>{
                    const {data} = v;
                    if(data.data){
                        tabledata.value = tabledata.value.filter(v=>v.cid!=cid);
                        success("恢复成功");
                    }else{
                        error("恢复失败");
                    }
                }).catch(err=>{
                    console.log(err);
                });
            });
        }
    },{
        label:"销毁",
        type:"danger",
        call:v=>{
            const row = v.row;
            const {cid} = row;
            sure(`是否销毁记录[${row.cid}]?-此状态不可逆`)(()=>{
                remove(tableNameDel,key,row,act).then(v=>{
                    const {data} = v;
                    if(data.data){
                        tabledata.value = tabledata.value.filter(v=>v.cid!=cid);
                        success("销毁成功");
                    }else{
                        error("销毁失败");
                    }
                }).catch(err=>{
                    console.log(err);
                });
            });
        }
    }
]);


const rules = ref({});

function setRules(){
    rules.value = {
        cid:{ required: true, message: `请输入矿物ID`, trigger: 'blur' }
    };
    codes.forEach(v=>{
        const {prop,label,disabled,need} = v;
        if(disabled||!need)return;
        rules.value[prop] = [
            { required: true, message: `请输入${label}`, trigger: 'blur' }
        ];
    });
}

setRules();

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