<template>
    <div id="header" class="shadow">
        <div class="box">
            <el-icon class="scale" @click="isCollapse=!isCollapse">
                <Expand v-show="isCollapse"/>
                <Fold v-show="!isCollapse"/>
            </el-icon>
            <div class="auto"></div>
            <el-button :icon="Refresh" v-if="show" @click="change" type="primary" size="small" circle/>
            <el-button :icon="SwitchButton" @click="exit" type="danger" size="small" circle/>
        </div>
    </div>
</template>

<script setup>
import { computed,onMounted,ref } from "vue";
import {useStore} from "vuex";
import { Expand, Fold,SwitchButton,Refresh } from '@element-plus/icons-vue';
import {removeKey,ID,sure} from "@/api/util";
import { useRoute, useRouter } from "vue-router";
import {verify} from "@/api/net";

const route = useRoute();
const router = useRouter();

const store = useStore();
const isCollapse = computed({
    get(){
        return store.state.isCollapse;
    },
    set(value=false){
        store.commit("setState",["isCollapse",value]);
    }
});

const path = computed(()=>route.path);
const ver = ref(false);
const show = computed(()=>/user|admin/.test(path.value)&&ver.value);

const change = ()=>{ 
    sure(`是否切换[${path.value==="/admin"?"普通用户":"管理员"}]系统?`)(()=>{
        if(path.value==="/admin"){
            router.push({path:"/user"});
        }else{
            router.push({path:"/admin"});
        }
    });
}

const exit = ()=>{
    sure('是否退出系统?')(()=>{
        removeKey("token");
        router.push({path:"/"});
    });
}

onMounted(()=>{
    verify("1").then(v=>{
        const {data:{data:{uuid}}} = v;
        ver.value = true;
    }).catch(err=>{ver.value=false;});
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