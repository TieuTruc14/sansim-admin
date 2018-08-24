/**
 * Created by Admin on 12/22/2017.
 */
app.controller('sansimCtrl',['$scope','$http','$filter','$window','$timeout','$q'
    ,function ($scope,$http,$filter,$window,$timeout,$q) {
        $scope.year="";
        $scope.month="";
        $scope.week="";
        $scope.showMonth=false;
        $scope.showWeek=false;
        $scope.showYear=false;
        $scope.type="";
        $scope.listYear=[];
        $scope.nameTitle="tuần này";
        var date=new Date();
        var week = getWeekNumber(date);
        var fullYear=date.getFullYear();
        for(var i=0;i<10;i++){
            $scope.listYear.push(fullYear);
            fullYear--;
        }



    /*BIEU DO TRON TY LE SO LUONG GIAO DICH*/
        $scope.sanluonggiaodich="";

        $http.get(preUrl+"/trade-count",{params:{year:date.getFullYear(),week:week}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.sanluonggiaodich=response.data;
                    $scope.loadBieuDoTronLuongGiaoDich();
                }
            });
        function init_chart_doughnut(){

            if( typeof (Chart) === 'undefined'){ return; }

            if ($('.canvasDoughnut').length){

                var chart_doughnut_settings = {
                    type: 'doughnut',
                    tooltipFillColor: "rgba(51, 51, 51, 0.55)",
                    data: {
                        labels: [
                            "Đăng ký",
                            "Gia Hạn",
                            "Xác thực",
                            "Gia hạn xác thực",
                            "Hủy"
                        ],
                        datasets: [{
                            data: [$scope.sanluonggiaodich.dangky, $scope.sanluonggiaodich.giahan, $scope.sanluonggiaodich.xacthuc, $scope.sanluonggiaodich.giahanxt, $scope.sanluonggiaodich.huyHT+$scope.sanluonggiaodich.huyNguoiDung],
                            backgroundColor: [
                                "#3498DB",
                                "#9B59B6",
                                "#26B99A",
                                "#BDC3C7",
                                "#E74C3C"
                            ],
                            hoverBackgroundColor: [
                                "#49A9EA",
                                "#B370CF",
                                "#36CAAB",
                                "#CFD4D8",
                                "#E95E4F"
                            ]
                        }]
                    },
                    options: {
                        legend: false,
                        responsive: false,
                        hover:false,
                        tooltips:false
                    }
                };
                $('.canvasDoughnut').each(function(){
                    // Chart.defaults.global.tooltips.enabled = false;
                    var chart_element = $(this);
                    var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);
                });

            }

        }
        $scope.loadBieuDoTronLuongGiaoDich=function () {
            init_chart_doughnut();
        };



/*BIEU DO TRON TY LE DOANH THU LOAI GIAO DICH*/
        $scope.doanhthuloaigiaodich="";
        $http.get(preUrl+"/revenue-count",{params:{year:date.getFullYear(),week:week}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.doanhthuloaigiaodich=response.data;
                    $scope.loadBieuDoTronDoanhThu();
                }
            });

        function init_chart_doughnut_doanhthu(){

            if( typeof (Chart) === 'undefined'){ return; }

            if ($('.canvasDoughnutDoanhThu').length){

                var chart_doughnut_settings1 = {
                    type: 'doughnut',
                    tooltipFillColor: "rgba(51, 51, 51, 0.55)",
                    data: {
                        labels: [
                            "Đăng ký",
                            "Gia Hạn",
                            "Xác thực",
                            "Gia hạn xác thực"
                        ],
                        datasets: [{
                            data: [$scope.doanhthuloaigiaodich.dangky, $scope.doanhthuloaigiaodich.giahan, $scope.doanhthuloaigiaodich.xacthuc, $scope.doanhthuloaigiaodich.giahanxt],
                            backgroundColor: [
                                "#3498DB",
                                "#9B59B6",
                                "#26B99A",
                                "#BDC3C7"
                            ],
                            hoverBackgroundColor: [
                                "#49A9EA",
                                "#B370CF",
                                "#36CAAB",
                                "#CFD4D8"
                            ]
                        }]
                    },
                    options: {
                        legend: false,
                        responsive: false,
                        hover:false,//huy bo hover
                        tooltips:false//huy bo tooltips boi khi dung angularjs load lai thi bi ghi de hinh anh luc hover
                    }
                };

                $('.canvasDoughnutDoanhThu').each(function(){
                    var chart_element1 = $(this);
                    var chart_doughnut = new Chart( chart_element1, chart_doughnut_settings1);

                });

            }

        }
        $scope.loadBieuDoTronDoanhThu=function () {
            init_chart_doughnut_doanhthu();
        };

