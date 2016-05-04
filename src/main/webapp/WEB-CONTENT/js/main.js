(function(){
var historics = 
[
	"Number of users",
	"Number of generations",
	"Number of created project",
	"Number of installed bundle"
];
  var data = {
	labels: ["January", "February", "March", "April", "May", "June", "July"],
    datasets: [
        {
            label: "My First dataset",
            fillColor: "rgba(220,220,220,0.5)",
            strokeColor: "rgba(220,220,220,0.8)",
            highlightFill: "rgba(220,220,220,0.75)",
            highlightStroke: "rgba(220,220,220,1)",
            data: [65, 59, 80, 81, 56, 55, 40]
        },
        {
            label: "My Second dataset",
            fillColor: "rgba(151,187,205,0.5)",
            strokeColor: "rgba(151,187,205,0.8)",
            highlightFill: "rgba(151,187,205,0.75)",
            highlightStroke: "rgba(151,187,205,1)",
            data: [28, 48, 40, 19, 86, 27, 90]
        }
    ]
}
function changeHistoric(idx) {
    $('#choose-historic').text(historics[idx]);
}


function init() {
    for(var i in historics)
    {
        document.getElementById('historic-choices').innerHTML += 
        '<li><a href="#!" onclick="changeHistoric('+i+')" class="historic-choice">'+historics[i]+'</a></li>'
    }


var historicChart = document.getElementById('historic-chart').getContext("2d");
var myBarChart = new Chart(historicChart).Bar(data);
}
init();


}

)()