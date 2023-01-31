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
        title="出库申请"
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
import {query,insert,borrow} from '@/api/net';

const tableName = "Collection";
const borrowName = 'Record';
const borrowKey = "id";
const key = "cid";
const act = "0";

const page = "矿物查阅";

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
        prop:"name",
        label:"名录",
        width:"auto",
        fixed:true,
        fold:false
    },{
        prop:"provider",
        label:"标本提供者",
        width:"auto",
        fixed:false,
        fold:true
    },{
        prop:"gatherer",
        label:"采集者",
        width:"auto",
        fixed:false,
        fold:true
    },{
        prop:"donor",
        label:"捐赠者",
        width:"auto",
        fixed:false,
        fold:true
    },{
        prop:"recorder",
        label:"录入者",
        width:"auto",
        fixed:false,
        fold:true
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
        fold:true
    },{
        prop:"purpose",
        label:"用途",
        width:"200",
        fixed:false,
        fold:true
    },{
        prop:"location",
        label:"库存位置号",
        width:"auto",
        fixed:false,
        fold:true
    },{
        prop:"access",
        label:"获取途径",
        width:"auto",
        fixed:false,
        fold:true
    }
]);

const codes = [
    {    
        type:"input",
        prop:"cid",
        label:"编号",
        disabled:true
    },{
        type:"input",
        prop:"name",
        label:"名录",
        disabled:true
    },{
        type:"textarea",
        prop:"purpose",
        label:"用途",
        disabled:false
    }
];

const from = ref(codes);

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
        label:"递交申请",
        type:"success",
        call:(v)=>{
            if(v){
                const {cid} = edit.value;
                const uuid = getInfo("uuid");
                const type = '借阅';
                sure(`是否递交申请?`)(()=>{
                    borrow(borrowName,borrowKey,{
                        cid,uuid,type
                    },act).then(v=>{
                        console.log(v);
                        success("申请成功");
                    }).catch(err=>{
                        console.log(err);
                        error("申请失败");
                    })
                    editShow.value = false;
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
        label:"申请借出",
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