(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.experiment.states')

    .config(function ($stateProvider, $urlRouterProvider) {
      $urlRouterProvider.when('/experiment', '/experiment/home');

      $stateProvider
        .state('experiment', {
          abstract: true,
          url: '/experiment',
          template: '<ui-view/>'
        })
        .state('experiment.home', {
          url: '/home',
          templateUrl: 'partials/modules/experiment/home.html',
          controller: 'ExperimentCtrl'
        });

    })

  ;

})(angular);
