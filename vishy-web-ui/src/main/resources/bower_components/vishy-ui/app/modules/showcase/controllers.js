(function (window, angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.showcase.controllers')

    .factory('ShowcaseMonitorFactory', ['$http', 'VisSense', 'tbkVishyConfig', 'tbkKeenClient',
      function ($http, VisSense, tbkVishyConfig, tbkKeenClient) {

        //return VisSense.Client.Vishy(tbkVishyConfig, $http);
        return VisSense.Client.KeenIO(tbkKeenClient);
      }])

    .controller('ShowcaseCtrl', [
      '$window', '$scope', '$timeout', '$http', 'VisSense', 'VisUtils', 'ShowcaseMonitorFactory',
      function ($window, $scope, $timeout, $http, VisSense, VisUtils, ShowcaseMonitorFactory) {
        var autoStop = 90;
        $scope.model = {
          running: false,
          autoStop: autoStop,
          autoStopCountdown: autoStop,
          inactiveAfter: 15000,
          hidden: 0.4999,
          requests: []
        };
        var resetRequestList = function() {
          $scope.model.requests = [];
        };
        var addToRequestList = function(name, data) {
          $scope.model.requests.push({
            name: name,
            data: data
          });
        };

        var stop = angular.noop;

        var startCountdown = function () {
          var autoStopTimeoutId = $timeout(function () {
            stop();
          }, $scope.model.autoStopCountdown * 1000);

          var countDownTimeoutId = $timeout(function countDownAutoStopCountdown() {
            $scope.model.autoStopCountdown--;
            countDownTimeoutId = $timeout(function () {
              countDownAutoStopCountdown();
            }, 1000);
          }, 1000);

          return function () {
            $timeout.cancel(autoStopTimeoutId);
            $timeout.cancel(countDownTimeoutId);
          };
        };

        var start = function (ids) {
          var elementIds = ids;
          if (!VisUtils.isArray(ids)) {
            elementIds = [ids];
          }

          $scope.model.running = true;
          $scope.model.autoStopCountdown = autoStop;

          resetRequestList();
          var monitors = [];
          VisUtils.forEach(elementIds, function (elementId) {
            var element = angular.element('#' + elementId)[0];
            var visobj = VisSense.of(element, {
              hidden: $scope.model.hidden
            });

            var showcaseMonitor = ShowcaseMonitorFactory
              .monitors({
                projectId: elementId
              }).custom(visobj, {
                interval: 1000,
                throttle: 100,
                inactiveAfter: $scope.model.inactiveAfter
              });

            monitors.push(showcaseMonitor);
          });

          VisUtils.forEach(monitors, function (monitor) {
            monitor.on('visibility-initial-request', function(monitor, data) {
              console.table(data);
              addToRequestList('visibility-initial-request', data);
            });
            monitor.on('visibility-status-50/1', function(monitor, data) {
              console.table(data);
              addToRequestList('visibility-status-50/1', data);
            });
            monitor.on('visibility-time-report', function(monitor, data) {
              console.table(data);
              addToRequestList('visibility-time-report', data);
            });

            monitor.start();
          });

          var removeBeforeUnloadStopCallback = (function () {
            var eventId = $window.addEventListener('beforeunload', function () {
              stop();
            }, true);

            return function () {
              $window.removeEventListener('beforeunload', eventId, true);
            }
          })();

          var cancelAutoStop = startCountdown();
          stop = function () {
            cancelAutoStop();
            removeBeforeUnloadStopCallback();

            VisUtils.forEach(monitors, function (monitor) {
              monitor.stop();
            });

            $scope.model.running = false;
          };
        };

        $scope.start = function (elementIdOrIds) {
          start(elementIdOrIds);
        };

        $scope.stop = function () {
          stop();
        };

        $scope.$on('$destroy', function () {
          console.log('scope destroy');
          stop();
        });

      }])
  ;
})(window, angular);
