const axios = require('axios');

const baseURL = `https://api.openai.com`;
// http://101.34.27.96:8201/login/do_login
const url = `/v1/completions`;

const headers = {
    accept: "*/*",
    'Content-Type': "application/json",
    authorization: "Bearer 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",
    // 'sec-ch-ua': '"Not?A_Brand";v="8", "Chromium";v="108", "Microsoft Edge";v="108"',
    // 'sec-ch-ua-mobile': '?0',
    // 'sec-ch-ua-platform': "Windows",
    // 'sec-fetch-dest': 'empty',
    // 'sec-fetch-mode': 'cors',
    // 'sec-fetch-site': 'cross-site',
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54'
};//设置请求头

const params = {
    "prompt": "java写快排",
    "max_tokens": 2048,
    "temperature": 0.5,
    "top_p": 1,
    "frequency_penalty": 0,
    "presence_penalty": 0,
    "model": "text-davinci-003"
}

const config = {
    baseURL,
    timeout:60000//超时时间
};//设置全局请求参数


config.headers = headers;

axios.create(config).post(url,params).then(v=>{
    // console.log(v); //打印整个请求响应
    const {data} = v;
    console.log(data);
}).catch(err=>console.log(err));

