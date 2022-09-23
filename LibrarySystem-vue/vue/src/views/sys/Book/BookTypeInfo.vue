<template>
  <div>
    <div>
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="float:left;" inline="true">
        <el-form-item>
          <el-input v-model="key" placeholder="Please input" />
        </el-form-item>
        <el-form-item>
          <el-button @click="findBookTypeInfos()" >搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="insertDialogVisible = true" v-if="this.userType=='0'">新增</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="ruleForm.data" border stripe style="width: 150%" @cell-click="selectClick"
                highlight-current-row>
        <el-table-column prop="book_TYPE_ID" label="图书类型编号" width="600" v-if="this.userType=='0'"/>
        <el-table-column prop="book_TYPE_ID" label="图书类型编号" width="650" v-if="this.userType!='0'"/>

        <el-table-column prop="book_TYPE_NAME" label="图书类型姓名" width="600" v-if="this.userType=='0'"/>
        <el-table-column prop="book_TYPE_NAME" label="图书类型姓名" width="650" v-if="this.userType!='0'"/>

         <el-table-column label="操作" width="100" v-if="this.userType=='0'">
          <el-popconfirm title="确定要删除这条记录吗？" @confirm="deleteBookTypeInfo()">
            <template #reference>
              <el-button v-if="this.userType=='0'">删除</el-button>
            </template>
          </el-popconfirm>
        </el-table-column>

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

      <el-dialog v-model="insertDialogVisible" title="请输入新增图书类型信息" width="30%">
        <el-form :model="insertForm"  :rules="insertrules" ref="insertForm" label-width="120px" class="demo-ruleForm">
          <el-form-item label="图书类型编号" prop="bookTypeID" style="width: 380px">
            <el-input v-model="insertForm.bookTypeID" />
          </el-form-item>

          <el-form-item label="图书类型名称" prop="bookTypeName" style="width: 380px">
            <el-input v-model="insertForm.bookTypeName" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitInsertForm('insertForm')">新增</el-button>
            <el-button @click="resetInsertForm('insertForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";

export default {
  name: "BookTypeInfo",
  data(){
    return{
      insertDialogVisible: false,
      ruleForm:{
        current: 1,
        size:10,
        total:0,
        tableData: []
      },
      insertForm:{
        bookTypeID: '',
        bookTypeName: '',
      },
      insertrules:{
        bookTypeID: [
          { required: true, message: '请输入图书类型编号', trigger: 'blur',},
          { max: 1, message: '图书类型编号长度不能超过1', trigger: 'blur' },
        ],
        bookTypeName: [
          { required: true, message: '请输入图书类型名称', trigger: 'blur',},
        ],
      },
      key: '',
      userType: "",
      book_TYPE_ID: ''
    }
  },
  methods:{
    selectClick(row, column, cell, event){
      console.log(row)
      this.book_TYPE_ID = row.book_TYPE_ID
    },
    getAllBookTypeInfos() {
        this.$axios.post('http://localhost:8087/booktype/getBookTypeInfos', this.ruleForm,{
      }).then(res => {
        this.ruleForm.data = res.data.data
        this.ruleForm.current = res.data.current
        this.ruleForm.size = res.data.size
        this.ruleForm.total = res.data.total
      })
    },
    findBookTypeInfos(){
      if(this.key == '')
      {
        ElMessage.error("请输入关键字");
        return false
      }
      this.$axios.post('http://localhost:8087/booktype/findBookTypeInfosByKey',{
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
    handleSizeChange(val) {
      this.ruleForm.size = val
      if(this.key == '')
      {
        this.getAllBookTypeInfos()
      }
      else {
        this.findBookTypeInfos()
      }
    },
    handleCurrentChange(val) {
      this.ruleForm.current = val
      if(this.key == '')
      {
        this.getAllBookTypeInfos()
      }
      else {
        this.findBookInfos()
      }
    },
    submitInsertForm(formName){
      this.$refs[formName].validate((valid) => {
        if(valid)
        {
          this.$axios.post('http://localhost:8087/booktype/insertBookTypeInfo',this.insertForm,{
          }).then(res => {
            if(res.data.code == 200) {
              ElMessage.success(res.data.msg)
              this.insertDialogVisible = false
              this.resetInsertForm('insertForm')
              this.getAllBookTypeInfos()
            }else{
              ElMessage.error(res.data.msg)
            }
          })
        }
      })
    },
    deleteBookTypeInfo(){
      if(this.book_TYPE_ID == '')
      {
        ElMessage.error("请先选择数据");
        return false
      }
      else{
        this.$axios.post('http://localhost:8087/booktype/deleteBookTypeInfo',{
          params: {
            book_TYPE_ID:this.book_TYPE_ID
          }
        },{
        }).then(res => {
          if(res.data.code == 200) {
            ElMessage.success(res.data.msg)
            this.getAllBookTypeInfos()
          }else{
            ElMessage.error(res.data.msg)
          }
        })
      }
    },
    resetInsertForm(formName){
      this.$refs[formName].resetFields();
    },
  },
  created() {
    this.userType = localStorage.getItem("userType")
    this.getAllBookTypeInfos()
  }
}
</script>

<style>
.el-table td,th {
  text-align: center !important;
}
</style>



