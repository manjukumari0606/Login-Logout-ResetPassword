$(document).ready(function() {
	
	  $('.table').DataTable({
            "columnDefs": [
                { "orderable": false, "targets": [5] } // Disable sorting for the 6th column (index 5)
            ]
        });
    // Add Button Click Event
    $(".nBtn").click(function(event) {
        console.log("Add button clicked.");
        event.preventDefault();
        // Clear all form fields
        $('.myForm #id').val('');
        $('.myForm #name').val('');
        $('.myForm #email').val('');
        $('.myForm #age').val('');
        $('.myForm #address').val('');
        // Disable the ID field
        $('.myForm #id').prop('disabled', true);
        // Change modal title to 'Create'
        $('.myForm #exampleModalLabel').text('Create Student Data');
        // Show the modal
        $('.myForm #exampleModal').modal('show');
    });

    // Edit Button Click Event
    $(document).on('click', '.delBtn', function () {
        // Get the row data
        var rowData = $(this).closest('tr').find('td');
        var studentId = $(rowData[0]).text(); // Assuming the ID is in the first column

        // Show the confirmation modal
        $('#exampleModal1').modal('show');

        // Set the student ID to be deleted when the confirmation button is clicked
        $('#deleteConfirmBtn').data('student-id', studentId);
    });

    // Edit Button Click Event (using event delegation)
    $(document).on('click', '.table .eBtn', function(event) {
        console.log("Edit button clicked.");
        event.preventDefault();

        var href = $(this).attr('href');
        console.log("Edit Href:", href);
        // Extract the ID from href
        var id = href.split('=')[1];
        $.ajax({
            url: '/emp/findOne/' + id,
            dataType: 'json',
            success: function(student, textStatus, xhr) {
                console.log("AJAX Request Status:", textStatus);
                console.log("Student data", student);
                // Populate the modal with student data
                $('.myForm #id').val(student.id);
                $('.myForm #name').val(student.name);
                $('.myForm #email').val(student.email);
                $('.myForm #age').val(student.age);
                $('.myForm #address').val(student.address);
                // Enable the ID field for editing
                $('.myForm #id').prop('disabled', false);
                // Change modal title back to 'Update or Create'
                $('.myForm #exampleModalLabel').text('Update Student Data');
                // Show the modal
                $('.myForm #exampleModal').modal('show');
            },
            error: function(xhr, textStatus, errorThrown) {
                console.error("AJAX Request failed:", textStatus);
                console.error("Error thrown:", errorThrown);
                // Handle failure, show an error message, etc.
            }
        });
    });


    // Delete Button Click Event
    $(".delBtn").click(function(event) {
        console.log("Delete button clicked.");
        event.preventDefault();
        var href = $(this).attr('href');
        console.log("Delete Href", href);
        // Implement your delete logic using AJAX or form submission
    });
});
