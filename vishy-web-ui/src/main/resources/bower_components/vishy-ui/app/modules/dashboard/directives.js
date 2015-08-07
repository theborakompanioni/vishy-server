(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.dashboard.directives')

    .directive('tbkVishyDashboardTimeframeSelect', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'VishyDashboardTimeframe',
          function ($scope, VishyDashboardTimeframe) {
            $scope.model = VishyDashboardTimeframe;
          }],
        template: '<div>' +
        '<div data-ng-click="showForm = !showForm" style="cursor: pointer">' +
        '{{model.start | date }} - {{model.end | date }}' +
        '</div>' +
        '<form data-ng-show="showForm">' +
        '<div class="form-group">' +
        '<label for="dashboardTimeframeStart">Start</label>' +
        '<input data-ng-model="model.start" type="date" class="form-control" id="dashboardTimeframeStart" placeholder="">' +
        '</div>' +
        '<div class="form-group">' +
        '<label for="dashboardTimeframeEnd">End</label>' +
        '<input data-ng-model="model.end" type="date" class="form-control" id="dashboardTimeframeEnd" placeholder="">' +
        '</div>' +
        '</form>' +
        '<div>'
      };

      return d;
    }])

    .directive('tbkDashboardLeadMetric', function () {
      return {
        restrict: 'E',
        transclude: true,
        scope: {
          title: '@',
          icon: '@',
          panelClass: '@'
        },
        templateUrl: 'partials/modules/dashboard/partials/lead-metric.html'
      };
    })
    .directive('tbkVishyDefaultTable', [function () {
      var d = {
        scope: {
          title: '@',
          height: '@',
          analysisType: '@',
          eventCollection: '@',
          groupBy: '@'
        },
        controller: ['$scope', 'tbkKeen', 'VishyDashboardTimeframe',
          function ($scope, tbkKeen, VishyDashboardTimeframe) {
            $scope.title = $scope.title || '';
            $scope.height = $scope.height || 100;

            $scope.query = new tbkKeen.Query($scope.analysisType, {
              eventCollection: $scope.eventCollection,
              groupBy: $scope.groupBy,
              timeframe: VishyDashboardTimeframe
            });
          }],
        template: '<div tbk-keen-table data-query="query" ' +
        ' data-title="{{title}}" data-height="{{height}}">' +
        '</div>'
      };

      return d;
    }])

    .directive('tbkVishyDefaultPiechart', [function () {
      var d = {
        scope: {
          analysisType: '@',
          eventCollection: '@',
          groupBy: '@',
          title: '@',
          width: '@',
          height: '@'
        },
        controller: ['$scope', 'VishyDashboardTimeframe',
          function ($scope, VishyDashboardTimeframe) {

            $scope.height = $scope.height || 250;
            $scope.width = $scope.width || 'auto';

            $scope.eventCollection = $scope.eventCollection || 'visibility-initial-request';
            $scope.queryOptions = {
              timeframe: VishyDashboardTimeframe
            };

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
        template: '<div data-tbk-keen-default-piechart ' +
        ' event-collection="{{eventCollection}}" ' +
        ' analysis-type="{{analysisType}}" ' +
        ' group-by="{{groupBy}}" ' +
        ' height="{{height}}" ' +
        ' width="{{width}}" ' +
        ' query-options="queryOptions" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])


    .directive('tbkVishyDefaultAreachart', [function () {
      var d = {
        scope: {
          analysisType: '@',
          eventCollection: '@',
          targetProperty: '@',
          groupBy: '@',
          interval: '@',
          timeframe: '@',
          isStacked: '@'
        },
        controller: ['$scope', 'tbkKeen', 'VishyDashboardTimeframe',
          function ($scope, tbkKeen, VishyDashboardTimeframe) {
          $scope.width = $scope.width || 'auto';
          $scope.height = $scope.height || 250;

          $scope.query = new tbkKeen.Query($scope.analysisType, {
            eventCollection: $scope.eventCollection,
            targetProperty: $scope.targetProperty,
            groupBy: $scope.groupBy || undefined,
            interval: $scope.interval || 'daily',
            timeframe: VishyDashboardTimeframe
          });

          $scope.isStacked = $scope.isStacked || false;
          $scope.chartOptions = $scope.chartOptions || {
            chartArea: {
              height: "85%",
              left: "5%",
              top: "5%",
              width: "80%"
            }
          };
          $scope.chartOptions.isStacked = $scope.isStacked;
        }],
        template: '<div data-tbk-keen-areachart ' +
        ' query="query" ' +
        ' height="{{height}}" ' +
        ' width="{{width}}" ' +
        ' is-stacked="{{isStacked}}" ' +
        ' chart-options="chartOptions" ' +
        '></div>'
      };

      return d;
    }])

  ;

})(angular);
