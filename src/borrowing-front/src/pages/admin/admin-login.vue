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
        :form="form"
        v-model:data="edit"
        :rules="rules"
        :action="exitBar"
        title="用户编辑"
    />
    <dataEditVue
        :show="true"
        v-show="show"
        :form="form"
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

const tableName = "User";
const key = "uuid";
const act = "1";

const page = "登录管理";

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
        prop:"uuid",
        label:"账户",
        width:"auto",
        fixed:true,
        fold:false
    },{
        prop:"realname",
        label:"姓名",
        width:"200",
        fixed:false,
        fold:false
    },{
        prop:"password",
        label:"密码",
        width:"200",
        fixed:false,
        fold:false
    },{
        prop:"state",
        label:"身份码",
        width:"auto",
        fixed:false,
        fold:false
    }
]);

const form = ref([
    {
        type:"input",
        label:"账户",
        prop:"uuid",
        disabled:true,
        placeholder:"1234"
    },
    {
        type:"input",
        label:"姓名",
        prop:"realname",
        disabled:false,
        placeholder:"safdsf"
    },
    {
        type:"input",
        label:"密码",
        prop:"password",
        disabled:false,
        placeholder:"safdsf"
    },
    {
        type:"select",
        label:"身份码",
        prop:"state",
        disabled:false,
        placeholder:"12313",
        option:[
            {
                label:"管理员",
                value:"0"
            },
            {
                label:"普通用户",
                value:"1"
            }
        ]
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

const exitBar = ref([
    {
        label:"修改",
        call:(v)=>{
            if(v){
                update(tableName,key,edit.value,act).then(v=>{
                    const {data} = v;
                    if(data){
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
                    if(data){
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