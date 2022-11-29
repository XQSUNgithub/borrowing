import { createStore } from "vuex";

const store = {
    state: {
        isCollapse:false,
        menuSelected:"登录管理",
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
