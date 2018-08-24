/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http' ,function ($scope,$http) {

    $scope.page=page;
    $scope.confirmStatus=confirmStatus;
    if(confirmStatus){
        $http.get(preUrl+"/administer/mo-mt/momt-of-msisdn", {params: {msisdnId:msisdnId}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });
    }

    $scope.loadPage=function (pageNumber) {
        if(pageNumber>=1){
            $scope.page.pageNumber=pageNumber;
            $http.get(preUrl+"/administer/mo-mt/momt-of-msisdn", {params: {p:pageNumber,msisdnId:msisdnId}})
                .then(function (response) {
                    $scope.page=response.data;
                    $scope.page.pageList=getPageList($scope.page);
                    $scope.page.pageCount=getPageCount($scope.page);
                });
        }

    }

}]);