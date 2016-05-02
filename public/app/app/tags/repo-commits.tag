
<repo-commits>

    <div id="timeline"></div>
    <!--<rg-chart chart="{ linechart }"></rg-chart>-->

    <script type="es6">
    // import _ from 'lodash'
    this.on('update', () => {
        console.log(this.opts);
    })
    // const testData = [
    //     {times: [{"starting_time": 1355752800000, "display": "circle"},
    //              {"starting_time": 1355767900000, "ending_time": 1355774400000}]},
    //     {times: [{"starting_time": 1355759910000, "display":"circle"}, ]},
    //     {times: [{"starting_time": 1355761910000, "ending_time": 1355763910000}]},
    // ];
    //
    // var chart = d3.timeline();
    //
    // var svg = d3.select("#timeline1").append("svg").attr("width", 500)
    //   .datum(testData).call(chart);

    // this.linechart = {
	// 		type: 'line',
	// 		data: {
	// 			labels: ["January", "February", "March", "April", "May", "June", "July"],
	// 			datasets: [{
	// 				label: "My First dataset",
	// 				fillColor: "rgba(220,220,220,0.2)",
	// 				strokeColor: "rgba(220,220,220,1)",
	// 				pointColor: "rgba(220,220,220,1)",
	// 				pointStrokeColor: "#fff",
	// 				pointHighlightFill: "#fff",
	// 				pointHighlightStroke: "rgba(220,220,220,1)",
	// 				data: [65, 59, 80, 81, 56, 55, 40]
	// 			}, {
	// 				label: "My Second dataset",
	// 				fillColor: "rgba(151,187,205,0.2)",
	// 				strokeColor: "rgba(151,187,205,1)",
	// 				pointColor: "rgba(151,187,205,1)",
	// 				pointStrokeColor: "#fff",
	// 				pointHighlightFill: "#fff",
	// 				pointHighlightStroke: "rgba(151,187,205,1)",
	// 				data: [28, 48, 40, 19, 86, 27, 90]
	// 			}]
	// 		}
	// 	}
    </script>

    <style type="text/css">
    .axis path,
    .axis line {
      fill: none;
      stroke: black;
      shape-rendering: crispEdges;
    }
    .axis text {
      font-family: sans-serif;
      font-size: 10px;
    }
    .timeline-label {
      font-family: sans-serif;
      font-size: 12px;
    }
    #timeline2 .axis {
      transform: translate(0px,40px);
      -ms-transform: translate(0px,40px); /* IE 9 */
      -webkit-transform: translate(0px,40px); /* Safari and Chrome */
      -o-transform: translate(0px,40px); /* Opera */
      -moz-transform: translate(0px,40px); /* Firefox */
    }

    .coloredDiv {
      height:20px; width:20px; float:left;
    }
    </style>
</repo-commits>
