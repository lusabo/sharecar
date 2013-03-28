function User() {
	this.id;
	this.name;
	this.displayName;
	this.email;
	this.telephoneNumber;
}

User.prototype._login = function(username, password) {

	$.ajax({
		type : "POST",
		url : "api/auth",
		data : {
			"username" : username,
			"password" : password
		},
		success : function() {
			window.location = "home";
		},
		error : function() {
			$("#msg").text("Login e/ou Senha inválidos.");
		}
	});

};

User.prototype._logoff = function() {

	$.ajax({
		type : "DELETE",
		url : "api/auth",
		success : function() {
			window.location = "login";
		}
	});

};
