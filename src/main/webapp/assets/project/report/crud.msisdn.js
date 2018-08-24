/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http','LoadCustomerUsernameAuto' ,function ($scope,$http,LoadCustomerUsernameAuto) {
        $scope.type="";
        $scope.username="";
        $scope.from="";
        $scope.to="";
        $scope.page=page;
        $scope.noData="";
        $scope.loadding=false;

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
            $scope.preGetData();
            $http.get(preUrl+"/report/crud-msisdn/search", {params: {type:$scope.type,username:$scope.username,from:$scope.from,to:$scope.to}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.page=response.data;
                        $scope.page.pageCount=getPageCount($scope.page);
                        $scope.page.pageList=getPageList($scope.page);
                        if(!$scope.page.items.length>0){
                            $scope.errorGetData();
                        }else{$scope.endGetData()}
                    }else{
                        $scope.errorGetData();
                    }
                },
                function(response){
                    $scope.errorGetData();
                });
        };

        $scope.download=function () {
            window.open(preUrl+"/report/crud-msisdn/download?type="+$scope.type+"&username="+$scope.username+"&from="+$scope.from+"&to="+$scope.to, '_blank');
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
                $http.get(preUrl+"/report/crud-msisdn/search", {params: {p:pageNumber,type:$scope.type,username:$scope.username,from:$scope.from,to:$scope.to}})
                    .then(function (response) {
                        if(response!=null && response!='undefined' && response.status==200){
                            $scope.page=response.data;
                            $scope.page.pageCount=getPageCount($scope.page);
                            $scope.page.pageList=getPageList($scope.page);
                            if($scope.page.items.length==0){
                                $scope.errorGetData();
                            }else{$scope.endGetData()}
                        }else{
                            $scope.errorGetData();
                        }
                    },
                    function(response){
                        $scope.errorGetData();
                    });
            }

        }

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
    };

}]);