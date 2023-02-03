<template>
  <el-divider content-position="left">{{ header }}</el-divider>
  <div class="box">
    <div class="right">
      <el-steps :active="active" process-status="finish" finish-status="success" :space="200" align-center>
        <el-step title="借出请求" :icon="Bell">
          <template #description v-if="active>=0">
            <el-tooltip effect="light" raw-content :content="detail" >
              <el-button type="warning" text>详情</el-button>
            </el-tooltip>
          </template>
        </el-step>
        <el-step title="等待审批"  :icon="Flag" description=""/>
        <el-step title="流程中" :icon="PieChart" description=""/>
        <el-step title="归还请求" :icon="Bell" description="">
          <template #description v-if="active>=3">
            <el-tooltip effect="light" raw-content :content="detail" >
              <el-button type="warning" text>详情</el-button>
            </el-tooltip>
          </template>
        </el-step>
        <el-step title="等待确认" :icon="Flag" description=""/>
      </el-steps>
    </div>
    <div class="action">
      <template v-if="display">
        <el-button type="success" :icon="Check" text circle @click="handle('yes')">确认</el-button>
        <div></div>
        <el-button type="danger" :icon="Close" text circle @click="handle('no')">拒绝</el-button>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ElDivider, ElStep, ElSteps, ElTooltip } from 'element-plus';
import { Bell, PieChart, Finished, Check, Close, Flag } from '@element-plus/icons-vue'
import { ref,computed } from 'vue';
import {update} from '@/api/net';
import { ID,error,success,sure } from '@/api/util';

const props = defineProps({
  process:Object,
  act:{
    type:String,
    default:"1"
  }
});

const emit = defineEmits();

const tableName = "Record";
const key = "id";

const listen = computed(()=>props.process||{});

const state = computed(()=>listen.value.type||"异常");

const active = computed(()=>{
  console.log(listen.value);
  const {type} = listen.value; 
  switch(type){
    case "借阅":return 1;
    case "已借":return 2;
    case "归还":return 3;
    case "已还":return 4;
    case "结束":return 5;
    default:return -1;
  }
});

const display = computed(()=>["借阅","归还"].includes(state.value)&&props.act=="1");

const header = computed(()=>{
  const {id,time0,time1,time2} = props.process||{};
  return `审批单号: ${id}——————节点时间: ${time0||time1||time2||''}`
});

const detail = computed(()=>{
  const {cid,uuid,time0} = props.process||{};
  return `用户ID: ${uuid}<br/>矿物ID: ${cid}<br/>时间戳: ${time0}`
});

const detailBack = computed(()=>{
  const {cid,uuid,time2} = props.process||{};
  return `用户ID: ${uuid}<br/>矿物ID: ${cid}<br/>时间戳: ${time2}`
});



// const next = () => {
//   setTimeout(() => {
//     if (active.value++ > 3) active.value = 1;
//   }, 500);
// }

const handle = (type)=>{
  type=="yes"&&sure(`确认同意?`)(async ()=>{
    let type = "";
    switch(state.value){
      case "借阅":type="已借";break;
      case "已借":type="归还";break;
      case "归还":type="已还";break;
      case "已还":type="结束";break;
      default:return;
    }
    const {id} = listen.value;
    const {data} = await update(tableName,key,{id,type},props.act||"0");
    if(data&&data.data){
      emit("resolve",data.data);
      success("审批通过");
    }else{
      emit("error",data.data);
      error("审批异常");
    }
  });
  type=="no"&&sure(`确认拒绝?`)(()=>{
    emit("reject",data.data);
    error("审批拒绝");
  });
}

</script>

<style scoped>
.box{
  width: 100%;
  display: flex;
}

.left{
  width:200px;
}

.right{
  flex: 1;
}

.action{
  width: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

</style>
  