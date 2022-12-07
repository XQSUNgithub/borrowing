<template>
    <dataViewVue v-show="show"
        :title="title"
        :data="tabledata"
        :action="action"
        :bar="bar"
        :label="label"
    />
    <dataEditVue
        v-model:show="editShow"
        v-show="show"
        :form="from"
        v-model:data="edit"
        :rules="rules"
        :action="exitBar"
        title="用户编辑"
    />
    <dataEditVue
        :show="true"
        v-show="show"
        :form="addform"
        v-model:data="addBuf"
        :rules="rules"
        :action="addBar"
        title="用户创建"
        :adder="true"
        :close="false"
    />
</template>

<script setup>
import dataViewVue from '@/components/data-view.vue';
import dataEditVue from '@/components/data-edit.vue';
import { useStore } from 'vuex';
import {ref,computed,onMounted} from 'vue';
import { ID,error,success } from '@/api/util';
import {query,insert,update,remove} from '@/api/net';

const tableName = "Collection";
const key = "cid";
const act = "1";

const page = "矿物管理";

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
    // {    
    //     type:"input",
    //     prop:"cid",
    //     label:"编号",
    //     disabled:false,
    //     placeholder:"1234"
    // },
    {
        type:"input",
        prop:"provider",
        label:"标本提供者",
        disabled:false,
        placeholder:"1234"
    },{
        type:"input",
        prop:"gatherer",
        label:"采集者",
        disabled:false,
        placeholder:"1234"
    },{
        type:"input",
        prop:"donor",
        label:"捐赠者",
        disabled:false,
        placeholder:"1234"
    },{
        type:"input",
        prop:"recorder",
        label:"录入者",
        disabled:false,
        placeholder:"1234"
    },{
        type:"input",
        prop:"providetime",
        label:"提供时间",
        disabled:false,
        placeholder:"1234"
    },{
        type:"input",
        prop:"num",
        label:"标本数量",
        disabled:false,
        placeholder:"1234"
    },{
        type:"input",
        prop:"source",
        label:"产地",
        disabled:false,
        placeholder:"1234"
    },{
        type:"input",
        prop:"purpose",
        label:"用途",
        disabled:false,
        placeholder:"1234"
    },{
        type:"input",
        prop:"location",
        label:"库存位置号",
        disabled:false,
        placeholder:"1234"
    },{
        type:"input",
        prop:"access",
        label:"获取途径",
        disabled:false,
        placeholder:"1234"
    }
];

const from = ref([{    
        type:"input",
        prop:"cid",
        label:"编号",
        disabled:false,
        placeholder:"1234"
    },...codes]);

const addform = ref(codes);

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
                    console.log(err);
                });
            }
        }
    }
]);

const action = ref([
    {
        label:"编辑",
        call:v=>{
            Object.assign(edit.value,v.row);
            row = v.row;
            editShow.value = true;
        }
    },
    {
        label:"删除",
        type:"danger",
        call:v=>{
            const row = v.row;
            const {uuid} = row;
            window.confirm(`是否删除用户${row.uuid}?`,"提示", {
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


const rules = ref({
    uuid: [
        { required: true, message: '账户不为空', trigger: 'blur' },
    ],
    realname: [
        { required: true, message: '用户名称不为空', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '密码不为空', trigger: 'blur' },
    ],
    state: [
        { required: true, message: '身份码不为空', trigger: 'blur' },
    ],
});

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