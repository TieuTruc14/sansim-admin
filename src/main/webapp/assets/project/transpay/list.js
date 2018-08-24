/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http','$filter','$window','$timeout','$q','LoadCustomerUsernameAuto'
    ,function ($scope,$http,$filter,$window,$timeout,$q,LoadCustomerUsernameAuto) {
        $scope.msisdn="";
        $scope.username="";
        $scope.from="";
        $scope.to="";
        $scope.packageId="";
        $scope.type="";
        $scope.dataLoaded=false;
        $scope.listPackage="";
        $scope.page=page;
        $http.get(preUrl+"/administer/package/listAll")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.listPackage=response.data;
                }
            });
        $http.get(preUrl+"/administer/pack-cus/search", {params: {msisdn:$scope.msisdn,username:$scope.username,from:$scope.from,to:$scope.to,packageId:$scope.packageId,type:$scope.type}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });
        /*----------------------for auto complete-------------------------------------*/
        $scope.names="";
        $scope.whenChange = function(typeKey){
            $scope.newnames = LoadCustomerUsernameAuto.getUsernames(typeKey);
            $scope.newnames.then(function(data){
                $scope.names = data;
            });
        };
        /*----------------------------------------------------------------------------------*/
        $scope.search=function () {
            $scope.errorDateFrom="";
            $scope.errorDateTo="";
            if($scope.from!=null && $scope.from!='undefined' && $scope.from.length>0){
                if(formatDate($scope.from)==null){
                    $scope.errorDateFrom="Nhập đúng định dạng dd/MM/yyyy";
                    return;
                }
            }
            if($scope.to!=null && $scope.to!='undefined' && $scope.to.length>0){
                if(formatDate($scope.to)==null){
                    $scope.errorDateTo="Nhập đúng định dạng dd/MM/yyyy";
                    return;
                }
            }
            $scope.page.pageNumber=1;
            $http.get(preUrl+"/administer/pack-cus/search", {params: {msisdn:$scope.msisdn,username:$scope.username,from:$scope.from,to:$scope.to,packageId:$scope.packageId,type:$scope.type}})
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
                $scope.errorDateFrom="";
                $scope.errorDateTo="";
                if($scope.from!=null && $scope.from!='undefined' && $scope.from.length>0){
                    if(formatDate($scope.from)==null){
                        $scope.errorDateFrom="Nhập đúng định dạng dd/MM/yyyy";
                        return;
                    }
                }
                if($scope.to!=null && $scope.to!='undefined' && $scope.to.length>0){
                    if(formatDate($scope.to)==null){
                        $scope.errorDateTo="Nhập đúng định dạng dd/MM/yyyy";
                        return;
                    }
                }
                $http.get(preUrl+"/administer/pack-cus/search", {params: {p:pageNumber,msisdn:$scope.msisdn,username:$scope.username,from:$scope.from,to:$scope.to,packageId:$scope.packageId,type:$scope.type}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }

        };

        $('#from').datepicker().on('changeDate', function (ev) {
            $scope.from=this.value;
        });
        $('#to').datepicker().on('changeDate', function (ev) {
            $scope.to=this.value;
        });
        $scope.clear=function () {
            $scope.type="";
            $scope.packageId="";
            $scope.from="";
            $scope.to="";
            $scope.msisdn="";
            $scope.username="";
            $("#active-select").select2("val",'');
            $("#active-select1").select2("val",'');
        };
    }]);