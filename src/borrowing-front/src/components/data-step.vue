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
      <template v-if="true">
        <el-button type="success" :icon="Check" text circle @click="next">确认</el-button>
        <div></div>
        <el-button type="danger" :icon="Close" text circle>拒绝</el-button>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ElDivider, ElStep, ElSteps, ElTooltip } from 'element-plus';
import { Bell, PieChart, Finished, Check, Close, Flag } from '@element-plus/icons-vue'
import { ref,computed } from 'vue'

const props = defineProps({
  process:Object
});

const active = computed(()=>{
  const {time0,time1,time2} = listen.value; 
  if(time2)return 4;
  if(time1)return 2;
  if(time0)return 1;
  return -1;
});

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

const listen = computed(()=>props.process||{});

const status = computed(()=>{
  const {time0,time1,time2} = listen.value; 
  console.log(233);
  if(time2)return active.value = 4;
  if(time1)return active.value = 2;
  if(time0)return active.value = 1;
});

const next = () => {
  setTimeout(() => {
    if (active.value++ > 3) active.value = 1;
  }, 500);
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
  