module.exports = function (config) {

  config.set({
    basePath: '..', //!\\ Ignored through gulp-karma //!\\

    files: [ //!\\ Ignored through gulp-karma //!\\
    ],

    autoWatch: false,

    frameworks: ['jasmine'],

    browsers: ['PhantomJS'],

    plugins: [
      'karma-phantomjs-launcher',
      'karma-jasmine'
    ]

  })
}
