(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.showcase.states')

    .config(function ($stateProvider, $urlRouterProvider) {
      $urlRouterProvider.when('/showcase', '/showcase/home');

      $stateProvider
        .state('showcase', {
          abstract: true,
          url: '/showcase',
          template: '<ui-view/>'
        })
        .state('showcase.home', {
          url: '/home',
          templateUrl: 'partials/modules/showcase/home.html',
          controller: 'ShowcaseCtrl'
        })
        .state('showcase.multi-element-showcase', {
          url: '/multi-element-showcase',
          templateUrl: 'partials/modules/showcase/multi-element-showcase.html',
          controller: 'ShowcaseCtrl'
        });

    })

  ;

})(angular);
