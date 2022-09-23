<template>
  <div>
    <el-row class="row-bg" justify="center">
      <el-col :xl="6" :lg="7"><div class="grid-content ep-bg-purple" />
        <el-avatar style="width: 250px; height: 250px" :src="this.avatar"></el-avatar>
      </el-col>

      <el-col :span="1"><div class="grid-content ep-bg-purple-light" />
        <el-divider direction="vertical" />
      </el-col>

      <el-col :xl="6" :lg="7"><div class="grid-content ep-bg-purple-light" />
          <el-form :model="userInfoForm" :rules="userInforules" ref="userInfoForm" label-width="100px" class="demo-ruleForm" >
            <el-form-item label="账号" prop="userID" style="width: 380px" >
              <el-input v-model="userInfoForm.userID" disabled="true"/>
            </el-form-item>
            <br>

            <el-form-item label="用户名" prop="userName" style="width: 380px">
              <el-input v-model="userInfoForm.userName" autocomplete="off" />
            </el-form-item>
            <br>

            <el-form-item label="性别" prop="userGender" style="width: 380px">
              <el-select v-model="userInfoForm.userGender" >
                <el-option label="男" value="M" />
                <el-option label="女" value="F" />
              </el-select>
            </el-form-item>

            <el-form-item label="身份证号码" prop="userPsptID" style="width: 380px">
              <el-input v-model="userInfoForm.userPsptID" autocomplete="off" />
            </el-form-item>
            <br>

            <el-form-item label="用户手机号" prop="userPhone" style="width: 380px">
              <el-input v-model="userInfoForm.userPhone" autocomplete="off" />
            </el-form-item>
            <br>

            <el-form-item label="邮箱" prop="userEmail" style="width: 380px">
              <el-input v-model="userInfoForm.userEmail" autocomplete="off" />
            </el-form-item>

            <el-form-item>
              <el-popconfirm title="确定要修改吗？" @confirm="updateUserInfo(userInfoForm)">
                <template #reference>
                  <el-button type="primary">修改</el-button>
                </template>
              </el-popconfirm>
              <el-button @click="dialogVisible = true">修改密码</el-button>
              <el-button type="info" @click="getUserInfoByUserID()">重置</el-button>
            </el-form-item>
          </el-form>
          <el-dialog v-model="dialogVisible" title="请输入旧密码和新密码" width="30%">
            <el-form :model="updatePasswordForm" :rules="updatePasswordrules" ref="updatePasswordForm" label-width="100px" class="demo-ruleForm">
              <el-form-item label="旧密码" prop="userOldPassword" style="width: 380px"   >
                <el-input v-model="updatePasswordForm.userOldPassword" autocomplete="off" type="password"/>
              </el-form-item>

              <el-form-item label="新密码" prop="userNewPassword" style="width: 380px"   >
                <el-input v-model="updatePasswordForm.userNewPassword" autocomplete="off" type="password"/>
              </el-form-item>

              <el-form-item label="确认新密码" prop="reNewPassword" style="width: 380px">
                <el-input v-model="updatePasswordForm.reNewPassword" autocomplete="off" type="password"/>
              </el-form-item>

              <el-form-item>
                <el-popconfirm title="确定要修改吗？" @confirm="updatePassword()">
                  <template #reference>
                    <el-button type="primary">修改密码</el-button>
                  </template>
                </el-popconfirm>
                <el-button type="info" @click="resetForm('updatePasswordForm')">重置</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
      </el-col>
    </el-row>
  </div>

</template>

<script>
import {ElMessage} from "element-plus";

export default {
  name: "UserInfo",
  data(){
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.updatePasswordForm.userNewPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      };
    };
    return{
      avatar : '',
      dialogVisible : false,
      userInfoForm: {
        userID: '',
        userName: '',
        userGender: '',
        userPsptID: '',
        userPhone: '',
        userEmail: '',
      },
      userInforules:{
        userID: [
          { required: true, message: '请输入账号', trigger: 'blur',},
        ],
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur',},
        ],
        userGender: [
          { required: true, message: '请选择性别', trigger: 'blur',},
        ],
        userPsptID: [
          { required: true, message: '请输入证件号码', trigger: 'blur',},
          { min: 18, max: 11,message: '请输入有效身份证', trigger: 'blur' }
        ],
        userPhone: [
          { required: true, message: '请输入手机号码', trigger: 'blur',},
          { min: 11, max: 11,message: '请输入有效手机', trigger: 'blur' }
        ],
        userEmail: [
          { required: false, message: '请输入证件号码', trigger: 'blur',},
        ],
      },
      updatePasswordForm:{
        userID: '',
        userOldPassword:'',
        userNewPassword:'',
        reNewPassword:''
      },
      updatePasswordrules:{
        userOldPassword: [
          { required: true, message: '请输入旧密码', trigger: 'blur',},
        ],
        userNewPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur',},
        ],
        reNewPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur',},
          { validator: validatePass, trigger: 'blur',},
        ],
      }
    }
  },
  methods: {
    getUserInfoByUserID(){
      this.userInfoForm.userID = localStorage.getItem("userID")
      this.$axios.post("http://localhost:8087/user/getUserInfoByUserID",this.userInfoForm).then(res =>{
        this.avatar = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fci.xiaohongshu.com%2Fa8294ab4-4964-35ca-9f74-5c736f1e48c4%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fci.xiaohongshu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666076615&t=f085dc95347cd378d44dacc0d4bc5018"
        this.userInfoForm.userID = res.data.data[0].user_ID
        this.userInfoForm.userName = res.data.data[0].user_NAME
        this.userInfoForm.userGender = res.data.data[0].user_GENDER
        this.userInfoForm.userPsptID = res.data.data[0].user_PSPT_ID
        this.userInfoForm.userPhone = res.data.data[0].user_PHONE
        this.userInfoForm.userEmail = res.data.data[0].user_EMAIL
      })
    },
    updateUserInfo(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:8087/user/updayeUserInfo",this.userInfoForm).then(res =>{
            if(res.data.code == 200) {
              ElMessage.success(res.data.msg)
              this.getUserInfoByUserID()
            }
          })
        }
      })
    },
    updatePassword(){
      this.updatePasswordForm.userID = localStorage.getItem("userID")
      this.$axios.post("http://localhost:8087/user/updayeUserPassword",this.updatePasswordForm).then(res =>{
        if(res.data.code == 200) {
          ElMessage.success(res.data.msg)
          localStorage.clear()
          sessionStorage.clear()

          this.$store.commit("resetUserTypeAndUserID")
          this.$router.push("/login")
        }
        else{
          ElMessage.error(res.data.msg)
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    this.getUserInfoByUserID()
  }
}
</script>

<style>
  .el-row
  {
    background-color: #fafafa;
    height: 80vh;
    display: flex;
    align-items: center;
    text-align: center;
  }
  .el-divider
  {
    height:400px;
  }
</style>