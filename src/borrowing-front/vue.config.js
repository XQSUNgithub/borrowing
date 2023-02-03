const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath:"./",
  lintOnSave: false,
  productionSourceMap:false,
  devServer:{
    port:8200,
    headers:{
      'Access-Control-Allow-Origin': '*'
    },
    allowedHosts:[
      'jluyyds.ltd',
      '*.jluyyds.ltd',
      "www.localhost.com"
    ],
    historyApiFallback: {
      index: '/'
    },
    proxy:{
      '/serve':{
        target: 'http://localhost:8201',// 后端接口
        changeOrigin: true, // 是否跨域
        secure:false,
        pathRewrite: {
          '/serve': ''
        }
      }
    }
  }
})
