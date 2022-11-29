<template>
    <div id="box">
        <div class="header">
            <div class="tab"></div>
            <el-input
                class="w-50 m-2 search"
                placeholder="请输入关键词"
                :prefix-icon="Search"
            />
            <div class="tab"></div>
            <a href="#">登录</a>
            <div style="width:20px;">|</div>
            <a href="#">注册</a>
        </div>
        <div class="body">
            <div class="card">
                <div class="ele" v-for="i in 10">

                </div>
                <!-- <img src="@/assets/logo.png" v-for="i in 10"/> -->
            </div>
            <div class="more">
                233
            </div>
        </div>
    </div>
</template>

<script setup>
import { getTokenFromService,getUserInfo } from '@/api/net';
import {getInfoFromAccessToken} from '@/api/config';
import { onMounted } from 'vue';
import { useRoute } from 'vue-router'
import {  Search } from '@element-plus/icons-vue'
// import { useStore } from 'vuex';

const $route = useRoute();

onMounted(()=>{
    const code = $route.query.code||"";

});


</script>

<style scoped>

#box{
    overflow-y: auto;
    height: 100%;
}

.header{
    background-color: #000000;
    color: aliceblue;
    text-align: center;
    position: sticky;
    top: 0;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0 50px 0 50px;
}
.title{
    font-size: 5rem;
}

.tab{
    margin-left:auto
}

.search{
    width: 32rem;
}

a{
    color: aliceblue;
}
a:hover{
    color: rgb(210, 17, 210);
}

.body{
    width: 100%;
    display: flex;
}

.card{
    flex: 1;
    border-right: 1px solid gray;
    column-count: 3;
    column-gap: 12px;
    padding: auto;
}

.more{
    width: 200px;
    background-color: antiquewhite;
}

.ele{
    width: 200px;
    height: 200px;
    border: 1px solid gray;
    border-radius: 1rem;
    margin-bottom: 12px;
    -webkit-column-break-inside: avoid; /* Chrome, Safari, Opera */
            page-break-inside: avoid; /* Firefox */
                 break-inside: avoid; /* IE 10+, Chrome, Safari, Opera */
}

/deep/ .el-input__wrapper{
    border-radius: 1.5rem;
}

</style>