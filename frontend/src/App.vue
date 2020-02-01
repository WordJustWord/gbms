<template>
  <div id="app">
    <div class="main">
      <div class="left">
        <div class="logo">
          <span>PIGMO综合管理后台</span>
        </div>
        <div class="menu">
          <Menu theme="dark" :active-name="active" :open-names="[open]" accordion>
            <Submenu name="user">
              <template slot="title">
                <Icon type="ios-paper" />用户管理
              </template>
              <MenuItem name="userlist" to="/user/userlist">用户列表</MenuItem>
              <MenuItem name="/userrole">用户角色</MenuItem>
              <MenuItem name="/userright">用户权限</MenuItem>
            </Submenu>
            <Submenu name="sysem">
              <template slot="title">
                <Icon type="ios-people" />系统信息
              </template>
              <MenuItem name="2-1">全局设置</MenuItem>
              <MenuItem name="2-2">系统字典</MenuItem>
              <MenuItem name="2-3">系统日志</MenuItem>
            </Submenu>
          </Menu>
        </div>
      </div>
      <div class="right">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      menuActive: "/dashboard"
    };
  },
  computed: {
    active() {
      return (this.menuActive = this.$route.name);
    },
    open() {
      let name = this.$route.path.split("/")[1];
      return name;
    }
  }
};
</script>

<style lang="less">
.main {
  height: 100vh;
  display: flex;
  .left {
    flex-basis: 200px;
    height: 100%;
    background-color: #34302d;
    .logo {
      height: 60px;
      color: #6db33f;
      line-height: 60px;
      font-size: 1.3rem;
      font-weight: bold;
      text-align: center;
    }
    .menu {
      height: calc(100% - 60px);
    }
  }
  .right {
    // flex-basis: calc(100% - 200px);
    padding: 20px;
    background-color: #e3e3e3;
    width: 100%;
  }
}
</style>
