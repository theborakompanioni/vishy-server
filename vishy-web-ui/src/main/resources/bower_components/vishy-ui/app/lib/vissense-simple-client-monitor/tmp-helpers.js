(function (VisSense, Utils) {
  'use strict';

  // FIXME: temporary -> Everything in here should be considered to be moved to an own plugin

  var simpleHelpers = function () {};

  simpleHelpers.createTimeReport = function (metrics) {
    var report = {};
    report.timeHidden = metrics.getMetric('time.hidden').get();
    report.timeVisible = metrics.getMetric('time.visible').get();
    report.timeFullyVisible = metrics.getMetric('time.fullyvisible').get();
    report.timeRelativeVisible = metrics.getMetric('time.relativeVisible').get();
    report.duration = metrics.getMetric('time.duration').get();

    report.percentage = {
      current: metrics.getMetric('percentage').get(),
      max: metrics.getMetric('percentage.max').get(),
      min: metrics.getMetric('percentage.min').get()
    };

    return report;
  };

  simpleHelpers.newInitialStateEventStrategy = function (eventName) {
    return {
      init: function (monitor) {
        console.debug('[InitialRequestEventStrategy] init');
        var stopSendingInitialRequestEvents = monitor.on('update', function (monitor) {
          var state = monitor.state();
          monitor.publish(eventName, [monitor, state]);
          stopSendingInitialRequestEvents();
        });
      }
    };
  };

  simpleHelpers.createSummaryEventStrategy = function (eventName) {
    return {
      init: function(monitor) {
        if (!Utils.isFunction(monitor.metrics) || !Utils.isFunction(VisSense.VisMon.Strategy.MetricsStrategy)) {
          throw new Error('Cannot load MetricsStrategy. Is it included?');
        }
      },
      start: function () {
        console.debug('[TimeReportEventStrategy] start');
      },
      stop: function (monitor) {
        monitor.metrics().update();

        var timeReport = simpleHelpers.createTimeReport(monitor.metrics());

        monitor.publish(eventName, [monitor, timeReport]);

        console.debug('[TimeReportEventStrategy] stop');
      }
    };
  };

  VisSense.Client = VisSense.Client || {};
  VisSense.Client.Helpers = VisSense.Client.Helpers || {};
  VisSense.Client.Helpers.Simple = simpleHelpers;


})(VisSense, VisSense.Utils);
