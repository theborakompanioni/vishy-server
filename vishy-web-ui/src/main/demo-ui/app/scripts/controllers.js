(function (window, document, angular, undefined) {
  'use strict';

  angular.module('tbk.vishy-ui')


    .controller('MainCtrl', [function () {

    }])

    .controller('NoopCtrl', [function () {

    }])

    .value('Pricings', [{
      title: 'Developer',
      costPerMonth: 0,
      currencySign: '$',
      buttonText: 'TRY NOW',
      linkTarget: 'app.login',
      features: [{
        message: '50,000 events/month'
      }, {
        message: '1 project'
      }, {
        message: 'Community support'
      }
      ]
    }, {
      title: 'Startup',
      costPerMonth: 0,
      currencySign: '$',
      buttonText: 'TRY NOW',
      features: [{
        message: '100,000 events/month'
      }, {
        message: '2 projects'
      }, {
        message: 'Mail support'
      }, {
        message: 'A/B Testing'
      }
      ]
    }, {
      title: 'Enterprise',
      costPerMonth: 0,
      currencySign: '$',
      linkTarget: 'app.login',
      buttonText: 'TRY NOW',
      features: [{
        message: '1,000,000 events/month'
      }, {
        message: '10 projects'
      }, {
        message: 'Mail support'
      }, {
        message: 'A/B Testing'
      }, {
        message: 'Plugins'
      }
      ]
    }, {
      title: 'Custom',
      priceMessage: 'Negotiable',
      linkTarget: 'app.login',
      buttonText: 'CONTACT US',
      features: [{
        message: 'Unlimited events/month'
      }, {
        message: 'Unlimited projects'
      }, {
        message: '24/7 support'
      }, {
        message: 'A/B Testing'
      }, {
        message: 'Custom Plugins'
      }, {
        message: 'Custom Script Configurations'
      }
      ]
    }
    ])
    .controller('PricingCtrl', ['$scope', 'Pricings', function ($scope, Pricings) {
      $scope.model = {
        pricings: Pricings
      }
    }])
  ;
})(window, document, angular);
