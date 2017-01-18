(function (angular) {
  'use strict';

  var now = function () {
    return moment().toISOString();
  };
  var tomorrow = moment().add(1, 'days').toISOString();
  var yesterday = moment().subtract(1, 'days').toISOString();
  var aWeekAgo = moment().subtract(7, 'days').toISOString();
  var aMonthAgo = moment().subtract(1, 'months').toISOString();
  var aYearAgo = moment().subtract(1, 'years').toISOString();

  angular.module('org.tbk.vishy.ui.project.directives')

    .directive('tbkVishyProjectRawDataTable', [function () {
      var d = {
        scope: {},
        controller: ['$scope', function ($scope) {
          $scope.queryOptions = {
            targetProperty: 'vishy.id',
            groupBy: 'vishy.projectId',
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

    .directive('tbkVishyProjectVisibleTimeAveragePiechart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("average", {
            eventCollection: "visibility-time-report",
            groupBy: "vishy.projectId",
            targetProperty: "report.timeVisible",
            timeframe: {
              start: aMonthAgo,
              end: tomorrow
            }
          });
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

    .directive('tbkVishyProjectFullyvisibleTimeAveragePiechart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', function ($scope, tbkKeen) {
          $scope.query = new tbkKeen.Query("average", {
            eventCollection: "visibility-time-report",
            groupBy: "vishy.projectId",
            targetProperty: "report.timeFullyVisible",
            timeframe: {
              start: aMonthAgo,
              end: tomorrow
            }
          });
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


    .directive('tbkVishyProjectComparePiechart', [function () {
      var d = {
        scope: {
          projectIds: '=',
          analysisType: '@',
          eventCollection: '@',
          targetProperty: '@'
        },
        controller: ['$scope', 'tbkKeen', '$timeout', function ($scope, tbkKeen, $timeout) {
          var query = function() {
            return new tbkKeen.Query($scope.analysisType || 'count', {
              eventCollection: $scope.eventCollection,
              groupBy: "vishy.projectId",
              targetProperty: $scope.targetProperty,
              filters: [{
                "property_name": "vishy.projectId",
                "operator": "in",
                "property_value": $scope.projectIds || []
              }],
              timeframe: {
                start: aMonthAgo,
                end: tomorrow
              }
            });
          };

          $scope.query = query();

          $scope.reload = function() {
            $scope.loading = true;
            $scope.query = query();
            $timeout(function() {
              $scope.loading = false;
            }, 0);
          };

          $scope.$watchCollection('projectIds', function(newValue) {
            if(newValue && newValue.length > 1) {
              $scope.reload();
            }
          })
        }],
        template: '<div>' +
        '  <div data-ng-if="!loading" data-tbk-keen-piechart ' +
        '    query="query" ' +
        '    height="450" ' +
        '    width="auto" ' +
        '    chart-options="chartOptions" ' +
        '  ></div>' +
        '</div>'
      };

      return d;
    }])
  ;

})(angular);
