var rendererOptions = {
  draggable: true
};
var directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
var directionsService = new google.maps.DirectionsService();
var map;
var pathsArray = [];
var markersArray = [];
var circlesArray = [];

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
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById('mapa'), mapOptions);

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            map.setCenter(pos);
        });
    }
    
    google.maps.event.addListener(map, 'click', function(event) {
        addMarker(event.latLng);
    });
}

function deleteOverlays() {
	  if (markersArray) {
	    for (i in markersArray) {
	      markersArray[i].setMap(null);
	    }
	    markersArray.length = 0;
	  }
	  if (circlesArray) {
		    for (i in circlesArray) {
		    	circlesArray[i].setMap(null);
		    }
		    circlesArray.length = 0;
		  }	  
	}

function obterEndereco(lat, lng){
	var geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(lat, lng);
    geocoder.geocode({'latLng': latlng}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
        	$("#endereco").val(results[0].formatted_address);
        }
    });
}

function addMarker(location) {
	deleteOverlays();
  
	marker = new google.maps.Marker({
		position: location,
		map: map,
		draggable: true
	});
	
	obterEndereco(location.Ya, location.Za);
	
	var circleOptions = {
			strokeColor: "#FF0000",
			strokeOpacity: 0.8,
			strokeWeight: 2,
			fillColor: "#FF0000",
			fillOpacity: 0.35,
			map: map,
			center: location,
			radius: 200
	    };
	
	circle = new google.maps.Circle(circleOptions);
  
	markersArray.push(marker);
	circlesArray.push(circle);
	
	$.ajax({
		url: "http://localhost:8080/sharecar/api/route/" + location.Ya + "/" + location.Za + "/" + 200,
		success: function(data){
			console.log(data);
		},
		dataType: 'json'
	});
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

function removeRotasDoMapa() {
  if (pathsArray) {
    for (i in pathsArray) {
    	pathsArray[i].setMap(null);
    }
    pathsArray.length = 0;
  }  
}

function mostrarRota(rotaId){
	removeRotasDoMapa();
	directionsDisplay.setMap(null);
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
	        var bounds = new google.maps.LatLngBounds();

            $.each(data.coords, function (key, val) {
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

function mostrarRotaNoMapa(ptoPartida, ptoChegada) {
	
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