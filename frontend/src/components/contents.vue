<template>
  <div class="content-warpper">
    <div class="breadcrumb">
      <Breadcrumb>
        <BreadcrumbItem v-for="item in br" :key="item">{{item}}</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="content">
      <slot></slot>
    </div>
    <div class="footer">
      <p>copyright©️2019 GBMS by pigmo studio 版权所有 翻版必究</p>
    </div>
  </div>
</template>
<script>
export default {
  props: ["br"]
};
</script>
<style lang="less" scoped>
.content-warpper {
  .breadcrumb,
  .content {
    border-radius: 3px;
    padding: 10px;
    background-color: #ffffff;
    margin-bottom: 10px;
  }
  .content {
    min-height: 300px;
  }
  .footer {
    text-align: center;
    p {
      color: #515a6e;
      font-size: 0.75rem;
    }
  }
}
</style>