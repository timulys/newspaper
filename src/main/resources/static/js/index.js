function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
}

function reload(newspaper) {
    $.ajax({
        type: "GET",
        data: {paperCode: newspaper},
        dataType: "html",
        success: function(data) {
            $("body").html(data);
            $("#paperCode").val(newspaper);
            $("#author").text($("#"+newspaper).text());
        }
    });
}

function refrash() {
    var newspaper = $("#paperCode").val();
    $.ajax({
        type: "GET",
        data: {paperCode: newspaper},
        dataType: "html",
        success: function(data) {
            $("body").html(data);
            $("#paperCode").val(newspaper);
            $("#author").text($("#"+newspaper).text());
        }
    });
}