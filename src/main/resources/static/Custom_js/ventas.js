document.addEventListener('DOMContentLoaded', function() {
	const productosTable = document.getElementById('productosSeleccionados');
	const registrarVentaButton = document.getElementById('registrarVenta');

	function validateForm() {
		const productosSeleccionados = productosTable.querySelectorAll('tbody tr').length > 0;

		if (productosSeleccionados) {
			registrarVentaButton.disabled = false;
		} else {
			registrarVentaButton.disabled = true;
		}
	}

	clienteSelect.addEventListener('change', validateForm);

	// Valida si hay productos seleccionados en la tabla al cargar la página
	validateForm();
});



