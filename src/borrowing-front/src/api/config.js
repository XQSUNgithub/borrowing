export const url = "http://localhost:8890";

// export const proxyURL = "/";
export const proxyURL = "/serve";

export const oauth2={
    baseOAuthUrl:"http://auth.jludreamworks.com:30080/",
    //请求授权地址
    userAuthorizationUri:"http://auth.jludreamworks.com:30080/oauth2/authorize",
    //accessToken请求地址
    accessTokenUri : "http://auth.jludreamworks.com:30080/oauth2/token",
    //用户信息请求地址
    userInfoUri:"http://auth.jludreamworks.com:30080/userinfo",
    //登出请求地址
    logoutUri:"https://github.com/logout",
    //项目地址
    localuri :"http://localhost:8889",
    redirect_uri : "http://www.localhost.com:8889",
    //案例资源服务器地址
    resUri:"http://thzk.jludreamworks.com:30080/api/users/details/self",
    //客户端相关标识，请从认证服务器申请
    clientId: "516e4616-c848-42f8-9a2e-ec9d2a414613",
    client_secret:"34f52dc2-081c-4d9f-990f-921aa285ddf2",
    //申请的权限范围
    scope:"openid",
    //可选参数，客户端的当前状态，可以指定任意值，用于校验，此次案例不做相关认证
    state:"",
    //一些固定的请求参数
    response_type:"code",
    grant_type : "authorization_code",
    code:"",
}

export function getInfoFromAccessToken(str){
    return JSON.parse(atob(str.split('.')[1]));
}

export const apiUrl = {
    verify:"/verify",
    login:"/login",
    regist:"/regist",
    query:"/query",
};