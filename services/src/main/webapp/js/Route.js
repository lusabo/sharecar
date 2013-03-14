/*
 * TODO: Tratar quando não for possível carregar as rotas
 * 		 Tratar quando não for possível carregar uma rota
 * 		 Verificar a possibilidade de tirar o async do delete, a tabela não recarrega com async = true
 * */
function Route() {
	this.id;
	this.description;
	this.user;
	this.coords;
	this.periods;
}

Route.prototype._insert = function(_description, _coords, _success, _error) {
	
	var coordinates = new Array();
	
	for (var i = 0; i < _coords.length; i++) {
		coordinates.push(new Coord(_coords[i].lat(), _coords[i].lng()));
	}
	
	this.description = _description;
	this.coords = coordinates;
	
	$.ajax({
		type: "PUT",
		url: "api/route",
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

Route.prototype._delete = function(_id, _success, _error) {
	$.ajax({
		async: false,
		type: "DELETE",
		url : "api/route/" + _id,
		success: function(){
			_success('delete ok');
		},
		error: function(){
			_error('delete erro');
		} 
	});
};

Route.prototype._load = function(_id, _callback){
	$.ajax({
		type : "GET",
		url : "api/route/" + _id,
		dataType : "json",
		success: function(data){
			_callback(data);
		}
	});
};

Route.prototype._findAll = function(_callback){
	$.ajax({
        type: "GET",
        url: "api/route",
        dataType: "json",
        success: function(data){
        	_callback(data);
        }
    });
};
