# szu-web-oj
深圳大学 Web期末大作业
## 项目简介
项目采用vue3.0 + vite + element-plus + pinia + vue-router + eslint 实现
## 项目结构
```│  App.vue
│  main.js
│
├─assets    //存放项目静态资源(图片/图标)的目录
│      base.css
│      logo.png
│      logo.svg
│      main.css
│
├─components    //存放项目组件的目录(方便全局复用)
│  │  Header.vue
│  │  HelloWorld.vue
│  │  TheWelcome.vue
│  │  WelcomeItem.vue
│  │
│  └─icons    //这个是新项目自带的，不用管它
│          IconCommunity.vue
│          IconDocumentation.vue
│          IconEcosystem.vue
│          IconSupport.vue
│          IconTooling.vue
│
├─pages
│  ├─about
│  │      index.vue    // 关于我们页面
│  │
│  ├─college
│  │      index.vue    //联盟院校页面
│  │
│  ├─course
│  │      index.vue    //课程页面
│  │
│  ├─home
│  │      index.vue    //首页
│  │
│  ├─student
│  │      index.vue    //学生模块的页面
│  │
│  ├─teacher
│  │      index.vue    //教师模块的页面
│  │
│  └─user
│          index.vue    //用户模块的页面
│
├─router
│      pinia.js    //路由配置文件
│
└─store
        user.js    //pinia仓库内容
```
上述关于模块的文件/目录都可以按照自己要做的内容更改，但是要保持目录结构的一致性，方便后期维护。
## 依赖安装
项目中的依赖已经配置好了，但是在没有环境的电脑上仍然需要自己安装  
包管理器安装(不行就自己搜，npm国内可能要使用淘宝镜像源)  
[npm官方文档](https://nodejs.cn/npm/getting-started/configuring-your-local-environment/downloading-and-installing-node-js-and-npm/)  
[yarn官方文档](https://yarn.bootcss.com/docs/install/#windows-stable)  
安装依赖  
```
npm install  
//或者
yarn install
```
## 项目运行
```
npm run dev
//或者
yarn dev
```
然后idea或者vscode会弹出来一个链接，点击即可在浏览器打开项目  
## element-plus的使用
element-plus是用于快速配置前端页面的  
[element-plus官方文档](https://element-plus.gitee.io/#/zh-CN/component/installation)
## pinia的使用
pinia是用于管理vue3.0项目中的状态的(就是保存数据到前端本地)  
[pinia官方文档](https://pinia.esm.dev/introduction.html)

