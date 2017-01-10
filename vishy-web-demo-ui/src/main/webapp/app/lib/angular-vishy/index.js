(function (angular) {

  angular.module('angular-vishy.config', [])
    .value('angular-vishy.config', {
      debug: true
    });

  angular.module('angular-vishy.directives', []);
  angular.module('angular-vishy.services', []);
  angular.module('angular-vishy', [
      'angular-vishy.config',
      'angular-vishy.directives',
      'angular-vishy.services'
    ]);

})(angular);

(function (angular) {

  angular.module('angular-vishy.directives')

    .directive('tbkVishyProjectTable', [function() {
      var d = {
        scope: {
        },
        controller: ['$scope', function($scope) {
          $scope.title = 'Project Table';
        }],
        template:
        '<div>{{ title }}</div>'
      };

      return d;
    }]);

})(angular);

(function (angular) {

  angular.module('angular-vishy.config')

    .value('tbkVishyDefaultConfig', {
      id: 'YOUR_VISHY_ID',   // String (required always)
      writeKey: 'YOUR_WRITE_KEY',     // String (required for sending data)
      readKey: 'YOUR_READ_KEY',       // String (required for querying data)
      protocol: 'https',              // String (optional: https | http | auto)
      host: 'api.vishy.io',        // String (optional)
      port: 443,
      requestType: 'jsonp'            // String (optional: jsonp, xhr, beacon)
    })

    .provider('tbkVishyConfig', [function () {
      var config = {};

      this.id = function (id) {
        config.id = id;
        return this;
      };
      this.writeKey = function (writeKey) {
        config.writeKey = writeKey;
        return this;
      };
      this.readKey = function (readKey) {
        config.readKey = readKey;
        return this;
      };
      this.protocol = function (protocol) {
        config.protocol = protocol;
        return this;
      };
      this.host = function (host) {
        config.host = host;
        return this;
      };
      this.port = function (port) {
        config.port = port;
        return this;
      };
      this.requestType = function (requestType) {
        config.requestType = requestType;
        return this;
      };

      this.$get = ['tbkVishyDefaultConfig', function (defaultConfig) {
        return angular.extend(defaultConfig, config);
      }];
    }])
  ;

})(angular);
