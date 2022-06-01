<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
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
          v-hasPermi="['service:spots:add']"
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
          v-hasPermi="['service:spots:edit']"
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
          v-hasPermi="['service:spots:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['service:spots:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="spotsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="景点ID" align="center" prop="spotsId" />
      <el-table-column label="相册" align="center" prop="photoAlbum" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.photoAlbum" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="路由地址" align="center" prop="path" />
      <el-table-column label="地址" align="center" prop="address" />
      <el-table-column label="服务热线" align="center" prop="phone" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['service:spots:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['service:spots:remove']"
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

    <!-- 添加或修改旅游景点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="相册">
          <image-upload v-model="form.photoAlbum"/>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="路由地址" prop="path">
          <el-input v-model="form.path" placeholder="请输入路由地址" />
        </el-form-item>
        <el-form-item label="按钮组" prop="buttonGroup">
          <el-input v-model="form.buttonGroup" type="textarea" placeholder="请输入按钮组" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input v-model="form.longitude" placeholder="请输入经度" />
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input v-model="form.latitude" placeholder="请输入纬度" />
        </el-form-item>
        <el-form-item label="官方通知">
          <editor v-model="form.notice" :min-height="192"/>
        </el-form-item>
        <el-form-item label="服务热线" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入服务热线" />
        </el-form-item>
        <el-form-item label="图文介绍">
          <editor v-model="form.introduce" :min-height="192"/>
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
import { listSpots, getSpots, delSpots, addSpots, updateSpots } from "@/api/service/spots";

export default {
  name: "Spots",
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
      // 旅游景点表格数据
      spotsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        photoAlbum: [
          { required: true, message: "相册不能为空", trigger: "blur" }
        ],
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        address: [
          { required: true, message: "地址不能为空", trigger: "blur" }
        ],
        longitude: [
          { required: true, message: "经度不能为空", trigger: "blur" }
        ],
        latitude: [
          { required: true, message: "纬度不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询旅游景点列表 */
    getList() {
      this.loading = true;
      listSpots(this.queryParams).then(response => {
        this.spotsList = response.rows;
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
        spotsId: null,
        photoAlbum: null,
        title: null,
        path: null,
        buttonGroup: null,
        address: null,
        longitude: null,
        latitude: null,
        notice: null,
        phone: null,
        introduce: null,
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
      this.ids = selection.map(item => item.spotsId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加旅游景点";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const spotsId = row.spotsId || this.ids
      getSpots(spotsId).then(response => {
        this.form = response.data;
        this.form.buttonGroup = JSON.stringify(JSON.parse(this.form.buttonGroup), null, 2)
        this.open = true;
        this.title = "修改旅游景点";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.spotsId != null) {
            updateSpots(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpots(this.form).then(response => {
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
      const spotsIds = row.spotsId || this.ids;
      this.$modal.confirm('是否确认删除旅游景点编号为"' + spotsIds + '"的数据项？').then(function() {
        return delSpots(spotsIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('service/spots/export', {
        ...this.queryParams
      }, `spots_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
