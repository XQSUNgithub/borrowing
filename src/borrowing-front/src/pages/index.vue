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
            <a href="/login">登录</a>
            <div style="width:20px;">|</div>
            <a href="/register">注册</a>
        </div>
        <div class="body">
            <div class="card">
                <div class="show">
                    <div class="ele" v-for="(item,i) in imgs" @click="(sel==i)?(sel=-1):(sel=i)">
                        <img :src="item.src">
                        <div class="text" :class="{showtext:sel==i}">
                            {{filter(item.discription)}}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted,ref,computed, onUnmounted } from 'vue';
import { useRoute } from 'vue-router'
import {  Search } from '@element-plus/icons-vue'
// import { useStore } from 'vuex';
import {get} from '@/api/net';
import {res} from '@/api/config';
import {filter} from '@/api/util';

const imgs = ref([]);

const text = `Lorem ipsum dolor, sit amet consectetur adipisicing elit. Officiis aut sunt harum voluptate. Non corporis voluptatem blanditiis doloribus eveniet eaque, fugiat nam incidunt, delectus earum error dolorem cum tenetur tempora minus consequatur sit aut? Officiis iste doloremque labore deserunt eius sapiente ducimus recusandae sunt doloribus, dolorum, necessitatibus sint adipisci deleniti magnam id pariatur illo maiores perspiciatis magni cupiditate nobis ipsa quidem. Illo, iste, delectus repellat, mollitia maxime ex quae voluptatem magni quasi sequi necessitatibus accusantium deserunt! Ipsam, consequatur! Autem omnis quod minima laboriosam mollitia iure ut, at eaque dolorum, sequi voluptate repellat, rem tenetur atque veniam! Quam commodi veniam veritatis?`;
const sel = ref(-1);
const width = ref(300);
const count = ref(4);

function init(){
    get('http://secret.jluyyds.ltd/imgs/').then(v=>{
        const {data:{src}} = v;
        imgs.value = src.map(i=>{
            return {
                src:res+i.name,
                discription:res+i.name+"  "+text
            }
        });
    });
    window.addEventListener("resize",resize);
    resize();
}
//1 12*2 2 12*3 3 12*4
function resize(){
    let w = window.innerWidth;
    if(w<300){
        count.value = 1;
        width.value = window.innerWidth - 24;
    }else if(w<960){
        count.value = 2;
        width.value = (window.innerWidth - 36)/2;
    }else if(w<1500){
        count.value = 3;
        width.value = 300;
    }else{
        count.value = 4;
        width.value = 300;
    }
    // width.value = (window.innerWidth - 12*(count.value+1))/count.value;
}

onMounted(init);

onUnmounted(()=>{
    window.removeEventListener("resize",resize);
});



</script>

<style scoped>

#box{
    overflow-y: auto;
    overflow-x: hidden;
    height: 100%;
}

/* 设置滚动条的样式 */
#box::-webkit-scrollbar {
    width:5px;
}
/* 滚动槽 */
#box::-webkit-scrollbar-track {
    -webkit-box-shadow:inset006pxrgba(0,0,0,0.3);
    border-radius:10px;
}
/* 滚动条滑块 */
#box::-webkit-scrollbar-thumb {
    border-radius:10px;
    -webkit-box-shadow:inset006pxrgba(0,0,0,0.5);
}
#box::-webkit-scrollbar-thumb:window-inactive {
    background:rgb(138, 130, 130);
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
    z-index: 1;
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
    display: flex;
    justify-content: center;
    align-items: center;
    border-right: 1px solid gray;
}
.show{
    width: auto;
    column-gap: 12px;
    padding: 12px;
    column-count: v-bind(count);
}


.ele{
    display:flex;
    flex-direction: column;
    align-items:center; 
    justify-content:center;
    overflow: hidden;
    width: v-bind(width+"px");
    border-radius: 1rem;
    margin-bottom: 12px;
    box-shadow: 2px 2px 10px gray;
    -webkit-column-break-inside: avoid; /* Chrome, Safari, Opera */
            page-break-inside: avoid; /* Firefox */
                 break-inside: avoid; /* IE 10+, Chrome, Safari, Opera */
}

.ele>img{
    width: 100%;
    height: auto;
}
.ele>.text{
    white-space:normal; 
    word-break:break-all;
    overflow:hidden; 
    max-height: 0;
    transition: max-height 1s;
}

.ele:hover{
    transform: scale(1.01,1.01);
    transition-duration: .5s;
}

.showtext{
    padding: 5px;
    max-height: 300px !important;
}

/* .ele:hover>.text{
    padding: 5px;
    max-height: 20rem;
} */

::v-deep .el-input__wrapper{
    border-radius: 1.5rem;
}

</style>