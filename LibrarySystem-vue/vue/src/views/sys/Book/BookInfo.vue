<template>
  <div>
    <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="float:left;" inline="true">
      <el-form-item>
        <el-input v-model="key" placeholder="Please input" />
      </el-form-item>
      <el-form-item>
        <el-button @click="findBookInfos()" >搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="insertDialogVisible = true" v-if="this.userType=='0'">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="ruleForm.data" border stripe style="width: 150%" @cell-click="selectClick"
              highlight-current-row>
      <el-table-column prop="book_ID" label="图书编号" width="150" v-if="this.userType!='2'"/>
      <el-table-column prop="book_ID" label="图书编号" width="200" v-if="this.userType=='2'"/>

      <el-table-column prop="book_NAME" label="图书名称"  width="200"/>
      <el-table-column prop="book_TYPE_ID" label="图书类型" width="100"/>
      <el-table-column prop="author_ID" label="作者" width="200" v-if="false"/>
      <el-table-column prop="author_NAME" label="作者名称" width="200"/>
      <el-table-column prop="press_ID" label="出版社" width="150" v-if="false"/>

      <el-table-column prop="press_NAME" label="出版社名称" width="150" v-if="this.userType!='2'"/>
      <el-table-column prop="press_NAME" label="出版社名称" width="200" v-if="this.userType=='2'"/>

      <el-table-column prop="borrowed_NUMBER" label="借阅" width="100" v-if="this.userType!='2'"/>
      <el-table-column prop="borrowed_NUMBER" label="借阅" width="150" v-if="this.userType=='2'"/>

      <el-table-column prop="remaining_NUMBER" label="剩余" width="100" v-if="this.userType!='2'"/>
      <el-table-column prop="borrowed_NUMBER" label="借阅" width="150" v-if="this.userType=='2'"/>

      <el-table-column prop="book_STATUS" label="图书状态" width="100" :filters="[
        { text: '有效', value: '有效' },
        { text: '失效', value: '失效' },
      ]" :filter-method="filterTag"/>
\      <el-table-column label="操作" width="200" v-if="this.userType!='2'">
        <div v-if="this.userType==0">
          <el-button @click="updateDialogVisible = true">修改</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-popconfirm title="确定要删除这个图书吗？" @confirm="deleteBookInfo()">
            <template #reference>
              <el-button >删除</el-button>
            </template>
          </el-popconfirm>
        </div>

        <div v-if="this.userType==1">
          <el-button @click="borrowedDialogVisible = true">借阅</el-button>
          <el-divider direction="vertical" ></el-divider>
          <el-button @click="returnDialogVisible = true">还书</el-button>
        </div>


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

    <el-dialog v-model="borrowedDialogVisible" title="请输入用户ID和借书数量" width="30%">
      <el-form>
        <el-form-item label="用户ID:" label-width="100px" style="width: 380px">
          <el-input v-model="this.custID" />
        </el-form-item>

        <el-form-item label="借书数量:" label-width="100px" style="width: 380px" >
          <el-input-number v-model="num" :min="1" :max="10"/>
        </el-form-item>


        <el-form-item style="float: right">
          <el-button @click="borrowedBook()">确定</el-button>
        </el-form-item>

      </el-form>
      <br>
    </el-dialog>

    <el-dialog v-model="returnDialogVisible" title="请输入用户ID和还书数量" width="30%">
      <el-form>
        <el-form-item label="用户ID:" prop="custID" style="width: 380px">
          <el-input v-model="this.custID" />
        </el-form-item>

        <el-form-item label="还书数量:" label-width="100px" style="width: 380px" >
          <el-input-number v-model="num" :min="1" :max="10"/>
        </el-form-item>

        <el-form-item style="float: right">
          <el-button @click="returnBook()">确定</el-button>


        </el-form-item>
      </el-form>
      <br>
    </el-dialog>


    <el-dialog v-model="insertDialogVisible" title="请输入新增图书信息" width="30%">
      <el-form :model="insertForm"  :rules="insertrules" ref="insertForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="图书编号" prop="bookID" style="width: 380px">
          <el-input v-model="insertForm.bookID" />
        </el-form-item>

        <el-form-item label="图书名称" prop="bookName" style="width: 380px">
          <el-input v-model="insertForm.bookName" />
        </el-form-item>

        <el-form-item label="图书类型" prop="bookTypeID" style="width: 380px">
          <el-select v-model="updateForm.bookTypeID" placeholder="请选择图书类型">
            <el-option
                v-for="item in this.bookTypes"
                :key="item.book_TYPE_ID"
                :label="item.book_TYPE_NAME"
                value="item.bookTypeID"
                >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="作者编号" prop="authorID" style="width: 380px">
          <el-input v-model="insertForm.authorID" />
        </el-form-item>

        <el-form-item label="出版社编号" prop="pressID" style="width: 380px">
          <el-input v-model="insertForm.pressID" />
        </el-form-item>


        <el-form-item label="图书数量" prop="remainingNumber" style="width: 380px">
          <el-input v-model="insertForm.remainingNumber" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitInsertForm('insertForm')">新增</el-button>
          <el-button @click="resetInsertForm('insertForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog v-model="updateDialogVisible" title="请输入修改图书信息" width="30%">
      <el-form :model="updateForm" :rules="insertrules" ref="updateForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="图书编号" prop="bookID" style="width: 380px" >
          <el-input v-model="updateForm.bookID" disabled="true"/>
        </el-form-item>

        <el-form-item label="图书名称" prop="bookName" style="width: 380px">
          <el-input v-model="updateForm.bookName" />
        </el-form-item>

        <el-form-item label="图书类型" prop="bookTypeID" style="width: 380px">
          <el-select v-model="updateForm.bookTypeID" placeholder="请选择图书类型">
            <el-option
                v-for="item in this.bookTypes"
                :key="item.book_TYPE_ID"
                :label="item.book_TYPE_NAME"
                value="item.bookTypeID"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="作者编号" prop="authorID" style="width: 380px">
          <el-input v-model="updateForm.authorID" />
        </el-form-item>

        <el-form-item label="出版社编号" prop="pressID" style="width: 380px">
          <el-input v-model="updateForm.pressID" />
        </el-form-item>

        <el-form-item label="借出数量" prop="borrowedNumber" style="width: 380px">
          <el-input v-model="updateForm.borrowedNumber" />
        </el-form-item>

        <el-form-item label="剩余数量" prop="remainingNumber" style="width: 380px">
          <el-input v-model="updateForm.remainingNumber" />
        </el-form-item>

        <el-form-item label="图书状态" prop="bookStatus" style="width: 380px">
          <el-select v-model="updateForm.bookStatus" placeholder="请选择图书状态">
            <el-option label="有效" value=0 />
            <el-option label="失效" value=1 />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button  type="primary" @click="submitUpdateForm('updateForm')">修改</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";

