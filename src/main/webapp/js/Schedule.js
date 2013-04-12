function Schedule() {
	this.id;
	this.weekdayId;
	this.weekday;
	this.hour;
	this.route;
}

Schedule.prototype._insert = function(weekday, hour, route, _success, _error) {

	this.weekday = weekday;
	this.hour = hour + ":00";
	this.route = route;

	return $.ajax({
		type : "PUT",
		url : "api/schedule",
		data : JSON.stringify(this),
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		success : function() {
			_success($("#sched-message"), 'Horário cadastrado com sucesso.');
		},
		error : function() {
			_error($("#sched-message"), 'Erro ao cadastrar horário.');
		}
	});

};

Schedule.prototype._delete = function(_id, _success, _error) {
	
	$.ajax({
		async : false,
		type : "DELETE",
		url : "api/schedule/" + _id,
		success : function() {
			_success($("#sched-message"), 'Horário removido com sucesso.');
		},
		error : function() {
			_error($("#sched-message"), 'Erro ao remover horário.');
		}
	});
};

Schedule.prototype._load = function(_id, _callback) {
	
	$.ajax({
		type : "GET",
		url : "api/schedule/" + _id,
		dataType : "json",
		success : function(data) {
			_callback(data);
		}
	});
};

Schedule.prototype._load2 = function(_id) {
	
	$.ajax({
		type : "GET",
		url : "api/schedule/" + _id,
		dataType : "json",
		context: this,
		success : function(data) {
			this.id = data.id;
			this.weekday = data.weekday;
			this.hour = data.hour;
			this.route = data.route;
		}
	});
};

Schedule.prototype._findByRoute = function(_routeId, _callback) {
	
	$.ajax({
		type : "GET",
		url : "api/schedule?routeId=" + _routeId,
		dataType : "json",
		success : function(data) {
			var aSchedule = new Array();
			$.each(data, function(){
				var schedule = new Schedule();
				schedule.id = this.id;
				schedule.weekday = this.weekday;
				schedule.hour = this.hour;
				schedule.route = this.route;
				
				switch (this.weekday) {
					case "DOMINGO":
						schedule.weekdayId = 1; 
						break;
					case "SEGUNDA":
						schedule.weekdayId = 2; 
						break;
					case "TERÇA":
						schedule.weekdayId = 3; 
						break;
					case "QUARTA":
						schedule.weekdayId = 4; 
						break;
					case "QUINTA":
						schedule.weekdayId = 5; 
						break;
					case "SEXTA":
						schedule.weekdayId = 6; 
						break;
					case "SÁBADO":
						schedule.weekdayId = 7; 
						break;
				}
				
				aSchedule.push(schedule);
			});
			_callback(aSchedule);
		}
	});
};