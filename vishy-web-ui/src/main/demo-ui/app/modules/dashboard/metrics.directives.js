(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.dashboard.directives')

    .directive('tbkKeenLinechart', [function() {
      var d = {
        scope: {
          query: '=',
          title: '@',
          width: '@',
          height: '@',
          isStacked: '@',
          chartOptions: '=?'
        },
        controller: ['$scope', function($scope) {
          $scope.chartOptions = $scope.chartOptions || {
            chartArea: {
              height: '85%',
              left: '5%',
              top: '5%',
              width: '80%'
            },
            isStacked: !!$scope.isStacked
          };
        }],
        template:
        '<div data-tbk-keen-chart="linechart" ' +
        ' query="query" ' +
        ' height="{{height}}" ' +
        ' width="{{width}}" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])
    .directive('tbkKeenDefaultLinechart', [function() {
      var d = {
        scope: {
          analysisType: '@',
          eventCollection: '@',
          targetProperty: '@',
          //groupBy: '@',
          queryOptions: '=?',
          chartOptions: '=?',
          interval: '@',
          timeframe: '@',
          title: '@',
          width: '@',
          height: '@'
        },
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {

          $scope.analysisType = $scope.analysisType || 'count';

          $scope.height = $scope.height || '250';
          $scope.width = $scope.width || 'auto';

          $scope.queryOptions = $scope.queryOptions || {};
          $scope.queryOptions.eventCollection = $scope.eventCollection;
          //$scope.queryOptions.groupBy = $scope.groupBy;
          $scope.queryOptions.targetProperty = $scope.targetProperty;
          $scope.queryOptions.interval = $scope.interval || 'daily';
          $scope.queryOptions.timeframe = $scope.timeframe || 'last_7_days';

          $scope.query = new tbkKeen.Query($scope.analysisType, $scope.queryOptions);

          $scope.chartOptions = $scope.chartOptions || {
            chartArea: {
              height: '85%',
              left: '5%',
              top: '5%',
              width: '80%'
            }
          };
        }],
        template:
        '<div data-tbk-keen-chart="linechart" ' +
        ' query="query" ' +
        ' height="{{height}}" ' +
        ' width="{{width}}" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])

    .directive('tbkKeenFunnel', ['tbkKeenClient', function (tbkKeenClient) {

      var d = {
        scope: {
          query: '=',
          labelMapping: '=',
          colors: '=?',
          height: '@',
          library: '@',
          chartType: '@'
        },
        controller: ['$scope', function ($scope) {
          $scope.options = {
            colors: $scope.colors || ['#79CDCD'],
            labelMapping: $scope.labelMapping || ['No mapping'],
            title: $scope.title || null,
            height: $scope.height || 340,
            library: $scope.library || 'google',
            chartType: $scope.chartType === 'columnchart' ? 'columnchart' : 'barchart'
          };
        }],
        link: function ($scope, $element) {

          tbkKeenClient.draw($scope.query, $element[0], {
            library: $scope.options.library,
            chartType: $scope.options.chartType,
            height: $scope.options.height,
            title: $scope.options.title,
            colors: $scope.options.colors,
            labelMapping: $scope.options.labelMapping,
            chartOptions: {
              chartArea: {height: "85%", left: "20%", top: "5%"},
              legend: {position: "none"}
            }
          });
        }
      };

      return d;
    }])

    .directive('tbkVishyTestFunnel', [function () {
      var d = {
        scope: {
          percentile: '@'
        },
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.model = {
            labelMapping: [
              'Browser 1',
              'Browser 2'
            ]
          };

          $scope.query = new tbkKeen.Query("funnel", {
            steps: [{
                event_collection: "visibility-time-report",
                actor_property: "report.timeVisible"
                /*filters: [{
                  "property_name": "initial",
                  "operator": "eq",
                  "property_value": true
                }],
                timeframe: {
                  start: aYearAgo,
                  end: now()
                }*/
              }, {
              event_collection: "visibility-time-report",
              actor_property: "report.timeHidden"
              /*filters: [{
               "property_name": "initial",
               "operator": "eq",
               "property_value": true
               }],*/
            }, {
              event_collection: "visibility-time-report",
              actor_property: "report.timeFullyVisible"
              /*filters: [{
               "property_name": "initial",
               "operator": "eq",
               "property_value": true
               }],*/
            }
            ]
          });
        }],
        template: '<div tbk-keen-funnel data-query="query" ' +
        ' label-mapping="model.labelMapping">' +
        '</div>'
      };

      return d;
    }])


  ;

})(angular);
