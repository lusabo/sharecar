// Classes
function User(username) {
	this.username = username;
}

function Coord(lat, lng) {
	this.lat = lat;
	this.lng = lng;
}

function Route(description, user, coords, weekday, hour) {
	this.description = description;
	this.user = user;
	this.coords = coords;
	this.weekday = weekday;
	this.hour = hour;
}

// Variávei Globais
var circlesArray = new Array();
var rendererOptions = {
	draggable : true
};
var directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
var directionsService = new google.maps.DirectionsService();
var map;
var markersArray = new Array();
var pathsArray = new Array();
var user = new User("93579551515");

// Funções de Negócio

/* 
 * Carrega o mapa centralizado conforme a geolocalização do usuário 
 */
function loadMapCenteredWithUserGeolocation() {
    var mapOptions = {
        zoom: 14,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        draggable: true
    };
    var pos;

    map = new google.maps.Map(document.getElementById('mapa'), mapOptions);

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            map.setCenter(pos);
        });
    }
}

/*
 * Carrega a tabela com as rotas salvas do usuário
 */
function loadRoutesTable() {
    $('#table-rotas tbody > tr').remove();
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/sharecar/api/route",
        dataType: "json",
        success: function (data) {
            $.each( data,
            function (key, val) {
                var tr = '';
                tr += '<tr>';
                tr += '<td width="24px">';
                tr += '<a href="#" name="route-' + val.id + '" route="' + val.id + '">';
                tr += '<img src="img/map.png" style="height: 24px; width: 24px;" border="0" title="Ver rota no mapa">';
                tr += '</a>';
                tr += '</td>';
                tr += '<td>' + val.description + '</td>';
                tr += '</tr>';
                $("#table-rotas > tbody:last").append(
                tr);
            });
        }
    });
}

function loadSearchRoutesTable(lat, lng, radius){
	$('#table-pesquisa tbody > tr').remove();
	$.ajax({
		type: "GET",
		url : "http://localhost:8080/sharecar/api/route/" + lat + "/" + lng + "/" + radius,
		dataType : 'json',
		success : function(data) {
			 $.each(data,
			        function (key, val) {
			                var tr = '';
			                tr += '<tr>';
			                tr += '<td width="24px">';
			                tr += '<a href="#" name="route-' + val.id + '" route="' + val.id + '">';
			                tr += '<img src="img/map.png" style="height: 24px; width: 24px;" border="0" title="Ver rota no mapa">';
			                tr += '</a>';
			                tr += '</td>';
			                tr += '<td>' + val.description + '</td>';
			                tr += '</tr>';
			                $("#table-pesquisa > tbody:last").append(tr);
			            });
		}
	});
}
/*
 * Adiciona um marcador no mapa com o raio definido pelo usuário
 */
function addMarker(location, _radius) {
	// Remove todas as marcações
	deleteOverlays(markersArray);
	deleteOverlays(pathsArray);

	var marker = new google.maps.Marker({
		position : location,
		map : map,
		draggable : true
	});
	
	var radius = _radius;
	
	getAddress(location.lat(), location.lng());
	addCircle(location, radius);
	markersArray.push(marker);
	
	google.maps.event.addListener(marker, 'dragstart', function () {
		radius =  circlesArray[0].getRadius();
		deleteOverlays(pathsArray);
		deleteOverlays(circlesArray);
	});

	google.maps.event.addListener(marker, 'dragend', function (position) {
		addCircle(position.latLng, radius);
		getAddress(position.latLng.lat(), position.latLng.lng());
		loadSearchRoutesTable(position.latLng.lat(), position.latLng.lng(), radius);
	});
}

function addCircle(location, _radius){
	deleteOverlays(circlesArray);
	var circleOptions = {
		strokeColor : "#FF0000",
		strokeOpacity : 0.8,
		strokeWeight : 2,
		fillColor : "#FF0000",
		fillOpacity : 0.35,
		map : map,
		center : location,
		radius : parseInt(_radius,10)
	};

	circle = new google.maps.Circle(circleOptions);
	circlesArray.push(circle);
}

/*
 * Carrega a rota selecionada no mapa 
 */
function loadRoute(routeId) {
	deleteOverlays(pathsArray);
	directionsDisplay.setMap(null);
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/sharecar/api/route/" + routeId,
		dataType : "json",
		success : function(data) {

			var polyOptions = {
				strokeColor : "#8D8DFF",
				strokeOpacity : 1.0,
				strokeWeight : 4
			};
			var poly = new google.maps.Polyline(polyOptions);
			var path = new Array();
			var bounds = new google.maps.LatLngBounds();

			$.each(data.coords, function(key, val) {
				path.push(new google.maps.LatLng(val.lat, val.lng));
				bounds.extend(new google.maps.LatLng(val.lat, val.lng));
			});
			poly.setPath(path);
			poly.setMap(map);
			pathsArray.push(poly);
			map.fitBounds(bounds);
		}
	});
}

/*
 * Mostrar rota entre dois pontos
 */
function showRouteBetweenPoints(start, end) {

	deleteOverlays(pathsArray);
	directionsDisplay.setMap(map);

	var request = {
		origin : start,
		destination : end,
		travelMode : google.maps.DirectionsTravelMode.DRIVING
	};

	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}
	});
}

function saveRoute(name){

	// Pega a rota atual, mesmo depois de ter sido mudada manualmente
	var coords = google.maps.geometry.encoding.decodePath(directionsDisplay.getDirections().routes[0].overview_polyline.points);
	var coordenadas = new Array();
	
	for (var i = 0; i < coords.length; i++) {
		coordenadas.push(new Coord(coords[i].lat(), coords[i].lng()));
	}
	
	var route = new Route(name, user, coordenadas);

	$.ajax({
		type: "PUT",
		url: "http://localhost:8080/sharecar/api/route",
		data: JSON.stringify(route),
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		success: function () {
			alert('Rota salva com sucesso.');
			loadRoutesTable();
		}
	});
}

// Funções Utilitárias

/*
 * Remove as rotas e marcadores do mapa
 */
function deleteOverlays(_array) {
	if (_array) {
		for (i in _array) {
			_array[i].setMap(null);
		}
		_array.length = 0;
	}
}

function getAddress(lat, lng) {
	var geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(lat, lng);
	geocoder.geocode({
		'latLng' : latlng
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			$("#endereco").val(results[0].formatted_address);
		}
	});
}


