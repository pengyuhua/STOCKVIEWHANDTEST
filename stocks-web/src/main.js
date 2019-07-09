import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import iview from 'iview'
import vueResource from 'vue-resource'
import 'iview/dist/styles/iview.css'
import moment from 'moment'
import echarts from 'echarts'


moment.locale('zh-cn')

//  自定义日期格式过滤器格式化数字类型日期数据
Vue.filter('dateTimeFormat', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(new Date(parseInt(dataStr))).format(pattern)
})
Vue.prototype.$echarts = echarts


Vue.use(iview)
Vue.use(vueResource)
Vue.use(echarts)


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
