<template>
  <div id="app">
    <div class="layout" style="position: relative">
      <Layout>
        <Header style="background-color: rgba(87,138,124,0.32); color: black;">
          <div style="float: left; margin-right: 10rem; margin-left: 2rem">
            <div class="logo">
              <a href="#"><img src="../public/STOCK.png" width="64" height="64" @click="home"/></a>
            </div>
          </div>
          <div style="float: left">
            <AutoComplete v-model="stock_search_num" :data="stocksNum" @on-search="handleSearch(0)"
                          @on-select="onSelected"  :clearable="clearable"
                          placeholder="输入股票代码进行查询" style="width:250px;"></AutoComplete>
            <Button shape="circle" icon="ios-search" style="margin-left: 7px;" @click="handleSearch(1)"></Button>
          </div>
          <div style="float: right; margin-right: 2rem">
            <Button type="dashed" ghost="" to="/frequency">涨幅一览</Button>
          </div>
        </Header>
        <Content style="min-height: 83vh"><router-view/></Content>
        <Footer style="font-size: 20px; color: #818a74"> -_- /其实我就是个小花瓶 0.0 </Footer>
      </Layout>
    </div>
  </div>
</template>
<style lang="stylus">
#app
  font-family 'Avenir', Helvetica, Arial, sans-serif
  -webkit-font-smoothing antialiased
  -moz-osx-font-smoothing grayscale
  text-align center
  color #2c3e50

  .logo
    width 64px
    height 64px
    a:hover
      cursor pointer



/*#nav*/
  /*padding 30px*/
  /*a*/
    /*font-weight bold*/
    /*color #2c3e50*/
    /*&.router-link-exact-active*/
      /*color #42b983*/
</style>
<script>
  import {showInfo} from '../public/js/appUtils'
  import {stockSearch} from '../public/js/appRequest'
  export default {
    name: 'App',
    data(){
      return {
        stock_search_num: '',
        stocks:[],
        stocksNum: [],
        clearable: true
      }
    },
    watch:{
      stocks: function (neww, old) {
        let stocks = []
        let lengthTotal = neww.length >10 ? 10 : neww.length
        for (let i = 0; i< lengthTotal; i++){
          stocks[i] =  neww[i].stock_num
        }
        this.stocksNum = stocks
      }
    },
    methods:{
      home(){
        this.$router.push("/")
      },
      handleSearch(type){
        // showInfo(this, 'info', this.stock_search_num)
        let data = {
          stock_num: this.stock_search_num.trim()
        }
        if(!this.stock_search_num){
          let num = this.$store.state.is_init
          if(num < 10){
            ++num;
          }else {
            --num;
          }
          this.$store.commit("setIs_init",num)
        }else{
          stockSearch(this, data).then((resp)=>{
            this.stocks = JSON.parse(resp)
            if(type == 1){
              this.$store.commit("setStocks", this.stocks)
            }
          })
        }
      },
      onSelected(value){
        this.stock_search_num = '' + value
        this.handleSearch(1)
        // showInfo(this, 'info', this.stock_search_num)
      }
    }
  }
</script>
