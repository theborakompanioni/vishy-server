(function (VisSense, Utils) {
  'use strict';

  /************** SegmentIo Client Usage
   var segmentIoClient = window.analytics || {
                track: function (event, data) {
                  console.log('No client available for event ', event, data);
                }
              };

   var decoratedSegmentIoClient = {
              track: function (event, data) {
                console.log('addEvent via segmentio-client', event);

                var _data = VisUtils.extend(data, {
                  projectId: elementId
                });
                segmentIoClient.track(event, _data);
              }
            };

   var simpleSegmentIoClientMonitor = VisSense.Client.SegmentIO(decoratedSegmentIoClient)
   .monitors()
   .custom(visobj, {
                interval: 1000,
                throttle: 100,
                inactiveAfter: $scope.model.inactiveAfter
              });

   monitors.push(simpleSegmentIoClientMonitor); */

  /************** SegmentIo Client End */

  VisSense.Client = VisSense.Client || {}

  VisSense.Client.SegmentIO = function (segmentClient) {
    if (!Utils.isFunction(VisSense.Client.Simple)) {
      throw new Error('Cannot load VisSense.Client.Simple. Is it included?');
    }

    if (!segmentClient || !segmentClient.track) {
      throw new Error('Please provide a compatible Segment.io client!');
    }

    var client = {
      addEvent: function (eventCollection, data, consumer) {
        segmentClient.track(eventCollection, data);
        consumer(null, data);
      }
    };

    return {
      monitors: function () {
        return {
          standard: function (visobj) {
            return VisSense.Client.Simple().monitors(client).standard(visobj);
          },
          custom: function (visobj, options) {
            return VisSense.Client.Simple().monitors(client).custom(visobj, options);
          }
        };
      }
    };
  };

})(VisSense, VisSense.Utils);
