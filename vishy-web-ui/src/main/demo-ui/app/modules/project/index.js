(function (angular) {
  'use strict';
  angular.module('org.tbk.vishy.ui.project.config', [])
    .value('DashboardConfig', {
      debug: true
    });

  angular.module('org.tbk.vishy.ui.project.states', [
    'ui.router'
  ]);
  angular.module('org.tbk.vishy.ui.project.services', []);

  angular.module('org.tbk.vishy.ui.project.directives', [
    'org.tbk.vishy.ui.project.services'
  ]);

  angular.module('org.tbk.vishy.ui.project.controllers', [
    'org.tbk.vishy.ui.project.services',
    'org.tbk.vishy.ui.project.directives'
  ]);


  angular.module('org.tbk.vishy.ui.project', [
    'org.tbk.vishy.ui.project.config',
    'org.tbk.vishy.ui.project.directives',
    'org.tbk.vishy.ui.project.controllers',
    'org.tbk.vishy.ui.project.services',
    'org.tbk.vishy.ui.project.states'
  ]);

})(angular);
