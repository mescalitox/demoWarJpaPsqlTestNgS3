$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/demoWarJpaPsqlTestNgS3/rest/greetingExpected?name=test"
    }).then(function(data, status, jqxhr) {
       $('.greeting-id').append(data["return"].id);
       $('.greeting-content').append(data["return"].content);
       console.log(jqxhr);
    });
});