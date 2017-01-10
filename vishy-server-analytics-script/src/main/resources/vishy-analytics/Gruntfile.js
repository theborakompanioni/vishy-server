module.exports = function (grunt) {
  'use strict';

  require('time-grunt')(grunt);

  grunt.initConfig({

    dirs: {
      bower: 'bower_components',
      src: 'src',
      build: 'dist',
      coverage: 'coverage'
    },

    pkg: grunt.file.readJSON('package.json'),

    banner: '/*! { ' +
    '"name": "<%= pkg.name %>", ' +
    '"version": "<%= pkg.version %>", ' +
    '<%= pkg.homepage ? "\\"homepage\\": \\"" + pkg.homepage + "\\"," : "" %>' +
    '"copyright": "(c) <%= grunt.template.today("yyyy") %> <%= pkg.author.name %>" ' +
    '} */\n',

    jshint: {
      options: {
        jshintrc: true
      },
      gruntfile: {
        src: 'Gruntfile.js'
      },
      source: {
        src: ['src/**/*.js']
      },
      jasmine: {
        src: ['spec/**/*.js']
      },
      karma: {
        src: ['karma.conf.js']
      }
    },
    concat: {
      options: {
        separator: ';'
      },
      dist: {
        src: [
          // dependencies
          '<%= dirs.bower %>/vissense/dist/vissense.min.js',
          '<%= dirs.bower %>/vissense-percentage-time-test/dist/vissense-percentage-time-test.min.js',
          '<%= dirs.bower %>/vissense-metrics/dist/vissense.metrics.min.js',
          '<%= dirs.bower %>/vissense-user-activity/dist/vissense-user-activity.min.js',
          // project files
          '<%= dirs.src %>/vissense-simple-client-monitor/index.js',
          '<%= dirs.src %>/vissense-simple-vishy-client-monitor/index.js',
          '<%= dirs.src %>/vishy-analytics.js'
        ],
        dest: '<%= dirs.build %>/<%= pkg.name %>.js'
      }
    },
    uglify: {
      dist: {
        options: {
          banner: '',
          report: 'gzip',
          compress: {
            drop_console: true
          },
          sourceMap: false
        },
        src: '<%= dirs.build %>/<%= pkg.name %>.js',
        dest: '<%= dirs.build %>/<%= pkg.name %>.min.js'
      }
    },
    compress: {
      dist: {
        options: {
          mode: 'gzip'
        },
        files: [
          {
            expand: true,
            cwd: '<%= dirs.build %>',
            src: ['*.js', '!*/*.min.js'],
            dest: '<%= dirs.build %>',
            ext: '.js.gz'
          }, {
            expand: true,
            cwd: '<%= dirs.build %>',
            src: ['*.min.js'],
            dest: '<%= dirs.build %>',
            ext: '.min.js.gz'
          }
        ]
      }
    },
    jasmine: {
      js: {
        src: '<%= dirs.build %>/<%= pkg.name %>.js',
        options: {
          display: 'full',
          summary: true,
          specs: 'spec/*Spec.js',
          helpers: 'spec/*Helper.js'
        }
      },
      coverage: {
        src: [
          '<%= dirs.src %>/vissense-simple-client-monitor/index.js',
          '<%= dirs.src %>/vissense-simple-vishy-client-monitor/index.js',
          '<%= dirs.src %>/vishy-analytics.js'
        ],
        options: {
          specs: ['spec/*Spec.js'],
          helpers: [
            '<%= dirs.bower %>/vissense/dist/vissense.min.js',
            '<%= dirs.bower %>/vissense-percentage-time-test/dist/vissense-percentage-time-test.min.js',
            '<%= dirs.bower %>/vissense-metrics/dist/vissense.metrics.min.js',
            '<%= dirs.bower %>/vissense-user-activity/dist/vissense-user-activity.min.js',
          ],
          template: require('grunt-template-jasmine-istanbul'),
          templateOptions: {
            coverage: '<%= dirs.coverage %>/coverage.json',
            report: [{
              type: 'html',
              options: {
                dir: '<%= dirs.coverage %>/html'
              }
            }, {
              type: 'cobertura',
              options: {
                dir: '<%= dirs.coverage %>/cobertura'
              }
            }, {
              type: 'lcov',
              options: {
                dir: '<%= dirs.coverage %>/lcov'
              }
            }, {
              type: 'text-summary'
            }
            ]
          }
        }
      }
    },
    karma: {
      unit: {
        configFile: 'karma.conf.js'
      }
    },
    coveralls: {
      options: {
        force: true
      },
      target: {
        src: '<%= dirs.coverage %>/lcov/lcov.info'
      }
    },
    notify: {
      js: {
        options: {
          title: 'Javascript - <%= pkg.title %>',
          message: 'Minified and validated with success!'
        }
      }
    }
  });

  require('time-grunt')(grunt);

  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-compress');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-jasmine');

  grunt.loadNpmTasks('grunt-karma');

  grunt.loadNpmTasks('grunt-coveralls');
  grunt.loadNpmTasks('grunt-notify');

  grunt.registerTask('test', ['jasmine', 'karma'/*, 'coveralls'*/]);
  grunt.registerTask('default', ['jshint', 'concat', 'uglify', 'compress', 'test', 'notify:js']);
};
