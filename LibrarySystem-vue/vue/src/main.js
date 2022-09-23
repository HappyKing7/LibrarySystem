import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import 'element-plus/theme-chalk/index.css'
import ElementPlus from 'element-plus'
import { ElMessage }  from "element-plus";

require("./mock.js")

const app = createApp(App)
app.config.globalProperties.$axios = axios
app.use(router)
app.use(store)
app.use(ElementPlus)
app.use(ElMessage)
app.mount('#app')


//axios拦截器
axios.create(
    {
        timeout:5000,
        headers:{
            'Content-type':"application/json;charset=utf-8"
        }
    }
)

axios.interceptors.request.use(config=>{
    config.headers['Authorization'] = localStorage.getItem("token")
    return config
})

axios.interceptors.response.use(response=>{
        let res = response.data

        if(res.code == 200){
            return response
        }else {
            ElMessage.error(res.msg?res.msg:'系统异常')
            return  Promise.reject(response.data.msg)
        }
    },error => {
        if(error.response.data){
            error.massage = error.response.data.msg
        }
        if(error.response.status === 401){
            router.push("/login")
        }

        ElMessage.error(error.massage,{duration:3000 })
        return Promise.reject(error)
    }
)

