(function (angular, moment) {


  var now = function () {
    return moment().toISOString();
  };
  var tomorrow = moment().add(1, 'days').toISOString();
  var yesterday = moment().subtract(1, 'days').toISOString();
  var aWeekAgo = moment().subtract(7, 'days').toISOString();
  var aMonthAgo = moment().subtract(1, 'months').toISOString();
  var aYearAgo = moment().subtract(1, 'years').toISOString();


  angular.module('angular-keenio-demo-directives', [])

    .directive('tbkVishyInitialRequestVisibilityPiechart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "state.state",
            timeframe: {
              start: aMonthAgo,
              end: tomorrow
            }
          });

          $scope.chartOptions = {
            chartArea: {
              height: "85%",
              left: "5%",
              top: "5%",
              width: "100%"
            },
            pieHole: 0.4
          };
        }],
        template: '<div data-tbk-keen-piechart ' +
        ' query="query" ' +
        ' height="250" ' +
        ' width="auto" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])

    .directive('tbkVishyInitialBrowserPiechart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "browser.name",
            timeframe: {
              start: aMonthAgo,
              end: tomorrow
            }
          });

          $scope.chartOptions = {
            chartArea: {
              height: "85%",
              left: "5%",
              top: "5%",
              width: "100%"
            },
            pieHole: 0.4
          };
        }],
        template: '<div data-tbk-keen-piechart ' +
        ' query="query" ' +
        ' height="250" ' +
        ' width="auto" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])

    .directive('tbkVishyRequestByStatePiechart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "state.state",
            timeframe: {
              start: aMonthAgo,
              end: tomorrow
            }
          });

          $scope.chartOptions = {
            chartArea: {
              height: "85%",
              left: "5%",
              top: "5%",
              width: "100%"
            },
            pieHole: 0.4
          };
        }],
        template: '<div data-tbk-keen-piechart ' +
        ' query="query" ' +
        ' height="250" ' +
        ' width="auto" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])

    .directive('tbkVishyBrowserHourlyAreachart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "browser.name",
            interval: "hourly",
            timeframe: {
              start: yesterday,
              end: now()
            }
          });

          $scope.chartOptions = {
            chartArea: {
              height: "85%",
              left: "5%",
              top: "5%",
              width: "80%"
            },
            isStacked: true
          };
        }],
        template: '<div data-tbk-keen-areachart ' +
        ' query="query" ' +
        ' height="250" ' +
        ' width="auto" ' +
        ' is-stacked="true" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])
    .directive('tbkVishyInitialBrowserDailyColumnchart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', 'VishyDashboardTimeframe',
          function ($scope, tbkKeen, VishyDashboardTimeframe) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "browser.name",
            interval: "daily",
            timeframe: VishyDashboardTimeframe
          });
          $scope.chartOptions = {
            chartArea: {
              height: "75%",
              left: "10%",
              top: "5%",
              width: "60%"
            },
            isStacked: true,
            groupWidth: "85%"
          };
        }],
        template: '<div data-tbk-keen-columnchart ' +
        ' query="query" ' +
        ' height="250" ' +
        ' width="auto" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])

    .directive('tbkVishyInitialBrowserHourlyColumnchart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "browser.name",
            interval: "hourly",
            timeframe: {
              start: yesterday,
              end: now()
            }
          });
          $scope.chartOptions = {
            chartArea: {
              height: "75%",
              left: "10%",
              top: "5%",
              width: "60%"
            },
            isStacked: true,
            groupWidth: "85%"
          };
        }],
        template: '<div data-tbk-keen-columnchart ' +
        ' query="query" ' +
        ' height="250" ' +
        ' width="auto" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])


    .directive('tbkVishyInitialBrowserMonthlyColumnchart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "browser.name",
            interval: "monthly",
            timeframe: {
              start: aYearAgo,
              end: tomorrow
            }
          });
          $scope.chartOptions = {
            chartArea: {
              height: "75%",
              left: "10%",
              top: "5%",
              width: "60%"
            },
            isStacked: true,
            groupWidth: "85%"
          };
        }],
        template: '<div data-tbk-keen-columnchart ' +
        ' query="query" ' +
        ' height="250" ' +
        ' width="auto" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])


    .directive('tbkVishySessionAreachart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("count_unique", {
            eventCollection: "visibility-initial-request",
            //groupBy: "sessionId",
            targetProperty: "sessionId",
            interval: "hourly",
            timeframe: {
              start: yesterday,
              end: now()
            }
          });

          $scope.chartOptions = {
            chartArea: {
              height: "85%",
              left: "5%",
              top: "5%",
              width: "100%"
            },
            isStacked: true
          };
        }],
        template: '<div data-tbk-keen-areachart ' +
        ' query="query" ' +
        ' height="250" ' +
        ' width="auto" ' +
        ' is-stacked="true" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])
    .filter('vishyReadableTimeRecusiveWithMinUnitSeconds', [
      'tbkReadableTimeRecursiveFilter', function (tbkReadableTimeRecursiveFilter) {
        return function (value) {
          return tbkReadableTimeRecursiveFilter(value, 'second');
        }
      }])
    .directive('tbkVishyTimeReportVisibleRecursive', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query('sum', {
            eventCollection: 'visibility-time-report',
            targetProperty: 'report.timeVisible',
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          });
        }],
        template: '<div tbk-keen-metric ' +
        ' data-filter="vishyReadableTimeRecusiveWithMinUnitSeconds" ' +
        ' data-query="query"></div>'
      };

      return d;
    }])
    .directive('tbkVishyTimeReportVisibleSecondsSumMetric', [function () {
      var d = {
        scope: {
          id: '@',
          projectId: '@'
        },
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query('sum', {
            eventCollection: 'visibility-time-report',
            targetProperty: 'report.timeVisible',
            filters: [{
              'property_name': 'vishy.projectId',
              'operator': 'eq',
              'property_value': $scope.projectId
            }, {
              'property_name': 'vishy.id',
              'operator': 'eq',
              'property_value': $scope.id
            }],
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          });
        }],
        template: '<div tbk-keen-metric ' +
        ' data-filter="tbkReadableTime" ' +
        ' data-scale="3" ' +
        ' data-query="query"></div>'
      };

      return d;
    }])
    .directive('tbkVishyTimeReportFullyvisibleSecondsSumMetric', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query('sum', {
            eventCollection: 'visibility-time-report',
            targetProperty: 'report.timeFullyVisible',
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          });
        }],
        template: '<div tbk-keen-metric ' +
        ' data-filter="tbkReadableTime" ' +
        ' data-scale="3" ' +
        ' data-query="query"></div>'
      };

      return d;
    }])
    .directive('tbkVishyTimeReportHiddenSecondsSumMetric', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query('sum', {
            eventCollection: 'visibility-time-report',
            targetProperty: 'report.timeHidden',
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          });
        }],
        template: '<div tbk-keen-metric ' +
        ' data-filter="tbkReadableTime" ' +
        ' data-scale="3" ' +
        ' data-query="query"></div>'
      };

      return d;
    }])

    .directive('tbkVishyInitialRequestPercentageAverageMetric', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("average", {
            eventCollection: "visibility-initial-request",
            targetProperty: "state.percentage",
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          });
        }],
        template: '<div tbk-keen-metric data-query="query" ' +
        ' data-postfix="%" data-scale="0" data-factor="100">' +
        '</div>'
      };

      return d;
    }])

    .directive('tbkVishyInitialRequestCountMetric', [function () {
      var d = {
        scope: {
          percentile: '@'
        },
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          });
        }],
        template: '<div tbk-keen-metric data-query="query" ' +
        ' data-postfix="" data-scale="0" data-factor="0">' +
        '</div>'
      };

      return d;
    }])

    .directive('tbkVishyInitialRequestPercentagePercentileMetric', [function () {
      var d = {
        scope: {
          percentile: '@'
        },
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("percentile", {
            eventCollection: "visibility-initial-request",
            targetProperty: "state.percentage",
            percentile: $scope.percentile || 50,
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          });
        }],
        template: '<div tbk-keen-metric data-query="query" ' +
        ' data-postfix="%" data-scale="0" data-factor="100">' +
        '</div>'
      };

      return d;
    }])

    .directive('tbkVishyInitialRequestRawDataTable', [function () {
      var d = {
        scope: {},
        controller: ['$scope', function ($scope) {
          $scope.queryOptions = {
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          };
        }],
        template: '<div tbk-keen-default-table ' +
        ' data-title="All requests" ' +
        ' data-query-options="queryOptions" ' +
        ' data-event-collection="visibility-initial-request" ' +
        '></div>'
      };

      return d;
    }])

    .directive('tbkVishyVisibilityTimeReportRawDataTable', [function () {
      var d = {
        scope: {},
        controller: ['$scope', function ($scope) {
          $scope.queryOptions = {
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          };
        }],
        template: '<div tbk-keen-default-table ' +
        ' data-title="All requests" ' +
        ' data-query-options="queryOptions" ' +
        ' data-event-collection="visibility-time-report" ' +
        '></div>'
      };

      return d;
    }])

    .directive('tbkVishyVisibilityPercentageTimeTestRawDataTable', [function () {
      var d = {
        scope: {},
        controller: ['$scope', function ($scope) {
          $scope.queryOptions = {
            timeframe: {
              start: aYearAgo,
              end: now()
            }
          };
        }],
        template: '<div tbk-keen-default-table ' +
        ' data-title="All requests" ' +
        ' data-query-options="queryOptions" ' +
        ' data-event-collection="visibility-percentage-time-test" ' +
        '></div>'
      };

      return d;
    }])

    .directive('tbkVishyInitialDeviceDailyColumnchart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "device.name",
            interval: "daily",
            timeframe: {
              start: aWeekAgo,
              end: tomorrow
            }
          });
          $scope.chartOptions = {
            chartArea: {
              height: "75%",
              left: "10%",
              top: "5%",
              width: "60%"
            },
            isStacked: true,
            groupWidth: "85%"
          };
        }],
        template: '<div data-tbk-keen-columnchart ' +
        ' query="query" ' +
        ' height="250" ' +
        ' width="auto" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])

    .directive('tbkVishyPercentageTimeTestPercentileMetricChart', [function () {
      var d = {
        scope: {
          percentile: '@',
          timeLimit: '@',
          percentageLimit: '@',
          targetProperty: '@'
        },
        controller: ['$scope', function ($scope) {
          $scope.queryOptions = {
            percentile: $scope.percentile,
            filters: [{
              'property_name': 'test.testConfig.timeLimit',
              'operator': 'eq',
              'property_value': $scope.timeLimit
            }, {
              'property_name': 'test.testConfig.percentageLimit',
              'operator': 'eq',
              'property_value': $scope.percentageLimit
            }]
          };
        }],
        templateUrl: 'partials/modules/dashboard/sections/percentage-time-test/directives/' +
        'tbk-vishy-percentage-time-test-percentile-metric-chart.html'
      };

      return d;
    }])
  ;

})(angular, moment);
