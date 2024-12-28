import axios from "axios";
import {ElMessage} from 'element-plus';
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import {getToken} from './token-utils.ts';
// 配置新建一个 axios 实例
const service = axios.create({
    baseURL: "http://8.134.98.31:8080",
    timeout: 50000,
});

// 添加请求拦截器
service.interceptors.request.use((config) => {
    NProgress.start() // 开启进度条
    // 如果有token, 通过请求头携带给后台
    const token = getToken()
    if (token) {
        // config.headers['token'] = token // 报错: headers对象并没有声明有token, 不能随便添加
        (config.headers)['token'] = token
    }
    return config;
});

// 添加响应拦截器
service.interceptors.response.use(
    (response) => {
        NProgress.done() // 关闭进度条

        if (response.data.code !== 200) {
            // 判断响应状态码
            if (response.data.code === 501) return Promise.reject(ElMessage.error("用户名有误"))
            else if (response.data.code === 502) return Promise.reject(ElMessage.error("帐户不存在"))
            else if (response.data.code === 503) return Promise.reject(ElMessage.error("密码有误"))
            else if (response.data.code === 504) return Promise.reject(ElMessage.error("登录已过期"))
            else if (response.data.code === 505) return Promise.reject(ElMessage.error("用户名已占用"))
            else if (response.data.code === 506) return Promise.reject(ElMessage.error("上传失败"))
            else if (response.data.code === 507) return Promise.reject(ElMessage.error("下载失败"))
        } else {
            return response.data.data; /* 返回成功响应数据 */
        }
    },
    (error) => {
        NProgress.done() // 关闭进度条
        return Promise.reject(error.message);
    }
);

export default service;