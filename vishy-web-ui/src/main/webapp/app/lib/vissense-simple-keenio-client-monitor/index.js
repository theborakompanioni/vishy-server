(function (VisSense, Utils) {
  'use strict';

  VisSense.Client = VisSense.Client || {}

  VisSense.Client.KeenIO = function(keenClient) {
    if (!Utils.isFunction(VisSense.Client.Simple)) {
      throw new Error('Cannot load VisSense.Client.Simple. Is it included?');
    }

    var client = {
      addEvent: function (eventCollection, data, consumer) {
        keenClient.addEvent(eventCollection, data, consumer);
      }
    };

    return {
      monitors: function () {
        return {
          standard: function(visobj) {
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
