<template>
  <div>
   <el-form label-width="100px" class="demo-ruleForm" style="float:left;" inline="true">
      <el-form-item>
        <el-input v-model="key" placeholder="Please input" />
      </el-form-item>
      <el-form-item>
        <el-button @click="findUserInfoByKey()" >搜索</el-button>
        <el-button @click="getAllUserInfos()" >重置</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="ruleForm.data" border stripe style="width: 150%" highlight-current-row>
      <el-table-column prop="user_TYPE" label="用户类型" width="100"/>
      <el-table-column prop="user_ID" label="账号" width="200"/>
      <el-table-column prop="user_NAME" label="用户名"  width="250"/>
      <el-table-column prop="user_GENDER" label="性别" width="150"/>
      <el-table-column prop="user_PSPT_ID" label="身份证号码" width="200"/>
      <el-table-column prop="user_PHONE" label="用户手机号" width="150"/>
      <el-table-column prop="user_EMAIL" label="邮箱" width="150"/>
      <el-table-column prop="user_STATUS" label="用户状态" width="100"/>
    </el-table>
    <br>

    <el-pagination style="float: right;display: flex;" justify-content="right"
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   layout="total, sizes, prev, pager, next, jumper"
                   :current-page="ruleForm.current"
                   :page-sizes="[10, 20, 50, 100]"
                   :page-size="ruleForm.size"
                   :total="ruleForm.total">
    </el-pagination>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";

export default {
  name: "UserManga",
  data() {
    return {
      ruleForm:{
        current: 1,
        size:10,
        total:0,
        tableData: []
      },
      key:'',
    }
  },
  methods:{
    getAllUserInfos() {
      this.key=""
      this.$axios.post('http://localhost:8087/user/getAllUserInfos', this.ruleForm,{
      }).then(res => {
        this.ruleForm.data = res.data.data
        this.ruleForm.current = res.data.current
        this.ruleForm.size = res.data.size
        this.ruleForm.total = res.data.total
      })
    },
    findUserInfoByKey(){
      if(this.key == '')
      {
        ElMessage.error("请输入关键字");
        return false
      }
      this.$axios.post('http://localhost:8087/user/findUserInfoByKey', {
        params:{
          ruleForm:this.ruleForm,
          key:this.key
        }
      },{
      }).then(res => {
        this.ruleForm.data = res.data.data
        this.ruleForm.current = res.data.current
        this.ruleForm.size = res.data.size
        this.ruleForm.total = res.data.total
      })
    },
    handleSizeChange(val) {
      this.ruleForm.size = val
      this.getAllUserInfos()
    },
    handleCurrentChange(val) {
      this.ruleForm.current = val
      this.getAllUserInfos()
    },
  },
  created() {
    this.getAllUserInfos()
  }
}
</script>

<style>
  .el-table td,th {
    text-align: center !important;
  }
</style>