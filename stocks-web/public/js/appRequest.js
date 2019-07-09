
let base_url  = 'http://localhost:8099'

// 请求方法基本定义  所有请求Jun返回Promise对象
// contentType: 'application/x-www-form-urlencoded',
function base_request (vm,url, data) {
  return new Promise((resove,reject)=>{$.ajax({ type: 'post', url: url, data: data,async: false, success: function (resp, status) {resove(resp)} })})
  // return new Promise((resove,reject)=>{vm.$http.post(url, data, {emulateJSON: true}).then(function (resp) { resove(resp.bodyText)}) })
}

// data参数为JSON类型
export const stockSelect = function (vm, data) {return base_request(vm, base_url + '/stockData/select', data)}
export const stockSearch = function (vm, data) {return base_request(vm, base_url + '/stockData/search', data)}
export const stockCount = function (vm, data) {return base_request(vm, base_url + '/stockData/count', data)}
export const stockDetailTimeIntervalSelect = function (vm, data) {return base_request(vm, base_url + '/stockData/latest', data)}
export const stockDetailAllSelect = function (vm, data) {return base_request(vm, base_url + '/stockData/all', data)}
//data 如{ intervalData: 0.05, intervalDay: 30}
export const frequencySelect = function (vm, data) {return base_request(vm, base_url + '/stockData/frequencySelect', data)}





