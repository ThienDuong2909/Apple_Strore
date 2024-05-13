console.log("Header was included!")

$(document).ready(function(){
    $("#manage__product").click(function(){
        console.log("San pham")
        $("#content__of__page").load("viewAll");
    })

    $("#manage__order").click(function(){
        $("#content__of__page").load("viewAllOrder");
    })

})
