var map;
var user = new Usuario("93579551515");

function Usuario(username) {
    this.username = username;
}

function Coord(lat, lng) {
    this.lat = lat;
    this.lng = lng;
}

function Rota(description, user, coords) {
    this.description = description;
    this.user = user;
    this.coords = coords;
}

function carregarMapaCentralizadoConformeGeolocalizacaoDoUsuario() {
    var mapOptions = {
        zoom: 6,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById('mapa'), mapOptions);

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            map.setCenter(pos);
        });
    }
}

function carregarTabelaDeRotas() {
	$('#table-rotas tbody > tr').remove();
	$.ajax({
		type: "GET",
        url: "http://localhost:8080/sharecar/api/route",
        dataType: "json",
        success: function (data) {
            $.each(data, function (key, val) {
            	var tr = '';
            	tr += '<tr>';
            	tr += '<td width="24px">';
            	tr += '<a href="#" name="route-' + val.id + '" route="' + val.id + '">';
            	tr += '<img src="img/map.png" style="height: 24px; width: 24px;" border="0" title="Ver rota no mapa">';
            	tr += '</a>';
            	tr += '</td>';
            	tr += '<td>' + val.description + '</td>';
            	tr += '</tr>';
            	$("#table-rotas > tbody:last").append(tr);
            });
        }
    });
}

function mostrarRota(rotaId){
	$.ajax({
        type: "GET",
        url: "http://localhost:8080/sharecar/api/route/" + rotaId,
        dataType: "json",
        success: function(data){
        	var polyOptions = {
	                strokeColor: "#8D8DFF",
	                strokeOpacity: 1.0,
	                strokeWeight: 4
	            };
            var poly = new google.maps.Polyline(polyOptions);
            var path = new Array();

            poly.setMap(null);									
	            
            var bounds = new google.maps.LatLngBounds();

            $.each(data.coords, function (key, val) {
                path.push(new google.maps.LatLng(val.lat, val.lng));
                bounds.extend(new google.maps.LatLng(val.lat, val.lng));
            });
            poly.setPath(path);
            poly.setMap(map);
            map.fitBounds(bounds);
        }
	});
}

function mostrarRotaNoMapa(ptoPartida, ptoChegada) {
	var directionsService = new google.maps.DirectionsService();
	var directionsDisplay = new google.maps.DirectionsRenderer();

    directionsDisplay.setMap(map);

    var request = {
        origin: ptoPartida,
        destination: ptoChegada,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };

    directionsService.route(request, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });
}

function pegarRota(ptoPartida, ptoChegada, callback) {
    var directionsService = new google.maps.DirectionsService();
    var request = {
        origin: ptoPartida,
        destination: ptoChegada,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };
    directionsService.route(request, function (response, status) {
    	var coords;
        if (status == google.maps.DirectionsStatus.OK) {
        	coords = google.maps.geometry.encoding.decodePath(response.routes[0].overview_polyline.points);
            callback(coords);
        }
    });
}