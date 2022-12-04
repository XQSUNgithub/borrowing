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

const url = `http://secret.jluyyds.ltd/static/10179.png`;

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

