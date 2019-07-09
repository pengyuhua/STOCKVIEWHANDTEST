<style scoped lang="stylus">
  .container
    width 100%
    height 80vh

    .draw
      width 90%
      height 70vh

</style>

<template>
  <div class="container">
    <!--<div id="stockLatest" class="draw"></div>-->
    <div id="stockAll" class="draw"></div>
    <!--<div id="stockLatest"></div>-->
  </div>
</template>

<script>

  import { stockDetailAllSelect } from '../../public/js/appRequest'

  export default {
    name: 'StockDetailPage',
    data () {
      return {
        stock_num: '',
        stockAll: []
      }
    },
    created () {
      this.stock_num = this.$route.params.stock_num
    },
    mounted () {
      let __this = this
      let myChartAll = this.$echarts.init(document.getElementById('stockAll'))
      stockDetailAllSelect(this, { stock_num: this.stock_num }).then((resp) => {
        this.stockAll = JSON.parse(resp)
        // console.log(this.stockAll)
        let stock_name = this.stockAll[0].stock_name
        let category = []
        let open = []
        let close = []
        let high = []
        let low = []
        let volume = []

        for (let i = 0; i < __this.stockAll.length; i++){
          category.push(__this.stockAll[i].up_time)
          open.push(__this.stockAll[i].open)
          close.push(__this.stockAll[i].close)
          high.push(__this.stockAll[i].high)
          low.push(__this.stockAll[i].low)
          volume.push(__this.stockAll[i].volume)
        }

        // console.log(category)
        // console.log(open)
        // console.log(close)
        // console.log(high)
        // console.log(low)
        // console.log(volume)

        // let myChartlatest = this.$echarts.init(document.getElementById('stockLatest'))
        // 指定图表的配置项和数据
        let option = {
          title: {
            text: stock_name + '交易记录',
            left: 0
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            },
            snap: true
          },
          grid: {
            left: '10%',
            right: '10%',
            bottom: '15%'
          },
          legend: {
            data: ['开盘价', '收盘价', '最高价', '最低价', '成交量']
          },
          xAxis: {
            type: 'category',
            data: category, //横坐标数据
            scale: true,
            name: '年份/日期',
            boundaryGap: false,
            axisLine: { onZero: false },
            splitLine: { show: false },
            splitNumber: 20,
            min: 'dataMin',
            max: 'dataMax'

          },
          yAxis: {
            scale: true,
            splitArea: {
              show: true
            },
            name: '成交价'
          },
          dataZoom: [
            {
              type: 'inside',
              start: 90,
              end: 100
            },
            {
              show: true,
              type: 'slider',
              top: '95%',
              start: 90,
              end: 100
            }
          ],
          series: [
            {
            name: '开盘价',
            type: 'line',
            smooth: true,
            lineStyle: {
              normal: { opacity: 0.5 }
            },
            // data: [5, 20, 36, 10, 10, 20]
            data: open
          }, {
            name: '收盘价',
            type: 'line',
            smooth: true,
            lineStyle: {
              normal: { opacity: 0.5 }
            },
            // data: [5, 20, 36, 10, 10, 20]
            data: close
          }, {
            name: '最高价',
            type: 'line',
            smooth: true,
            lineStyle: {
              normal: { opacity: 0.5 }
            },
            // data: [5, 20, 36, 10, 10, 20]
            data: high
          },
            {
            name: '最低价',
            type: 'line',
            smooth: true,
            lineStyle: {
              normal: { opacity: 0.5 }
            },
            // data: [5, 20, 36, 10, 10, 20]
            data: low
          }
          //   {
          //   name: '成交量',
          //   type: 'line',
          //   smooth: true,
          //   lineStyle: {
          //     normal: { opacity: 0.5 }
          //   },
          //   // data: [5, 20, 36, 10, 10, 20]
          //   data: volume
          // },
          ]
        }

        // 使用指定的配置项和数据显示图表。
        myChartAll.setOption(option)
      })


    }
  }
</script>

