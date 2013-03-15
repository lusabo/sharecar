function Schedule(){
	this.id;
	this.weekday;
	this.time;
	this.route;
}

Schedule.prototype._insert = function(weekday, time, route, _success, _error) {
	
	this.weekday = weekday;
	this.time = time;
	this.route = route;

	$.ajax({
		type: "PUT",
		url: "api/schedule",
		data: JSON.stringify(this),
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		success: function(){
			_success('insert ok');
		},
		error: function(){
			_error('insert erro');
		} 
	});

};