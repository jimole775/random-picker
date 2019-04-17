var HtmlWebpackPlugin = require("html-webpack-plugin");
var MiniCssExtractPlugin = require("mini-css-extract-plugin");
var path = require('path');
module.exports={
   entry:[path.join(__dirname,"react-cli-demo/src/index.js")],
   
   target: "web",
   output:{
       path:__dirname,
       filename:"bundle.js"
   },

   plugins:[
    new HtmlWebpackPlugin({
        title: '测试',
        template: path.join(__dirname,'react-cli-demo/public/index.html'),
        filename: path.join(__dirname,'index.html'),
        inject: 'body',
        favicon: path.join(__dirname,'react-cli-demo/public/favicon.ico')
    }),
    new MiniCssExtractPlugin({
      // Options similar to the same options in webpackOptions.output
      // both options are optional
      filename: '[name].css',
      chunkFilename: '[id].css',
    }),
   ],
   module: {
    rules: [
      // { test: /\.(css|less)$/, 
      //   use: [
      //     { loader: "style-loader" },
      //     { loader: "css-loader" }
      //   ]
      //  },
       {
        test: /\.(css|less)$/, 
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
            options: {
              // you can specify a publicPath here
              // by default it uses publicPath in webpackOptions.output
              // publicPath: '../',
              hmr: true, //hot-module-reload
              reloadAll:true,
            },
          },
          // {loader:'style-loader'}, //不能使用style-loader,使用就会报window is not defined
          {loader:'css-loader'},
          {loader:'less-loader'}
        ],
      },
      {
        test: /\.(js|jsx)$/,
        // loader: "babel-loader",
        // exclude: /node_modules/,
        use: {
            loader: 'babel-loader'
        },
        exclude: [/node_modules/,/lib/]
    },
      { test: /\.jade$/, use: 'jade-loader' },
      {
        test: /\.(jpe?g|png|gif|svg)$/i,
        use: [
            // {
            //     loader: 'url-loader',
            //     options: {
                    
            //     }
            // },
            {
                loader: 'file-loader',
                options: {
                    outputPath: 'images',
                    name: '[hash].[ext]'
                }
            },
            {
                loader: 'image-webpack-loader',
                options: {
                    mozjpeg: {
                        progressive: true
                    },
                    gifsicle: {
                        interlaced: true
                    },
                    optipng: {
                        optimizationLevel: 7
                    }
                }

            }]
    }
    ]
  }
   
}