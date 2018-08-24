/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http','$filter','$window','$timeout','$q','LoadCustomerUsernameAuto'
    ,function ($scope,$http,$filter,$window,$timeout,$q,LoadCustomerUsernameAuto) {
        // $scope.search={msisdn:"",username:"",fullName:"",packageCode:"",active:""};
        $scope.msisdn="";
        $scope.username="";
        $scope.fullName="";
        $scope.packageCode="";
        $scope.active="";
        $scope.dataLoaded=false;
        $scope.itemLock={id:"",username:""};
        //gan goi cuoc
        $scope.listPackage="";
        $scope.packageId="";
        $scope.itemCache={id:"",username:""};
        $scope.dateExpired="";

        $scope.page=page;
        $http.get(preUrl+"/administer/customer/search", {params: {msisdn:$scope.msisdn,username:$scope.username,fullName:$scope.fullName,packageCode:$scope.packageCode,active:$scope.active}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });
        $http.get(preUrl+"/administer/package/listAll")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.listPackage=response.data;
                    console.log($scope.listPackage);
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
            $scope.page.pageNumber=1;
            $http.get(preUrl+"/administer/customer/search", {params: {msisdn:$scope.msisdn,username:$scope.username,fullName:$scope.fullName,packageCode:$scope.packageCode,active:$scope.active}})
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
                $http.get(preUrl+"/administer/customer/search", {params: {p:pageNumber,msisdn:$scope.msisdn,username:$scope.username,fullName:$scope.fullName,packageCode:$scope.packageCode,active:$scope.active}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);

                    });
            }

        }


        $scope.lockItem=function (item) {
            $scope.itemLock.id=item[0];
            $scope.itemLock.username=item[3];
            $("#lockItem").modal('show');
        }
        $scope.unlockItem=function (item) {
            $scope.itemLock.id=item[0];
            $scope.itemLock.username=item[3];

            $("#unlockItem").modal('show');
        }
        
        $scope.lockClick=function () {
            $scope.messageStatus="";
            if($scope.itemLock.id!=null && $scope.itemLock.id>0){
                $http.put(preUrl+"/administer/customer/lock",$scope.itemLock.id, {headers: {'Content-Type': 'application/json'} })
                    .then(function (response) {
                            if(response.data==true){
                                $scope.messageStatus="Khóa người bán"+$scope.itemLock.username+" thành công!";
                                $scope.updateActiveItem($scope.itemLock.id,2);
                                $("#Message").modal('show');
                            }else{
                                $scope.messageStatus="Có lỗi xảy ra khi khoá người bán, hãy thử lại sau!";
                                $("#Message").modal('show');
                            }
                        },
                        function(response){
                            $scope.messageStatus="Có lỗi xảy ra khi khoá người bán, hãy thử lại sau!";
                            $("#Message").modal('show');
                        });
            }
        }
        $scope.unlockClick=function () {
            $scope.messageStatus="";
            if($scope.itemLock.id!=null && $scope.itemLock.id>0){
                $http.put(preUrl+"/administer/customer/unlock",$scope.itemLock.id, {headers: {'Content-Type': 'application/json'} })
                    .then(function (response) {
                            if(response.data==true){
                                $scope.messageStatus="Kích hoạt người bán"+$scope.itemLock.username+" thành công!";
                                $scope.updateActiveItem($scope.itemLock.id,1);
                                $("#Message").modal('show');
                            }else{
                                $scope.messageStatus="Có lỗi xảy ra khi kích hoạt người bán, hãy thử lại sau!";
                                $("#Message").modal('show');
                            }
                        },
                        function(response){
                            $scope.messageStatus="Có lỗi xảy ra khi kích hoạt người bán, hãy thử lại sau!";
                            $("#Message").modal('show');
                        });
            }
        }
        
        $scope.updateActiveItem=function (id,value) {
            for(var i=0;i<$scope.page.items.length;i++){
                if($scope.page.items[i][0]===id){
                    $scope.page.items[i][10]=value;
                    return;
                }
            }
        };

        $scope.clear=function () {
            $scope.active="";
            $("#active-select").select2("val",'');
        };
        
        //gan goi cuoc
        $scope.dialogGanGoiCuoc=function (item) {
            $scope.itemCache.id=item[0];
            $scope.itemCache.username=item[3];
            $("#ganGoiCuoc").modal('show');
        };

        $scope.validateAddPackage=function () {
            $scope.errorDateExpired="";
            $scope.errorPackageId="";
          if($scope.packageId==null || $scope.packageId==""||$scope.packageId=='undefined'){
              $scope.errorPackageId="Chọn gói cước";
              return false;
          }
            if($scope.dateExpired!=null && $scope.dateExpired!='undefined' && $scope.dateExpired!='' && $scope.dateExpired.length>0){
                if(formatDate($scope.dateExpired)==null){
                    $scope.errorDateExpired="Nhập đúng định dạng dd/MM/yyyy";
                    return false;
                }
            }else{
                $scope.errorDateExpired="Nhập đúng định dạng dd/MM/yyyy";
                return false;
            }
          if(!$scope.itemCache.id>0){
              return false;
          }
          return true;
        };

        $scope.setPackage=function () {
            if(!$scope.validateAddPackage() ){
                return;
            }else{
                $scope.messageStatus="";
                var data = $.param({
                    cusId: $scope.itemCache.id,
                    packageId: $scope.packageId,
                    dateExpired:formatDate($scope.dateExpired)
                });
                $scope.custSerive={cusId:$scope.itemCache.id,packageId:$scope.packageId,dateExpired:$scope.dateExpired};
                $scope.data={id:"",customer:{id:$scope.itemCache.id},configPackage:{id:$scope.packageId},expiredDate:$scope.dateExpired,status:1};
                if($scope.itemCache.id!=null && $scope.itemCache.id>0){
                    $http.post(preUrl+"/administer/customer/add-cus-package",data,{headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'} })
                        .then(function (response) {
                                if(response.data==true){
                                    $scope.messageStatus="Gán gói cước thành công!";
                                    $scope.updatePackage($scope.itemCache.id,$scope.getCodePackage($scope.packageId));
                                    $scope.clearDialogPackage();
                                    $("#Message").modal('show');
                                }else{
                                    $scope.messageStatus="Có lỗi xảy ra khi, hãy thử lại sau!";
                                    $scope.clearDialogPackage();
                                    $("#Message").modal('show');
                                }
                            },
                            function(response){
                                $scope.messageStatus="Có lỗi xảy ra, hãy thử lại sau!";
                                $scope.clearDialogPackage();
                                $("#Message").modal('show');
                            });
                }
            }

        };

        $scope.updatePackage=function (id,value) {
            for(var i=0;i<$scope.page.items.length;i++){
                if($scope.page.items[i][0]===id){
                    $scope.page.items[i][5]=value;
                    $scope.page.items[i][6]=formatDate($scope.dateExpired);
                    return;
                }
            }
        };
        $scope.getCodePackage=function (id) {
            for(var i=0;i<$scope.listPackage.length;i++){
                if($scope.listPackage[i].id==id){
                    return $scope.listPackage[i].packageCode;
                }
            }
        };
        $('#dateExpired').datepicker().on('changeDate', function (ev) {
            $scope.dateExpired=this.value;
        });

        $scope.clearDialogPackage=function () {
            $scope.dateExpired="";
            $scope.packageId="";
            $scope.itemCache={id:"",username:""};
            $('#dateExpired').value="";
            angular.element("#dateExpired")[0].value = null;
            $("#packageSelect").select2("val", "");
            $("#ganGoiCuoc").modal('hide');
        }

}]);