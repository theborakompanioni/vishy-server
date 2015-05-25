/*global jasmine,describe,it,expect,beforeEach,afterEach*/
describe('vishy-analytics', function () {
  'use strict';

  beforeEach(function () {
    jasmine.clock().install();
    jasmine.clock().mockDate();
  });

  afterEach(function () {
    jasmine.clock().uninstall();
  });

  describe('untested code', function () {
    it('should have a test to remind everyone to eagerly write tests.. erm..', function () {
        expect(true).toBe(true);
    });
  });

});
