const axios = require('axios');

const baseURL = `http://101.34.27.96:8201`;
// http://101.34.27.96:8201/login/do_login
const url = `/login/do_login`;

const headers = {
    // "Accept": "application/json, text/plain, */*",
    // "Accept-Encoding": "gzip, deflate",
    // "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
    // 'Authorization': `token...`,
    // "Referer":"http://www.localhost.com:8889/",
};//设置请求头

const params = {
    uuid:"0424585384",
    password:"228322"
};//设置请求参数

const config = {
    baseURL,
    timeout:5000//超时时间
};//设置全局请求参数


config.headers = headers;

axios.create(config).post(url,params).then(v=>{
    // console.log(v); //打印整个请求响应
    const {data} = v;
    console.log(data);
}).catch(err=>console.log(err));