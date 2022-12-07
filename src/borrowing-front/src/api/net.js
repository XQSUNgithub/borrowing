import axios from "axios";
import {ID,success,error,setMemory,loadMemory,removeKey} from './util';
import {proxyURL,apiUrl} from "./config";
// import querystring from "querystring";

export const handle = axios.create({
    baseURL: proxyURL,
    timeout: 5000,
});

// console.log(loadMemory("token"));

export const login = (uuid,password)=>{
    return new Promise((resolve,reject)=>{
        if(uuid.length&&password.length){
            post(apiUrl.login,{
                uuid,password
            }).then(v=>{
                const {data:{code,msg,data:{token,action}}} = v;
                if(token){
                    success("登陆成功");
                    setMemory("token",token);
                    resolve(action);
                }else{
                    error("登陆失败");
                    reject(null);
                }
            })
            .catch(err=>{
                console.log(err);
                error("请求失败");
                reject(err);
            });
        }else{
            error("账号密码不为空");
            reject(null);
        }
    });
}

export const regist = (realname,password,again,invitecode)=>{
    return new Promise((resolve,reject)=>{
        if(realname.length&&password.length&&again.length&&invitecode.length){
            if(password===again){
                post(apiUrl.regist,{
                    realname,password,invitecode
                }).then(v=>{
                    const {data:{code,msg,data}} = v;
                    if(data){
                        success("注册成功");
                        resolve(data);//"0424585384"
                    }else{
                        error("注册失败");
                        reject(null);
                    }
                })
                .catch(err=>{
                    console.log(err);
                    error("请求失败");
                    reject(err);
                });
            }else{
                error("两次输入密码不一致");
                reject(null);
            }
        }else{
            error("参数不全");
            reject(null);
        }
    });
}

export const verify = (action="1")=>{
    return new Promise((resolve,reject)=>{
        const token = loadMemory("token");
        if(!token){
            reject(null);
        }else{
            proxy(apiUrl.verify,{
                action
            },{
                headers: {
                    'Authorization': token
                }
            }).then(v=>{
                resolve(v)
            }).catch(err=>{
                removeKey("token");
                reject(err);
            });
        }
    });
}

export const query = (tableName,keyword="",action="1")=>{
    const token = loadMemory("token");
    return proxy(apiUrl.query,{
        tableName,
        keyword,
        action
    },{
        headers: {
            'Authorization': token
        }
    });
}

export const insert = (tableName,key,value={},action="1")=>{
    const token = loadMemory("token");
    return proxy(apiUrl.insert,{
        tableName,
        key,value,
        action
    },{
        headers: {
            'Authorization': token
        }
    });
}

export const update = (tableName,key,value={},action="1")=>{
    const token = loadMemory("token");
    return proxy(apiUrl.update,{
        tableName,
        key,value,
        action
    },{
        headers: {
            'Authorization': token
        }
    });
}

export const remove = (tableName,key,value,action="1")=>{
    const token = loadMemory("token");
    return proxy(apiUrl.delete,{
        tableName,
        key,value,
        action
    },{
        headers: {
            'Authorization': token
        }
    });
}

export function proxy(url,params={},config={},method="post"){
    return handle.post("/proxy",{
        url,params,config,method
    });
}

export function post(url,data={}){
    return proxy(url,data);
}

export function get(url,data={}){
    return proxy(url,data,{},"get");
}