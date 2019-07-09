<style scoped>

</style>

<template>
  <div class="container">
    <Table width="100%" border :columns="stockColumn" no-data-text="数据可能跑路了哦 (^_^) " :loading="loading" highlight-row
           :data="stocks">
      <template slot-scope="{ row, index }" slot="action">
        <Button dashed size="small" style="margin-right: 5px" @click="detailLook(row)">
          ViewPriseDetail
          <Icon type="ios-arrow-forward"></Icon>
        </Button>
      </template>
    </Table>
    <Page style="margin-top: 10px;" :total="total" :page-size-opts="pageChangeData" :current.sync="pageNumber"
          :page-size="pageSize" @on-page-size-change="onPageSizeChange" show-total @on-change="onPageChange"
          show-sizer show-elevator/>
  </div>
</template>

<script>
  import { showInfo } from '../../public/js/appUtils'
  import { stockSelect, stockCount } from '../../public/js/appRequest'

  export default {
    name: 'StockOverlookPage',
    data () {
      return {
        stockColumn: [
          {
            title: '序号',
            type: 'index',
            align: 'center',
            width: 200
          },{
            title: '股票名称',
            key: 'stock_name',
            align: 'center',
            sortable: true
            // width: '20%'
          },
          {
            title: '股票代码',
            key: 'stock_num',
            align: 'center',
            sortable: true
            // width: '20%'
          },
          {
            title: '股票代码(含城市编号)',
            key: 'stock_search_num',
            align: 'center',
            sortable: true
            // width: '30%'
          },
          {
            title: '操作选项',
            slot: 'action',
            align: 'center',
            width: 300,
            // render: (h, params) => {
            //   return h('div', [
            //     h('Button', {
            //       props: {
            //         type: 'text',
            //         size: 'large'
            //       }
            //     }, '查看详细信息')
            //   ]);
            // }
          }
        ],
        stocks: [],
        pageChangeData: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100],//条数切换值
        total: 0,  //分页预留
        pageNumber: 1, //分页预留
        pageSize: 10,  //分页预留
        loading: false //数据加载状态展示控制
      }
    },
    computed:{
      stockStore(){
        return this.$store.state.stocks
      },
      is_initStore(){
        return this.$store.state.is_init
      }
    },
    watch: {
      stockStore: function (neww, old) {
        this.stocks = neww
        this.total = this.stocks.length
      },
      is_initStore: function () {
        this.total = this.$store.state.total
        this.onPageChange(1) //查询所有
      }
    },
    methods: {
      onPageChange (num) {
        this.pageNumber = num
        this.loading = true
        let data = {
          limit: this.pageSize,
          offset: (num - 1) * this.pageSize
        }
        stockSelect(this, data).then((resp) => {
          this.stocks = JSON.parse(resp)
          this.loading = false
        })
      },
      onPageSizeChange (num) {
        this.pageSize = num
        this.onPageChange(1)
      },
      detailLook(row){
        this.$router.push("/detail/" + row.stock_num)
      }
    },
    created () {
      console.log('初始化信息:')
      stockCount(this, {}).then((resp) => {
        this.$store.commit("setTotal",JSON.parse(resp)[0].stock_total)
        this.total = this.$store.state.total
        this.onPageChange(1)
      })
    }
  }
</script>

