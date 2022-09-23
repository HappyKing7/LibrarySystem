<template>
  <div style="float: left" v-if="this.userType=='2'">您的借阅信息如下：</div>
  <el-form label-width="100px" class="demo-ruleForm" style="float:left;" inline="true">
    <el-form-item>
      <el-input v-model="key" placeholder="Please input" />
    </el-form-item>
    <el-form-item>
      <el-button @click="findBorrowedInfosByKey()" >搜索</el-button>
    </el-form-item>
  </el-form>
  <br>
  <br>
  <div>
    <el-table :data="ruleForm.data" border stripe style="width: 150%" highlight-current-row>
      <el-table-column prop="borrowed_ID" label="借阅编号" width="250"/>
      <el-table-column prop="user_ID" label="用户账号"  width="250"/>
      <el-table-column prop="book_ID" label="图书编号" width="250"/>
      <el-table-column prop="borrowed_START_DATE" label="借阅开始时间" width="200"/>
      <el-table-column prop="borrowed_END_DATE" label="借阅到期时间" width="200"/>
      <el-table-column prop="borrowed_STATUS" label="借阅状态" width="150"/>
    </el-table>
    <br>
    <!--页码-->
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
  name: "BorrowedInfo",
  data() {
    return {
      ruleForm:{
        current: 1,
        size:10,
        total:0,
        tableData: []
      },
      user_ID:'',
      key:'',
      userType: '',
    }
  },
  methods:{
    handleSizeChange(val) {
      this.ruleForm.size = val
      if(this.key=='')
      {
        this.findBorrowedInfosByUserID()
      }
      else {
        this.findBorrowedInfosByKey()
      }
    },
    handleCurrentChange(val) {
      this.ruleForm.current = val
      if(this.key== '')
      {
        this.findBorrowedInfosByUserID()
      }
      else {
        this.findBorrowedInfosByKey()
      }
    },
    findBorrowedInfosByUserID() {
      this.$axios.post('http://localhost:8087/borrowed/findAllBorrowedInfosByUserID', {
        params:{
          ruleForm:this.ruleForm,
          user_ID:localStorage.getItem("userID")
        }
      },{
      }).then(res => {
        this.ruleForm.data = res.data.data
        this.ruleForm.current = res.data.current
        this.ruleForm.size = res.data.size
        this.ruleForm.total = res.data.total
      })
    },
    findBorrowedInfosByKey(){
      if(this.key == '')
      {
        ElMessage.error("请输入关键字");
        return false
      }
      this.$axios.post('http://localhost:8087/borrowed/findBorrowedInfosByKey',{
        params: {
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
  },
  created() {
    this.userType = localStorage.getItem("userType")
    if(this.userType == '2'){
      this.findBorrowedInfosByUserID();
    }
  }
}
</script>

<style>
  .el-table td,th {
    text-align: center !important;
  }
</style>