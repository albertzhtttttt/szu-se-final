import {defineStore} from 'pinia';
import {useUserStore} from './user.js';
import {uploadProfile, downloadProfile} from '@/pages/user/utils/api.js'
import {ElMessage} from "element-plus";

export const useAccountStore = defineStore('account', {
    state: () => ({
        accountData: {
            avatar: 'src/assets/default-avatar.png',
            nickname: '我的昵称',
            signature: '这是我的签名',
            todos: [],
            history: [],
            notes: [],
            favorites: [],
            wrongQuestions: [],
            discussions: [],
        },
    }),
    actions: {
        async saveAccountProfile() {
            const userStore = useUserStore()

            const accountForm = {
                uid: userStore.currentUser.uid,  // 获取当前用户的 uid
                avatar: this.accountData.avatar,  // 当前 avatar 的值
                nickname: this.accountData.nickname,  // 当前 nickname 的值
                signature: this.accountData.signature,  // 当前 signature 的值
            };

            await uploadProfile(accountForm)
            ElMessage.success('上传成功')
        },
        async downloadAccountProfile(uid) {
            try {
                const profileData = await downloadProfile({"uid": uid});
                this.accountData.avatar = profileData.avatar != null ? profileData.avatar : 'src/assets/default-avatar.png';
                this.accountData.nickname = profileData.nickname != null ? profileData.nickname : '我的昵称';
                this.accountData.signature = profileData.signature != null ? profileData.signature : '这是我的签名';
            } catch {
                this.accountData.avatar = 'src/assets/default-avatar.png';
                this.accountData.nickname = '我的昵称';
                this.accountData.signature = '这是我的签名';
            }
        },
    },
});
