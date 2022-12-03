<template>
    <dataViewVue v-show="show"
        :title="title"
        :data="tabledata"
        :action="action"
        :bar="bar"
        :label="label"
    />
    <!-- <el-button @click="handle">test</el-button> -->
    <dataEditVue
        v-model:show="editShow"
        v-show="show"
        v-model:form="form"
        v-model:data="edit"
        :rules="rules"
    />
    <!-- <dataEditVue
        v-show="show"
        :test="test"
        v-model:form="form"
        v-model:data="edit"
        :rules="rules"
    />
    <dataEditVue
        v-show="show"
        :test="test"
        v-model:form="form"
        v-model:data="edit"
        :rules="rules"
    /> -->
</template>

<script setup>
import dataViewVue from '@/components/data-view.vue';
import dataEditVue from '@/components/data-edit.vue';
import { useStore } from 'vuex';
import {ref,computed,onMounted} from 'vue';
import { ID } from '@/api/util';
import {query} from '@/api/net';

const page = "登录管理";

const store = useStore();
const show = computed(()=>store.state.menuSelected===page);

const tabledata = ref([]);

const title = ref(page);

const editShow = ref(true);

const handle = ()=>{
    tabledata.value[0].uuid = ID();
}

const action = ref([
    {
        label:"编辑",
        call:v=>{
            console.log(v);
            editShow.value = true;
        }
    },
    {
        label:"下线",
        call:v=>{console.log(v)}
    }
]);

const bar = ref([
    {
        label:"刷新",
        call:()=>{console.log(1234)}
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
        label:"身份",
        width:"auto",
        fixed:false,
        fold:false
    }
]);

const form = ref([
{
        type:"input",
        label:"用户ID",
        prop:"user_id",
        disabled:true,
        placeholder:"1234"
    },
    {
        type:"input",
        label:"姓名",
        prop:"name",
        disabled:false,
        placeholder:"safdsf"
    },
    {
        type:"select",
        label:"身份",
        prop:"state",
        disabled:false,
        placeholder:"12313",
        option:[
            {
                label:"管理用户",
                value:"管理用户"
            },
            {
                label:"普通用户",
                value:"普通用户"
            }
        ]
    },
    {
        type:"input",
        label:"登出时间",
        prop:"deadline",
        disabled:false,
        placeholder:"asdasdas"
    }
]);

const edit = ref({
    user_id:"2333",
    name:"1357",
    state:"普通用户",
    deadline:1234567
});

const rules = ref({
    user_id: [
        { required: true, message: '请输入用户ID', trigger: 'blur' },
    ],
    name: [
        { required: true, message: '请输入用户名称', trigger: 'blur' },
    ],
    state: [
        { required: true, message: '请输入用户身份', trigger: 'blur' },
    ],
    abcd: [
        { required: true, message: '测试', trigger: 'blur' },
    ],
});

function init(){
    query("user",null).then(v=>{
        const {data:{data}} = v;
        tabledata.value = data;
    }).catch(err=>{
        console.log(err);
    });
}

onMounted(()=>{
    init();
});


</script>