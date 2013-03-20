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
			_success($("#sched-message"),'Horário cadastrado com sucesso.');
		},
		error: function(){
			_error($("#sched-message"),'Erro ao cadastrar horário.');
		} 
	});

};

Schedule.prototype._delete = function(_id, _success, _error) {
	$.ajax({
		async: false,
		type: "DELETE",
		url : "api/schedule/" + _id,
		success: function(){
			_success($("#sched-message"),'Horário removido com sucesso.');
		},
		error: function(){
			_error($("#sched-message"),'Erro ao remover horário.');
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