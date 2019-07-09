import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    stocks: [],
    total: '',
    is_init: -1
  },
  mutations: {
    setStocks: function (state, stocks) {
      state.stocks = stocks
    },
    setTotal: function (state, total) {
      state.total = total
    },
    setIs_init: function (state, data) {
      state.is_init = data
    }
  },
  actions: {

  }
})
