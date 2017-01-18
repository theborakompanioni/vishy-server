(function (angular) {
  'use strict';
  angular.module('org.tbk.vishy.ui.showcase.config', [])
    .value('DashboardConfig', {
      debug: true
    });

  angular.module('org.tbk.vishy.ui.showcase.states', [
    'ui.router'
  ]);
  angular.module('org.tbk.vishy.ui.showcase.services', []);

  angular.module('org.tbk.vishy.ui.showcase.directives', [
    'org.tbk.vishy.ui.showcase.services'
  ]);

  angular.module('org.tbk.vishy.ui.showcase.controllers', [
    'org.tbk.vishy.ui.showcase.services',
    'org.tbk.vishy.ui.showcase.directives'
  ]);


  angular.module('org.tbk.vishy.ui.showcase', [
    'angular-vissense',
    'angular-vissense.directives.debug',
    'angular-keenio',

    'tbk.draggable',

    'org.tbk.vishy.ui.showcase.config',
    'org.tbk.vishy.ui.showcase.directives',
    'org.tbk.vishy.ui.showcase.controllers',
    'org.tbk.vishy.ui.showcase.services',
    'org.tbk.vishy.ui.showcase.states'
  ]);

})(angular);
