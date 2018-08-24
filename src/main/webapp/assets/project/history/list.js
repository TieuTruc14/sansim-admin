/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http' ,function ($scope,$http) {
        $scope.username="";
        $scope.dataLoaded=false;
        $scope.page=page;
        $http.get(preUrl+"/system/history/search", {params: {username:$scope.username}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });

        $scope.search=function () {
            $scope.page.pageNumber=1;
            $http.get(preUrl+"/system/history/search", {params: {username:$scope.username}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.page=response.data;
                        $scope.page.pageCount=getPageCount($scope.page);
                        $scope.page.pageList=getPageList($scope.page);
                    }
                });
        };

        $scope.loadPage=function (pageNumber) {
            if(pageNumber>=1){
                $http.get(preUrl+"/system/history/search", {params: {p:pageNumber,username:$scope.username}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }
        }


}]);