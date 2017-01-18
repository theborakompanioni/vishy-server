(function (angular) {
  'use strict';

  angular.module('tbk.vishy-ui')

    .filter('reverse', function () {
      return function (items) {
        return items ? items.slice().reverse() : items;
      };
    })

    .filter('range', ['$log', function ($log) {
      return function (input, total) {
        try {
          total = parseInt(total, 10);
          for (var i = 0; i < total; i++) {
            input.push(i);
          }
        } catch (e) {
          $log.warn('invalid input in repeat range' + total);
        }
        return input;
      };
    }])
  ;
})(angular);
