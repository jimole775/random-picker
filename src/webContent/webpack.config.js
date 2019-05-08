var HtmlWebpackPlugin = require("html-webpack-plugin");
var MiniCssExtractPlugin = require("mini-css-extract-plugin");
var CleanWebpackPlugin = require("clean-webpack-plugin");
var path = require("path");
var root = path.join(__dirname,"/");
var srcRoot = path.join(__dirname,"src");
var distRoot = path.join(__dirname,"dist");
module.exports={
   entry:[
       path.join(srcRoot,"index.tsx")
   ],
   
   target: "web",
   output:{
       path:distRoot,
       filename:"bundle.[chunkHash:5].js"
   },
   mode:"development",
   resolve: {
    extensions: [".ts", ".tsx", ".js", "jsx"],
   alias:{
        "types":".d"
   }
   },
   devtool: "inline-source-map",
   plugins:[
    new HtmlWebpackPlugin({
        title: '测试',
        template: path.join(srcRoot,'index.pug'),
        filename: path.join(root,'index.html'),
        inject: 'body',
        favicon: path.join(srcRoot,'favicon.ico')
    }),
    new MiniCssExtractPlugin({
      // Options similar to the same options in webpackOptions.output
      // both options are optional
      filename: path.join(srcRoot,"[name].css"),
      chunkFilename: '[id].css',
    }),
    new CleanWebpackPlugin(),
    
    
   ],
   module: {
    rules: [
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
          // {loader:'style-loader'}, //不能使用style-loader,否则就会报window is not defined
          {loader:'css-loader'},
          {loader:'less-loader'}
        ],
      },
      {
        test: /\.(jsx?|tsx?)$/,
        use: [{
            loader: 'babel-loader'
        }],
        exclude: [/node_modules/,/lib/]
    },
    //   {
    //     test: /\.tsx$/,
    //     // loader: "babel-loader",
    //     // exclude: /node_modules/,
    //     use: [{
    //         loader: 'ts-loader'
    //     }],
    //     exclude: [/node_modules/,/lib/]
    // },
      { test: /\.pug$/, use: [{
        loader: 'pug-loader'
    }] },
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