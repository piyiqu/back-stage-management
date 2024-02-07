<template>
  <div style="width: 100%; height: 100vh;background-color:darkslateblue;overflow: hidden;">
    <div style="width: 400px;margin: 150px auto">
      <div style="color: #cccccc;font-size:30px;text-align: center;padding: 30px 0 ">
        欢迎注册
      </div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="form.username" :prefix-icon="Avatar" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" :prefix-icon="Lock" show-password placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input v-model="form.comfirm" :prefix-icon="Lock" show-password placeholder="请再次确认密码"/>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { Avatar,Lock} from '@element-plus/icons-vue'
import request from "@/utils/request";


export default {
  name: "Register",
  setup(){
    return {
      Avatar,
      Lock
    }
  },

  data()
  {
    return {
      form: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名称', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入用户密码', trigger: 'blur'},
          { min: 6, max: 20, message: '密码长度在6到20个字符', trigger: 'blur' },
          { pattern: /^(?=.*[a-z])(?=.*[A-Z])|(?=.*[@$!%*?&])[A-Za-z@$!%*?&]+$/,
            message: '密码必须包含数字，还有大小写字母或特殊符号', trigger: 'blur' }
        ],

      }
    }
  },
  methods:
    {
    register(){
      if(this.form.password !=this.form.comfirm)
      {
        this.$message({
          type:"error",
          message:"两次密码不一致!"
        })
        return
      }
    this.$refs['form'].validate((valid) =>{
      if(valid)
      {
        request.post("/user/register",this.form).then(res =>{
          if(res.code == '0')
          {
            this.$message({
              type:"success",
              message:"注册成功"
            })
            this.$router.push("/login")//注册成功进行页面跳转到登录页面
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