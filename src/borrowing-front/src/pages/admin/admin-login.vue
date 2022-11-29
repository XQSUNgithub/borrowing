<template>
    <dataViewVue v-show="show"
        :title="title"
        :data="data"
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
import {ref,computed} from 'vue';
import { ID } from '@/api/util';

const page = "登录管理";

const store = useStore();
const show = computed(()=>store.state.menuSelected===page);

const data = ref([]);

const title = ref(page);

const editShow = ref(true);

for(let i=0;i<11;i++){
    const v = {
        user_id:ID(),
        name:"asdasd",
        state:ID(),
        token:"asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd",
        power:"admin",
        deadline:"12312321"
    }
    data.value.push(v);
}

const handle = ()=>{
    data.value[0].user_id = ID();
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
        prop:"user_id",
        label:"账户",
        width:"auto",
        fixed:true,
        fold:false
    },{
        prop:"name",
        label:"姓名",
        width:"120",
        fixed:false,
        fold:false
    },{
        prop:"state",
        label:"身份",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"token",
        label:"TOKEN",
        width:"auto",
        fixed:false,
        fold:true
    },{
        prop:"power",
        label:"权级",
        width:"80",
        fixed:false,
        fold:false
    },{
        prop:"deadline",
        label:"登出时间",
        width:"130",
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

</script>