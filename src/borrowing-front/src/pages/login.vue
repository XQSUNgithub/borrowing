<template>
    <div id="box">
        <section id="content">
            <div class="content-header clearfix">
                <a :class="{current:sel}" @click="(sel=true)">登录</a>
                <a :class="{current:!sel}" @click="(sel=false)">注册</a>
            </div>
            <div class="content-body">
                <div class="dom" v-show="sel">
                    <form action="#">
                        <div class="s1">
                            <h4>账号</h4>
                            <input name="user" v-model="loginForm.uuid" type="text" placeholder="用户名">
                        </div>
                        <div class="s1">
                            <h4>密码</h4>
                            <input name="pwd" v-model="loginForm.password" type="password" placeholder="请输入密码">
                        </div>
                        <div class="s2">
                            <input type="checkbox">
                            <span>记住密码</span>
                        </div>
                        <input type="submit" class="btn" value="登&nbsp;录" @click="login">
                    </form>
                    <div class="dom-footer">
                        <div class="login-another">
                            <a href="#">找回密码</a>
                            <span>|</span>
                            <span>还没有注册帐号?</span>
                            <a href="/register">立即注册</a>
                        </div>
                    </div>
                </div>
                <div class="dom" v-show="!sel">
                    <form action="#">
                        <div class="s1">
                            <h4>真实姓名</h4>
                            <input name="user" v-model="registForm.realname" type="text" placeholder="填写你的真实姓名">
                        </div>
                        <div class="s1">
                            <h4>密码</h4>
                            <input name="pwd" v-model="registForm.password" type="text" placeholder="6-30位英文、数字、符号, 区分大小写">
                        </div>
                        <div class="s1">
                            <h4>再次输入</h4>
                            <input name="pwd" v-model="registForm.again" type="text" placeholder="再次输入密码">
                        </div>
                        <div class="s1">
                            <h4>邀请码<span style="color: purple">(选填)</span></h4>
                            <input name="code" v-model="registForm.invitecode" type="text" placeholder="如果有邀请码, 请填写">
                        </div>
                        <input type="submit" class="btn" value="注&nbsp;册" @click="register">
                    </form>
                </div>
            </div>
        </section>
    </div>
</template>

<script setup>
import { ElMessage } from 'element-plus';
import { login as Login,regist as Register} from '@/api/net';
import { onMounted,ref,computed } from 'vue';
import {useRoute} from 'vue-router';

const route = useRoute();

const sel = ref(true);

const loginForm = ref({
    uuid:"admin",
    password:"123123"
});

const registForm = ref({
    realname:"孙晓祺",
    password:"228322",
    again:"228322",
    invitecode:"2333"
});


onMounted(()=>{
    sel.value = route.path==='/login';
});

const login = (e)=>{
    const {uuid,password} = loginForm.value;
    Login(uuid,password);
    e.preventDefault();
}

const register = (e)=>{
    const {realname,password,again,invitecode} = registForm.value;
    Register(realname,password,again,invitecode);
    e.preventDefault();
}

</script>


<style scoped>

#box{
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.clearfix::before,
.clearfix::after{
    content: '';
    display: block;
    clear: both;
}

input, button{
    outline: none;
}

a{
    color: purple;
}

/*主要内容*/
#content{
    width: 50rem;
    /*height: 60rem;*/
    border: 0.1rem solid #e0e0e0;
    /*background-color: red;*/
    /* margin: 15rem auto; */
    
}

#content .content-header a{
    width: 50%;
    height: 60px;
    background-color: #f5f5f5;
    display: inline-block;
    float: left;
    /*居中*/
    text-align: center;
    line-height: 60px;
    color: #262626;
}

#content .content-header a.current{
    background-color: transparent;
    color: purple;
}

/*内容*/
#content .content-body{
    /*background-color: red;*/
    padding: 4rem;
}

#content .content-body .dom{
    width: 100%;
    /* display: none; */
}

#content .content-body .dom .s1{
    /*background-color: red;*/
    margin-bottom: 2rem;
}

#content .content-body .dom .s1 h4{
    color: #666666;
}

#content .content-body .dom .s1 input{
    width: 100%;
    height: 4rem;
    margin-top: 0.5rem;
    padding-left: 0.5rem;
    border-radius: 5px;
    border: 1px solid #cccccc;
}

#content .content-body .dom .s2{
    /*background-color: red;*/
    margin-bottom: 2rem;
}

#content .content-body .dom .btn{
    width: 100%;
    height: 4rem;
    border: none;
    font-size: 2rem;
    color: #fff;
    background-color: purple;
    border-radius: 3px;
    margin-bottom: 2rem;
    cursor: pointer;
}

a:hover{
    color: rgb(215, 54, 215) !important;
}
.btn:hover{
    background-color: rgb(209, 55, 209) !important;
}


#content .content-body .dom-footer .login-another{
    /*background-color: red;*/
    color: #666666;
    margin-bottom: 2rem;
}

#content .content-body .dom-footer .login-another span{
    margin: 0 5px;
}

#content .content-body .dom-footer .login-three{
    /*background-color: red;*/
    color: #666666;
    text-align: center;
}

#content .content-body .dom-footer .login-three span{
    /*background-color: red;*/
    position: relative;
}

#content .content-body .dom-footer .login-three span::before,
#content .content-body .dom-footer .login-three span::after{
    content: '';
    display: inline-block;
    width: 10rem;
    height: 0.1rem;
    border-bottom: 1px solid #e0e0e0;

    position: absolute;
    top: 50%;
}

#content .content-body .dom-footer .login-three span::before{
    left: -65%;
}

#content .content-body .dom-footer .login-three span::after{
    right: -65%;
}

#content .content-body .dom-footer .login-icon{
    margin-top: 2rem;
}

#content .content-body .dom-footer .login-icon img{
    width: 5rem;
}

#content .content-body .dom  .msg-code input{
    width: 60%;
}


#content .content-body .dom  .msg-code input:last-child{
    color: #666666;
    cursor: pointer;
    width: 38%;

}

</style>