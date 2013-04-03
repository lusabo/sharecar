// Variáveis globais
var directionsDisplay = new google.maps.DirectionsRenderer({
	draggable : true
});
var directionsService = new google.maps.DirectionsService();
var map;
var circlesArray = new Array();
var markersArray = new Array();
var pathsArray = new Array();

// Inicializa mapa centralizado conforme geolocalização do usuário
function loadMapCenteredWithUserGeolocation(_elemento, _disableDefaultUI,
		_draggable) {

	var mapOptions = {
		zoom : 13,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		draggable : _draggable,
		disableDefaultUI : _disableDefaultUI
	};
	var pos;

	map = new google.maps.Map(document.getElementById(_elemento), mapOptions);

	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			pos = new google.maps.LatLng(position.coords.latitude,
					position.coords.longitude);
			map.setCenter(pos);
		});
	}
}

// Mostrar rota entre dois pontos
function showRouteBetweenPoints(start, end) {

	var request = {
		origin : start,
		destination : end,
		travelMode : google.maps.DirectionsTravelMode.DRIVING
	};

	deleteOverlays(pathsArray);
	directionsDisplay.setMap(map);

	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}
	});
}

// Carrega a tabela com as rotas salvas do usuário
function loadRoutesTable(data) {
	$('#table-rotas tbody > tr').remove();
	var tr = '';
	if (data.length == 0) {
		tr = '<tr><td colspan="4">Nenhuma rota cadastrada.</td></tr>';
		$("#table-rotas > tbody:last").append(tr);
	} else {
		$
				.each(
						data,
						function(key, val) {
							tr = '';
							tr += '<tr>';
							tr += '<td width="20px">';
							tr += '<a href="#" name="route-' + val.id
									+ '" route="' + val.id + '">';
							tr += '<img src="img/map.png" style="height: 20px; width: 20px;" border="0" title="Ver rota no mapa">';
							tr += '</a>';
							tr += '</td>';
							tr += '<td>' + val.description + '</td>';
							tr += '<td width="20px">';
							tr += '<a href="#" name="sched-' + val.id
									+ '" route="' + val.id + '">';
							tr += '<img src="img/calendar.png" style="height: 20px; width: 20px;" border="0" title="Agendar rota">';
							tr += '</a>';
							tr += '</td>';
							tr += '<td width="20px">';
							tr += '<a href="#" name="del-' + val.id
									+ '" route="' + val.id + '">';
							tr += '<img src="img/delete.png" style="height: 20px; width: 20px;" border="0" title="Apagar rota">';
							tr += '</a>';
							tr += '</td>';
							tr += '</tr>';
							$("#table-rotas > tbody:last").append(tr);
						});
	}
}

// Carrega a tabela com os horários da rota
function loadSchedulesTable(data) {
	$('#table-schedules tbody > tr').remove();
	var tr = '';
	if (data.length == 0) {
		tr = '<tr><td colspan="3">Rota sem horário disponibilizado.</td></tr>';
		$("#table-schedules > tbody:last").append(tr);
	} else {
		$
				.each(
						data,
						function(key, val) {
							tr = '';
							tr += '<tr>';
							tr += '<td>' + val.weekday + '</td>';
							tr += '<td>' + (val.hour).substr(0, 5) + '</td>';
							tr += '<td width="20px">';
							tr += '<a href="#" name="del-' + val.id
									+ '" schedule="' + val.id + '" route="'
									+ val.route.id + '">';
							tr += '<img src="img/delete.png" style="height: 20px; width: 20px;" border="0" title="Apagar rota">';
							tr += '</a>';
							tr += '</td>';
							tr += '</tr>';
							$("#table-schedules > tbody:last").append(tr);
						});
	}
}

// Mostra determinada rota no mapa
function showRouteOnMap(route) {
	deleteOverlays(pathsArray);
	directionsDisplay.setMap(null);
	var polyOptions = {
		strokeColor : "#8D8DFF",
		strokeOpacity : 1.0,
		strokeWeight : 4
	};
	var poly = new google.maps.Polyline(polyOptions);
	var path = new Array();
	var bounds = new google.maps.LatLngBounds();
	$.each(route.coords, function(key, val) {
		path.push(new google.maps.LatLng(val.lat, val.lng));
		bounds.extend(new google.maps.LatLng(val.lat, val.lng));
	});
	poly.setPath(path);
	poly.setMap(map);
	pathsArray.push(poly);
	map.fitBounds(bounds);
}

function openRouteSchedDialog(route) {
	var schedule = new Schedule();
	schedule._load(route.id, loadSchedulesTable);
	$("#dialog-sched").dialog("open").dialog(
			{
				open : function() {
					console.log('open');
					$("#sched-message").text("");
				},
				create : function() {
					console.log('create');
					$("#sched-message").text("");
				},
				focus : function() {
					console.log('focus');
					$("#sched-message").text("");
				},
				title : "Rota: " + route.description,
				buttons : [ {
					text : "Salvar",
					click : function() {
						$('input:checked[name=weekday]').each(
								function() {
									schedule._insert($(this).val(), $("#hour")
											.val(), route, success, error);
								});
						schedule._load(route.id, loadSchedulesTable);
					}
				} ],
				close : function() {
					$("#frm-sched").each(function() {
						this.reset();
					});
					$("#sched-message").text("");
				}
			});
}

// Funções utilitárias
function deleteOverlays(_array) {
	if (_array) {
		for (i in _array) {
			_array[i].setMap(null);
		}
		_array.length = 0;
	}
}

function success(target, msg) {
	$(target).addClass("sucesso").text(msg);
}

function error(target, msg) {
	$(target).addClass("erro").text(msg);
}

