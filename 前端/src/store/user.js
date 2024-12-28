import {defineStore} from 'pinia';
import {removeToken, setToken} from '@/pages/user/utils/token-utils';
import {getLogin, getUserInfo, registerApi, isUserOverdue} from '@/pages/user/utils/api.js'
import CryptoJS from 'crypto-js'

export const useUserStore = defineStore('user', {
    state: () => ({
        // 当前用户信息
        currentUser: null, // 当前登录用户
        isAuthenticated: false, // 是否已认证
    }),

    actions: {
        // 用户登录
        async login(userForm) {
            // 发送登录的请求
            const encryptUserForm = this.encryptPassword(userForm);
            const result = await getLogin(encryptUserForm)
            // 请求成功后, 取出token保存到local中
            await setToken(result.token)
            this.isAuthenticated = true
            this.currentUser = (await getUserInfo()).account
        },

        // 用户注册
        async register(registerForm) {
            // 移除确认密码属性
            const {confirmPwd, ...registerData} = registerForm;
            // 对密码进行加密
            const encryptedRegisterForm = this.encryptPassword(registerData);
            // 发送注册的请求
            await registerApi(encryptedRegisterForm);
            // 注册成功自动登录
            this.isAuthenticated = true;
            const result = await getLogin(encryptedRegisterForm);
            await setToken(result.token)
            this.isAuthenticated = true
            this.currentUser = (await getUserInfo()).account
        },

        // 检验登录状态是否过期
        async checkToken() {
            try {
                const res = await isUserOverdue()
                this.isAuthenticated = true;
                this.currentUser = (await getUserInfo()).account
                return true;
            } catch (e) {
                this.isAuthenticated = false;
                this.currentUser = null
                return false;
            }
        },

        // 用户登出
        logout() {
            removeToken();
            this.currentUser = null;
            this.isAuthenticated = false;
        },

        // 加密函数：接收表单并将密码加密
        encryptPassword(form) {
            const {password} = form;
            // 对密码进行 SHA256 加密
            const encryptedPassword = CryptoJS.SHA256(password).toString(CryptoJS.enc.Base64);
            // 返回加密后的密码
            return {...form, password: encryptedPassword};
        }
    },

    getters: {
        // 获取是否是管理员
        isAdmin: (state) => state.currentUser?.role === 'admin',

        // 获取是否是学生
        isStudent: (state) => state.currentUser?.role === 'student',

        // 获取是否是教师
        isTeacher: (state) => state.currentUser?.role === 'teacher',
        // 获取当前账户信息
        getAccount: (state) => state.currentUser,
    },
});
