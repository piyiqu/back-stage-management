<template>
  <div style="padding: 10px">
<!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
<!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入关键字"
                style="width: 20%"clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
<!--    表格区域-->
    <el-table :data= "tableData" border stripe style="width: 100%;">
      <el-table-column prop="id" label="ID"
      sortable />
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="sex" label="性别" />
      <el-table-column prop="address" label="地址" />
      <el-table-column prop="role"  label="角色">
        <template #default="scope">
          <span v-if="scope.row.role === 1">老板</span>
          <span v-if="scope.row.role === 2">普通员工</span>
        </template>
      </el-table-column>
<!--      删除与编辑-->
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary"
                     size="small" @click="handleEdit(scope.row)">
            编辑</el-button
          >
          <el-popconfirm title="你确定要删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button link type="primary" size="small"
              >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin:10px 0">
      <el-pagination
          :current-page="currentPage"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
      <el-dialog v-model="dialogVisible" title="Tips" width="30%">
        <el-form :model="form" label-width="12 0px">
          <el-form-item label="用户名">
            <el-input v-model="form.username" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="form.nickname" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="form.age" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="form.sex" class="ml-4">
              <el-radio label="男" size="small">男</el-radio>
              <el-radio label="女" size="small">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="角色">
            <el-input v-model="form.role" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="地址框">
            <el-input v-model="form.address" type="textarea" style="width:80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确定
        </el-button>
      </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>

<script>
// @ is an alias to /src


import request from "@/utils/request";
import axios from "axios";

export default {
  name: 'HomeView',
  components: {},
  data() {
    return {
      form:{},
      dialogVisible:false,
      currentPage: 1,
      pageSize:10,
      total: 0,
      search: '',
      tableData: [

      ]
    }
  },
  created()
  {
    this.load()
  },
  methods:
      {
        load()
        {
          request.get("/user",
             {
               params:{
                 pageNum: this.currentPage,
                 pageSize: this.pageSize,
                 search: this.search
               }
              }).then(res =>{
                console.log(res)
                this.tableData = res.data.records
                this.total = res.data.total
          })
        },
        save()
        {
          if(this.form.id)
          {
              request.put("/user",this.form).then(res =>{
                console.log(res)
                if(res.code =='0')
                {
                  this.$message({
                    type:"success",
                    message:"更新成功"
                  })
                }
          else {
                  this.$message({
                    type:"error",
                    message: res.msg
                  })
                }
               this.load()//刷新数据
                this.dialogVisible = false//关闭弹窗

              })
          }
          else
          {
            request.post("/user",this.form).then(res =>
            {
              console.log(res)
              if(res.code =='0')
              {
                this.$message({
                  type:"success",
                  message:"新增成功"
                })
              }
              else {
                this.$message({
                  type:"error",
                  message:res.msg
                })
              }
              this.load()//刷新数据
              this.dialogVisible = false//关闭弹窗
            })
          }

        },
        add()
        {
              this.dialogVisible = true
              this.form={}
        },
        handleEdit(row)
        {
          this.form = JSON.parse(JSON.stringify(row))
          this.dialogVisible=true
        },
        handleSizeChange(pageSize)//改变当前每页个数触发
        {
          this.pageSize=pageSize;
            this.load()
        },
        handleCurrentChange(pageNum)//改变当前页码触发
        {
          this.currentPage=pageNum;
          this.load()
        },
        handleDelete(id)//删除操作
        {
          console.log(id)
          request.delete("/user/"+ id).then(res =>{
            if(res.code =='0')
            {
              this.$message({
                type:"success",
                message:"删除成功"
              })
            }
            else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
            this.load()//重新加载删除后的表格数据
          })
        },
      }
}
</script>
