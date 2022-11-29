import {createApp } from 'vue'
import App from './App.vue'
import router from './router';
import state from './state';
import VueCookies from 'vue-cookies';
import {ElButton,ElIcon,ElScrollbar,ElCard,ElInput} from 'element-plus';
import 'element-plus/dist/index.css';
import 'element-plus/es/components/icon/style/css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import EIcon from "@/components/EIcon.vue";

const app = createApp(App);

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
    .component('e-icon',EIcon)
    .mount('#app');

