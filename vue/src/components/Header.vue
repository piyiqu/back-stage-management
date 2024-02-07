<template>
<div style="height: 50px;line-height: 50px;border-bottom: 1px solid
 #ccc; display: flex">
  <div style="width: 250px;padding-left: 30px;font-weight: bold;color: dodgerblue;">甜品店后台管理</div>
  <div style="flex: 1"></div>
  <div style="width: 100px;">
    <el-dropdown>
      <span class="el-dropdown-link" style="padding: 20px;font-weight: bold;">
        {{form.username}}<el-icon class="el-icon--right">
    <arrow-down />
</el-icon>
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
          <el-dropdown-item @click="$router.push('/login') ">退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</div>
</template>

<script>
import { ArrowDown } from '@element-plus/icons-vue'
import user from "@/views/User.vue";
export default {
  name: "Header",
  components: {
    ArrowDown,

  },

  data()
  {
    return {
      form:{},


    }
  },
  created()
  {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
  }
}
</script>


<style scoped>

</style>