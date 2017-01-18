(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.dashboard.controllers')
    .controller('NoopDashboardCtrl', [
      function () {
        /**
         * A special controller that does nothing.
         * States can reference it as a placeholder
         * when they have nothing to do yet.
         */
      }])
    .controller('DashboardCtrl', [
      '$scope',
      function ($scope) {
        $scope.model = {
          headline: 'Dashboard'
        };
      }])
    .controller('HomeDashboardCtrl', [
      '$scope',
      function ($scope) {
        $scope.model = {
          headline: 'Home'
        };
      }])
    .controller('OverviewDashboardCtrl', [
      '$scope',
      function ($scope) {
        $scope.model = {
          headline: 'Overview'
        };
      }])
    .controller('OverviewHomeDashboardCtrl', [
      '$scope',
      function ($scope) {
        $scope.model = {
          headline: 'Home'
        };
      }])
    .controller('InitialRequestsDashboardCtrl', [
      '$scope',
      function ($scope) {
        $scope.model = {
          headline: 'Initial Requests'
        };
      }])
    .controller('InitialRequestsHomeDashboardCtrl', [
      '$scope',
      function ($scope) {
        $scope.model = {
          headline: 'Home'
        };
      }])
    .controller('InitialRequestsPercentilesDashboardCtrl', [
      '$scope',
      function ($scope) {
        $scope.model = {
          headline: 'Percentiles'
        };
      }])
    .controller('RawDataDashboardCtrl', [
      '$scope',
      function ($scope) {
        $scope.model = {
          headline: 'Raw Data'
        };
      }])
  ;
})(angular);
