'use strict';
import riot   from 'riot'

/**
 * Taken from http://riotgear.js.org/components/charts/
 */
riot.tag2('rg-chart', '<canvas></canvas>', 'rg-chart,[riot-tag="rg-chart"],[data-is="rg-chart"]{ display: inline-block; width: 100%; }', '', function(opts) {
var _this = this;

Chart.defaults.global.responsive = true;

this.on('mount', function () {
  drawChart();
});

this.on('loaded', function (c) {
  _this.on('unmount', function () {
    c.destroy();
  });
  // Add to fix update of chart
  _this.on('update', () => {
    c.destroy();
    drawChart();
  });
});

var drawChart = function drawChart() {
  if (!opts.chart) opts.chart = {};

  var ctx = _this.root.querySelector('canvas').getContext('2d');
  var chart = new Chart(ctx);
  let options = {
        xAxes: [{
            display: false
        }],
        yAxes: [{
           ticks: {
               beginAtZero:true
           }
       }]
    };

  var c = null;
  switch (opts.chart.type) {
    case 'line':
      c = Chart.Line(ctx, { data: opts.chart.data, options: opts.chart.options });
      break;
    case 'radar':
      c = chart.Radar(opts.chart.data, opts.chart.options);
      break;
    case 'polar':
      c = chart.PolarArea(opts.chart.data, opts.chart.options);
      break;
    case 'pie':
      c = chart.Pie(opts.chart.data, opts.chart.options);
      break;
    case 'doughnut':
      c = chart.Doughnut(opts.chart.data, opts.chart.options);
      break;
    default:
      c = Chart.Bar(ctx, { data: opts.chart.data, options: opts.chart.options });
      break;
  }
  _this.trigger('loaded', c);
};
});
