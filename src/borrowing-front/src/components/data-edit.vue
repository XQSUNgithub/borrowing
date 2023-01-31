<template>
    <el-card v-if="show">
        <template #header>
            <div class="card-header">
                <span>{{title}}</span>
                <div class="action">
                    <!-- <el-button type="primary" text>添加</el-button>
                    <el-button type="primary" text>重置</el-button> -->
                    <el-button v-if="refresh" @click="$emit('reset')" :icon="Refresh" type="success" text>刷新</el-button>
                    <el-button v-for="item in action" :type="item.type||'primary'" @click="func(item.call)" text>{{item.label}}</el-button>
                    <el-button type="danger" @click="$emit('update:show', false)" v-if="close" text>关闭</el-button>
                </div>
            </div>
        </template>
        <el-form :model="model" label-width="150px" :rules="rules" ref="mydata">
            <template v-for="item in form" >
                <el-form-item v-if="!(item.disabled&&adder)" :label="item.label" :prop="item.prop">
                    <el-input v-if="item.type=='input'" v-model="model[item.prop]" :disabled="item.disabled" :placeholder="placeholder(item)"/> 
                    <el-select v-if="item.type=='select'" v-model="model[item.prop]" :disabled="item.disabled" :placeholder="placeholder(item)">
                        <el-option v-for="opt in item.option" :label="opt.label" :value="opt.value" />
                    </el-select>
                    <el-input
                        v-if="item.type=='textarea'"
                        v-model="model[item.prop]"
                        :autosize="{ minRows: 2, maxRows: 4 }"
                        type="textarea"
                        :disabled="item.disabled"
                        :placeholder="placeholder(item)"
                    />
                    <blurInputVue v-if="item.type=='blurInput'"  
                        v-model="model[item.prop]"
                        :option="item.option"
                        :disabled="item.disabled"
                        :placeholder="placeholder(item)"
                    />
                </el-form-item>
            </template>
        </el-form>
        <div class="bar">
            <!-- <el-button @click="func" text>2333</el-button> -->
        </div>
    </el-card>
</template>

<script setup>
import { ref,defineProps,computed } from 'vue';
import { ElForm, ElFormItem, ElOption, ElSelect} from 'element-plus';
import blurInputVue from './blur-input.vue';
import { Refresh } from '@element-plus/icons-vue';
import {error} from "@/api/util";

const placeholder = (item)=>{
    if(item.placeholder)return item.placeholder;
    return `请输入“${item.label}”`
}

const props = defineProps({
    show:Boolean,
    form:Array,
    data:Object,
    rules:Object,
    title:String,
    action:Array,
    refresh:Boolean,
    adder:{
        type:Boolean,
        default:false
    },
    close:{
        type:Boolean,
        default:true
    }
});

const model = computed(()=>props.data);

const mydata = ref(null);

const func = (call=()=>{})=>{ 
    mydata._value.validate(v=>v).then(v=>{
        call(v);
        !v&&error("参数有误");
    }).catch(err=>{
        error("参数有误");
        call(err);
    });
}
</script>

<style scoped>
/* .el-card{
    margin-bottom: 12px;
} */
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.el-table{
    min-height: 200px;
}

.action{
    margin-left: auto;
    display: flex;
    align-items: center;
    justify-content: space-between;
}
.action>.el-input{
    margin-right: 30px;
}
.bar{
    display: flex;
    flex-direction: row-reverse;
    padding: 12px;
}

</style>