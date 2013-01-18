function carregarMapaCentralizadoConformeGeolocalizacaoDoUsuario() {
	var mapOptions = {
		zoom : 6,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById('mapa'), mapOptions);

	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			map.setCenter(pos);
		});
	}
}

function mostrarRotaNoMapa(ptoPartida, ptoChegada){
	var mapOptions = {
			zoom : 6,
			mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById('mapa'), mapOptions);
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