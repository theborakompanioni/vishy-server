(function (angular) {
  'use strict';
  angular.module('org.tbk.vishy.ui.report.config', [])
    .value('DashboardConfig', {
      debug: true
    });

  angular.module('org.tbk.vishy.ui.report.states', [
    'ui.router'
  ]);
  angular.module('org.tbk.vishy.ui.report.services', []);

  angular.module('org.tbk.vishy.ui.report.directives', [
    'org.tbk.vishy.ui.report.services'
  ]);

  angular.module('org.tbk.vishy.ui.report.controllers', [
    'org.tbk.vishy.ui.report.services',
    'org.tbk.vishy.ui.report.directives'
  ]);


  angular.module('org.tbk.vishy.ui.report', [
    'org.tbk.vishy.ui.report.config',
    'org.tbk.vishy.ui.report.directives',
    'org.tbk.vishy.ui.report.controllers',
    'org.tbk.vishy.ui.report.services',
    'org.tbk.vishy.ui.report.states'
  ]);

})(angular);
