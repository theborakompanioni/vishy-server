(function (VisSense, Utils) {
  'use strict';

  /************** Google Analytics Client Usage
   var googleMonitor = VisSense.Client.Google(window.ga || function () {})
   .monitors({
      projectId: elementId
    })
   .custom(visobj, {
      interval: 1000,
      throttle: 100,
      inactiveAfter: $scope.model.inactiveAfter
    });

   monitors.push(googleMonitor);
   */

  VisSense.Client = VisSense.Client || {}

  VisSense.Client.Google = function (gaClient) {
    if (!Utils.isFunction(VisSense.Client.Simple)) {
      throw new Error('Cannot load VisSense.Client.Simple. Is it included?');
    }

    if (!gaClient) {
      throw new Error('Please provide a compatible Google Analytics client!');
    }

    return {
      monitors: function (config) {
        var _config = Utils.defaults(config, {
          projectId: null
        });

        var client = {
          addPercentageTimeTestEvent: function (data) {
            var percentage = data.test.testConfig.percentageLimit * 100;
            var seconds = data.test.testConfig.timeLimit / 1000;
            var eventName = percentage + '/' + seconds + ' Test';

            gaClient('send', 'event', 'visibility', _config.projectId, eventName, 1);
          },
          addInitialRequestEvent: function (data) {
            gaClient('send', 'event', 'visibility', _config.projectId, 'Initial Request', 1);

            if (data.state.visible) {
              gaClient('send', 'event', 'visibility', _config.projectId, 'Initial Request Visible', 1);
            }
            if (data.state.hidden) {
              gaClient('send', 'event', 'visibility', _config.projectId, 'Initial Request Hidden', 1);
            }
            if (data.state.fullyvisible) {
              gaClient('send', 'event', 'visibility', _config.projectId, 'Initial Request Fully Visible', 1);
            }
          },
          addTimeReportEvent: function (data) {
            var roundSeconds = function(val) {
              return Math.round(val / 1000);
            };

            var timeVisible = roundSeconds(data.report.timeVisible);
            var timeFullyvisible = roundSeconds(data.report.timeFullyVisible);
            var timeHidden = roundSeconds(data.report.timeHidden);
            var timeRelativeVisible = roundSeconds(data.report.timeRelativeVisible);

            gaClient('send', 'event', 'visibility', _config.projectId, 'Time Report', 1);

            gaClient('send', 'event', 'visibility', _config.projectId, 'Time Report Time Visible', timeVisible);
            gaClient('send', 'event', 'visibility', _config.projectId, 'Time Report Time Hidden', timeHidden);
            gaClient('send', 'event', 'visibility', _config.projectId, 'Time Report Time Fully Visible', timeFullyvisible);
            gaClient('send', 'event', 'visibility', _config.projectId, 'Time Report Time Relative Visible', timeRelativeVisible);
          },
          addEvent: function (eventCollection, data, consumer) {
            // gaClient('send', 'event', 'category', 'action', 'label', value);
            if (eventCollection === 'visibility-percentage-time-test') {
              this.addPercentageTimeTestEvent(data);
            } else if (eventCollection === 'visibility-initial-request') {
              this.addInitialRequestEvent(data);
            } else if (eventCollection === 'visibility-time-report') {
              this.addTimeReportEvent(data);
            }

            consumer(null, data);
          }
        };

        return {
          standard: function (visobj) {
            return VisSense.Client.Simple().monitors(client).standard(visobj);
          },
          custom: function (visobj, config) {
            return VisSense.Client.Simple().monitors(client).custom(visobj, config);
          }
        };
      }
    };
  };

})(VisSense, VisSense.Utils);
