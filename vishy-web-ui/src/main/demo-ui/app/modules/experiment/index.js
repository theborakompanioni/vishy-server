(function (angular) {
  'use strict';
  angular.module('org.tbk.vishy.ui.experiment.config', [])
    .value('ExperimentConfig', {
      debug: true
    });

  angular.module('org.tbk.vishy.ui.experiment.states', [
    'ui.router'
  ]);
  angular.module('org.tbk.vishy.ui.experiment.services', []);

  angular.module('org.tbk.vishy.ui.experiment.directives', [
    'org.tbk.vishy.ui.experiment.services'
  ]);

  angular.module('org.tbk.vishy.ui.experiment.controllers', [
    'org.tbk.vishy.ui.experiment.services',
    'org.tbk.vishy.ui.experiment.directives'
  ]);


  angular.module('org.tbk.vishy.ui.experiment', [
    'org.tbk.vishy.ui.experiment.config',
    'org.tbk.vishy.ui.experiment.directives',
    'org.tbk.vishy.ui.experiment.controllers',
    'org.tbk.vishy.ui.experiment.services',
    'org.tbk.vishy.ui.experiment.states'
  ]);

})(angular);
