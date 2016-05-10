var timeFormat = 'MM/DD/YYYY HH:mm';
var ctx = document.getElementById("historyChart").getContext("2d");
var myLineChart = null;

// prepare the ids of our series of data
var averageDiskUsageId = "averageDiskUsage";
var modelsCountId = "modelsCount";
var usersCountId = "usersCount";
var diskUsageId = "diskUsage";
var projectsCountId = "projectsCount";
var averageModelsId = "averageModels";
var averageProjectsId = "averageProjects";

// ChartPointList constructor
function ChartPointList(jsonData, id) {
    var self = this;
    self.index = null;
    self.byYearData = jsonData;
    self.byMonthData = jsonData;
    self.byDayData = jsonData;
    self.id = id;
    self.immutableData = jsonData;
    self.mutableData = jsonData;
    self.filterByYear = function() {
        var years = [];
        // get all the selected years
        $('#yearFilter option:selected').each(function(){
            if($(this).val() !== "") {
                years.push($(this).val());
            }
        });
        if(years.length === 0) {
            self.byYearData = self.immutableData;
        } else {
            // filter the data
            self.byYearData = self.immutableData.filter(function(chartPoint) {
                var currentYear = new Date(chartPoint.x).getFullYear().toString();
                if (jQuery.inArray(currentYear, years) !== -1) {
                    return chartPoint;
                }
            });
        }
        self.updateMutableData();
        // update the chart to rebuild the line in the graph
        self.updateChartData();
    };
    self.filterByMonth = function() {
        var months = [];
        // get all the selected months
        $('#monthFilter option:selected').each(function(){
            if($(this).val() !== "") {
                months.push($(this).val());
            }
        });
        if(months.length === 0 ) {
            self.byMonthData = self.immutableData;
        } else {
            // filter the data
            self.byMonthData = self.immutableData.filter(function(chartPoint) {
                var currentMonth = new Date(chartPoint.x).getMonth().toString();
                if (jQuery.inArray(currentMonth, months) !== -1) {
                    return chartPoint;
                }
            });
        }

        self.updateMutableData();
        // update the chart to rebuild the line in the graph
        self.updateChartData();
    };
    self.filterByDay = function() {
        var days = [];
        // get all the selected days
        $('#dayFilter option:selected').each(function(){
            if($(this).val() !== "") {
                days.push($(this).val());
            }
        });
        if(days.length === 0 ) {
            self.byDayData = self.immutableData;
        } else {
            // filter the data
            self.byDayData = self.immutableData.filter(function(chartPoint) {
                var currentDay = new Date(chartPoint.x).getDate().toString();
                if (jQuery.inArray(currentDay, days) !== -1) {
                    return chartPoint;
                }
            });
        }

        self.updateMutableData();
        // update the chart to rebuild the line in the graph
        self.updateChartData();
    };
    // get the index of this dataset from the config of the chart
    self.getDataIndex = function() {
        if(self.index === null) {
            for (var i in myLineChart.config.data.datasets) {
                if (myLineChart.config.data.datasets[i].id === self.id) {
                    self.index = i;
                    break;
                }
            }
        }
        return self.index;

    };
    // update the chart dataset corresponding to this object
    self.updateChartData = function() {
        myLineChart.config.data.datasets[self.getDataIndex()].data = self.mutableData;
        myLineChart.update();
    };

    // set our mutable data to the origin ones
    self.resetMutableData = function(){
        self.mutableData = self.immutableData;
    };

    self.getData = function(){
        return self.mutableData;
    };
    self.updateMutableData = function() {
        var allArrays = [self.byDayData, self.byMonthData, self.byYearData];
        self.mutableData =  allArrays.shift().filter(function(v) {
            return allArrays.every(function(a) {
                return jQuery.grep(a, function(item) {
                        return(item.x === v.x);
                    }).length > 0;
            });
        });
    };
    self.getLength = function(obj) {
        return Object.keys(obj).length;
    };
    // add an event listener on the year select
    $("#yearFilter").change(function(){
        self.filterByYear();
    });

    // add an event listener on the month select
    $("#monthFilter").change(function(){
        self.filterByMonth();
    });

    // add an event listener on the day select
    $("#dayFilter").change(function(){
        self.filterByDay();
    });
}

