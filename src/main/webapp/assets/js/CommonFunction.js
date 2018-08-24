//Viết các hàm javascript sử dụng chung
CommonFunction = {

	 alpha:function(e) {
	    var k;
	    document.all ? k = e.keyCode : k = e.which;
	    return (k!=34 && k!=39 && k!=47 && k!=92 && k!=124);
	},

	replaceAllSC:function(find, replace, str){
		while( str.indexOf(find) > -1)
	      {
	        str = str.replace(find, replace);
	      }
	      return str;
	},

	formatCurrency : function(ctrl, event) {
	    //Check if arrow keys are pressed - we want to allow navigation around textbox using arrow keys
	    if (event != null)
	    {
	    	if(event.keyCode == 37 || event.keyCode == 38 || event.keyCode == 39 || event.keyCode == 40)
	    		return false;
	    }
	    var val = ctrl.value.replace(/,/g, "");
	    val = '' + (val != '' ? parseInt(val) : '');
	    ctrl.value = "";
	    val += '';
	    x = val.split('.');
	    x1 = x[0];
	    x2 = x.length > 1 ? '.' + x[1] : '';

	    var rgx = /(\d+)(\d{3})/;

	    while (rgx.test(x1)) {
	        x1 = x1.replace(rgx, '$1' + ',' + '$2');
	    }

	    ctrl.value = x1 + x2;
	},
	
	formatCurrencyHtml : function(ctrl) {
	    var val = ctrl.innerHTML;
	    val = val.replace(/,/g, "");
	    val += '';
	    x = val.split('.');
	    x1 = x[0];
	    x2 = x.length > 1 ? '.' + x[1] : '';

	    var rgx = /(\d+)(\d{3})/;

	    while (rgx.test(x1)) {
	        x1 = x1.replace(rgx, '$1' + ',' + '$2');
	    }

	    ctrl.innerHTML = x1 + x2;
	},

	checkNumeric: function(event) {
		var x = event.which || event.keyCode;
	    return x == 8 || x == 46 || (x >= 48 && x <= 57);
	},
	
	currencyToNum:function(ctrl){
		if(ctrl.value!=""){
			ctrl.value=CommonFunction.replaceAll(",","",ctrl.value);
		}
	},
	replaceAll:function(find, replace, str) {
	  return str.replace(new RegExp(find, 'g'), replace);
	},
	submitForm: function (){
		$(".currency").each(function(){
			CommonFunction.currencyToNum(this);
		});
		$('#btnSave').click();
		$(".currency").each(function(){
			CommonFunction.formatCurrency(this, null);
		});
	},

}


$(document).ready(function(){
	$(".currency").each(function(){
		CommonFunction.formatCurrency(this, null);
	});
	$(".currencyHtml").each(function(){
		CommonFunction.formatCurrencyHtml(this);
	});
	$('.currency')
    .keyup(function(event){
    	CommonFunction.formatCurrency(this, event);
    })
    .keypress(function(event){
    	return CommonFunction.checkNumeric(event);
    })
    /*.blur(function(){
    	CommonFunction.checkRound(this);
    })*/
    .change(function(event){
    	CommonFunction.formatCurrency(this, event);
    });
});
