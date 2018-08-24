/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http','$filter' ,function ($scope,$http,$filter) {
    $scope.loadContinue=true;
    var pageNumber=1;
    $scope.list={items:"",rowCount:0};
    $scope.page=page;
    $http.get(preUrl+"/system/history/user-log", {params: {userId:id,p:pageNumber}})
        .then(function (response) {
            if(response!=null && response!='undefined' && response.status==200){
                $scope.page=response.data;
                $scope.page.pageCount=getPageCount($scope.page);
                $scope.page.pageList=getPageList($scope.page);
                $scope.list.rowCount=$scope.page.rowCount;
                $scope.list.items=$scope.page.items;
            }
        });

    $scope.loadPage=function (pageNumber) {
        $scope.loadContinue=false;
        $http.get(preUrl+"/system/history/user-log", {params: {p:pageNumber,userId:id}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    if($scope.page.items.length>0){
                        $scope.page.pageCount=getPageCount($scope.page);
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.list.rowCount=$scope.page.rowCount;
                        addItemToListFromPage();
                        $scope.loadContinue=true;
                    }

                }else{
                    $scope.loadContinue=true;
                }
            },
            function(response){
                $scope.loadContinue=true;
            });
    }

    function checkChildren(idLog) {
        var log = $filter('filter')($scope.list.items, {id: idLog}, true);
        if (log != null && log != 'undefined' && log != "") return false;
        return true;
    }
    function addItemToListFromPage() {
        for(var i=0;i<$scope.page.items.length;i++){
            var id=$scope.page.items[i].id;
            if(checkChildren(id)){
                $scope.list.items.push($scope.page.items[i]);
            }
        }
    }

}]);