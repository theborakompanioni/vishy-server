(function (angular) {
  'use strict';

  angular.module('org.tbk.vishy.ui.project.controllers')

    .controller('ProjectCtrl', [
      '$scope',
      'ProjectService',
      function ($scope, ProjectService) {
        $scope.model = {
          projectIds: null,
          projects: null,
          selectedProjectIds: []
        };

        $scope.toggleSelection = function(project) {
          project.selected = !project.selected;

          if(project.selected) {
            $scope.model.selectedProjectIds.push(project.id);
          } else {
            var index = $scope.model.selectedProjectIds.indexOf(project.id);
            if (index > -1) {
              $scope.model.selectedProjectIds.splice(index, 1);
            }
          }
        };

        ProjectService.getAllProjectIds(function (err, data) {
          if (!err) {
            var projectIds = data.result;
            $scope.model.projectIds = projectIds;

            $scope.model.projects = {};
            angular.forEach(projectIds, function (projectId) {
              $scope.model.projects[projectId] = {
                id: projectId
              };
            });

            if(projectIds.length > 1) {
              $scope.toggleSelection($scope.model.projects[projectIds[0]]);
              $scope.toggleSelection($scope.model.projects[projectIds[1]]);
            }

            ProjectService.getTimeVisibleAverages(function (err, data) {
              if (!err) {
                angular.forEach(data.result, function (entry) {
                  var projectId = entry['vishy.projectId'];
                  $scope.model.projects[projectId].timeVisibleAverage = entry.result;
                });
                $scope.$digest();
              }
            });

            ProjectService.getInitialPercentageAverages(function (err, data) {
              if (!err) {
                angular.forEach(data.result, function (entry) {
                  var projectId = entry['vishy.projectId'];
                  $scope.model.projects[projectId].initialPercentageAverage = entry.result;
                });
                $scope.$digest();
              }
            });

            ProjectService.getInitialRequestCount(function (err, data) {
              if (!err) {
                angular.forEach(data.result, function (entry) {
                  var projectId = entry['vishy.projectId'];
                  $scope.model.projects[projectId].initialRequestCount = entry.result;
                });
                $scope.$digest();
              }
            });

            ProjectService.getPassedPercentageTimeTestCount(0.5, 1000, function (err, data) {
              if (!err) {
                angular.forEach(data.result, function (entry) {
                  var projectId = entry['vishy.projectId'];
                  $scope.model.projects[projectId].passedPercentageTimeTestCount = entry.result;
                });
                $scope.$digest();
              }
            });

            ProjectService.getTimeFullyVisibleAverages(function (err, data) {
              if (!err) {
                angular.forEach(data.result, function (entry) {
                  var projectId = entry['vishy.projectId'];
                  $scope.model.projects[projectId].timeFullyVisibleAverage = entry.result;
                });
                $scope.$digest();
              }
            });

            $scope.$digest();
          }
        });
      }])
    .factory('ProjectService', [
      'tbkKeen', 'tbkKeenClient', 'tbkKeenHttpGet', 'tbkVishyConfig',
      function (tbkKeen, tbkKeenClient, tbkKeenHttpGet, tbkVishyConfig) {
        var addStandardProperties = function (data) {
          data.filters = (data.filters || []).concat([{
            "property_name": "vishy.id",
            "operator": "eq",
            "property_value": tbkVishyConfig.id
          }]);

          return data;
        };

        var query = function (type, options, consumer) {
          var config = addStandardProperties(options);
          var query = new tbkKeen.Query(type, config);
          tbkKeenClient.run(query, consumer);
        };


        return {
          getAllProjectIds: function (consumer) {
            var url = '/projects/<project_id>/queries/select_unique';

            var options = addStandardProperties({
              event_collection: 'visibility-initial-request',
              target_property: 'vishy.projectId'
            });

            return tbkKeenHttpGet(url, options, consumer);
          },
          getTimeVisibleAverages: function (consumer) {
            query("average", {
              eventCollection: "visibility-time-report",
              targetProperty: "report.timeVisible",
              groupBy: "vishy.projectId"
            }, consumer);
          },
          getTimeFullyVisibleAverages: function (consumer) {
            query("average", {
              eventCollection: "visibility-time-report",
              targetProperty: "report.timeFullyVisible",
              groupBy: "vishy.projectId"
            }, consumer);
          },
          getInitialPercentageAverages: function (consumer) {
            query("average", {
              eventCollection: "visibility-initial-request",
              targetProperty: "state.percentage",
              groupBy: "vishy.projectId"
            }, consumer);
          },
          getInitialRequestCount: function (consumer) {
            query("count", {
              eventCollection: "visibility-initial-request",
              targetProperty: "vishy.id",
              groupBy: "vishy.projectId"
            }, consumer);
          },
          getPassedPercentageTimeTestCount: function (percentageLimit, timeLimit, consumer) {
            query("count", {
              eventCollection: "visibility-percentage-time-test",
              targetProperty: "vishy.id",
              groupBy: "vishy.projectId",
              filters: [{
                "property_name": "test.testConfig.percentageLimit",
                "operator": "eq",
                "property_value": percentageLimit
              }, {
                "property_name": "test.testConfig.timeLimit",
                "operator": "eq",
                "property_value": timeLimit
              }, {
                "property_name": "test.timeReport.timeVisible",
                "operator": "gt",
                "property_value": 0
              }]
            }, consumer);
          }

        }
      }])
  ;
})(angular);
