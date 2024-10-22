document.addEventListener('DOMContentLoaded', function() {
	const productosTable = document.getElementById('productosSeleccionados');
	const registrarVentaButton = document.getElementById('registrarVenta');

	function validateForm() {
		const productosSeleccionados = productosTable.querySelectorAll('tbody tr').length > 0;

		registrarVentaButton.disabled = !productosSeleccionados;
	}

	productosTable.addEventListener('DOMNodeInserted', validateForm);
	productosTable.addEventListener('DOMNodeRemoved', validateForm);

	// Valida si hay productos seleccionados en la tabla al cargar la página
	validateForm();

	//Actualizamos el sctockActual de forma dianamica
	const productoSelect = document.getElementById('productoSelect');
	const stockDisponibleInput = document.getElementById('stockDisponible');

	productoSelect.addEventListener('change', function() {
		const selectedOption = productoSelect.options[productoSelect.selectedIndex];
		const stockDisponible = selectedOption.getAttribute('data-stock'); 
		stockDisponibleInput.value = stockDisponible ? stockDisponible : ''; 
	});
});



