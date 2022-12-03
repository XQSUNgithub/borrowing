<template>
    <el-scrollbar>
        <div class="layout-logo-left" :style="isCollapse ? 'font-size:12px': ''" style="max-width:150px;">{{title}}</div>
        <el-menu
            class="el-menu-vertical-demo"
            :collapse="isCollapse"
            active-text-color="#fff"
            background-color="#515A6E"
            text-color="#C4CED4"
            :default-active="selected"
        >
        <template v-for="(item,index) in menu">
            <template v-if="item.children">
                <el-sub-menu :index="item.label" :key="index">
                    <template #title>
                        <e-icon :type="item.icon"></e-icon>
                        <span>{{item.label}}</span>
                    </template>
                    <el-menu-item-group >
                        <el-menu-item v-for="(val,i) in item.children" :index="val.label" @click="call(val)">{{val.label}}</el-menu-item>
                    </el-menu-item-group>
                </el-sub-menu>
            </template>
            <template v-else>
                <el-menu-item :index="item.label" @click="call(item)">
                    <e-icon :type="item.icon"></e-icon>
                    <template #title>{{item.label}}</template>
                </el-menu-item>
            </template>
        </template>
        </el-menu>
    </el-scrollbar>
</template>

<script setup>
import { 
    ElMenu,ElSubMenu,ElMenuItemGroup,ElMenuItem
} from 'element-plus';
import { computed,defineProps } from 'vue';
import { useStore } from 'vuex';
const store = useStore();

const isCollapse = computed(()=>store.state.isCollapse);
const selected = computed({
    get(){return store.state.menuSelected;},
    set(value){
        store.commit("setState",["menuSelected",value])}
});

const props = defineProps({
    menu:{
        type:Array,
        default:()=>[]
    },
    title:{
        type:String,
        default:"管理系统"
    }
});

const call = e=>{
    selected.value = e.label;
}


</script>

<style scoped>
.layout-logo-left{
    transition: font-size 0.5s;
    user-select: none;
}
.layout-logo-left {
    width: 90%;
    height: 44px;
    margin: 10px auto;
    color: #fff;
    font-size: 16px;
    text-align: center;
    line-height: 44px;
}
.is-active{
    background-color: #2D8CF0;
    color: #fff;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-menu{
    border-right: none;
    user-select: none;
}


.el-scrollbar{
    width: 100%;
}

</style>