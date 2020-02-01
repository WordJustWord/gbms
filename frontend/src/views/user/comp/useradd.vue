<template>
  <div class="user-add">
    <Button type="primary" @click="show">新增用户</Button>
    <Modal title="新增用户" v-model="showModal" class-name="vertical-center-modal">
      <Form ref="formAdd" :model="formItem" :rules="formRules" :label-width="80">
        <FormItem label="用户名" prop="username">
          <Input v-model="formItem.username" placeholder="输入16个字以内的中文或英文"></Input>
        </FormItem>
        <FormItem label="真实姓名">
          <Input v-model="formItem.realname" placeholder="输入身份证/护照上的姓名"></Input>
        </FormItem>
        <FormItem label="电子邮件">
          <Input v-model="formItem.email" placeholder="输入正确的电子邮件地址"></Input>
        </FormItem>
        <FormItem label="联系方式">
          <Input v-model="formItem.telephone" placeholder="输入11位手机号码"></Input>
        </FormItem>
        <FormItem label="用户类型">
          <Select placeholder="用户类型">
            <Option value="0">管理员</Option>
            <Option value="1">普通用户</Option>
          </Select>
        </FormItem>
        <FormItem label="用户状态">
          <Select placeholder="用户状态">
            <Option value="0">未激活</Option>
            <Option value="1">已激活</Option>
            <Option value="2">已注销</Option>
          </Select>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>
<script>
export default {
  data() {
    return {
      showModal: false,
      formItem: {
        username: "",
        realname: "",
        email: "",
        telephone: "",
        usertype: "",
        status: ""
      },
      formRules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    show() {
      this.showModal = true;
    }
  }
};
</script>