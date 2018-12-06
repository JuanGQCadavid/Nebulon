function updateDoc(){
    var xhttp = new XMLHttpRequest();

    // Changes when the xhttp.readyState changes
    xhttp.onreadystatechange = function(){
	if(this.readyState == 4 && this.status == 200){
	    var levels = this.responseText;
	    var levels_array = eval('(' + levels + ')');

	    for(lvl in levels_array){
		document.getElementById("lvl1").innetHTML =
		    lvl['nebulon_fg1name'] + ": " + lvl['nebulon_fg1level'];
		document.getElementById("lvl2").innetHTML =
		    lvl['nebulon_fg2name'] + ": " + lvl['nebulon_fg2level'];
	    }
	}
    };
}
