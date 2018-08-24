/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http' ,function ($scope,$http) {
    $scope.passwordCurrent="";
    $scope.passwordNew="";
    $scope.confirmPassword="";

    $scope.change=function () {
        if(validate()){
            $('#btn-check').button('complete');
            $http.put(preUrl+"/system/user/change-my-pass",{},{params: {passwordCurrent:$scope.passwordCurrent,passwordNew:$scope.passwordNew}}, {headers: {'Content-Type': 'application/json'} })
                .then(function (response) {
                    if(response!=null && response!='underfined' && response.status==200){
                        switch(response.data) {
                            case 1:
                                $scope.messageStatus="Đổi mật khẩu thành công!";
                                $("#Message").modal('show');
                                break;
                            case 2:
                                $scope.messageStatus="Thất bại!Mật khẩu không đúng với mật khẩu hiện tại!";
                                $("#Message").modal('show');
                                break;
                            default:
                                $scope.messageStatus="Có lỗi xảy ra, hãy thử lại sau!";
                                $("#Message").modal('show');
                                break;
                        }

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
        $('#btn-check').button('complete');
    };

    function validate() {
        $scope.messageCurrent="";
        $scope.messageNew="";
        $scope.messageConfirm="";
        if($scope.passwordCurrent==null || $scope.passwordCurrent=='undefined' || $scope.passwordCurrent.length==0){
            $scope.messageCurrent="Mật khẩu không được để trống!";
            return false;
        }
        if($scope.passwordNew==null || $scope.passwordNew=='undefined' || $scope.passwordNew.length==0){
            $scope.messageNew="Mật khẩu không được để trống!";
            return false;
        }
        if($scope.confirmPassword==null || $scope.confirmPassword=='undefined' || $scope.confirmPassword.length==0){
            $scope.messageConfirm="Xác nhận mật khẩu không được để trống!";
            return false;
        }else{
            if(!$scope.confirmPassword===$scope.passwordNew){
                $scope.messageConfirm="Không trùng khớp mật khẩu mới!";
                return false;
            }
        }
        return true;
    }

}]);