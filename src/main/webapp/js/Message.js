function Message() {
	this.text;
}

Message.prototype._send = function(_text, _success, _error) {
	this.text = _text;

	$.ajax({
		type : "POST",
		url : "api/message",
		data : JSON.stringify(this),
		contentType : "application/json;charset=UTF-8",
		success : function() {
			//_success($("#msg-message"), 'Mensagem enviada com sucesso.');
			console.log('mail ok');
		},
		error : function() {
			//_error($("#msg-message"), 'Erro ao enviar mensagem.');
			console.log('mail erro');
		}
	});

};