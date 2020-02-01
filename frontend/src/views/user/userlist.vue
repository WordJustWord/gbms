<template>
  <Content :br="br">
    <div class="user-list">
      <Row>
        <Col span="18">
          <Form :inline="true">
            <FormItem style="width:120px">
              <Select placeholder="用户类型">
                <Option value="-1">所有类型</Option>
                <Option value="0">管理员</Option>
                <Option value="1">普通用户</Option>
              </Select>
            </FormItem>
            <FormItem style="width:300px">
              <Input placeholder="输入用户名/姓名/邮箱搜索"></Input>
            </FormItem>
            <FormItem style="width:300px">
              <Button type="primary">过滤</Button>
            </FormItem>
          </Form>
        </Col>
        <Col span="6" style="text-align:right">
          <UserAdd></UserAdd>
        </Col>
      </Row>
    </div>
    <div class="list-data">
      <Table stripe :columns="columns" :data="listData">
        <template slot-scope="{ row }" slot="usertype">
          <span>{{row.usertype == 0?"管理员":"普通用户"}}</span>
        </template>
        <template slot-scope="{ row }" slot="status">
          <span>{{row.status == 0?"未激活":(row.status == 1?"已激活":"已注销")}}</span>
        </template>
        <template slot-scope="{ row }" slot="opt">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(row.id)">编辑</Button>
          <Button type="error" size="small" @click="remove(row.id)">删除</Button>
        </template>
      </Table>
    </div>
  </Content>
</template>
<script>
import Content from "../../components/contents";
import UserAdd from "./comp/useradd";

const col = [
  {
    title: "ID",
    key: "id",
    width: 50
  },
  {
    title: "用户名",
    key: "username"
  },
  {
    title: "姓名",
    key: "realname"
  },
  {
    title: "邮箱",
    key: "email",
    width: 220
  },
  {
    title: "联系电话",
    key: "telephone"
  },
  {
    title: "用户类型",
    key: "usertype",
    slot: "usertype"
  },
  {
    title: "用户状态",
    key: "status",
    slot: "status"
  },
  {
    title: "操作",
    slot: "opt"
  }
];

export default {
  data() {
    return {
      br: ["用户管理", "用户列表"],
      columns: col,
      listData: [
        {
          id: 1,
          username: "zhangsan",
          realname: "张三",
          email: "zhangsan@pigmo.com",
          telephone: "18112345678",
          usertype: 1,
          status: 1
        }
      ]
    };
  },
  components: {
    Content,
    UserAdd
  },
  methods: {
    show(id) {
      console.log("编辑" + id);
    },
    remove(id) {
      console.log("删除" + id);
    }
  }
};
</script>