// create our ChartPointList objects
var averageDiskUsageStatsPoints = new ChartPointList(averageDiskUsageStats, averageDiskUsageId);
var modelsCountStatsPoints = new ChartPointList(modelsCountStats, modelsCountId);
var usersCountStatsPoints = new ChartPointList(usersCountStats, usersCountId);
var diskUsageStatsPoints = new ChartPointList(diskUsageStats, diskUsageId);
var projectsCountStatsPoints = new ChartPointList(projectsCountStats, projectsCountId);
var averageModelsStatsPoints = new ChartPointList(averageModelsStats, averageModelsId);
var averageProjectsStatsPoints = new ChartPointList(averageProjectsStats, averageProjectsId);

function randomColorFactor() {
    return Math.round(Math.random() * 255);
}

function randomColor(opacity) {
    return 'rgba(' + randomColorFactor() + ',' + randomColorFactor() + ',' + randomColorFactor() + ',' + (opacity || '.3') + ')';
}

// prepare the config
var config = {
    type: 'line',
    data: {
        xValueType: "dateTime",
        datasets: [{
            label: "Average disk usage",
            data: averageDiskUsageStatsPoints.getData(),
            fill: false,
            id: averageDiskUsageId,
            borderColor: "rgba(209, 223, 46, 0.4)", // yellow
            pointBackgroundColor: "rgba(227, 247, 7, 0.5)",
        },{
            label: "Models count",
            data: modelsCountStatsPoints.getData(),
            fill: false,
            id: modelsCountId,
            borderColor: "rgba(223, 124, 31, 0.4)", // orange
            pointBackgroundColor: "rgba(242, 121, 7, 0.5)",
        },{
            label: "Users count",
            data: usersCountStatsPoints.getData(),
            fill: false,
            id: usersCountId,
            borderColor: "rgba(118, 220, 40, 0.4)", // green
            pointBackgroundColor: "rgba(114, 237, 7, 0.5)",
        },{
            label: "Disk usage",
            data: diskUsageStatsPoints.getData(),
            fill: false,
            id: diskUsageId,
            borderColor: "rgba(43, 221, 192, 0.4)", // light blue
            pointBackgroundColor: "rgba(7, 242, 218, 0.5)",
        },{
            label: "Projects count",
            data: projectsCountStatsPoints.getData(),
            fill: false,
            id: projectsCountId,
            borderColor: "rgba(43, 93, 221, 0.4)", // dark blue
            pointBackgroundColor: "rgba(7, 19, 242, 0.5)",
        },{
            label: "Average models",
            data: averageModelsStatsPoints.getData(),
            fill: false,
            id: averageModelsId,
            borderColor: "rgba(218, 43, 221, 0.4)", // purple
            pointBackgroundColor: "rgba(242, 7, 242, 0.5)",
        },{
            label: "Average projects",
            data: averageProjectsStatsPoints.getData(),
            fill: false,
            id: averageProjectsId,
            borderColor: "rgba(221, 43, 46, 0.5)", // red
            pointBackgroundColor: "rgba(242, 7, 11, 0.6)",
        }]
    },
    options: {
        responsive: true,
        title:{
            display:true,
            text:"Historic"
        },
        scales: {
            xAxes: [{
                type: "time",
                time: {
                    format: timeFormat,
                    // round: 'day'
                    tooltipFormat: 'll HH:mm',
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Date'
                }
            }, ],
            yAxes: [{
                scaleLabel: {
                    display: true,
                    labelString: 'Amount'
                }
            }]
        },
    }
};

$.each(config.data.datasets, function(i, dataset) {
    dataset.pointBorderColor = "rgba(0,0,0)";
    dataset.pointBorderWidth = 1;
    dataset.lineTension = 0;
});

myLineChart = new Chart(ctx, config);
/**var ctxPieChart = document.getElementById("pieChart").getContext("2d");
 var myObject = [[${filesTypes}]];
 var labels = [];
 var data=[];
 for(var key in myObject)
 {
     labels.push(key);
     data.push(myObject[key]);
 }
 var chartData = {
        labels: labels,
        datasets: [
            {
                data: data,
            }]
    };
 console.log(chartData);
 var myPieChart = new Chart(ctxPieChart,{
        type: 'pie',
        data: chartData,
        options : {segmentShowStroke : false,
            animateScale : true}
    });**/