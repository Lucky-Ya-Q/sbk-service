<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="补卡ID" prop="bukaId">
        <el-input
          v-model="queryParams.bukaId"
          placeholder="请输入补卡ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否已补卡" prop="sfybk">
        <el-select v-model="queryParams.sfybk" placeholder="请选择是否已补卡" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="姓名" prop="xm">
        <el-input
          v-model="queryParams.xm"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件号码" prop="zjhm">
        <el-input
          v-model="queryParams.zjhm"
          placeholder="请输入证件号码"
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
          v-hasPermi="['service:customer:add']"
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
          v-hasPermi="['service:customer:edit']"
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
          v-hasPermi="['service:customer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['service:customer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="customerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="补卡ID" align="center" prop="bukaId" />
      <el-table-column label="是否已补卡" align="center" prop="sfybk">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.sfybk"/>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center" prop="xm" />
      <el-table-column label="证件种类" align="center" prop="zjzl" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="证件有效期开始时间" align="center" prop="zjyxqkssj" />
      <el-table-column label="证件有效期结束时间" align="center" prop="zjyxqjssj" />
      <el-table-column label="性别" align="center" prop="xb" />
      <el-table-column label="国籍" align="center" prop="gj" />
      <el-table-column label="民族" align="center" prop="mz" />
      <el-table-column label="出生日期" align="center" prop="csrq" />
      <el-table-column label="职业" align="center" prop="zy" />
      <el-table-column label="联系方式" align="center" prop="lxfs" />
      <el-table-column label="地址" align="center" prop="dz" />
      <el-table-column label="银行名称" align="center" prop="yhmc" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['service:customer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['service:customer:remove']"
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

    <!-- 添加或修改客户信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="补卡ID" prop="bukaId">
          <el-input v-model="form.bukaId" placeholder="请输入补卡ID" />
        </el-form-item>
        <el-form-item label="是否已补卡" prop="sfybk">
          <el-select v-model="form.sfybk" placeholder="请选择是否已补卡">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="xm">
          <el-input v-model="form.xm" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="证件种类" prop="zjzl">
          <el-input v-model="form.zjzl" placeholder="请输入证件种类" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" placeholder="请输入证件号码" />
        </el-form-item>
        <el-form-item label="证件有效期开始时间" prop="zjyxqkssj">
          <el-input v-model="form.zjyxqkssj" placeholder="请输入证件有效期开始时间" />
        </el-form-item>
        <el-form-item label="证件有效期结束时间" prop="zjyxqjssj">
          <el-input v-model="form.zjyxqjssj" placeholder="请输入证件有效期结束时间" />
        </el-form-item>
        <el-form-item label="性别" prop="xb">
          <el-input v-model="form.xb" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="国籍" prop="gj">
          <el-input v-model="form.gj" placeholder="请输入国籍" />
        </el-form-item>
        <el-form-item label="民族" prop="mz">
          <el-input v-model="form.mz" placeholder="请输入民族" />
        </el-form-item>
        <el-form-item label="出生日期" prop="csrq">
          <el-input v-model="form.csrq" placeholder="请输入出生日期" />
        </el-form-item>
        <el-form-item label="职业" prop="zy">
          <el-input v-model="form.zy" placeholder="请输入职业" />
        </el-form-item>
        <el-form-item label="联系方式" prop="lxfs">
          <el-input v-model="form.lxfs" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="地址" prop="dz">
          <el-input v-model="form.dz" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="银行名称" prop="yhmc">
          <el-input v-model="form.yhmc" placeholder="请输入银行名称" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listCustomer, getCustomer, delCustomer, addCustomer, updateCustomer } from "@/api/service/customer";

export default {
  name: "Customer",
  dicts: ['sys_yes_no'],
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
      // 客户信息表格数据
      customerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bukaId: null,
        sfybk: null,
        xm: null,
        zjhm: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        xm: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        zjhm: [
          { required: true, message: "证件号码不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询客户信息列表 */
    getList() {
      this.loading = true;
      listCustomer(this.queryParams).then(response => {
        this.customerList = response.rows;
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
        bukaId: null,
        sfybk: null,
        xm: null,
        zjzl: null,
        zjhm: null,
        zjyxqkssj: null,
        zjyxqjssj: null,
        xb: null,
        gj: null,
        mz: null,
        csrq: null,
        zy: null,
        lxfs: null,
        dz: null,
        yhmc: null,
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
      this.title = "添加客户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCustomer(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCustomer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCustomer(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除客户信息编号为"' + ids + '"的数据项？').then(function() {
        return delCustomer(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('service/customer/export', {
        ...this.queryParams
      }, `customer_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
