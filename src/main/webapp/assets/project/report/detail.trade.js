/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http','LoadCustomerUsernameAuto'
    ,function ($scope,$http,LoadCustomerUsernameAuto) {
        $scope.type="";
        $scope.username="";
        $scope.from="";
        $scope.to="";
        $scope.packageId="";
        $scope.listPackage="";
        $scope.page=page;
        $scope.show=true;
        $scope.noData="";
        $scope.loadding=false;
        /*load all package*/
        $http.get(preUrl+"/administer/package/listAll")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.listPackage=response.data;
                }
            });

        $scope.changeType=function (type) {
            if(type==1 || type==2){
                $scope.show=true;
            }else{
                $scope.packageId="";
                $scope.show=false;
            }
        }


        $scope.search=function () {
            $scope.errorDateFrom="";
            $scope.errorDateTo="";
            if($scope.from!=null && $scope.from!='undefined' && $scope.from.length>0){
                if(formatDate($scope.from)==null){
                    $scope.errorDateFrom="Nhập đúng định dạng dd/MM/yyyy";
                    return;
                }
            }else{
                $scope.errorDateFrom="Không để trống. Nhập đúng định dạng dd/MM/yyyy";
                return;
            }
            if($scope.to!=null && $scope.to!='undefined' && $scope.to.length>0){
                if(formatDate($scope.to)==null){
                    $scope.errorDateTo="Nhập đúng định dạng dd/MM/yyyy";
                    return;
                }
            }
            $scope.page.pageNumber=1;
            $scope.loadding=true;
            $http.get(preUrl+"/report/detail-trade/search", {params: {type:$scope.type,username:$scope.username,from:$scope.from,to:$scope.to,packageId:$scope.packageId}})
                .then(function (response) {
                        if(response!=null && response!='undefined' && response.status==200){
                            $scope.page=response.data;
                            $scope.page.pageCount=getPageCount($scope.page);
                            $scope.page.pageList=getPageList($scope.page);
                            if($scope.page.items.length==0){
                                $scope.errorGetData();
                            }else{
                                $scope.endGetData();
                            }
                        }else{
                            $scope.errorGetData();
                        }
                },
                function(response){
                    $scope.errorGetData();
                });
        };
        $scope.download=function () {
            window.open(preUrl+"/report/detail-trade/download?type="+$scope.type+"&username="+$scope.username+"&from="+$scope.from+"&to="+$scope.to+"&packageId="+$scope.packageId, '_blank');
        }

        $scope.loadPage=function (pageNumber) {
            if(pageNumber>=1){
                $scope.errorDateFrom="";
                $scope.errorDateTo="";
                if($scope.from!=null && $scope.from!='undefined' && $scope.from.length>0){
                    if(formatDate($scope.from)==null){
                        $scope.errorDateFrom="Nhập đúng định dạng dd/MM/yyyy";
                        return;
                    }
                }else{
                    $scope.errorDateFrom="Không để trống. Nhập đúng định dạng dd/MM/yyyy";
                    return;
                }
                if($scope.to!=null && $scope.to!='undefined' && $scope.to.length>0){
                    if(formatDate($scope.to)==null){
                        $scope.errorDateTo="Nhập đúng định dạng dd/MM/yyyy";
                        return;
                    }
                }
                $scope.loadding=true;
                $http.get(preUrl+"/report/detail-trade/search", {params: {p:pageNumber,type:$scope.type,username:$scope.username,from:$scope.from,to:$scope.to,packageId:$scope.packageId}})
                    .then(function (response) {
                            if(response!=null && response!='undefined' && response.status==200){
                                $scope.page=response.data;
                                $scope.page.pageCount=getPageCount($scope.page);
                                $scope.page.pageList=getPageList($scope.page);
                                if($scope.page.items.length==0){
                                    $scope.errorGetData();
                                }else{
                                    $scope.endGetData();
                                }
                            }else{
                                $scope.errorGetData();
                            }
                    },
                    function(response){
                        $scope.errorGetData();
                    });
            }

        }

        /**/
        $scope.preGetData=function () {
            $scope.page=page;
            $scope.loadding=true;
        }
        $scope.endGetData=function () {
            $scope.loadding=false;
        }
        $scope.errorGetData=function () {
            $scope.noData="Không có dữ liệu";
            $scope.page=page;
            $scope.loadding=false;
        }

        /*----------------------for auto complete-------------------------------------*/
        $scope.names="";
        $scope.whenChange = function(typeKey){
            $scope.newnames = LoadCustomerUsernameAuto.getUsernames(typeKey);
            $scope.newnames.then(function(data){
                $scope.names = data;
            });
        };
        /*----------------------------------------------------------------------------------*/
        $('#from').datepicker().on('changeDate', function (ev) {
            $scope.from=this.value;
        });
        $('#to').datepicker().on('changeDate', function (ev) {
            $scope.to=this.value;
        });
        $scope.clear=function () {
            $scope.type="";
            $scope.username="";
            $scope.from="";
            $scope.to="";
            $("#active-select").select2("val",'');
            $("#active-select1").select2("val",'');
        };
    }]);