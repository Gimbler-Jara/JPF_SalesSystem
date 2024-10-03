document.addEventListener('DOMContentLoaded', function() {
	// Verifica si el botón existe antes de agregar el listener
	const agregarProductoBtn = document.getElementById('agregarProducto');
	const productoSelect = document.getElementById('productoSelect');
	const cantidadInput = document.getElementById('cantidadInput');

	agregarProductoBtn.addEventListener('click', function() {
		const productoId = productoSelect.value;
		const productoNombre = productoSelect.options[productoSelect.selectedIndex].text;
		const precioUnitario = parseFloat(productoSelect.options[productoSelect.selectedIndex].getAttribute('data-precio'));
		const cantidad = parseInt(cantidadInput.value);

		if (!productoId) {
			alert('Por favor, selecciona un producto.');
			return;
		}

		if (!cantidad || cantidad <= 0) {
			alert('Por favor, ingresa una cantidad válida.');
			return;
		}

		const tabla = document.getElementById('productosSeleccionados').getElementsByTagName('tbody')[0];
		const nuevaFila = tabla.insertRow();

		const celdaProducto = nuevaFila.insertCell(0);
		celdaProducto.textContent = productoNombre;

		const celdaPrecioUnitario = nuevaFila.insertCell(1);
		celdaPrecioUnitario.textContent = precioUnitario.toFixed(2);

		const celdaCantidad = nuevaFila.insertCell(2);
		celdaCantidad.textContent = cantidad;

		const celdaPrecioTotal = nuevaFila.insertCell(3);
		const precioTotal = precioUnitario * cantidad;
		celdaPrecioTotal.textContent = precioTotal.toFixed(2);

		const celdaAcciones = nuevaFila.insertCell(4);
		const botonEliminar = document.createElement('button');
		botonEliminar.textContent = 'Eliminar';
		botonEliminar.className = 'btn btn-danger btn-sm';
		botonEliminar.onclick = function() {
			tabla.deleteRow(nuevaFila.rowIndex - 1);
			actualizarTotalGeneral();
		};
		celdaAcciones.appendChild(botonEliminar);

		// Limpiar la selección y el input de cantidad
		productoSelect.selectedIndex = 0;
		cantidadInput.value = '';

		actualizarTotalGeneral();

	});

	function actualizarTotalGeneral() {
		const tabla = document.getElementById('productosSeleccionados').getElementsByTagName('tbody')[0];
		let totalGeneral = 0;

		for (let i = 0; i < tabla.rows.length; i++) {
			const precioTotal = parseFloat(tabla.rows[i].cells[3].textContent);
			totalGeneral += precioTotal;
		}

		// Mostrar total con dos decimales
		document.getElementById('totalGeneral').textContent = totalGeneral.toFixed(2);
	}
});
