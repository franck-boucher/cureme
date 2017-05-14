const path = require('path');
const webpack = require('webpack');
const merge = require('webpack-merge');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const CleanPlugin = require('clean-webpack-plugin');
const autoprefixer = require('autoprefixer');

const TARGET = process.env.npm_lifecycle_event;
const PATHS = {
    app: path.join(__dirname, 'src/main/resources/public'),
    style: path.join(__dirname, 'src/main/resources/public/styles', 'foundation.scss'),
    build: path.join(__dirname, 'src/main/resources/public/build')
};

const devEntryPointsLoadersAndServers = ['webpack-dev-server/client?http://localhost:3000', 'webpack/hot/only-dev-server'];

const common = {
    entry: [
        'babel-polyfill',
        PATHS.style,
        path.join(PATHS.app, 'jsx/App.jsx')
    ],
    output: {
        path: PATHS.build,
        filename: 'bundle.js',
        publicPath: '/build/'
    },
    plugins: [
        new webpack.ProvidePlugin({
            'Promise': 'es6-promise', // Thanks Aaron (https://gist.github.com/Couto/b29676dd1ab8714a818f#gistcomment-1584602)
            'fetch': 'imports?this=>global!exports?global.fetch!whatwg-fetch'
        })
    ],
    resolve: {
        extensions: [ '', '.js', '.jsx' ]
    },
    module: {
        loaders: [
            { test: /\.png$/, loader: 'url-loader?limit=10000' },
            /* TODO : loaders for TWBS glyphicons ? */
            { test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: 'url-loader?limit=10000&mimetype=application/font-woff' },
            { test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: 'file-loader' },
            /* loader for JSX / ES6 */
            { test: /\.jsx?$/, loaders: ['react-hot', 'babel?cacheDirectory'], include: path.join(PATHS.app, 'jsx')}
        ]
    },
    postcss: function(webpack) {
        return [
            autoprefixer({browsers: ['last 2 versions', 'ie >= 9', 'and_chr >= 2.3']})
        ]
    },
    sassLoader: {
        includePaths: [path.resolve(__dirname, "node_modules")]
    },
    debug: true
};

// Default configuration
if(TARGET === 'start' || !TARGET) {
    module.exports = merge(common, {
        devtool: 'eval-source-map',
        devServer: {
            publicPath: common.output.publicPath,
            contentBase: '/build',
            hot: true,
            inline: true,
            historyApiFallback: true,
            progress: true,
            stats: { colors: true },
            port: 3000,
            proxy : {
                '**': 'http://localhost:8080'
            }
        },
        entry: common.entry.concat(devEntryPointsLoadersAndServers),
        plugins: [
            new webpack.HotModuleReplacementPlugin()
        ],
        module: {
            loaders: [
                {test: /\.scss$/, loaders: ['style', 'css', 'postcss', 'sass?outputStyle=expanded'],
                    include: path.join(PATHS.app, 'styles')},
                {test: /\.css$/, loaders: ['style', 'css', 'postcss'], include: path.join(PATHS.app, 'styles')}
            ]
        }
    });
}
if(TARGET === 'build' || TARGET === 'stats') {
    module.exports = merge(common, {
        module: {
            loaders: [
                {test: /\.scss$/, loader: ExtractTextPlugin.extract('style', 'css!postcss!sass?outputStyle=expanded')},
                {test: /\.css$/, loader: ExtractTextPlugin.extract('style', 'css!postcss')}
            ]
        },
        plugins: [
            new CleanPlugin([PATHS.build]),
            // Setting DefinePlugin affects React library size!
            // DefinePlugin replaces content "as is" so we need some extra quotes
            // for the generated code to make sense
            new webpack.DefinePlugin({
                'process.env.NODE_ENV': '"production"'
            }),
            new webpack.optimize.UglifyJsPlugin({
                compress: {
                    warnings: false
                }
            }),
            new ExtractTextPlugin('styles.min.css', {
                allChunks: true
            })
        ]
    });
}
