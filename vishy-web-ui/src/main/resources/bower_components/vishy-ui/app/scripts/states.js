(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.states', [
    'ui.router'
  ])

    .config(function ($stateProvider, $urlRouterProvider) {
      //$urlRouterProvider.when('', '/demos/overview');
      //$urlRouterProvider.when('/', '/demos/overview');

      //$urlRouterProvider.otherwise('/demos/overview');
      $urlRouterProvider.otherwise('/');

      $stateProvider
        .state('home', {
          url: '/',
          templateUrl: 'partials/main.html',
          controller: 'MainCtrl'
        })
        .state('info', {
          abstract: true,
          url: '/info',
          template: '<ui-view/>'
        })
        .state('info.overview', {
          url: '/overview',
          templateUrl: 'partials/info.html',
          controller: 'NoopCtrl'
        })
        .state('product', {
          abstract: true,
          url: '/product',
          template: '<ui-view/>'
        })
        .state('product.overview', {
          url: '/overview',
          templateUrl: 'partials/product/home.html',
          controller: 'NoopCtrl'
        })
        .state('product.pricing', {
          url: '/pricing',
          templateUrl: 'partials/product/pricing.html',
          controller: 'PricingCtrl'
        })
        .state('app', {
          abstract: true,
          url: '/app',
          template: '<ui-view/>'
        })
        .state('app.login', {
          url: '/login',
          templateUrl: 'partials/login.html',
          controller: 'NoopCtrl'
        });
    })

  ;

})(angular);
