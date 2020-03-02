<template>
  <div class="app-container">
    <div class="user-list">
      <div class="filter-container">
        <el-input
          v-model="listQuery.keyword"
          placeholder="关键字"
          style="width: 350px;"
          class="filter-item"
          @keyup.enter.native="handleFilter"
        />
        <el-select
          v-model="listQuery.category"
          placeholder="用户类别"
          clearable
          style="width: 200px"
          class="filter-item"
        >
          <el-option
            v-for="item in categoryOptions"
            :key="item.id"
            :label="item.title"
            :value="item.key"
          />
        </el-select>
        <el-select
          v-model="listQuery.status"
          placeholder="用户状态"
          clearable
          class="filter-item"
          style="width: 200px"
        >
          <el-option
            v-for="item in statusOptions"
            :key="item.id"
            :label="item.title"
            :value="item.key"
          />
        </el-select>
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
        <el-button
          class="filter-item"
          style="margin-left: 10px;"
          type="primary"
          icon="el-icon-edit"
          @click="handleCreate"
        >新增</el-button>
        <el-button
          :loading="downloadLoading"
          class="filter-item"
          type="primary"
          icon="el-icon-download"
          @click="handleDownload"
        >导出</el-button>
      </div>

      <el-table
        :key="tableKey"
        v-loading="listLoading"
        :data="list"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column label="ID" prop="id" align="center" width="80">
          <template slot-scope="{row}">
            <span>{{ row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="用户名" width="250px" align="center">
          <template slot-scope="{row}">
            <span>{{ row.username }}</span>
          </template>
        </el-table-column>
        <el-table-column label="姓名" min-width="150px">
          <template slot-scope="{row}">
            <span>{{ row.realname }}</span>
          </template>
        </el-table-column>
        <el-table-column label="邮箱" width="280px" align="center">
          <template slot-scope="{row}">
            <span>{{ row.email }}</span>
          </template>
        </el-table-column>
        <el-table-column label="用户类型" width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{ row.category.title }}</span>
          </template>
        </el-table-column>
        <el-table-column label="用户状态" width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{ row.status.title }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="230"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="{row,$index}">
            <el-button
              v-if="row.status.key != 2"
              type="text"
              size="mini"
              @click="handleEdit(row)"
            >编辑</el-button>
            <el-popconfirm title="确定注销吗？">
              <el-button
                v-if="row.status.key != 2"
                type="text"
                size="mini"
                @click="handleDel(row)"
                slot="reference"
              >注销</el-button>
            </el-popconfirm>
            <el-popconfirm title="确定重置吗？">
              <el-button
                v-if="row.status.key != 2"
                type="text"
                size="mini"
                @click="handleDel(row)"
                slot="reference"
              >重置密码</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="listQuery.page"
        :limit.sync="listQuery.limit"
        @pagination="getList"
      />

      <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
        <el-form
          ref="dataForm"
          :rules="rules"
          :model="user"
          label-position="left"
          label-width="70px"
          style="width: 400px; margin: 0 auto;"
        >
          <el-form-item label="用户名" prop="username">
            <el-input v-model="user.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="真实姓名" prop="realname">
            <el-input v-model="user.realname" placeholder="请输入姓名" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="user.email" placeholder="请输入邮箱" />
          </el-form-item>
          <el-form-item label="用户类型">
            <el-select v-model="user.category" class="filter-item" placeholder="请选择用户类型">
              <el-option
                v-for="item in categoryOptions"
                :key="item.id"
                :label="item.title"
                :value="item.key"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="用户状态">
            <el-select v-model="user.status" class="filter-item" placeholder="请选择用户状态">
              <el-option
                v-for="item in statusOptions"
                :key="item.id"
                :label="item.title"
                :value="item.key"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">提交</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import Pagination from "@/components/Pagination"; // secondary package based on el-pagination
export default {
  data() {
    return {
      tableKey: 0,
      downloadLoading: false,
      listLoading: false,
      dialogStatus: undefined,
      total: 1,
      textMap: {
        update: "编辑用户",
        create: "新增用户"
      },
      rules: [],
      dialogFormVisible: false,
      list: [
        {
          id: 1,
          username: "shengnan",
          realname: "张胜男",
          email: "zhangshengnan@pigmo.com",
          category: { id: 1, title: "普通用户", key: 0 },
          status: { id: 1, title: "激活中", key: 1 }
        }
      ],
      user: {},
      listQuery: {
        keyword: "",
        category: undefined,
        status: undefined,
        page: 1,
        limit: 15
      },
      categoryOptions: [
        { id: 0, key: 1, title: "普通用户" },
        { id: 1, key: 2, title: "管理员" },
        { id: 2, key: 3, title: "其他类型" }
      ],
      statusOptions: [
        { id: 0, key: 1, title: "未激活" },
        { id: 1, key: 2, title: "激活中" },
        { id: 2, key: 3, title: "已注销" }
      ]
    };
  },
  methods: {
    handleFilter() {},
    handleCreate() {
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    handleDownload() {},
    handleEdit(id) {
      //通过id查询到用户
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    handleDel(row) {},
    getList() {},
    createData() {},
    updateData() {}
  },
  components: {
    Pagination
  }
};
</script>