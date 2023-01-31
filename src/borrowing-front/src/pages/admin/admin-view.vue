<template>
    <div id="view">
        <div class="box">
            <el-scrollbar> 
                <div class="ctx">
                    <adminLoginVue/>
                    <adminAssetVue/>
                    <adminIOVue/>
                    <adminBinVue/>
                    <adminRecordVue/>
                    <adminProcessVue/>
                </div>
            </el-scrollbar>
        </div>
    </div>
</template>

<script setup>
import adminLoginVue from './admin-login.vue';
import adminAssetVue from './admin-asset.vue';
import adminIOVue from './admin-io.vue';
import adminBinVue from './admin-bin.vue';
import adminRecordVue from './admin-record.vue';
import adminProcessVue from './admin-process.vue';

import { onMounted } from 'vue';
import { useStore } from 'vuex';
const store = useStore();

onMounted(()=>{
    store.commit("setState",["menuSelected","账户管理"]);
    store.commit("setState",["hint",[]]);
});


</script>

<style scoped>

#view{
    position: relative;
    width: 100%;
    height: calc(100% - 60px);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: stretch;
}
#view>.box{
    margin: 10px;
    flex: 1;
    overflow-y: hidden;
}

.ctx{
    width: 100%;
}

::v-deep.ctx>.el-card{
    margin-bottom: 12px !important;
}

::v-deep.ctx>.el-card:last-child{
    margin-bottom: 0 !important;
}


</style>