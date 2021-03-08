import React, { Component } from 'react';
import CanvasJSReact from '../assets/canvasjs.react';
import { getChart } from '../services/api';
var CanvasJSChart = CanvasJSReact.CanvasJSChart;

var openValues = [];
var closeValues = [];

class LineChart extends Component {
	constructor() {
		super();
		this.updateChart = this.updateChart.bind(this);
	}
	componentDidMount() {
		setInterval(this.updateChart, 1000);
	}
	fetchData() {
		getChart()
			.then(result => result.data)
			.then(data => {
				openValues.push({
					x: new Date(data._embedded.quotes[0].timestamp),
					y: data._embedded.quotes[0].openValue
				});
				if(openValues.length > 10)
					openValues.shift();
				closeValues.push({
					x: new Date(data._embedded.quotes[0].timestamp),
					y: data._embedded.quotes[0].closeValue
				});
				if(closeValues.length > 10)
					closeValues.shift();
			});
	}
	updateChart() {
		this.fetchData();
		this.chart.render();
	}
	render() {
		const options = {
			title :{
				text: "Stock Quotes"
			},
			axisY: {
				includeZero:false,
				valueFormatString: "0.##0",
				title: "Price (in USD)"
			},
			axisX: {
				valueFormatString: "HH:mm"
			},
			data: [{
				type: "line",
				name: "Open Value",
				showInLegend: true,
				yValueFormatString: "0.##0",
				xValueFormatString: "HH:mm",
				dataPoints: openValues
			},{
				type: "line",
				name: "Close Value",
				showInLegend: true,
				yValueFormatString: "0.##0",
				xValueFormatString: "HH:mm",
				dataPoints: closeValues
			}]
		}
		
		return (
		<div>
			<h1>Live Stock Quotes</h1>
			<CanvasJSChart options = {options} 
				onRef={ref => this.chart = ref}
			/>
			{/*You can get reference to the chart instance as shown above using onRef. This allows you to access all chart properties and methods*/}
		</div>
		);
	}
}

export default LineChart;