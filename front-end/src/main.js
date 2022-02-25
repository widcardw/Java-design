import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index.js'

import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import Emitter from 'tiny-emitter'

const emitter = new Emitter();

const app = createApp(App)
app.config.globalProperties.$emitter = emitter

app.use(router)
    .use(store)
    .use(ElementPlus, {locale: zhCn})
    .mount('#app')
