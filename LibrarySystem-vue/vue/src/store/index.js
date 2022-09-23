import { createStore } from 'vuex'

export default createStore({
  state: {
    userType:'',
    userID:'',
    token:''
  },
  getters: {
  },
  mutations: {
    SET_TOKEN:(state,token) =>{
      state.token = token
      localStorage.setItem("token",token)
    },

    resetState:(state)=>{
      state.token = ''
    },

    setUserType:(state,userType) =>{
      state.userType = userType
      localStorage.setItem("userType",userType)

    },

    setUserID:(state,userID) =>{
      state.userID = userID
      localStorage.setItem("userID",userID)
    },

    resetUserTypeAndUserID:(state)=>{
      state.userType = ''
      state.userID = ''
    },
  },
  actions: {
  },
  modules: {
  }
})
