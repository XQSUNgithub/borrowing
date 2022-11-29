const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath:"./",
  lintOnSave: false,
  devServer:{
    port:8889,
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
        target: 'http://localhost:8900',// 后端接口
        changeOrigin: true, // 是否跨域
        secure:false,
        pathRewrite: {
          '/serve': ''
        }
      }
    }
  }
})
