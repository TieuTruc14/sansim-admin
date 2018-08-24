/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http','LoadCustomerUsernameAuto' ,function ($scope,$http,LoadCustomerUsernameAuto) {
        $scope.username="";
        $scope.from="";
        $scope.to="";
        $scope.msisdn="";

        $scope.dataLoaded=false;

        $scope.page=page;
        $http.get(preUrl+"/administer/mo-mt/search", {params: {username:$scope.username,from:$scope.from,to:$scope.to,msisdn:$scope.msisdn}})
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
            $http.get(preUrl+"/administer/mo-mt/search", {params: {username:$scope.username,from:$scope.from,to:$scope.to,msisdn:$scope.msisdn}})
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
                if($scope.to!=null && $scope.to!='underfined' && $scope.to.length>0){
                    if(formatDate($scope.to)==null){
                        $scope.errorDateTo="Nhập đúng định dạng dd/MM/yyyy";
                        return;
                    }
                }
                $http.get(preUrl+"/administer/mo-mt/search", {params: {p:pageNumber,username:$scope.username,from:$scope.from,to:$scope.to,msisdn:$scope.msisdn}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }

        }

        $scope.MT={id:"",mtInfo:"",mtNumber:""}
    $scope.sendMT=function (item) {
        $scope.MT.id=item[6];
        $scope.MT.mtInfo=item[7];
        $scope.MT.mtNumber=item[8];
        $("#sendMT").modal('show');
    }

    $scope.sendMTOK=function () {
        $scope.messageStatus="";
        if($scope.MT.id!=null && $scope.MT.id>0){
            $http.post(preUrl+"/administer/mo-mt/resendMT",$scope.MT.id, {headers: {'Content-Type': 'application/json'} })
                .then(function (response) {
                        if(response.data==true){
                            $scope.messageStatus="Gửi lại MT cho thuê bao "+$scope.MT.mtNumber+" thành công!";
                            $("#Message").modal('show');
                        }else{
                            $scope.messageStatus="Có lỗi xảy ra, hãy thử lại sau!";
                            $("#Message").modal('show');
                        }
                    },
                    function(response){
                        $scope.messageStatus="Có lỗi xảy ra, hãy thử lại sau!";
                        $("#Message").modal('show');
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
        $scope.username="";
        $scope.from="";
        $scope.to="";
        $scope.msisdn="";
    };

}]);