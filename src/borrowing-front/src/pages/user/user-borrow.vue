<template>
    <dataViewVue v-show="show"
        :title="title"
        :data="tabledata"
        :action="action"
        :bar="bar"
        :label="label"
    />
    <dataBoxVue
        v-model:show="editShow"
        v-show="show"
        :form="from"
        v-model:data="edit"
        :rules="rules"
        :action="exitBar"
        title="归还申请"
        :close="false"
    />
</template>

<script setup>
import dataViewVue from '@/components/data-view.vue';
import dataEditVue from '@/components/data-edit.vue';
import dataBoxVue from '@/components/data-box.vue';
import { useStore } from 'vuex';
import {ref,computed,onMounted} from 'vue';
import { ID,error,success,sure,getInfo } from '@/api/util';
import {query,insert,update} from '@/api/net';

const tableName = "Record module:commonRecord";
const key = "id";
const act = "0";

const page = "借出查阅";

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
        label:"发起节点",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"time1",
        label:"处理节点",
        width:"auto",
        fixed:false,
        fold:false
    },{
        prop:"time2",
        label:"归还节点",
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
        disabled:true,
        need:true
    },{
        type:"input",
        prop:"time0",
        label:"发起节点",
        disabled:true,
        need:false
    },{
        type:"input",
        prop:"time1",
        label:"处理节点",
        disabled:true,
        need:false
    },{
        type:"input",
        prop:"time2",
        label:"归还节点",
        disabled:false,
        need:false
    }
];

const from = ref(codes);

const addform = ref(codes);

const edit = ref({});

const addBuf = ref({
    destroyed:"no"
});

const exitBar = ref([
    {
        label:"递交申请",
        type:"success",
        call:(v)=>{
            if(v){
                const {id} = row||{};
                sure(`是否递交申请?`)(async ()=>{
                    editShow.value = false;
                    const {data} = await update(tableName,key,{id,type:"归还"},act);
                    if(data&&data.data){
                        success("申请成功");
                        refresh();
                    }else{
                        error("审批异常");
                    }
                });
                // update(tableName,key,edit.value,act).then(v=>{
                //     const {data} = v;
                //     if(data&&data.data){
                //         Object.assign(row,data.data);
                //         success("修改成功");
                //     }else{
                //         error("修改失败");
                //     }
                // }).catch(err=>{
                //     console.log(err);
                // });
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
        label:"申请归还",
        call:v=>{
            Object.assign(edit.value,v.row);
            row = v.row;
            editShow.value = true;
        }
    },
    // {
    //     label:"删除",
    //     type:"danger",
    //     call:v=>{
    //         const row = v.row;
    //         const {cid} = row;
    //         row.destroyed = "yes";
    //         sure(`是否删除记录[${cid}]?`)(()=>{
    //             remove(tableName,key,row,act).then(v=>{
    //                 const {data} = v;
    //                 if(data.data){
    //                     console.log(data.data);
    //                     tabledata.value = tabledata.value.filter(v=>v.cid!=cid);
    //                     success("删除成功");
    //                 }else{
    //                     error("删除失败");
    //                 }
    //             }).catch(err=>{
    //                 console.log(err);
    //             });
    //         });
    //     }
    // }
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
    const uuid = getInfo("uuid");
    query(tableName,null,null,act,{uuid}).then(v=>{
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