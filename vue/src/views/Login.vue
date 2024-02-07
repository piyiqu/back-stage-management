<template>
  <div style="width: 100%; height: 100vh;background-color:darkslateblue;overflow: hidden;">
    <div style="width: 400px;margin: 150px auto">
      <div style="color: #cccccc;font-size:30px;text-align: center;padding: 30px 0 ">
        欢迎登录
      </div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="form.username" :prefix-icon="Avatar" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" :prefix-icon="Lock" show-password placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item>
          <div style="display: flex">
            <el-input prefix-icon="el-icon-key" v-model="form.validCode" style="width: 50%;" placeholder="请输入验证码"></el-input>
            <ValidCode @input="createdValidCode"/>
          </div>
        </el-form-item>
        <el-form-item>
          <el-radio v-model="form.role" :label="1">管理员</el-radio>
          <el-radio v-model="form.role" :label="2">普通用户</el-radio>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
      <span @click="$router.push('/register') ">没有账号？点击此处进行注册</span>
    </div>
  </div>
</template>

<script>
import { Avatar,Lock,ArrowDown} from '@element-plus/icons-vue'
import request from "@/utils/request";
import ValidCode from "@/components/ValidCode";

export default {
  name: "Login",
  components:{
    ValidCode
  },
  setup(){
    return {
      Avatar,
      Lock,
      ArrowDown
    }
  },

  data()
  {
    return{
      form:{},

      rules: {
        username:[
          {required: true,message:'请输入用户名称',trigger:'blur'}
        ],
        password:[
          {required: true,message:'请输入用户密码',trigger:'blur'}
        ],
      },
      validCode:''

    }
  },
  created(){
    sessionStorage.removeItem("user")
  },
  methods:{
    //接受验证码组件提交的4位验证码
    createdValidCode(data){
      this.validCode = data
    },
    login(){
      this.$refs['form'].validate((valid) =>{
        if(valid)
        {
          if(!this.form.validCode){
            this.$message.error("请填写验证码")
            return
          }
          if(this.form.validCode.toLowerCase()!== this.validCode.toLowerCase()){
            this.$message.error("验证码错误")
            return
          }
          request.post("/user/login",this.form).then(res =>{
            if(res.code == '0')
            {
              this.$message({
                type:"success",
                message:"登录成功"
              })
              //缓存用户信息
              sessionStorage.setItem("user",JSON.stringify(res.data))
              this.$router.push("/")//登录成功进行页面跳转到主页
            }
            else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })
        }
      })

    }
  }
}
</script>

<style scoped>

</style>