export default {
  name: "BookInfo",
  data() {
    return {
      notVisible: false,
      insertDialogVisible: false,
      updateDialogVisible: false,
      borrowedDialogVisible: false,
      returnDialogVisible: false,
      num:1,
      ruleForm:{
        current: 1,
        size:10,
        total:0,
        tableData: []
      },
      insertForm:{
        bookID: '',
        bookName: '',
        bookType: '',
        authorID: '',
        pressID: '',
        remainingNumber: 0,
        borrowedNumber:0,
        bookStatus:'',
      },
      updateForm:{
        bookID: '',
        bookName: '',
        bookType: '',
        authorID: '',
        pressID: '',
        remainingNumber: 0,
      },
      key:'',
      book_ID:'',
      userType:'',
      custID:'',
      bookTypes:{
        book_TYPE_ID: '',
        book_TYPE_NAME: '',
      },
      insertrules:{
        bookID: [
          { required: true, message: '请输入图书编号', trigger: 'blur',},
        ],
        bookName: [
          { required: true, message: '请输入图书名称', trigger: 'blur',},
        ],
        bookTypeID: [
          { required: true, message: '请选择图书类型', trigger: 'blur',},
        ],
        authorID: [
          { required: true, message: '请输入作者编号', trigger: 'blur',},
        ],
        pressID: [
          { required: true, message: '请输入出版社编号', trigger: 'blur',},
        ],
        remainingNumber: [
          { required: true, message: '请输入图书数量', trigger: 'blur',},
        ],
      },
      updaterules:{
        bookID: [
          { required: true, message: '请输入图书编号', trigger: 'blur',},
        ],
        bookName: [
          { required: true, message: '请输入图书名称', trigger: 'blur',},
        ],
        bookTypeID: [
          { required: true, message: '请选择图书类型', trigger: 'blur',},
        ],
        authorID: [
          { required: true, message: '请输入作者编号', trigger: 'blur',},
        ],
        pressID: [
          { required: true, message: '请输入出版社编号', trigger: 'blur',},
        ],
        remainingNumber: [
          { required: true, message: '请输入剩余数量', trigger: 'blur',},
        ],
        borrowedNumber: [
          { required: true, message: '请输入借出数量', trigger: 'blur',},
        ],
        bookStatus: [
          { required: true, message: '请选择图书状态', trigger: 'blur',},
        ],
      }
    }
  },
  methods:{
    filterTag(row, value){
      return row === value.book_STATUS
    },
    selectClick(row, column, cell, event) {
      this.book_ID = row.book_ID
      this.updateForm.bookID = row.book_ID
      this.updateForm.bookName = row.book_NAME
      this.updateForm.bookTypeID = row.book_TYPE_ID
      this.updateForm.authorID = row.author_ID
      this.updateForm.pressID = row.press_ID
      this.updateForm.remainingNumber = row.remaining_NUMBER
      this.updateForm.borrowedNumber = row.borrowed_NUMBER
      this.updateForm.bookStatus = row.book_STATUS
    },
    handleSizeChange(val) {
      this.ruleForm.size = val
      if(this.key == '')
      {
        this.getAllBookInfos()
      }
      else {
        this.findBookInfos()
      }
    },
    handleCurrentChange(val) {
      this.ruleForm.current = val
      if(this.key == '')
      {
        this.getAllBookInfos()
      }
      else {
        this.findBookInfos()
      }
    },
    getAllBookInfos() {
      this.$axios.post('http://localhost:8087/book/getBookInfos', this.ruleForm,{
      }).then(res => {
        this.ruleForm.data = res.data.data
        this.ruleForm.current = res.data.current
        this.ruleForm.size = res.data.size
        this.ruleForm.total = res.data.total
      })
    },
    findBookInfos(){
      if(this.key == '')
      {
        ElMessage.error("请输入关键字");
        return false
      }
      this.$axios.post('http://localhost:8087/book/findBookInfosByKey',{
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
    deleteBookInfo(){
      if(this.book_ID == '')
      {
        ElMessage.error("请先选择数据");
        return false
      }
      else{
        this.$axios.post('http://localhost:8087/book/deleteBookInfo',{
          params: {
            book_ID:this.book_ID
          }
        },{
        }).then(res => {
          if(res.data.code == 200) {
            ElMessage.success(res.data.msg)
            this.getAllBookInfos()
          }else{
            ElMessage.error(res.data.msg)
          }
        })
      }
    },
    submitInsertForm(formName){
      this.$refs[formName].validate((valid) => {
        if(valid)
        {
          this.$axios.post('http://localhost:8087/book/insertBookInfo',this.insertForm,{
          }).then(res => {
            if(res.data.code == 200) {
              ElMessage.success(res.data.msg)
              this.insertDialogVisible = false
              this.resetInsertForm('insertForm')
              this.getAllBookInfos()
            }else{
              ElMessage.error(res.data.msg)
            }
          })
        }
      })
    },
    submitUpdateForm(formName){
      this.$refs[formName].validate((valid) => {
        if(valid)
        {
          this.$axios.post('http://localhost:8087/book/updateBookInfo',this.updateForm,{
          }).then(res => {
            if(res.data.code == 200) {
              ElMessage.success(res.data.msg)
              this.updateDialogVisible = false
              this.resetInsertForm('updateForm')
              this.getAllBookInfos()
            }else{
              ElMessage.error(res.data.msg)
            }
          })
        }
      })
    },
    borrowedBook(){
      this.$axios.post('http://localhost:8087/book/borrowedBook',{
        params:{
          updateForm:this.updateForm,
          num:this.num,
          user_ID:this.custID
        }
      },{
      }).then(res => {
        if(res.data.code == 200) {
          ElMessage.success(res.data.msg)
          this.borrowedDialogVisible = false
          this.num=1
          this.custID=''
          this.getAllBookInfos()
        }else{
          ElMessage.error(res.data.msg)
        }
      })
    },
    returnBook(){
      this.$axios.post('http://localhost:8087/book/returnBook',{
        params:{
          updateForm:this.updateForm,
          num:this.num,
          user_ID:this.custID
        }
      },{
      }).then(res => {
        if(res.data.code == 200) {
          ElMessage.success(res.data.msg)
          this.returnDialogVisible = false
          this.num=1
          this.custID=''
          this.getAllBookInfos()
        }else{
          ElMessage.error(res.data.msg)
        }
      })
    },
    getAllBookTypeInfos(){
      this.$axios.post('http://localhost:8087/booktype/getAllBBookTypeInfos', this.ruleForm,{
      }).then(res => {
        console.log(res.data.data)
        console.log(this.bookTypes)
        this.bookTypes = res.data.data
      })
    },
    resetInsertForm(formName){
      this.$refs[formName].resetFields();
      console.log()
    },
  },
  created() {
    this.userType = localStorage.getItem("userType")
    console.log(this.userType)
    this.getAllBookInfos()
    this.getAllBookTypeInfos()
  }
}

</script>

<style>
  .el-table td,th {
    text-align: center !important;
  }
</style>