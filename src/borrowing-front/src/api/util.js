import {ElMessage} from 'element-plus';

export const ID = ()=>Date.now().toString(36)+Math.random().toString(36).substr(2,8);

export function Adapter(){
    const config={
        buffer:[],
        length:10,
        index:1
    };
    return {
        config,
        pageTo(index=0){
            let start = (index-1)*config.length;
            return config.buffer.slice(start,start+config.length);
        },
        setConfig(buffer,length=10){
            config.buffer = buffer;
            config.length = length;
        },
        show(){
            let {index}=config;
            let start = (index-1)*config.length;
            return config.buffer.slice(start,start+config.length);
        }
    }
}

export function searchAdapter(){
    let lastBuf = "";
    let buf = [];
    const config={
        buffer:[],
        length:10,
        index:1,
        searchText:""
    };
    return {
        config,
        pageTo(index=0){
            let start = (index-1)*config.length;
            if(config.searchText.length){
                if(lastBuf===config.searchText){
                    return buf;
                }
                let exp = RegExp(config.searchText);
                buf = config.buffer.filter(item=>{
                    return exp.test(Object.values(item).join("\n"));
                }).slice(start,start+config.length);
                ElMessage({
                    type: 'success',
                    message:`搜索到${buf.length}条记录`
                })
                lastBuf=config.searchText;
                return buf;
            }
            return config.buffer.slice(start,start+config.length);
        },
        setConfig(buffer,length=10){
            config.buffer = buffer;
            config.length = length;
        },
        show(){
            let {index}=config;
            let start = (index-1)*config.length;
            return config.buffer.slice(start,start+config.length);
        }
    }
}

export function randomKey(){
    return Date.now().toString(36)+Math.random().toString(36).substr(1);
}