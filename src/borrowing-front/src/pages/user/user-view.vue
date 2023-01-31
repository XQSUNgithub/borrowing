<template>
    <div id="view">
        <div class="box">
            <el-scrollbar> 
                <div class="ctx">
                    <userAssetVue/>
                    <userBorrowVue/>
                    <userProcessVue/>
                    <!-- <userTestVue/> -->
                </div>
            </el-scrollbar>
        </div>
    </div>
</template>

<script setup>
// import userTestVue from './user-test.vue';
import userAssetVue from './user-asset.vue';
import userBorrowVue from './user-borrow.vue';
import userProcessVue from './user-process.vue';
import { onMounted } from 'vue';
import { useStore } from 'vuex';
const store = useStore();

onMounted(()=>{
    store.commit("setState",["menuSelected","矿物查阅"]);
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

::v-deep .ctx>.el-card:last-child{
    margin-bottom: 0 !important;
}


</style>