(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.report.states')

    .config(function ($stateProvider, $urlRouterProvider) {
      $urlRouterProvider.when('/report', '/report/home');

      $stateProvider
        .state('report', {
          abstract: true,
          url: '/report',
          template: '<ui-view/>'
        })
        .state('report.home', {
          url: '/home',
          templateUrl: 'partials/modules/report/home.html',
          controller: 'ReportCtrl'
        });

    })

  ;

})(angular);
