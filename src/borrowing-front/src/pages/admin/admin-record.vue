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
        title="记录编辑"
    />
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

const tableName = "Record";
const key = "id";
const act = "1";
const module = "record";

const page = "审批记录";

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
        prop:"id",
        label:"审批单号",
        width:"auto",
        fixed:true,
        fold:false
    },{
        prop:"cid",
        label:"矿物编号",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"time0",
        label:"time0",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"time1",
        label:"time1",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"time2",
        label:"time2",
        width:"auto",
        fixed:false,
        fold:false
    }
]);

const codes = [
    {
        type:"input",
        prop:"cid",
        label:"矿物编号",
        disabled:false,
        need:true
    },{
        type:"input",
        prop:"time0",
        label:"time0",
        disabled:false
    },{
        type:"input",
        prop:"time1",
        label:"time0",
        disabled:false
    },{
        type:"input",
        prop:"time2",
        label:"time0",
        disabled:false
    }
];

const from = ref([{
        type:"input",
        prop:"id",
        label:"审批单号",
        disabled:false
    },...codes]);

const addform = ref(codes);

const edit = ref({});

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
        label:"编辑",
        call:v=>{
            Object.assign(edit.value,v.row);
            row = v.row;
            editShow.value = true;
        }
    },{
        label:"同意",
        type:"success",
        call:v=>{
            sure("是否同意?")(()=>{
                success("已批准");
            });
        }
    },{
        label:"打回",
        type:"warning",
        call:v=>{
            sure("是否打回审批?")(()=>{
                success("已打回");
            });
        }
    },{
        label:"删除",
        type:"danger",
        call:v=>{
            const row = v.row;
            const {uuid} = row;
            sure(`是否删除记录[${row.cid}]?`)(()=>{
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
            });
        }
    }
]);


const rules = ref({});

function setRules(){
    rules.value = {
        id:{ required: true, message: `请输入矿物ID`, trigger: 'blur' }
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
    query(tableName,null,module,act).then(v=>{
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