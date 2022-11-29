<template>
    <div id="header" class="shadow">
        <div class="box">
            <el-icon class="scale" @click="isCollapse=!isCollapse">
                <Expand v-show="isCollapse"/>
                <Fold v-show="!isCollapse"/>
            </el-icon>
            <div class="auto"></div>
            <el-button :icon="SwitchButton" type="danger" size="small" circle/>
        </div>
    </div>
</template>

<script setup>
import { computed  } from "vue";
import {useStore} from "vuex";
import { Expand, Fold,SwitchButton } from '@element-plus/icons-vue';

const store = useStore();
const isCollapse = computed({
    get(){
        return store.state.isCollapse;
    },
    set(value=false){
        store.commit("setState",["isCollapse",value]);
    }
});


</script>

<style scoped>

#header{
    height: 58px;
    display: flex;
    background-color: #FFFFFF;
    border-bottom: 1px solid rgb(207, 199, 199);
}

.box{
    margin: 20px;
    width: 100%;
    display: flex;
    align-items: center;
}

.el-icon{
    font-size: 1.2rem;
}
</style>