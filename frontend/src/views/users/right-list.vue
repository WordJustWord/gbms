<template>
  <div class="app-container">
    <div class="right-list">
      <div class="filter-container">
        <el-input
          v-model="listQuery.keyword"
          placeholder="关键字"
          style="width: 350px;"
          class="filter-item"
          @keyup.enter.native="handleFilter"
        />
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
        <el-button
          class="filter-item"
          style="margin-left: 10px;"
          type="primary"
          icon="el-icon-edit"
          @click="handleCreate"
        >新增</el-button>
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
        <el-table-column label="权限名称" width="250px" align="center">
          <template slot-scope="{row}">
            <span>{{ row.rightname }}</span>
          </template>
        </el-table-column>
        <el-table-column label="权限编号" width="150px" align="center">
          <template slot-scope="{row}">
            <span>{{ row.rightnum }}</span>
          </template>
        </el-table-column>
        <el-table-column label="权限详情" min-width="280px" align="center">
          <template slot-scope="{row}">
            <span>{{ row.description }}</span>
          </template>
        </el-table-column>
        <el-table-column label="权限状态" width="150px" align="center">
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
          :model="right"
          label-position="left"
          label-width="70px"
          style="width: 400px; margin: 0 auto;"
        >
          <el-form-item label="权限名" prop="rightname">
            <el-input v-model="right.rightname" placeholder="请输入权限名" />
          </el-form-item>
          <el-form-item label="权限编号" prop="rightnum">
            <el-input v-model="right.rightnum" placeholder="请输入权限编号" />
          </el-form-item>
          <el-form-item label="权限描述" prop="email">
            <el-input v-model="right.description" type="textarea" placeholder="请输入权限描述" />
          </el-form-item>
          <el-form-item label="权限状态">
            <el-select v-model="right.status" class="filter-item" placeholder="请选择权限状态">
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
        update: "编辑权限",
        create: "新增权限"
      },
      rules: [],
      dialogFormVisible: false,
      list: [
        {
          id: 1,
          rightname: "管理员",
          rightnum: "R0001",
          description: "系统管理员的角色",
          status: { id: 1, title: "激活中", key: 1 }
        }
      ],
      right: {},
      listQuery: {
        keyword: "",
        category: undefined,
        status: undefined,
        page: 1,
        limit: 15
      },
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