import { createStore } from "vuex";

const store = {
    state: {
        isCollapse:false,
        menuSelected:"",
        hint:["审批处理"],
        info:{

        },
    },
    mutations: {
        setState(state,[key,value]){
            state[key]=value;
        }
    },
    actions: {

    },
    getters: {

    },
    modules: {
        
    }
}


export default createStore(store)
