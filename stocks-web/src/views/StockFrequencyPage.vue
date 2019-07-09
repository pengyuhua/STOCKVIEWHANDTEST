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
    <div id="stockAll" class="draw"></div>
  </div>
</template>

<script>
  import { frequencySelect } from '../../public/js/appRequest'
  export default {
    name: 'StockFrequencyPage',
    data () {
      return {
        stock_num: '',
        stockAll: []
      }
    },
    mounted () {
      let __this = this
      let myChartAll = this.$echarts.init(document.getElementById('stockAll'))

      myChartAll.showLoading()
      frequencySelect(this, { intervalData: 0.05, intervalDay: 30 }).then((resp) => {
        this.stockAll = JSON.parse(resp)
        // console.log(this.stockAll)
        let category = []
        let frequency = []
        //数据解析
        for (let i = 0; i < __this.stockAll.length; i++){
          category.push(__this.stockAll[i].stock_name)
          frequency.push(__this.stockAll[i].frequency)
        }
        // console.log(category)
        // console.log(frequency)
        // 指定图表的配置项和数据
        let option = {
          title: {
            text: '各大股票过去一月涨幅超5%统计数据:',
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
            data: ['涨幅次数']
          },
          xAxis: {
            type: 'category',
            data: category, //横坐标数据
            scale: true,
            name: '股票名称',
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
            name: '次数'
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
            name: '涨幅次数',
            type: 'bar',
            // smooth: true,
            // lineStyle: {
            //   normal: { opacity: 0.5 }
            // },
            // data: [5, 20, 36, 10, 10, 20]
            data: frequency
          }
          ]
        }
        myChartAll.hideLoading()
        // 使用指定的配置项和数据显示图表。
        myChartAll.setOption(option)
      })


    }
  }
</script>

