import {createApp } from 'vue'
import App from './App.vue'
import router from './router';
import state from './state';
import VueCookies from 'vue-cookies';
import {ElButton,ElIcon,ElScrollbar,ElCard,ElInput,ElMessageBox} from 'element-plus';
import 'element-plus/dist/index.css';
import 'element-plus/es/components/icon/style/css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import EIcon from "@/components/EIcon.vue";
import lazyPlugin from 'vue3-lazy'

const url = `https://ts3.cn.mm.bing.net/th?id=ORMS.dc541f157031d9e2478cc5cafeedc9a1&pid=Wdp&w=300&h=156&qlt=90&c=1&rs=1&dpr=1&p=0`;

const app = createApp(App);

window.confirm = ElMessageBox.confirm;

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
  
app
    .use(VueCookies)
    .use(router)
    .use(state)
    .use(ElButton)
    .use(ElIcon)
    .use(ElScrollbar)
    .use(ElCard)
    .use(ElInput)
    .use(lazyPlugin, {
        loading: url,
        error: url
    })
    .component('e-icon',EIcon)
    .mount('#app');

