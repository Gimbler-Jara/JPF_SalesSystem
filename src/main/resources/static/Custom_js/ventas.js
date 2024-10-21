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


document.getElementById('productoSelect').addEventListener('change', function() {
	const productoId = this.value; // Obtener el ID del producto seleccionado
	if (productoId) {
		fetch(`/productos/stock/${productoId}`)
			.then(response => response.json())
			.then(data => {
				document.getElementById('stockDisponible').value = data.stockActual; // Actualizar el campo de stock disponible
			})
			.catch(error => console.error('Error:', error));
	} else {
		document.getElementById('stockDisponible').value = ''; // Limpiar el campo si no se selecciona un producto
	}
});


