'use strict';

var gulp = require('gulp');

var $ = require('gulp-load-plugins')();

var wiredep = require('wiredep');

gulp.task('test', function () {
  var bowerDeps = wiredep({
    directory: 'app/bower_components',
    exclude: ['bootstrap-sass-official'],
    dependencies: true,
    devDependencies: true
  });

  var testFiles = bowerDeps.js.concat([
    'app/bower_components/tbk-angular-background-animations/dist/tbk-angular-background-animations.js',
    'app/bower_components/vissense-metrics/dist/vissense.metrics.js',

    'app/lib/**/*.js',

    'app/scripts/app.js',
    'app/scripts/states.js',
    'app/scripts/directives.js',
    'app/scripts/controllers.js',

    'app/modules/**/index.js',
    'app/modules/**/*.js',

    'test/unit/**/*.js'
  ]);

  return gulp.src(testFiles)
    .pipe($.karma({
      configFile: 'test/karma.conf.js',
      action: 'run'
    }))
    .on('error', function (err) {
      // Make sure failed tests cause gulp to exit non-zero
      throw err;
    });
});
