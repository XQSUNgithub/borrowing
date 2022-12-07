import { createWebHistory, createRouter } from "vue-router";

import index from "@/pages/index.vue";
import login from "@/pages/login.vue";
import main from "@/pages/main.vue";
import error404 from "@/pages/error/error-404.vue";
import error from "@/pages/error/error.vue";

import adminMenu from "@/pages/admin/admin-menu.vue";
import adminView from "@/pages/admin/admin-view.vue";

import userMenu from "@/pages/user/user-menu.vue";
import userView from "@/pages/user/user-view.vue";

import {loadMemory} from "@/api/util";
import {verify} from "@/api/net";

const routes = [
    {
        path: "/",
        component:index
    },
    {
        path: "/login",
        component:login
    },
    {
      path: "/register",
      component:login
    },
    {
      path: "/404",
      name:"404",
      component:error404
    },
    {
      path: "/error",
      name:"error",
      component:error
    },
    {
      path:"/:pathMatch(.*)*",
      redirect: '/404'
    },
    {
      path:'/admin',
      name:'admin',
      meta:{
        auth:true
      },
      component:main,
      children:[
        {
          name:"adminBody",
          path:'',
          components:{
            menu:adminMenu,
            view:adminView
          }
        }
      ]
    },
    {
      path:'/user',
      name:'user',
      meta:{
        auth:true
      },
      component:main,
      children:[
        {
          name:"userBody",
          path:'',
          components:{
            menu:userMenu,
            view:userView
          }
        }
      ]
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
  });
  
router.beforeEach((to,from,next)=>{
  const flag = to.matched.some(item => item.meta.auth);
  if(flag){
    if(to.path=="/admin"){
      verify("1").then(v=>{
        const {data:{data:{uuid}}} = v;
        if(uuid){
          return next();
        }else{
          return next({
            path:"/login"
          });
        }
      }).catch(err=>{
        next({
          path:"/login"
        });
      });
    }else{
      verify("0").then(v=>{
        const {data:{data:{uuid}}} = v;
        if(uuid){
          return next();
        }else{
          return next({
            path:"/login"
          });
        }
      }).catch(err=>{
        next({
          path:"/login"
        });
      });
    }
    return;
  }
  next();
})

export default router;
