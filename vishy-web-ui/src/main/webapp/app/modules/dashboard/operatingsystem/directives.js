(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.dashboard.directives')

    .directive('tbkVishyOperatingSystemTable', [function () {
      var d = {
        scope: {
          height: '@'
        },
        controller: ['$scope', 'tbkKeen', 'VishyDashboardTimeframe',
          function ($scope, tbkKeen, VishyDashboardTimeframe) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "os.groupName",
            timeframe: VishyDashboardTimeframe
          });
        }],
        template: '<div tbk-keen-table data-query="query" ' +
        ' data-title="All Operating Systems" data-height="height">' +
        '</div>'
      };

      return d;
    }])

    .directive('tbkVishyOperatingSystemPiechart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', 'VishyDashboardTimeframe',
          function ($scope, tbkKeen, VishyDashboardTimeframe) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "os.groupName",
            timeframe: VishyDashboardTimeframe
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
  ;

})(angular);
