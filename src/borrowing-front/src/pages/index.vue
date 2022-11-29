<template>
    <div>loading</div>
</template>

<script setup>
import { getTokenFromService,getUserInfo } from '@/api/net';
import {getInfoFromAccessToken} from '@/api/config';
import { onMounted } from 'vue';
import { useRoute } from 'vue-router'
// import { useStore } from 'vuex';

const $route = useRoute();

onMounted(()=>{
    const code = $route.query.code||"";
    // if(code){
    //     getTokenFromService(code).then(v=>{
    //         const {access_token} = v.data;
    //         console.log(getInfoFromAccessToken(access_token));
    //         getUserInfo(access_token).then(v=>{
    //             console.log(v);
    //         }).catch(err=>{
    //             console.log(err);
    //         });
    //     }).catch(err=>console.log(err));
    // }
});


</script>