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

	for ( var i = 0; i < _coords.length; i++) {
		coordinates.push(new Coord(_coords[i].lat(), _coords[i].lng()));
	}

	this.description = _description;
	this.coords = coordinates;

	return $.ajax({
		type : "PUT",
		url : "api/route",
		data : JSON.stringify(this),
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		success : function() {
			_success($("#route-message"), 'Rota cadastrada com sucesso.');
		},
		error : function() {
			_error($("#route-message"), 'Erro ao cadastrar rota.');
		}
	});
};

Route.prototype._delete = function(_id, _success, _error) {
	
	$.ajax({
		async : false,
		type : "DELETE",
		url : "api/route/" + _id,
		success : function() {
			_success($("#route-message"), 'Rota removida com sucesso.');
		},
		error : function() {
			_error($("#route-message"), 'Erro ao remover rota.');
		}
	});
};

Route.prototype._load = function(_id, _callback) {
	
	$.ajax({
		type : "GET",
		url : "api/route/" + _id,
		dataType : "json",
		success : function(data) {
			_callback(data);
		}
	});
};

Route.prototype._findAll = function(_callback) {
	
	$.ajax({
		type : "GET",
		url : "api/route",
		dataType : "json",
		success : function(data) {
			_callback(data);
		}
	});
};

Route.prototype._find = function(_parameters, _callback) {
	console.log(_parameters);
	
	$.ajax({
		type : "GET",
		url : "api/route?" + _parameters,
		dataType : "json",
		//data: _parameters,
		success : function(data) {
			console.log('teste1');
			_callback(data);
		}
	});
	
};