/** **************************************** */
// Busca pekas rotas
function loadSearchRoutesTable(lat, lng, radius, weekday, hourini, hourend) {
	$('#table-pesquisa tbody > tr').remove();
	$
			.ajax({
				type : "GET",
				url : "api/route?lat=" + lat + "&lng=" + lng + "&radius="
						+ radius + "&weekday=" + weekday + "&hourini="
						+ hourini + ":00&hourend=" + hourend + ":00",
				dataType : 'json',
				success : function(data) {
					var tr = '';
					if (data.length == 0) {
						tr = '<tr><td colspan="3">Nenhuma rota encontrada</td></tr>';
						$("#table-pesquisa > tbody:last").append(tr);
					} else {
						$
								.each(
										data,
										function(key, val) {
											tr = '';
											tr += '<tr>';
											tr += '<td width="20px">';
											tr += '<a href="#" name="route-'
													+ val.id + '" route="'
													+ val.id + '">';
											tr += '<img src="img/map.png" style="height: 20px; width: 20px;" border="0" title="Ver rota no mapa">';
											tr += '</a>';
											tr += '</td>';
											tr += '<td>' + val.user.displayName
													+ '<br/>'
													+ val.user.telephoneNumber
													+ '<br/>' + val.user.email
													+ '</td>';
											tr += '</tr>';
											$("#table-pesquisa > tbody:last")
													.append(tr);
										});
					}
				}
			});
	$("#table-pesquisa").show();
}

// Adiciona um marcador no mapa com o raio definido pelo usuário
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

	google.maps.event.addListener(marker, 'dragstart', function() {
		radius = circlesArray[0].getRadius();
		deleteOverlays(pathsArray);
		deleteOverlays(circlesArray);
	});

	google.maps.event.addListener(marker, 'dragend', function(position) {
		addCircle(position.latLng, radius);
		getAddress(position.latLng.lat(), position.latLng.lng());
		loadSearchRoutesTable(position.latLng.lat(), position.latLng.lng(), $(
				"#radius").val(), $("input:radio[name=radio]:checked").val(),
				$("#hour-ini").val(), $("#hour-end").val());
	});
}

// Adicionar o raio de busca.
function addCircle(location, _radius) {

	deleteOverlays(circlesArray);
	var circleOptions = {
		strokeColor : "#FF0000",
		strokeOpacity : 0.8,
		strokeWeight : 2,
		fillColor : "#FF0000",
		fillOpacity : 0.35,
		map : map,
		center : location,
		radius : parseInt(_radius, 10)
	};

	circle = new google.maps.Circle(circleOptions);
	circlesArray.push(circle);
}

function openSearchDialog() {
	$("#dialog-buscar")
			.dialog("open")
			.dialog("option", "title", "Buscar Carona")
			.dialog(
					{
						buttons : [ {
							text : "Pesquisar",
							click : function() {
								if (markersArray.length == 0) {
									alert("Você deve selecionar um ponto no mapa!");
								} else {
									loadSearchRoutesTable(
											markersArray[0].position.lat(),
											markersArray[0].position.lng(),
											$("#radius").val(),
											$("input:radio[name=radio]:checked")
													.val(), $("#hour-ini")
													.val(), $("#hour-end")
													.val());
								}
							}
						} ]
					});
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

function teste(data) {
	$('#table-rotas')
			.dataTable(
					{
						"bFilter" : false,
						"bLengthChange" : false,
						"bInfo" : false,
						"sDom" : "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
						"sPaginationType" : "bootstrap",
						"oLanguage" : {
							//"sLengthMenu" : "_MENU_ records per page",
							"sProcessing" : "Carregando ...",
							"sZeroRecords" : "Você ainda não cadastrou rotas.",
							"oPaginate" : {
								"sNext" : "",
								"sPrevious" : ""
							}
						},
						"iDisplayLength" : 4,
						"aaData" : data,
						"aoColumnDefs" : [
								{
									"aTargets" : [ 0 ],
									"mData" : "description",
									"sTitle" : "Minhas Rotas"
								},

								{
									"aTargets" : [ 1 ],
									"sWidth" : "20px",
									"mData" : "id",
									"bSortable" : false,
									"mRender" : function(data) {
										return '<a href="#" name="route-'
												+ data
												+ '" route="'
												+ data
												+ '"><img src="img/map.png" style="height: 20px; width: 20px;"/></a>';
									}
								},
								{
									"aTargets" : [ 2 ],
									"sWidth" : "20px",
									"mData" : "id",
									"bSortable" : false,
									"mRender" : function(data) {
										return '<a href="#" name="route-'
												+ data
												+ '" route="'
												+ data
												+ '"><img src="img/calendar.png" style="height: 20px; width: 20px;"/></a>';
									}
								},
								{
									"aTargets" : [ 3 ],
									"sWidth" : "20px",
									"mData" : "id",
									"bSortable" : false,
									"mRender" : function(data) {
										return '<a href="#" name="route-'
												+ data
												+ '" route="'
												+ data
												+ '"><img src="img/delete.png" style="height: 20px; width: 20px;"/></a>';
									}
								} ],
						"fnHeaderCallback" : function(nHead) {
							$(nHead.getElementsByTagName('th')[0]).attr(
									"colspan", "4");
							for ( var i = 1; i <= 3; i++) {
								$(nHead.getElementsByTagName('th')[1]).remove();
							}
						},
						"fnRowCallback" : function(nRow) {
							$(nRow.getElementsByTagName('td')[1]).attr("width",
									"20px");
							$(nRow.getElementsByTagName('td')[2]).attr("width",
									"20px");
							$(nRow.getElementsByTagName('td')[3]).attr("width",
									"20px");
						}

					});
}
