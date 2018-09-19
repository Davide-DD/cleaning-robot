// CHECKED

// Funzioni per mostrare i valori correnti degli slider di tempo e temperatura nei corrispondenti campi di output

$('#temperatureControlRange').change(function() {
	$('#temperatureValue').val($(this).val());
});

$('#hourControlRange').change(function() {
	$('#hourValue').val($(this).val());
});


// Funzioni per inviare cambiamenti di temperatura e orario

$( "#confirmTemperature" ).click(function() {
	var data = {};
	data.message = $('#temperatureValue').val();
	$.ajax({
		type: 'POST',
		data: JSON.stringify(data),
		contentType: 'application/json',
		url: 'simulator/change/temperature',
		success: function(data) {
			alert('Temperatura inviata!');
		}
	});
});


$( "#confirmHour" ).click(function() {
	var data = {};
	data.message = $('#hourValue').val();
	$.ajax({
		type: 'POST',
		data: JSON.stringify(data),
		contentType: 'application/json',
		url: 'simulator/change/hour',						
		success: function(data) {
			alert('Orario inviato!');
		}
	});
});