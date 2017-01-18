(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.dashboard.directives')


    .directive('tbkVishyLanguageTable', [function () {
      var d = {
        scope: {
          height: '@'
        },
        controller: ['$scope', 'tbkKeen', 'VishyDashboardTimeframe',
          function ($scope, tbkKeen, VishyDashboardTimeframe) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "locale.display_language",
            timeframe: VishyDashboardTimeframe
          });
        }],
        template: '<div tbk-keen-table data-query="query" ' +
        ' data-title="All languages" data-height="height">' +
        '</div>'
      };

      return d;
    }])

    .directive('tbkVishyLanguagePiechart', [function () {
      var d = {
        scope: {},
        controller: ['$scope', 'tbkKeen', 'VishyDashboardTimeframe',
          function ($scope, tbkKeen, VishyDashboardTimeframe) {
          $scope.query = new tbkKeen.Query("count", {
            eventCollection: "visibility-initial-request",
            groupBy: "locale.display_language",
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
