'use strict';

describe('controllers', function () {
  var scope;

  beforeEach(module('tbk.vishy-ui'));

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should define an autoStop option with 30 seconds', inject(function ($controller) {
    expect(scope.model).toBeUndefined()

    $controller('ShowcaseCtrl', {
      $scope: scope
    })

    expect(angular.isObject(scope.model)).toBeTruthy();
    expect(scope.model.autoStopCountdown).toBe(90);
    expect(scope.model.inactiveAfter).toBe(15000);
  }));
});
