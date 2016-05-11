$('.datepicker').pickadate({
	selectMonths: true, // Creates a dropdown to control month
	selectYears: 15, // Creates a dropdown of 15 years to control year
	format: 'dd/mm/yyyy',
	onClose: function(){
		$('.datepicker').blur();
		$('.picker').blur();
	}
});