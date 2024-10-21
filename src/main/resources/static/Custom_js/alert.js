document.addEventListener("DOMContentLoaded", function() {
    var successMessage = document.body.getAttribute('data-success-message');
    var errorMessage = document.body.getAttribute('data-error-message');

    if (successMessage && successMessage.trim() !== "") {
        Swal.fire({
            title: 'Éxito',
            text: successMessage,
            showConfirmButton: false,
            icon: 'success',
            timer: 2000
        });
    } else if (errorMessage && errorMessage.trim() !== "") {
        Swal.fire({
            title: 'Error',
            text: errorMessage,
            showConfirmButton: false,
            icon: 'error',
            timer: 2000
        });
    }
});


