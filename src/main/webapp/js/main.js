var user = new Usuario("93579551515");

function Usuario(username){
	this.username = username;
}

function Coord(lat, lng){
	this.lat = lat;
	this.lng = lng;
}

function Rota(description, user, coords){
	this.description = description;
	this.user = user;
	this.coords = coords;
}

function carregarMapaCentralizadoConformeGeolocalizacaoDoUsuario() {
	var mapOptions = {
			zoom : 6,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
	// map é uma variável global - está sem o var
	map = new google.maps.Map(document.getElementById('mapa'), mapOptions);
	
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			map.setCenter(pos);
		});
	}
}

function mostrarRotaNoMapa(ptoPartida, ptoChegada){
	var directionsService = new google.maps.DirectionsService();
	var directionsDisplay = new google.maps.DirectionsRenderer();
	directionsDisplay.setMap(map);
	var request = {
    	origin: ptoPartida,
        destination: ptoChegada,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };
	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}
	});
}

function pegarRota(ptoPartida, ptoChegada, callback){
    var directionsService = new google.maps.DirectionsService();
    var request = {
        origin: ptoPartida,
        destination: ptoChegada,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };
    directionsService.route(request, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            pontos = google.maps.geometry.encoding.decodePath(response.routes[0].overview_polyline.points);
            callback(pontos);
        }
    });
}

function carregarRotasDoUsuarioNaTabela() {
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/sharecar/api/route",
		dataType : "json",
		success: function(data){
			$.each(data, function (key, val) {
				$('#table-rotas > tbody:last').append('<tr><td>'+val.description+'</td></tr>');
                console.log(val);
            });
		}
	});
}