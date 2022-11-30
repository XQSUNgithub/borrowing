import axios from "axios";
import {ID} from './util';
import {proxyURL,apiUrl} from "./config";
import querystring from "querystring";
import { ElMessage } from 'element-plus';

export const handle = axios.create({
    baseURL: proxyURL,
    timeout: 5000,
});

export const login = (uuid,password)=>{
    return new Promise((resolve,reject)=>{
        if(uuid.length&&password.length){
            handle.post(apiUrl.login,{
                data:{uuid,password}
            }).then(v=>{
                console.log(v);
                resolve(v);
            })
            .catch(err=>{
                console.log(err);
                ElMessage({
                    message:"请求失败",
                    type:"error"
                });
                reject(err);
            });
        }else{
            ElMessage({
                message:"账号密码不为空",
                type:"error"
            });
            reject(null);
        }
    });
}

export const regist = (realname,password,again,invitecode)=>{
    return new Promise((resolve,reject)=>{
        if(realname.length&&password.length&&again.length&&invitecode.length){
            console.log(invitecode);
            if(password===again){
                handle.post(apiUrl.regist,{
                    data:{realname,password,invitecode}
                }).then(v=>{
                    console.log(v);
                    resolve(v);
                })
                .catch(err=>{
                    console.log(err);
                    ElMessage({
                        message:"请求失败",
                        type:"error"
                    });
                    reject(err);
                });
            }else{
                ElMessage({
                    message:"两次输入密码不一致",
                    type:"error"
                });
                reject(null);
            }
        }else{
            ElMessage({
                message:"参数不全",
                type:"error"
            });
            reject(null);
        }
    });
}