<template>
  <el-container>

    <el-aside width="200px">
      <el-menu
          active-text-color="#ffd04b"
          background-color="#545c64"
          class="el-menu-vertical-demo"
          default-active="1"
          text-color="#fff"
      >
        <router-link to = "/index">
        <el-menu-item index="1">
          <el-icon><House/></el-icon>
          <span>首页</span>
        </el-menu-item>
        </router-link>

        <el-sub-menu index="2" v-if="userInfo.userType=='0'">
          <template #title>
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </template>
          <!--<router-link to = "/userLogin"><el-menu-item index="2-1">用户账号管理</el-menu-item></router-link>-->
          <router-link to = "/userManga"><el-menu-item index="2-2">用户信息管理</el-menu-item></router-link>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <el-icon><Notebook /></el-icon>
            <span>图书管理</span>
          </template>
          <router-link to ="/bookInfo"><el-menu-item index="3-1">图书信息管理</el-menu-item></router-link>
          <router-link to = "/authorInfo"><el-menu-item index="3-2">作者信息管理</el-menu-item></router-link>
          <router-link to = "/pressInfo"><el-menu-item index="3-3">出版社信息管理</el-menu-item></router-link>
          <router-link to = "/bookTypeInfo"><el-menu-item index="3-4">图书类型信息管理</el-menu-item></router-link>
        </el-sub-menu>
        <router-link to = "/borrowedInfo" v-if="userInfo.userType!='0'">
        <el-menu-item index="4">
          <el-icon><EditPen/></el-icon>
          <span>借阅管理</span>
        </el-menu-item>
        </router-link>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header>
        <strong>图书管理系统</strong>
        <div class="header-avatar">
          <el-avatar size="medium" :src="userInfo.avatar"></el-avatar>

          <el-dropdown>
            <span class="el-dropdown-link" style="margin-top:5px">
              {{userInfo.userName}}
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <router-link to = "/userInfo">
                  <el-dropdown-item v-if="userInfo.userType=='2'">个人中心</el-dropdown-item>
                </router-link>
                <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main>
        <Tabs></Tabs>
        <div style="margin-top: -10px;">
          <router-view></router-view>
        </div>
      </el-main>
    </el-container>

  </el-container>

</template>

<script>
import {House,User,Notebook,EditPen} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
export default {
  name: "home",
  components:{
    House,
    User,
    Notebook,
    EditPen
  },
  data(){
    return{
      userInfo:{
        userID:"",
        userName:"",
        userType:"",
        avatar:"",
      }
    };
  },
  created() {
    this.getUserInfoByUserID()
  },
  methods:{
    getUserInfoByUserID(){
      this.userInfo.userID = localStorage.getItem("userID")
      this.userInfo.userType = localStorage.getItem("userType")
      if(this.userInfo.userID == null)
      {
        ElMessage.error("请先登录")
        this.$router.push("/login")
        return false
      }
      this.$axios.post("http://localhost:8087/user/getUserInfoByUserID",this.userInfo).then(res =>{
        if(res.data.code == 200)
        {
          this.userInfo.avatar = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fci.xiaohongshu.com%2Fa8294ab4-4964-35ca-9f74-5c736f1e48c4%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fci.xiaohongshu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666076615&t=f085dc95347cd378d44dacc0d4bc5018"
          this.userInfo.userName=res.data.data[0].user_NAME
        }

      })
    },
    logout(){
      this.$axios.get("/logout").then(res =>{
        localStorage.clear()
        sessionStorage.clear()

        this.$store.commit("resetUserTypeAndUserID")
        this.$router.push("/login")
      })
    }
  }
}
</script>

<style>
  .el-container{
    padding: 0;
    margin: 0;
    height: 100vh;
    width: 100%;
  }

  .el-header {
    background-color: #17B3A3;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .header-avatar{
    float: right;
    width: 150px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-top:10px ;
  }

  .el-dropdown-link{
    cursor: pointer;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
  }

  .el-menu-vertical-demo{
    height: 100%;
  }

  a{
    text-decoration: none;
  }
</style>