/*BIEU DO COT DOANH THU*/
        $scope.bieudodoanhthu="";
        $http.get(preUrl+"/revenue",{params:{year:date.getFullYear(),week:week}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.bieudodoanhthu=response.data;
                    $scope.loadBieuDoDoanhThu();
                }
            });

        $scope.loadBieuDoDoanhThu=function () {
            var chart = Highcharts.chart('container', {

                title: {
                    text: ''
                },

                subtitle: {
                    text: 'Dạng cột'
                },

                xAxis: {
                    categories: $scope.bieudodoanhthu.names
                    // ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
                },
                series: [{
                    type: 'column',
                    colorByPoint: true,
                    // data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
                    data: $scope.bieudodoanhthu.values,
                    name:"doanh thu",
                    showInLegend: false
                }]

            });

/*Không có tác dụng mấy nút biểu đồ do dữ liệu là load sau khi load trang.*/
            // $('#plain').click(function () {
            //     chart.update({
            //         chart: {
            //             inverted: false,
            //             polar: false
            //         },
            //         subtitle: {
            //             text: 'Dạng cột'
            //         }
            //     });
            // });
            //
            // $('#inverted').click(function () {
            //     chart.update({
            //         chart: {
            //             inverted: true,
            //             polar: false
            //         },
            //         subtitle: {
            //             text: 'Dạng ngang'
            //         }
            //     });
            // });
            //
            // $('#polar').click(function () {
            //     chart.update({
            //         chart: {
            //             inverted: false,
            //             polar: true
            //         },
            //         subtitle: {
            //             text: 'Đường đối cực'
            //         }
            //     });
            // });
        };

        /*BIEU DO DUONG KHACH HANG MOI*/
        $scope.khachhangmoi="";
        $http.get(preUrl+"/cus-new",{params:{year:date.getFullYear(),week:week}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.khachhangmoi=response.data;
                    $scope.bieudoduongkhachhangmoi();
                }
            });
        $scope.bieudoduongkhachhangmoi=function () {
            Highcharts.chart('khachhangmoi', {
                chart: {
                    type: 'line'
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    // categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
                    categories: $scope.khachhangmoi.names
                },
                yAxis: {
                    title: {
                        text: 'Số lượng'
                    }
                },

                series: [{
                    name: 'Số lượng',
                    data: $scope.khachhangmoi.values
                }]
            });
        };

        /*BIEU DO DUONG THUE BAO MOI*/
        $scope.thuebaomoi="";
        $http.get(preUrl+"/msisdn-new",{params:{year:date.getFullYear(),week:week}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.thuebaomoi=response.data;
                    $scope.bieudoduongthuebaomoi();
                }
            });
        $scope.bieudoduongthuebaomoi=function () {
            Highcharts.chart('thuebaomoi', {
                chart: {
                    type: 'line'
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    // categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
                    categories: $scope.thuebaomoi.names
                },
                yAxis: {
                    title: {
                        text: 'Số lượng'
                    }
                },

                series: [{
                    name: 'Số lượng',
                    data: $scope.thuebaomoi.values
                }]
            });
        };

        /*CHO MUC CHON CHI TIEU*/

        $scope.changeType=function () {
            switch($scope.type){
                case "0":
                    $scope.showWeek=true;
                    $scope.showYear=true;
                    $scope.showMonth=false;
                    break;
                case "1":
                    $scope.showWeek=false;
                    $scope.showYear=true;
                    $scope.showMonth=true;
                    break;
                case "2":
                    $scope.showWeek=false;
                    $scope.showYear=true;
                    $scope.showMonth=false;
                    break;
                default:
                    break;
            }
        };

        $scope.typeError="";
        $scope.yearError="";
        $scope.monthError="";
        $scope.weekError="";
        $scope.nameTitleSwrap="";
        $scope.refreshStatus=function () {
            $scope.typeError="";
            $scope.yearError="";
            $scope.monthError="";
            $scope.weekError="";
        };
        $scope.checkBeforeSearch=function () {
            $scope.refreshStatus();
          if($scope.type==null || $scope.type=='undefined' || $scope.type.length==0){
              $scope.typeError="Bạn phải chọn chỉ tiêu thống kê.";
              return false;
          }
          if($scope.year==null ||$scope.year=='undefined' || $scope.year.length==0){
              $scope.yearError="Bạn phải chọn năm.";
              return false;
          }
          switch ($scope.type){
              case "0":
                  $scope.month="";
                  if($scope.week==null ||$scope.week=='undefined' || $scope.week.length==0){
                      $scope.weekError="Bạn phải chọn tuần.";
                      return false;
                  }
                  $scope.nameTitleSwrap="tuần "+$scope.week+" của năm "+$scope.year;
                  break;
              case "1":
                  $scope.week="";
                  if($scope.month==null ||$scope.month=='undefined' || $scope.month.length==0){
                      $scope.monthError="Bạn phải chọn tháng.";
                      return false;
                  }
                  $scope.nameTitleSwrap="tháng "+$scope.month+"/"+$scope.year;
                  break;
              case "2":
                  $scope.month="";
                  $scope.week="";
                  $scope.nameTitleSwrap="năm "+$scope.year;
                  break;
              default:break;
          }
          return true;
        };

        $scope.search=function () {
            if($scope.checkBeforeSearch()){
                $scope.reloadAllChart();
            }
        };
        
        $scope.reloadAllChart=function () {
            $http.get(preUrl+"/trade-count",{params:{year:$scope.year,month:$scope.month,week:$scope.week}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.sanluonggiaodich=response.data;
                        $scope.loadBieuDoTronLuongGiaoDich();

                    }
                });

            $http.get(preUrl+"/revenue-count",{params:{year:$scope.year,month:$scope.month,week:$scope.week}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.doanhthuloaigiaodich=response.data;
                        $scope.loadBieuDoTronDoanhThu();

                    }
                });

            $http.get(preUrl+"/revenue",{params:{year:$scope.year,month:$scope.month,week:$scope.week}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.bieudodoanhthu=response.data;
                        $scope.loadBieuDoDoanhThu();
                    }
                });
            $http.get(preUrl+"/cus-new",{params:{year:$scope.year,month:$scope.month,week:$scope.week}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.khachhangmoi=response.data;
                        $scope.bieudoduongkhachhangmoi();
                    }
                });
            $http.get(preUrl+"/msisdn-new",{params:{year:$scope.year,month:$scope.month,week:$scope.week}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.thuebaomoi=response.data;
                        $scope.bieudoduongthuebaomoi();
                    }
                });

            $scope.nameTitle=$scope.nameTitleSwrap;
        }


    }]);