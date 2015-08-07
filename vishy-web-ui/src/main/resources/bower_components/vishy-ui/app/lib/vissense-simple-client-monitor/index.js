(function (VisSense, factory) {
  'use strict';

  VisSense.Client = VisSense.Client || {};
  VisSense.Client.Simple = factory(VisSense, VisSense.Utils);
})(VisSense, function (VisSense, Utils) {
  'use strict';

  var uuid = function () {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  };

  var viewportSize = function () {
    var width = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
    var height = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
    return {
      width: width,
      height: height
    };
  };

  var sessionId = uuid();

  var addStandardProperties = function (data) {
    return Utils.extend(data, {
      sessionId: sessionId,
      viewport: viewportSize()
    });
  };

  var sendEventWithClient = function (client, eventCollection, event) {
    var data = addStandardProperties(event);

    client.addEvent(eventCollection, data, function (err/*, res*/) {
      if (err) {
        // there was an error!
        console.log('error on event ' + eventCollection);
      }
      else {
        // see sample response below
        console.info('successfully sent event ' + eventCollection);
      }
    });
  };

  return function () {
    if (!Utils.isFunction(VisSense.VisMon.Strategy.MetricsStrategy)) {
      throw new Error('Cannot load MetricsStrategy. Is it included?');
    }

    return {
      monitors: function (externalClient) {
        var client = {
          addEvent: function (eventCollection, data, consumer) {
            externalClient.addEvent(eventCollection, data, consumer);
          }
        };

        return {
          standard: function (visobj) {
            return this.newBuilder(visobj).build();
          },
          custom: function (visobj, options) {
            return this.newBuilder(visobj, options).build();
          },
          newBuilder: function (visobj, options) {
            var config = Utils.defaults(options, {
              interval: 1000,
              throttle: 100,
              inactiveAfter: 60000
            });

            var builder = VisSense.VisMon.Builder(visobj)
              .strategy(new VisSense.VisMon.Strategy.PollingStrategy({interval: config.interval}))
              .strategy(new VisSense.VisMon.Strategy.EventStrategy({throttle: config.throttle}))
              .strategy(new VisSense.VisMon.Strategy.UserActivityStrategy({
                inactiveAfter: config.inactiveAfter
              }));

            return this.prepareBuilder(builder);
          },
          prepareBuilder: function (builder) {
            var monitorId = uuid();

            var r = monitorId.substring(0, 7);
            var asInternalEventName = function (eventName) {
              return r + '#' + eventName;
            };

            var initEventName = 'visibility-initial-request';
            var status501TestPassedEventName = 'visibility-status-50/1';
            var summaryEventName = 'visibility-time-report';

            var internalInitEventName = asInternalEventName(initEventName);
            var internalStatus501TestPassedEventName = asInternalEventName(status501TestPassedEventName);
            var internalSummaryEventName = asInternalEventName(summaryEventName);

            return builder
              .strategy(new VisSense.VisMon.Strategy.MetricsStrategy())
              .strategy(VisSense.Client.Helpers.Simple.newInitialStateEventStrategy(internalInitEventName))
              .strategy(VisSense.VisMon.Strategy.PercentageTimeTestEventStrategy(internalStatus501TestPassedEventName, {
                percentageLimit: 0.5,
                timeLimit: 1000,
                interval: 100
              }))
              .strategy(VisSense.Client.Helpers.Simple.createSummaryEventStrategy(internalSummaryEventName))
              .on(internalInitEventName, function (monitor, state) {
                var initEventData = {
                  type: 'initial',
                  monitorId: monitorId,
                  initial: true,
                  state: state
                };

                sendEventWithClient(client, 'visibility-initial-request', initEventData);

                monitor.publish(initEventName, [monitor, initEventData]);
              })
              .on(internalStatus501TestPassedEventName, function (monitor, data) {
                var timeReport = VisSense.Client.Helpers.Simple.createTimeReport(monitor.metrics());

                var dataWithTimeReport = Utils.extend(data, {
                  timeReport: timeReport
                });

                var status501TestPassedEventData = {
                  type: 'status',
                  monitorId: monitorId,
                  test: dataWithTimeReport
                };

                sendEventWithClient(client, 'visibility-percentage-time-test', status501TestPassedEventData);
                monitor.publish(status501TestPassedEventName, [monitor, status501TestPassedEventData]);
              })
              .on(internalSummaryEventName, function (monitor, timeReport) {
                var summaryEventData = {
                  type: 'summary',
                  monitorId: monitorId,
                  report: timeReport
                };

                sendEventWithClient(client, 'visibility-time-report', summaryEventData);
                monitor.publish(summaryEventName, [monitor, summaryEventData]);
              });
          }
        };

      }
    };
  };
});
