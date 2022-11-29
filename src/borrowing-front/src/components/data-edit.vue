<template>
    <el-card v-if="show">
        <template #header>
            <div class="card-header">
                <span>123123</span>
                <div class="action">
                    <el-button type="primary" text>添加</el-button>
                    <el-button type="primary" text>重置</el-button>
                    <el-button type="danger" @click="$emit('update:show', false)" text>关闭</el-button>
                </div>
            </div>
        </template>
        <el-form :model="model" label-width="150px" :rules="rules" ref="mydata">
            <el-form-item v-for="item in form" :label="item.label" :prop="item.prop" :placeholder="item.placeholder">
                <el-input v-if="item.type=='input'" v-model="model[item.prop]" :disabled="item.disabled" /> 
                <el-select v-if="item.type=='select'" v-model="model[item.prop]" :disabled="item.disabled" >
                    <el-option v-for="opt in item.option" :label="opt.label" :value="opt.value" />
                </el-select> 
            </el-form-item>
        </el-form>
        <div class="bar">
            <el-button @click="func" text>2333</el-button>
        </div>
    </el-card>
</template>

<script setup>
import { ref,defineProps,computed } from 'vue';
import { ElForm, ElFormItem, ElOption, ElSelect} from 'element-plus';

const props = defineProps({
    show:Boolean,
    form:Array,
    data:Object,
    rules:Object
});

const model = computed(()=>props.data);

const mydata = ref(null);

const func = ()=>{
    mydata._value.validate(v=>v).then(v=>{
        console.log(v);
    }).catch(err=>{
        console.log(err);
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