$.ajaxSetup({
	statusCode : {
		401 : function() {
			$("#msg").text("Login e/ou Senha Inválidos.");
		},
		500 : function() {
			$("#msg").text("Falha no processo de autenticação.");
		}
	}
});

function User() {
	this.id;
	this.username;
	this.fullname;
}

User.prototype._login = function(username, password) {

	$.ajax({
		type : "POST",
		url : "api/auth",
		data : {"username":username,"password":password},
		success : function() {
			window.location = "home";
		}
	});
};

User.prototype._logoff = function() {

	$.ajax({
		type: "DELETE",
		url: "api/auth",
		success: function(){
			window.location = "home";
		}						
	});
};
