<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入订单关键字"
                style="width: 20%"clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <!--    表格区域-->
    <el-table :data= "tableData" border stripe style="width: 100%;">
      <el-table-column prop="id" label="订单ID"
                       sortable />
      <el-table-column prop="name" label="甜品名称"/>
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="number" label="商品数量"
                       sortable />
      <el-table-column prop="orderdate" label="订单日期" />
      <!--      删除与编辑-->
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary"
                     size="small" @click="handleEdit(scope.row)" >
            编辑</el-button
          >
          <el-popconfirm title="你确定要删除吗?" @confirm="handleDelete(scope.row.id)" >
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
          <el-form-item label="甜品名称">
            <el-input v-model="form.name" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model="form.price" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="商品数量">
            <el-input v-model="form.number" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="订单日期">
            <el-date-picker value-format="YYYY-MM-DD" type="date" v-model="form.orderdate" style="width:80%" clearable></el-date-picker>
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
  name: 'Order',
  components: {},
  data() {
    return {
      user:{},
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

    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
//请求服务器，确认当前登录用户的合法信息
    request.get("/user/" + this.user.id).then(res =>{
      if(res.code ==='0'){
        this.user = res.data
      }
    })
    this.load()
  },
  methods:
      {
        filesUploadSuccess(res)
        {
          console.log(res);
          this.form.cover = res.data
        },
        load()
        {
          request.get("/order",
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
            request.put("/order",this.form).then(res =>{
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
            request.post("/order",this.form).then(res =>
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
          //解决未来dom不存在的问题，因为上传图片在弹窗组件后出现
          this.$nextTick(() =>{
            this.$refs['upload'].clearFiles()//清空历史文件列表
          })
        },
        handleEdit(row)
        {
          this.form = JSON.parse(JSON.stringify(row))
          this.dialogVisible=true
          this.$nextTick(() =>{
            this.$refs['upload'].clearFiles()//清空历史文件列表
          })

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
          request.delete("/order/"+ id).then(res =>{
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
