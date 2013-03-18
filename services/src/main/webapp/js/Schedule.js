function Schedule(){
	this.id;
	this.weekday;
	this.hour;
	this.route;
}

Schedule.prototype._insert = function(weekday, hour, route, _success, _error) {
	
	this.weekday = weekday;
	this.hour = hour + ":00";
	this.route = route;

	$.ajax({
		async: false,
		type: "PUT",
		url: "api/schedule",
		data: JSON.stringify(this),
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		success: function(){
			_success('insert schedule ok');
		},
		error: function(){
			_error('insert schedule erro');
		} 
	});

};

Schedule.prototype._delete = function(_id, _success, _error) {
	$.ajax({
		async: false,
		type: "DELETE",
		url : "api/schedule/" + _id,
		success: function(){
			_success('delete schedule ok');
		},
		error: function(){
			_error('delete schedule erro');
		} 
	});
};

Schedule.prototype._load = function(_id, _callback){
	$.ajax({
		
		type : "GET",
		url : "api/schedule?routeId=" + _id,
		dataType : "json",
		success: function(data){
			_callback(data);
		}
	});
	
};