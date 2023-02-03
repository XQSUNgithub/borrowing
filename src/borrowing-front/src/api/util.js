import {ElMessage} from 'element-plus';

export const ID = ()=>Date.now().toString(36)+Math.random().toString(36).substr(2,8);

export const success = (message)=>ElMessage({message,type:"success"});
export const error = (message)=>ElMessage({message,type:"error"});

export const sure = (hint="确定?")=>{
    var succ = ()=>{},fail = ()=>{};
    window.confirm(hint,"提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
    }).then(()=>{
        succ();
    }).catch(()=>{
        fail();
    });
    return (s=()=>{},f=()=>{})=>{
        succ = s;
        fail = f;
    }
}

export const isNULL = (v)=>Boolean(Object.keys(v).length);

export function debounce(fn,wait=500){
    var timer = null;
    return function(){
        if(timer !== null){
            clearTimeout(timer);
        }
        timer = setTimeout(fn,wait);
    }
}

export function setMemory(key,value){
    localStorage.setItem(key,value);
}

export function removeKey(key){
    localStorage.removeItem(key);
}

export function getMemory(key){
    return localStorage.getItem(key)||null;
}

export function loadMemory(key){
    return localStorage.getItem(key)||null;
}

// export function syncMemory(key){
//     sessionStorage.setItem(key,loadMemory(key));
// }

export function clearMemory(key){
    localStorage.removeItem(key);
    // sessionStorage.removeItem(key);
}

export function setInfo(json={}){
    Object.entries(json).forEach(([key,value])=>{
        sessionStorage.setItem(key,value);
    });
}

export function getInfo(key){
    return sessionStorage.getItem(key)||"";
}


export function filter(str,length=50){
    return (str.length>length)?`${str.substr(0,length)}...`:str;
}