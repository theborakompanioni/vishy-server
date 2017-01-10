(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.project.states')

    .config(function ($stateProvider, $urlRouterProvider) {
      $urlRouterProvider.when('/project', '/project/home');

      $stateProvider
        .state('project', {
          abstract: true,
          url: '/project',
          template: '<ui-view/>'
        })
        .state('project.home', {
          url: '/home',
          templateUrl: 'partials/modules/project/home.html',
          controller: 'ProjectCtrl'
        });

    })

  ;

})(angular);
