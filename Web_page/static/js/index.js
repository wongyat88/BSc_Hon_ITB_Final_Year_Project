
function updateMap(selectControl){
    alert(selectControl);
    switch(selectControl){
    case 'school1':
        x = 22.336956;
        y = 114.1515134;
        break;

    case 'school2':
        x = 22.292592;
        y = 114.2353423;
        break;

    default:
        break;
    }
    initialize(x, y);
}

function initialize(x, y) {
    var map = new google.maps.Map(document.getElementById("map_canvas"),{
        center: {lat: x, lng: y},
        zoom: 18
    });
    new google.maps.Marker({
        position: {lat: x, lng: y},
        map: map,
    });
}