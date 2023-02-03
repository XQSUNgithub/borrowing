const axios = require('axios');

const baseURL = `http://127.0.0.1:8201`;
// http://101.34.27.96:8201/login/do_login
const url = `/insert`;

const headers = {
    // "Accept": "application/json, text/plain, */*",
    // "Accept-Encoding": "gzip, deflate",
    // "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
     'Authorization':`eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsImlhdCI6MTY3NTI3MDcxNywiZXhwIjoxNjc1NzAyNzE3LCJpZCI6IjM3MzQzMjEwNzMifQ.FJ3-4VwOwBW_P2kOqhAl0ZoFXUyylnQ7BJU_KWEuhAA`
         //`eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsImlhdCI6MTY3MDczODUxMSwiZXhwIjoxNjcxMTcwNTExLCJpZCI6IjkxNDExMDY2NDUifQ.43jXoh2kaypzxUA2i6rfkXWWdI70Zkv75UvDlL_doUw`
         //`eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsImlhdCI6MTY3MDIzNjkxMiwiZXhwIjoxNjcwNjY4OTEyLCJpZCI6IjkxMjIwOTg4NjEifQ.LS4-QRnLVyLx4urFERXeqYONH_L1Mj3rfQWt69nYy4c`
     //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsImlhdCI6MTY3MDE0Njk1MCwiZXhwIjoxNjcwNTc4OTUwLCJpZCI6Ijk1MDc0MTkwNjcifQ.E1Nq6IdX12oENCCGk2ggS1BBflV2sCmhWlceLzFZbbQ`
         //`eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsImlhdCI6MTY3MDE0Njk1MCwiZXhwIjoxNjcwNTc4OTUwLCJpZCI6Ijk1MDc0MTkwNjcifQ.E1Nq6IdX12oENCCGk2ggS1BBflV2sCmhWlceLzFZbbQ`
         //`eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsImlhdCI6MTY3MDE0MzA5NiwiZXhwIjoxNjcwMTQ2Njk2LCJpZCI6IjgxNzA0MzExODEifQ.PHfmxp_bMKlceTDhsQOTFpPOmX9y_hmlMbWfQekS8U8`
         //`eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsImlhdCI6MTY3MDEzMTI4OSwiZXhwIjoxNjcwMTMxMjg5LCJpZCI6IjEyODQ2ODUzNzUifQ.4NeKfEZQnw13JgVzgCTqxP48GJqS_YGx4BPRsXFNUX0`
     //iJ0b2tlbiIsImlhdCI6MTY2OTk0OTc4MywiZXhwIjoxNjY5OTUwMzgzLCJpZCI6IjcxNjEwOTQ5NTAifQ.2mvZ7dm4pGN4dMHcZtoZzf866RdryllHPEzZfP5fJYc`
    // "Referer":"http://www.localhost.com:8889/",
};//设置请求头

const params = {
    action:"0",
    tableName:"Collection",
    key:"cid",
    value:{
        idInfo:"矿物自然元素类自然金属元素铜族 自然2",
        source:"长春",    //产地
        purpose:"varchar(16)",  //用途
        location:"123", //库存位置号
        provider:"孙晓祺",  //标本提供者
        providetime:"20010730", //提供时间
        gatherer:"孙", //采集者
        donor:"孙晓祺", //捐赠者
        destroyed:"no", //不显示
        num:"10", //标本数量
        recorderid:"9141106645",// 录入者
        access:"网上订购" //获取途径
    }
};//设置请求参数

const config = {
    baseURL,
    timeout:5000//超时时间
};//设置全局请求参数


config.headers = headers;

axios.create(config).post(url,params).then(v=>{
     console.log(v); //打印整个请求响应
    const {data} = v;
    console.log(data);
}).catch(err=>console.log(err));

/*
axios.create().get(baseURL+url).then(v=>{
    console.log(v); //打印整个请求响应
    //const {data} = v;
    //console.log(data);
}).catch(err=>console.log(err));*/