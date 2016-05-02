require('./rg-chart.js')
<repo-contribution>

    <h2>Contributions</h2>
    <rg-chart chart="{ linechart }"></rg-chart>

    <script type="es6">
    const _ = require('lodash')
    const self = this
    this.on('update', () => {
        this.contributors = _(this.opts.commits).groupBy('committer.login').mapValues((list) => list.length).value()
        this.labels = _.keys(this.contributors)
        this.values = _.values(this.contributors)
        this.linechart = {
            type: 'bar',
            data: {
                labels: this.labels,
                datasets: [{
                    label: "Contributors",
                    fillColor: "rgba(220,220,220,0.2)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(220,220,220,1)",
                    fill: true,
                    lineTension: 0.1,
                    backgroundColor: "rgba(75,192,192,0.4)",
                    borderColor: "rgba(75,192,192,1)",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: "rgba(75,192,192,1)",
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: "rgba(75,192,192,1)",
                    pointHoverBorderColor: "rgba(220,220,220,1)",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: this.values
                }]
            }
        }
    })
    </script>
</repo-contribution>
