<template>
    <el-card>
        <template #header>
            <div class="card-header">
                <span>{{title}}</span>
                <div class="action">
                    <el-input v-model="searchText" placeholder="关键词" :prefix-icon="Search"/>
                    <el-button v-for="item in bar" :type="item.type||'primary'" @click="item.call" text>{{item.label}}</el-button>
                </div>
            </div>
        </template>
        <el-table :data="buffer" style="flex:1;">
            <el-table-column type="expand">
                <template #default="props">
                    <template v-for="item in label">
                        <div class="more" v-if="item.fold">
                            <span><b>{{item.label}}:</b></span>
                            <span>&nbsp;&nbsp;&nbsp;&nbsp;{{props.row[item.prop]||"暂无"}}</span><br/>
                        </div>
                    </template>
                </template>
            </el-table-column>
            <template v-for="item in label">
                <el-table-column 
                    v-if="!item.fold"
                    :fixed="item.fixed"
                    :prop="item.prop"
                    :label="item.label"
                    :width="item.width"
                />
            </template>
            <el-table-column fixed="right" label="操作" width="auto">
                <template #default="scope">
                    <el-button link size="small" v-for="item in action" :type="item.type||'primary'" @click="item.call(scope)">{{item.label}}</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div style="display: flex;justify-content: center;margin-top: 40px;">
            <el-pagination
                v-model:currentPage="index"
                v-model:page-size="pageSize"
                small
                background
                layout="prev, pager, next"
                hide-on-single-page
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

const size = 10;

const searchText = ref("");
let index = ref(1);
const pageSize = ref(size);

const props = defineProps({
    title:{
        type:String,
        default:""
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

const length = ref(0);

const buffer = computed(()=>{
    let buf;
    if(searchText.value.length){
        const reg = new RegExp(searchText.value,"i");
        buf = props.data.filter(v=>reg.test(Object.values(v).join(',')));
    }else{
        buf = props.data;
    }
    length.value = buf.length;
    let max = Math.max(Math.ceil(buf.length/size),1);
    index.value = Math.min(index.value,max);
    return buf.slice(size*(index.value-1),size*index.value);
});

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