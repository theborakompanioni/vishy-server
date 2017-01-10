(function (VisSense, Utils) {
  'use strict';

  VisSense.Client = VisSense.Client || {}

  var createBaseEndpoint = function (protocol, host, port) {
    return protocol + '://' + host + ':' + port + '';
  };

  VisSense.Client.Vishy = function(config, http) {
    if (!Utils.isFunction(VisSense.Client.Simple)) {
      throw new Error('Cannot load VisSense.Client.Simple. Is it included?');
    }

    var vishyConfig = Utils.defaults(config, {
      id: null,
      projectId: null,
      protocol: 'http',
      host: 'api.vishy.io',
      port: 80
    });

    if (!vishyConfig.id) {
      throw new Error('Please provide a vishy.id!');
    }
    if (!http || !http.post) {
      throw new Error('Please provide a compatible http client!');
    }

    var baseEndpoint = createBaseEndpoint(vishyConfig.protocol, vishyConfig.host, vishyConfig.port);

    return {
      monitors: function(config) {
        var moreMonitorsConfig = Utils.defaults(config, {
          projectId: vishyConfig.projectId
        });

        if (!moreMonitorsConfig.projectId) {
          throw new Error('Please provide a vishy.projectId!');
        }

        var client = {
          addEvent: function (eventCollection, data, consumer) {
            var url = baseEndpoint + '/openmrc/keenio/track/' + vishyConfig.id + '/' + eventCollection;

            var _data = Utils.extend(data, {
              vishy: {
                id: vishyConfig.id,
                projectId: moreMonitorsConfig.projectId
              }
            });

            http.post(url, _data, {}).then(function (data) {
              consumer(null, data);
            }, function (error) {
              consumer(error);
            });
          }
        };

        return {
          standard: function (visobj) {
            return VisSense.Client.Simple().monitors(client).standard(visobj);
          },
          custom: function (visobj, options) {
            return VisSense.Client.Simple().monitors(client).custom(visobj, options);
          },
          newBuilder: function (visobj, options) {
            return VisSense.Client.Simple()
              .monitors(client)
              .newBuilder(visobj, options);
          }
        };
      }
    };
  };


})(VisSense, VisSense.Utils);
