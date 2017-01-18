(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.dashboard.states')

    .config(function ($stateProvider, $urlRouterProvider) {
      $urlRouterProvider.when('/dashboard', '/dashboard/home');
      $urlRouterProvider.when('/dashboard/initial-requests', '/dashboard/initial-requests/home');
      $urlRouterProvider.when('/dashboard/overview', '/dashboard/overview/home');
      $urlRouterProvider.when('/dashboard/time-report', '/dashboard/time-report/home');
      $urlRouterProvider.when('/dashboard/percentage-time-test', '/dashboard/percentage-time-test/home');

      $stateProvider
        .state('dashboard', {
          abstract: true,
          url: '/dashboard',
          templateUrl: 'partials/modules/dashboard/home.html',
          controller: 'DashboardCtrl'
        })
        .state('dashboard.home', {
          url: '/home',
          templateUrl: 'partials/modules/dashboard/sections/home.html',
          controller: 'HomeDashboardCtrl'
        })
      /**
       * Overview Dashboard
       */
        .state('dashboard.overview', {
          url: '/overview',
          templateUrl: 'partials/modules/dashboard/sections/overview.html',
          controller: 'OverviewDashboardCtrl'
        })
        .state('dashboard.overview.home', {
          url: '/home',
          templateUrl: 'partials/modules/dashboard/sections/overview/home.html',
          controller: 'OverviewHomeDashboardCtrl'
        })
        .state('dashboard.overview.language', {
          url: '/language',
          templateUrl: 'partials/modules/dashboard/sections/overview/language.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.overview.geolocation', {
          url: '/geolocation',
          templateUrl: 'partials/modules/dashboard/sections/overview/geolocation.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.overview.browser', {
          url: '/browser',
          templateUrl: 'partials/modules/dashboard/sections/overview/browser.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.overview.device', {
          url: '/device',
          templateUrl: 'partials/modules/dashboard/sections/overview/device.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.overview.operatingsystem', {
          url: '/operatingsystem',
          templateUrl: 'partials/modules/dashboard/sections/overview/operatingsystem.html',
          controller: 'NoopDashboardCtrl'
        })
      /**
       * Initial Requests Dashboard
       */
        .state('dashboard.initialRequests', {
          url: '/initial-requests',
          templateUrl: 'partials/modules/dashboard/sections/initial-requests.html',
          controller: 'InitialRequestsDashboardCtrl'
        })
        .state('dashboard.initialRequests.home', {
          url: '/home',
          templateUrl: 'partials/modules/dashboard/sections/initial-requests/home.html',
          controller: 'InitialRequestsHomeDashboardCtrl'
        })
        .state('dashboard.initialRequests.percentiles', {
          url: '/percentiles',
          templateUrl: 'partials/modules/dashboard/sections/initial-requests/percentiles.html',
          controller: 'InitialRequestsPercentilesDashboardCtrl'
        })
        .state('dashboard.initialRequests.rawData', {
          url: '/raw-data',
          templateUrl: 'partials/modules/dashboard/sections/initial-requests/raw-data.html',
          controller: 'NoopDashboardCtrl'
        })
      /**
       * Time Report Dashboard
       */
        .state('dashboard.timeReport', {
          url: '/time-report',
          templateUrl: 'partials/modules/dashboard/sections/time-report.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.timeReport.home', {
          url: '/home',
          templateUrl: 'partials/modules/dashboard/sections/time-report/home.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.timeReport.percentiles', {
          url: '/percentiles',
          templateUrl: 'partials/modules/dashboard/sections/time-report/percentiles.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.timeReport.browser', {
          url: '/browser',
          templateUrl: 'partials/modules/dashboard/sections/time-report/browser.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.timeReport.rawData', {
          url: '/raw-data',
          templateUrl: 'partials/modules/dashboard/sections/time-report/raw-data.html',
          controller: 'NoopDashboardCtrl'
        })
      /**
       * Percentage Time Test Dashboard
       */
        .state('dashboard.percentageTimeTest', {
          url: '/percentage-time-test',
          templateUrl: 'partials/modules/dashboard/sections/percentage-time-test.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.percentageTimeTest.home', {
          url: '/home',
          templateUrl: 'partials/modules/dashboard/sections/percentage-time-test/home.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.percentageTimeTest.percentiles', {
          url: '/percentiles',
          templateUrl: 'partials/modules/dashboard/sections/percentage-time-test/percentiles.html',
          controller: 'NoopDashboardCtrl'
        })
        .state('dashboard.percentageTimeTest.rawData', {
          url: '/raw-data',
          templateUrl: 'partials/modules/dashboard/sections/percentage-time-test/raw-data.html',
          controller: 'NoopDashboardCtrl'
        })
      /**
       * Raw Data Dashboard
       */
        .state('dashboard.rawData', {
          url: '/raw-data',
          templateUrl: 'partials/modules/dashboard/sections/raw-data/home.html',
          controller: 'RawDataDashboardCtrl'
        })
      ;
    })

  ;

})(angular);
