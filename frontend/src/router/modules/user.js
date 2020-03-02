/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const userRouter = {
    path: '/user',
    component: Layout,
    redirect: 'noRedirect',
    name: 'User',
    meta: {
        title: '用户管理',
        icon: 'user'
    },
    children: [
        {
            path: 'list',
            component: () => import('@/views/users/user-list'),
            name: 'UserList',
            meta: { title: '用户列表', noCache: true }
        },
        {
            path: 'role',
            component: () => import('@/views/users/role-list'),
            name: 'RoleList',
            meta: { title: '角色列表', noCache: true }
        },
    ]
}

export default userRouter
