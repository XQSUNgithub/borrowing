<template>
    <div id="view">
        <div class="box">
            <el-scrollbar> 
                <div class="ctx">
                    <adminLoginVue/>
                </div>
            </el-scrollbar>
        </div>
    </div>
</template>

<script setup>
import adminLoginVue from './admin-login.vue';


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

/deep/.ctx>.el-card{
    margin-bottom: 12px !important;
}

/deep/ .ctx>.el-card:last-child{
    margin-bottom: 0 !important;
}


</style>