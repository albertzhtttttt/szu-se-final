import request from "./request.js"

//登录的接口
export const getLogin = (info) => {
    return request.post("account/login", info);
};

//获取用户信息的接口
export const getUserInfo = () => {
    return request.get("account/getAccountInfo");
};

//注册校验的接口  user/checkUserName
export const registerValidateApi = (username) => {
    return request({
        method: "post",
        url: "account/checkUsername",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        },
        data: `username=${username}`
    })
};

// 注册的接口
export const registerApi = (userInfo) => {
    return request.post("account/register", userInfo)
}
// 判断用户登录过期的接口
export const isUserOverdue = () => {
    return request.get("account/checkLogin")
}

// 上传个人信息的接口
export const uploadProfile = (accountForm) => {
    return request.post("account/uploadProfile", accountForm);
}

// 下载个人信息的接口
export const downloadProfile = (uid) => {
    return request.post("account/downloadProfile", uid);
}

// 获取账号状态的接口
export const getStatus = (uid) => {
    // const form = {"uid": uid};
    return request.post("account/getStatus", uid);
}

// 设置账号状态的接口
export const setStatus = (uid, status) => {
    const form = {"uid": uid.uid, "status": status};
    return request.post("account/setStatus", form);
}

// 获取所有账户信息的接口
export const getAllManagementInfo = () => {
    return request.get("account/findAllAccount");
}

// 重置密码的接口
export const resetPassword = (uid, defaultPwd) => {
    const form = {"uid": uid, "password": defaultPwd};
    return request.post("account/resetPassword", form);
}

// 删除账号的接口
export const deleteAccount = (uid) => {
    const form = {"uid": uid};
    return request.post("account/deleteAccount", form);
}

// 根据账户名查询账户ID的接口
export const findUidByUsername = (username) => {
    const form = {"username": username};
    return request.post("account/findUidByUsername", form);
}