import {ElMessage} from 'element-plus';

export const ID = ()=>Date.now().toString(36)+Math.random().toString(36).substr(2,8);

export const success = (message)=>ElMessage({message,type:"success"});
export const error = (message)=>ElMessage({message,type:"error"});



export function setMemory(key,value){
    localStorage.setItem(key,value);
}

export function getMemory(key){
    return sessionStorage.getItem(key)||null;
}

export function loadMemory(key){
    return localStorage.getItem(key)||null;
}

export function syncMemory(key){
    sessionStorage.setItem(key,loadMemory(key));
}

export function clearMemory(key){
    localStorage.removeItem(key);
    sessionStorage.removeItem(key);
}


export function filter(str,length=200){
    return (str.length>length)?`${str.substr(0,length)}...`:str;
}