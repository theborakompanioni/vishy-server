(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.showcase.directives')

    .directive('vishyShowcaseDefaultDraggableElement', [function () {
      var d = {
        scope: {
          elementId: '@vishyShowcaseDefaultDraggableElement'
        },
        controller: ['$scope', function($scope) {
          $scope.model = {
            elementId: $scope.elementId
          };
        }],
        templateUrl: 'partials/modules/showcase/directives/default-draggable-element.html'
      };

      return d;
    }])
    .directive('tbkLoremIpsum', [function () {
      var d = {
        scope: {
          paragraphs: '@'
        },
        controller: ['$scope', function($scope) {
        }],
        link: function($scope, $element) {
          var paragraphCount = parseInt($scope.paragraphs, 10);
          var options = {
            paragraphs:  paragraphCount > 0 ? paragraphCount : 5
          };
          $scope.range = [];
          for(var i = 0; i < options.paragraphs; i++) {
            $scope.range.push(i);
          }

          $element.addClass('tbk-lorem-ipsum');
        },
        template: '<div>' +
          '<p data-ng-repeat="i in range">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. </p>' +
        '</div>'
      };

      return d;
    }])
  ;

})(angular);
