    /**
     * Upload the file sending it via Ajax at the Spring Boot server.
     */
    function uploadFile() {
      $.ajax({
        url: "/uploadFile",
        type: "POST",
        data: new FormData($("#upload-file-form")[0]),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function () {
          // Handle upload success
          $("#upload-file-message").text("File succesfully uploaded");
        },
        error: function () {
          // Handle upload error
          $("#upload-file-message").text(
              "File not uploaded (perhaps it's too much big)");
        }
      });
    } // function uploadFile
    
    function getUserDetail() {
        $.ajax({
          url: "/user/khoubyari",
          type: "GET",
          cache: false,
          success: function (result) {
        	alert("success");
          },
          error: function () {
            // Handle upload error
        	  alert("failed");
          }
        });
      }