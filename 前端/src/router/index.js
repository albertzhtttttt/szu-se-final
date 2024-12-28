import {createRouter, createWebHistory} from 'vue-router';
import {useUserStore} from '../store/user';  // 导入 store


// 路由配置
const routes = [
    // 默认重定向到 /home
    {
        path: '/',
        redirect: '/home',
    },
    {
        path: '/home',
        name: 'homeIndex',
        component: () => import('../pages/home/index.vue'),
        meta: {requiresAuth: false},  // 不需要认证的路由
    },
    {
        path: '/course',
        name: 'courseIndex',
        component: () => import('../pages/course/index.vue'),
        meta: {requiresAuth: true},  // 不需要认证的路由
    },
    {
        path: '/course/detail',
        name: 'courseDetailIndex',
        component: () => import('../pages/courseDetail/index.vue'),
        meta: {requiresAuth: false},  // 不需要认证的路由
    },
    {
        path: '/college',
        name: 'collegeIndex',
        component: () => import('../pages/college/index.vue'),
        meta: {requiresAuth: false},  // 不需要认证的路由
    },
    {
        path: '/about',
        name: 'aboutIndex',
        component: () => import('../pages/about/index.vue'),
        meta: {requiresAuth: false},  // 不需要认证的路由
    },
    {
        path: '/user',
        name: 'userIndex',
        component: () => import('../pages/user/index.vue'),
        meta: {requiresAuth: true},  // 不需要认证的路由
    },
    {
        path: '/student',
        name: 'studentIndex',
        component: () => import('../pages/student/index.vue'),
        meta: {requiresAuth: false},  // 不需要认证的路由
      },
    {
        path: '/exercise',
        name: 'exerciseIndex',
        component: () => import('../pages/exercise/index.vue'),
        meta: {requiresAuth: true},  // 不需要认证的路由
    },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),  // 使用 import.meta.env.BASE_URL 动态获取基础路径
    routes,
});

// 路由守卫：检查是否需要权限认证
router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();  // 使用 store 获取用户认证信息
    await userStore.checkToken();
    const isAuthenticated = userStore.isAuthenticated;  // 有一个 isAuthenticated 标志来判断是否已登录
    const userRole = userStore.currentUser == null ? null : userStore.currentUser.role;  // 假设用户角色存储在 userRole 中
    console.log(userRole);

    // 如果目标路径在 routes 中且不需要认证，直接放行
    const routePaths = routes.map(route => route.path);
    if (routePaths.includes(to.path) && !to.meta.requiresAuth) {
        return next();  // 直接放行
    }
    // 如果目标路径在 routes 中且需要认证，用户认证后跳转
    if (routePaths.includes(to.path) && to.meta.requiresAuth && isAuthenticated) {
        return next();  // 直接放行
    }

    // 如果目标路由需要认证且用户未认证，跳转到首页
    if (to.meta.requiresAuth && !isAuthenticated) {
        return next('/home');
    }

    // 如果用户未认证且当前路径不是 home，跳转到首页
    if (!isAuthenticated && to.path !== '/home') {
        return next('/home');
    }
});

export default router;
