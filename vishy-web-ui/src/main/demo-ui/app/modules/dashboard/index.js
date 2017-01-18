(function (angular, moment) {
  'use strict';
  angular.module('org.tbk.vishy.ui.dashboard.config', [])
    .value('VishyDashboardConfig', {
      debug: true
    })
    .value('VishyDashboardTimeframe', {
      start: moment().subtract(1, 'months').toISOString(),
      end: moment().toISOString()
    });

  angular.module('org.tbk.vishy.ui.dashboard.states', [
    'ui.router'
  ]);
  angular.module('org.tbk.vishy.ui.dashboard.filters', []);
  angular.module('org.tbk.vishy.ui.dashboard.services', []);

  angular.module('org.tbk.vishy.ui.dashboard.directives', [
    'org.tbk.vishy.ui.dashboard.services'
  ]);

  angular.module('org.tbk.vishy.ui.dashboard.controllers', [
    'org.tbk.vishy.ui.dashboard.services',
    'org.tbk.vishy.ui.dashboard.directives'
  ]);


  angular.module('org.tbk.vishy.ui.dashboard', [
    'angular-keenio',

    'angular-keenio-demo-directives',
    'nvd3ChartDirectives',

    'org.tbk.vishy.ui.dashboard.config',
    'org.tbk.vishy.ui.dashboard.directives',
    'org.tbk.vishy.ui.dashboard.controllers',
    'org.tbk.vishy.ui.dashboard.services',
    'org.tbk.vishy.ui.dashboard.states',
    'org.tbk.vishy.ui.dashboard.filters'
  ]);

})(angular, moment);
