<template>
<div>
  <el-menu
      style="width: 200px;min-height:calc(100vh - 50px);"
      :default-active="path"
     router
      :default-openeds="[1]"
      class="el-menu-vertical-demo">
      <el-sub-menu index="1" v-if="user.role === '老板'">
        <template #title>系统管理</template>
        <el-menu-item index="/user" :router>用户管理</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/goods">甜品管理</el-menu-item>
      <el-menu-item index="/order">订单管理</el-menu-item>
    <el-menu-item index="/purchase">原料采购</el-menu-item>
    <el-menu-item index="/staff"  v-if="user.role === '老板'">员工管理</el-menu-item>
  </el-menu>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Aside",
  data()
  {
    return {
      user: {},
      path:this.$route.path//设置默认高亮的菜单
    }
  },
  created()
  {
     let userStr = sessionStorage.getItem("user") || "{}"
     this.user = JSON.parse(userStr)
    //请求服务器，确认当前登录用户的合法信息
     request.get("/user/" + this.user.id).then(res =>{
       if(res.code ==='0'){
         this.user = res.data
       }
     })
  }
}

</script>

<style scoped>

</style>