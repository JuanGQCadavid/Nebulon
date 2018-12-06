function updateDoc(){

    var xhttp = new XMLHttpRequest();
    
    // Changes when the xhttp.readyState changes
    xhttp.onreadystatechange = function(){
	if(this.readyState == 4 && this.status == 200){
	    var levels = this.responseText;
	    var levels_array = eval('(' + levels + ')');

	    var row = 2;
	    levels_array.forEach(function(lvl, index){

		var update1 = lvl['nebulon_fg1name'] + ": " + lvl['nebulon_fg1level'] + '%';
		var update2 = lvl['nebulon_fg2name'] + ": " + lvl['nebulon_fg2level'] + '%';

		
		document.getElementById("nebulizer-info").rows[index+2].cells[1].innerHTML = update1.toString();
		
		document.getElementById("nebulizer-info").rows[index+2].cells[2].innerHTML = update2.toString();
		
		row += 1;
	    });

	}
    };
    xhttp.open("GET", "/management/update", true);
    xhttp.send();
}

setInterval(updateDoc, 1500);
