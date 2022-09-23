<template>
  <div>
    <div>
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="float:left;" inline="true">
        <el-form-item>
          <el-input v-model="key" placeholder="Please input" />
        </el-form-item>
        <el-form-item>
          <el-button @click="findAuthorInfos()" >搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="insertDialogVisible = true" v-if="this.userType=='0'">新增</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="ruleForm.data" border stripe style="width: 150%" @cell-click="selectClick"
                highlight-current-row >
        <el-table-column prop="author_ID" label="作者编号" width="600" v-if="this.userType=='0'"/>
        <el-table-column prop="author_ID" label="作者编号" width="650" v-if="this.userType!='0'"/>

        <el-table-column prop="author_NAME" label="作者姓名" width="600" v-if="this.userType=='0'"/>
        <el-table-column prop="author_NAME" label="作者姓名" width="650" v-if="this.userType!='0'"/>
        <el-table-column label="操作" width="100" v-if="this.userType=='0'">
          <el-popconfirm title="确定要删除这条记录吗？" @confirm="deleteAuthorInfo()" >
            <template #reference>
              <el-button >删除</el-button>
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

      <el-dialog v-model="insertDialogVisible" title="请输入新增作者信息" width="30%">
        <el-form :model="insertForm"  :rules="insertrules" ref="insertForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="作者编号" prop="authorID" style="width: 380px">
            <el-input v-model="insertForm.authorID" />
          </el-form-item>

          <el-form-item label="作者名称" prop="authorName" style="width: 380px">
            <el-input v-model="insertForm.authorName" />
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
  name: "AuthorInfo",
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
        authorID: '',
        authorName: '',
      },
      insertrules:{
        authorID: [
          { required: true, message: '请输入作者编号', trigger: 'blur',},
        ],
        authorName: [
          { required: true, message: '请输入作者名称', trigger: 'blur',},
        ],
      },
      key: '',
      author_ID: '',
      userType:''
    }
  },
  methods:{
    selectClick(row, column, cell, event){
      this.author_ID = row.author_ID
    },
    getAllAuthorInfos() {
      this.$axios.post('http://localhost:8087/author/getAuthorInfos', this.ruleForm,{
      }).then(res => {
        this.ruleForm.data = res.data.data
        this.ruleForm.current = res.data.current
        this.ruleForm.size = res.data.size
        this.ruleForm.total = res.data.total
      })
    },
    findAuthorInfos(){
      if(this.key == '')
      {
        ElMessage.error("请输入关键字");
        return false
      }
      this.$axios.post('http://localhost:8087/author/findAuthorInfosByKey',{
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
        this.getAllAuthorInfos()
      }
      else {
        this.findAuthorInfos()
      }
    },
    handleCurrentChange(val) {
      this.ruleForm.current = val
      if(this.key == '')
      {
        this.getAllAuthorInfos()
      }
      else {
        this.findBookInfos()
      }
    },
    submitInsertForm(formName){
      this.$refs[formName].validate((valid) => {
        if(valid)
        {
          this.$axios.post('http://localhost:8087/author/insertAuthorInfo',this.insertForm,{
          }).then(res => {
            if(res.data.code == 200) {
              ElMessage.success(res.data.msg)
              this.insertDialogVisible = false
              this.resetInsertForm('insertForm')
              this.getAllAuthorInfos()
            }else{
              ElMessage.error(res.data.msg)
            }
          })
        }
      })
    },
    deleteAuthorInfo(){
      if(this.author_ID == '')
      {
        ElMessage.error("请先选择数据");
        return false
      }
      else{
        this.$axios.post('http://localhost:8087/author/deleteAuthorInfo',{
          params: {
            author_ID:this.author_ID
          }
        },{
        }).then(res => {
          if(res.data.code == 200) {
            ElMessage.success(res.data.msg)
            this.getAllAuthorInfos()
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
    this.getAllAuthorInfos()
  }
}
</script>

<style>
  .el-table td,th {
    text-align: center !important;
  }
</style>