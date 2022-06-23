<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="姓名" prop="rdname">
        <el-input
          v-model="queryParams.rdname"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号码" prop="rdcertify">
        <el-input
          v-model="queryParams.rdcertify"
          placeholder="请输入身份证号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['service:reader:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['service:reader:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['service:reader:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['service:reader:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="readerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="姓名" align="center" prop="rdname" />
<!--      <el-table-column label="密码" align="center" prop="rdpasswd" />-->
      <el-table-column label="身份证号码" align="center" prop="rdcertify" width="180"/>
<!--      <el-table-column label="办证人员" align="center" prop="operator" />-->
      <el-table-column label="开户馆" align="center" prop="rdlib" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.rdlib === 'SJZTSG' ? '石家庄市图书馆' : scope.row.rdlib }}</span>
        </template>
      </el-table-column>
      <el-table-column label="读者类型" align="center" prop="rdtype" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.rdtype === '008' ? '新读者免押金' : scope.row.rdtype }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号码" align="center" prop="rdloginid" />
      <el-table-column label="email" align="center" prop="rdemail" width="180"/>
      <el-table-column label="性别" align="center" prop="rdsex" />
      <el-table-column label="住址" align="center" prop="rdaddress" width="200"/>
      <el-table-column label="职业" align="center" prop="rdsort2" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['service:reader:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['service:reader:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改图书馆读者证对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="姓名" prop="rdname">
          <el-input v-model="form.rdname" placeholder="请输入姓名" />
        </el-form-item>
<!--        <el-form-item label="密码" prop="rdpasswd">-->
<!--          <el-input v-model="form.rdpasswd" placeholder="请输入密码" />-->
<!--        </el-form-item>-->
        <el-form-item label="身份证号码" prop="rdcertify">
          <el-input v-model="form.rdcertify" placeholder="请输入身份证号码" />
        </el-form-item>
<!--        <el-form-item label="办证人员" prop="operator">-->
<!--          <el-input v-model="form.operator" placeholder="请输入办证人员" />-->
<!--        </el-form-item>-->
        <el-form-item label="开户馆" prop="rdlib">
          <el-input v-model="form.rdlib" placeholder="请输入开户馆" />
        </el-form-item>
        <el-form-item label="手机号码" prop="rdloginid">
          <el-input v-model="form.rdloginid" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="email" prop="rdemail">
          <el-input v-model="form.rdemail" placeholder="请输入email" />
        </el-form-item>
        <el-form-item label="住址" prop="rdaddress">
          <el-input v-model="form.rdaddress" placeholder="请输入住址" />
        </el-form-item>
        <el-form-item label="职业" prop="rdsort2">
          <el-input v-model="form.rdsort2" placeholder="请输入职业" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listReader, getReader, delReader, addReader, updateReader } from "@/api/service/reader";

export default {
  name: "Reader",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 图书馆读者证表格数据
      readerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rdname: null,
        rdcertify: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询图书馆读者证列表 */
    getList() {
      this.loading = true;
      listReader(this.queryParams).then(response => {
        this.readerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        rdname: null,
        rdpasswd: null,
        rdcertify: null,
        operator: null,
        rdlib: null,
        rdtype: null,
        rdloginid: null,
        rdemail: null,
        rdsex: null,
        rdaddress: null,
        rdsort2: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图书馆读者证";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getReader(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图书馆读者证";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReader(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReader(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除图书馆读者证编号为"' + ids + '"的数据项？').then(function() {
        return delReader(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('service/reader/export', {
        ...this.queryParams
      }, `reader_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
