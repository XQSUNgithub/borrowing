import axios from "axios";
import {ID} from './util';
import {oauth2,proxyURL} from "./config";
import querystring from "querystring";

export const handle = axios.create({
    baseURL: proxyURL,
    timeout: 5000,
});

export function loginFromService(){
    const {userAuthorizationUri} = oauth2;
    const url = `${userAuthorizationUri}?${querystring.stringify({
        client_id:oauth2.clientId,
        response_type:oauth2.response_type,
        scope:oauth2.scope,
        redirect_uri:oauth2.redirect_uri,
        nonce:ID()
    })}`;
    console.log(url);
    window.location.href = url;
}

export function getTokenFromService(code){
    return proxy(oauth2.accessTokenUri,{
        client_id:oauth2.clientId,
        client_secret:oauth2.client_secret,
        redirect_uri:oauth2.redirect_uri,
        grant_type:oauth2.grant_type,
        code
    },{
        headers:{
            "Content-type":"application/x-www-form-urlencoded"
        }
    });
}

export function getUserInfo(access_token){
    return proxy(oauth2.resUri,{},{
        headers: {
            "Accept": "application/json, text/plain, */*",
            "Accept-Encoding": "gzip, deflate",
            "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
            'Authorization': 'Bearer ' + access_token,
            "Referer":"http://www.localhost.com:8889/",
        }
    },"get");
}

export function proxy(url,params={},config={},method="post"){
    return handle.post("/proxy",{
        url,params,config,method
    });
}