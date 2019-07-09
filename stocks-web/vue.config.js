const webpack = require('webpack')

module.exports = {
  css: {
    sourceMap: true
  },
  publicPath: process.env.NODE_ENV === 'production' ? '/' : './' /** 打包发布时使用相对路径 **/,
  lintOnSave: false /* 禁用eslint检查**/,
  // configureWebpack: {
  //   plugins: [
  //     new webpack.ProvidePlugin({
  //       jquery: 'jquery',
  //       $j: 'jquery'
  //     })
  //   ]
  // }
}
