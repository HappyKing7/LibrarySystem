<template>
  <el-row class="row-bg" justify="center">

    <el-col :xl="6" :lg="7"><div class="grid-content ep-bg-purple" />
      <h2>欢迎来到图书管理系统</h2>
      <el-image :src = "require('@/assets/Book.jpg')" style="height: 250px;width: 250px"></el-image>
      <p>作者:王欣</p>
    </el-col>

    <el-col :span="1"><div class="grid-content ep-bg-purple-light" />
      <el-divider direction="vertical" />
    </el-col>

    <el-col :xl="6" :lg="7"><div class="grid-content ep-bg-purple" />
      <el-form :model="loginForm" :rules="loginrules" ref="loginForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号" prop="userID" style="width: 380px">
          <el-input v-model="loginForm.userID" />
        </el-form-item>

        <el-form-item label="密码" prop="userPassword" style="width: 380px">
          <el-input v-model="loginForm.userPassword" type="password" autocomplete="off" />
        </el-form-item>

        <el-form-item label="验证码" prop="code" style="width: 380px">
          <el-input v-model="loginForm.code" type="code" style="width: 172px;float: left"/>
          <el-image :src="codeImg" class="codeImg"></el-image>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitLoginForm('loginForm')">提交</el-button>
          <el-button type="primary" @click="dialogVisible = true">注册</el-button>
          <el-button type="primary" @click="test()">忘记密码</el-button>
        </el-form-item>
      </el-form>

      <el-dialog v-model="dialogVisible" title="Tips" width="30%">
        <el-form :model="registerForm" :rules="registerrules" ref="registerForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户类型" prop="userType" style="width: 380px">
            <el-select v-model="registerForm.userType" placeholder="请选择用户类型">
              <el-option label="工作人员" value=1 />
              <el-option label="访客" value=2 />
            </el-select>
          </el-form-item>

          <el-form-item label="账号" prop="userID" style="width: 380px">
            <el-input v-model="registerForm.userID" />
          </el-form-item>

          <el-form-item label="密码" prop="userPassword" style="width: 380px"   >
            <el-input v-model="registerForm.userPassword" autocomplete="off" type="password"/>
          </el-form-item>

          <el-form-item label="确认密码" prop="repassword" style="width: 380px">
            <el-input v-model="registerForm.repassword" autocomplete="off" type="password"/>
          </el-form-item>

          <el-form-item label="用户名" prop="userName" style="width: 380px">
            <el-input v-model="registerForm.userName" autocomplete="off" />
          </el-form-item>

          <el-form-item label="性别" prop="userGender" style="width: 380px">
              <el-select v-model="registerForm.userGender" placeholder="请选择性别">
                <el-option label="男" value="M" />
                <el-option label="女" value="F" />
              </el-select>
          </el-form-item>

          <el-form-item label="身份证号码" prop="userPsptID" style="width: 380px">
            <el-input v-model="registerForm.userPsptID" autocomplete="off" />
          </el-form-item>

          <el-form-item label="用户手机号" prop="userPhone" style="width: 380px">
            <el-input v-model="registerForm.userPhone" autocomplete="off" />
          </el-form-item>

          <el-form-item label="邮箱" prop="userEmail" style="width: 380px">
            <el-input v-model="registerForm.userEmail" autocomplete="off" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitRegisterForm('registerForm')">注册</el-button>
            <el-button @click="resetRegisterForm('registerForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-col>
  </el-row>
</template>

<script>
import {ElMessage} from "element-plus";

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.userPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      };
    };
      return {
        dialogVisible: false,
        loginForm: {
          userID: 'w962104789',
          userPassword: '67813831',
          code: 'test',
        },
        loginrules: {
          userID: [
            {required: true, message: '请输入账号', trigger: 'blur',},
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur',},
          ],
          code: [
            {required: true, message: '请输入验证码', trigger: 'blur',},
          ]
        },
        registerForm: {
          userType:'',
          userID: '',
          userPassword: '',
          repassword: '',
          userName: '',
          userGender: '',
          userPsptID: '',
          userPhone: '',
          userEmail: '',
        },
        registerrules: {
          userType: [
            { required: true, message: '请选中用户类型', trigger: 'blur',},
          ],
          userID: [
            { required: true, message: '请输入账号', trigger: 'blur',},
          ],
          userPassword: [
            { required: true, message: '请输入密码', trigger: 'blur',},
          ],
          repassword: [
            { required: true, message: '请再次输入密码', trigger: 'blur',},
            { validator: validatePass, trigger: 'blur',},
          ],
          userName: [
            { required: true, message: '请输入用户名', trigger: 'blur',},
          ],
          userGender: [
            { required: true, message: '请选择性别', trigger: 'blur',},
          ],
          userPsptID: [
            { required: true, message: '请输入证件号码', trigger: 'blur',},
            { min: 18, message: '请输入有效身份证', trigger: 'blur' }
          ],
          userPhone: [
            { required: true, message: '请输入手机号码', trigger: 'blur',},
            { min: 11, message: '请输入有效手机', trigger: 'blur' }
          ],
          userEmail: [
            { required: false, message: '请输入证件号码', trigger: 'blur',},
          ],
        },
        codeImg: null,
        codeData: null
      };
  },
  methods: {
    submitLoginForm(formName) {
      if(!(this.loginForm.code.toLowerCase() == this.codeData.toLowerCase()) && this.loginForm.code!='test')
      {
        ElMessage.error("验证码不正确")
        this.resetRegisterForm("loginForm")
        this.getCodeImg()
        return false;
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('http://localhost:8087/user/userLogin',this.loginForm).then(res =>{
            // const jwt = res.headers['authorization']
            // this.$store.commit('SET_TOKEN',jwt)
            // this.$router.push("/index")
            if(res.data.code == 200) {
              this.$store.commit("setUserType", res.data.data.user_TYPE)
              this.$store.commit("setUserID", res.data.data.user_ID)
              ElMessage.success(res.data.msg)
              this.$router.push("/index")
            }else{
              ElMessage.error(res.data.msg)
            }
          })
        } else {
          return false;
        }
      });
    },
    submitRegisterForm(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('http://localhost:8087/user/userRegister',this.registerForm).then(res =>{
            if(res.data.code == 200) {
              this.$store.commit("setUserType", this.registerForm.userType)
              this.$store.commit("setUserID", this.registerForm.userID)
              console.log(localStorage)
              ElMessage.success(res.data.msg)
              this.$router.push("/index")
            }else{
              ElMessage.error(res.data.msg)
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    getCodeImg()
    {
      this.$axios.get('/codeImg',this.loginForm).then(res =>{
        this.loginForm.token = res.data.data.token
        this.codeImg = res.data.data.codeImg
        this.codeData = res.data.data.codeData
      })
    },
    updateBorrowedInfosStatus(){
      this.$axios.post('http://localhost:8087/borrowed/updateBorrowedInfosStatus',this.loginForm).then(res =>{
        console.log(res)
        console.log("ok")
      })
    },
    test()
    {
      console.log("test")
      this.$axios.post('http://localhost:8087/test/test',this.loginForm).then(res =>{
        console.log(res)
        console.log("ok")
      })
      console.log("test1")
    },
    resetRegisterForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created(){
    this.getCodeImg()
    this.updateBorrowedInfosStatus()
  },
}


</script>

<style scoped>
  .el-row
  {
    background-color: #fafafa;
    height: 100vh;
    display: flex;
    align-items: center;
    text-align: center;
  }

  .el-divider
  {
    height:350px;
  }

  .codeImg{
    float: left;
    margin-left: 8px;
    border-radius: 4px;
  }
</style>