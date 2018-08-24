/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http'
    ,function ($scope,$http) {
        $scope.name="";
        $scope.page=page;
        $scope.listCate="";
        $http.get(preUrl+"/content/category/list/all")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.listCate=response.data;
                }
            });

        $http.get(preUrl+"/content/article/list/search", {params: {name:$scope.name}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });

        $scope.search=function () {
            $scope.page.pageNumber=1;
            $http.get(preUrl+"/content/article/list/search", {params: {name:$scope.name,categoryId:$scope.categoryId}})
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
                $http.get(preUrl+"/content/article/list/search", {params: {p:pageNumber,name:$scope.name,categoryId:$scope.categoryId}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }

        };

        $scope.itemDeleteId=0;
        $scope.deleteItem=function (id) {
            $scope.itemDeleteId=0;
            if(id>0){
                $scope.itemDeleteId=id;
            }
        }

    }]);