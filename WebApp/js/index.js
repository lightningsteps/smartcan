$(document).ready(function () {

    $("#AddNewCanButton").click(function () {
        $('#AddNewCanModal').modal('show');
    });

    $("#PostCanButton").click(function () {
        var can = {
            id: -1,
            capacity: "50",
            type: $("#type").val().toUpperCase(),
            latitude: parseFloat($("#latitude").val()),
            longitude: parseFloat($("#longitude").val()),
            address: $("#address").val()
        };

        var json = JSON.stringify(can);

        $.ajax({
            method: "PUT",
            contentType: "application/json",
            url: "http://dubrovniksmartcan-bracketscan.rhcloud.com/addCan",
            data: json
        })
          .done(function (msg) {
              console.log("Data Saved: " + msg);
          });
    });

});