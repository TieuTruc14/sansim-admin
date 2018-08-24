/*Factory for auto complete customer username*/
app.factory('LoadCustomerUsernameAuto', function($http, $q, $timeout){
    var CustomerLoadUsername = new Object();
    CustomerLoadUsername.getUsernames = function(key) {
        var dataName = $q.defer();
        var names=[];
        $http.get(preUrl+"/administer/customer/autosearch-username", {params: {key:key}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    names=response.data;
                    // dataName.resolve(names);
                }
            });
        /*timeout giup viec ko gui request lien tuc len server, ma sau khi nhap ty tu dau tien, thi doi load hoac doi nhap tiep roi dung` lai 1s moi gui tiep*/
        $timeout(function(){
            dataName.resolve(names);
        },1000);
        return dataName.promise;
    };

    return CustomerLoadUsername;
});