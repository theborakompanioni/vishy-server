(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.dashboard.directives')

    .directive('tbkVishyDevicePiechart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', 'VishyDashboardTimeframe',
          function ($scope, tbkKeen, VishyDashboardTimeframe) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "device.name",
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
