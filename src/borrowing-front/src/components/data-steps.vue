<template>
    <el-card>
        <template #header>
            <div class="card-header">
                <span>{{title}}</span>
                <div class="action">
                    <el-input v-model="searchText" placeholder="关键词" :prefix-icon="Search"/>
                    <el-button :type="item.type||'primary'" v-for="item in bar" @click="item.call" text>{{item.label}}</el-button>
                </div>
            </div>
        </template>
        <dataStepVue v-for="item in data" :process="item"/>
        <div style="display: flex;justify-content: center;margin-top: 40px;">
            <el-pagination
                v-model:currentPage="index"
                v-model:page-size="pageSize"
                small
                background
                layout="prev, pager, next"
                :hide-on-single-page="false"
                :total="length"
                class="mt-4"
            />
        </div>
    </el-card>
</template>

<script setup>
import { ref,defineProps,computed } from 'vue';
import { Search } from '@element-plus/icons-vue'
import { ElTable, ElTableColumn,ElPagination } from 'element-plus';
import dataStepVue from './data-step.vue';

const size = 4;

const searchText = ref("");
let index = ref(1);
const pageSize = ref(size);

const props = defineProps({
    title:{
        type:String,
        default:"流程管理"
    },
    data:{
        type:Array,
        default:()=>[]
    },
    action:{
        type:Array,
        default:()=>[]
    },
    bar:{
        type:Array,
        default:()=>[]
    },
    label:{
        type:Array,
        default:()=>[]
    }
});

const length = computed(()=>props.data.length);

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

.more{
    width: 100%;
    white-space:normal; 
    word-break:break-all;
    overflow:hidden;
}

</style>