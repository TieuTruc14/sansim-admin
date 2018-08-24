/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http','$filter','$window','$timeout','$q'
    ,function ($scope,$http,$filter,$window,$timeout,$q) {
        $scope.name="";
        $scope.list="";
        $scope.dataLoaded=false;
        $scope.page=page;
        $http.get(preUrl+"/category/group-type/list/search", {params: {name:$scope.name}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });

        /*----------------------------------------------------------------------------------*/
        $scope.search=function () {
            $scope.page.pageNumber=1;
            $http.get(preUrl+"/category/group-type/list/search", {params: {name:$scope.name}})
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
                $http.get(preUrl+"/category/group-type/list/search", {params: {p:pageNumber,name:$scope.name}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);

                    });
            }

        };

        /*FOR SAP XEP*/
        $scope.listSapXep=[];
        $scope.genValues=function () {
            var lis = document.getElementById("nabar-list-group").getElementsByTagName("li");
            $scope.listSapXep=[];
            for(var k=0;k<lis.length;k++){
                var itemGroup={id:lis[k].getElementsByTagName("input")[0].value,order:k+1};
                $scope.listSapXep.push(itemGroup);
            }
            $scope.orderGroup();
        };

        $scope.orderGroup=function () {
            var listSapXep=JSON.parse(JSON.stringify($scope.listSapXep));
            $http.put(preUrl+"/category/group-type/order-list-all",listSapXep, {headers: {'Content-Type': 'application/json'} })
                .then(function (response) {
                        if(response!=null && response!='undefined' && response.status==200){
                            if(response.data==true){
                                $("#showOke").modal('show');
                            }else{
                                $("#errorEdit").modal('show');
                            }
                        }else{
                            $("#errorEdit").modal('show');
                        }
                    },
                    function(response){
                        $("#errorEdit").modal('show');
                    }
                );
        };

        $scope.reload=function () {
            $window.location.href=preUrl+"/category/group-type/list";
        };


    }]);