(function (VisSense, Utils) {
  'use strict';

  /************** Piwik Client Usage
   var piwikClient = window._paq || [];

   var simplePiwikClientMonitor = VisSense.Client.Piwik(piwikClient).monitors({
              projectId: elementId
            })
   .custom(visobj, {
              interval: 1000,
              throttle: 100,
              inactiveAfter: $scope.model.inactiveAfter
            });

   monitors.push(simplePiwikClientMonitor); */

  VisSense.Client = VisSense.Client || {}

  VisSense.Client.Piwik = function (piwikClient) {
    if (!Utils.isFunction(VisSense.Client.Simple)) {
      throw new Error('Cannot load VisSense.Client.Simple. Is it included?');
    }

    if (!piwikClient || !piwikClient.push) {
      throw new Error('Please provide a compatible Piwik client!');
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

            piwikClient.push(['trackEvent', 'visibility', _config.projectId, eventName, 1]);
          },
          addInitialRequestEvent: function (data) {
            piwikClient.push(['trackEvent', 'visibility', _config.projectId, 'Initial Request', 1]);

            if (data.state.visible) {
              piwikClient.push(['trackEvent', 'visibility', _config.projectId, 'Initial Request Visible', 1]);
            }
            if (data.state.hidden) {
              piwikClient.push(['trackEvent', 'visibility', _config.projectId, 'Initial Request Hidden', 1]);
            }
            if (data.state.fullyvisible) {
              piwikClient.push(['trackEvent', 'visibility', _config.projectId, 'Initial Request Fully Visible', 1]);
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

            piwikClient.push(['trackEvent', 'visibility', _config.projectId, 'Time Report', 1]);

            piwikClient.push(['trackEvent', 'visibility', _config.projectId, 'Time Report Time Visible', timeVisible]);
            piwikClient.push(['trackEvent', 'visibility', _config.projectId, 'Time Report Time Hidden', timeHidden]);
            piwikClient.push(['trackEvent', 'visibility', _config.projectId, 'Time Report Time Fully Visible', timeFullyvisible]);
            piwikClient.push(['trackEvent', 'visibility', _config.projectId, 'Time Report Time Relative Visible', timeRelativeVisible]);
          },
          addEvent: function (eventCollection, data, consumer) {
            // trackEvent(category, action, [name], [value]):
            // _paq.push(['trackEvent', 'Documentary', 'Play', 'Thrive']);
            if (eventCollection === 'visibility-percentage-time-test') {
              this.addPercentageTimeTestEvent(data);
            } else if (eventCollection === 'visibility-initial-request') {
              this.addInitialRequestEvent(data);
            } else if (eventCollection === 'visibility-time-report') {
              this.addTimeReportEvent(data);
            }

            consumer(null, data);

            // _paq.push(['trackContentImpression', 'Content Name', 'Content Piece', 'http://www.example.com']);
            //piwikClient.push(['trackContentImpression', _config.projectId, eventCollection, data]);

            // _paq.push(['trackContentInteraction', 'tabActivated', 'Content Name', 'Content Piece', 'http://www.example.com']);
            //piwikClient.push(['trackContentInteraction', eventCollection, _config.projectId, 'visibility', data]);
          }
        };

        return {
          standard: function (visobj) {
            return VisSense.Client.Simple().monitors(client).standard(visobj);
          }
        };
      }
    };
  };

})(VisSense, VisSense.Utils);
