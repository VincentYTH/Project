document.addEventListener("DOMContentLoaded", function() {
  function updateAskAndBid(symbol) {
    fetch(`/five-minute/${symbol}/ask`)
      .then(response => response.json())
      .then(askData => {
        document.getElementById('ask-data').textContent = askData.toFixed(2);
      });
  
    fetch(`/five-minute/${symbol}/bid`)
      .then(response => response.json())
      .then(bidData => {
        document.getElementById('bid-data').textContent = bidData.toFixed(2);
      });
  }
  
  function updateChart(symbol, type, mavPeriod) {
    if (type === '5Min') {
      let lastUpdateTime = null;
      let lastStockData = null;
      fetch(`/five-minute/${symbol}`)
        .then(response => response.json())
        .then(priceData => {
          fetch(`/five-minute/ma/${symbol}/${mavPeriod}`)
            .then(response => response.json())
            .then(maData => {
              const pricePoints = priceData.map(item => [new Date(item.timestamp).getTime(), item.price]);
              const maPoints = maData.map((item, index) => {
                if (item === null) {
                  return [new Date(priceData[index].timestamp).getTime(), null];
                } else {
                  return [new Date(priceData[index].timestamp).getTime(), item.price];
                }
              });
  
              // Check if the stock data has changed
              const currentStockData = JSON.stringify({ pricePoints, maPoints });
              if (lastStockData !== currentStockData) {
                Highcharts.stockChart('stockChart', {
                  rangeSelector: {
                    selected: 1
                  },
                  title: {
                    text: `Stock High Chart: ${symbol}`
                  },
                  time: {
                    timezone: 'Asia/Hong_Kong'
                  },
                  series: [
                    {
                      name: 'Price',
                      data: pricePoints,
                      tooltip: {
                        valueDecimals: 2
                      }
                    },
                    {
                      name: 'Moving Average',
                      data: maPoints,
                      tooltip: {
                        valueDecimals: 2
                      }
                    }
                  ]
                });
                lastStockData = currentStockData;
                lastUpdateTime = new Date().getTime();
                updateAskAndBid(symbol);
              }
            });
        });
    } else {
      fetch(`/history/${symbol}/${type}`)
        .then(response => response.json())
        .then(data => {
          var candleData = data.map(item => [
            new Date(item.date).getTime(),
            item.open,
            item.high,
            item.low,
            item.close
          ]);
  
          var mavData = [];
          for (var i = mavPeriod - 1; i < candleData.length; i++) {
            var sum = 0;
            for (var j = i - mavPeriod + 1; j <= i; j++) {
              sum += candleData[j][4];
            }
            mavData.push([candleData[i][0], parseFloat((sum / mavPeriod).toFixed(2))]);
          }
  
          var today = new Date();
          var sixMonthsAgo = new Date(today.getFullYear(), today.getMonth() - 6, today.getDate());
          var minDate = sixMonthsAgo.getTime();
          var maxDate = today.getTime();
          var chart = Highcharts.stockChart('stockChart', {
            chart: {
              spacingTop: 10,
              spacingBottom: 10,
              spacingLeft: 10,
              spacingRight: 10,
              marginTop: 10,
              marginBottom: 10,
              marginLeft: 10,
              marginRight: 10
            },
            title: {
              text: `Stock Candlestick Chart: ${symbol}`
            },
            series: [
              {
                type: 'candlestick',
                name: symbol,
                data: candleData
              },
              {
                type: 'line',
                name: `${mavPeriod}-day MA`,
                data: mavData
              }
            ],
            xAxis: {
              type: 'datetime',
              min: minDate,
              max: maxDate,
              labels: {
                formatter: function () {
                  return Highcharts.dateFormat('%Y-%m', this.value);
                }
              }
            },
            yAxis: {
              title: {
                text: 'price'
              }
            },
            navigator: {
              enabled: true,
              height: 50
            },
            scrollbar: {
              enabled: true
            },
            plotOptions: {
              candlestick: {
                lineWidth: 2,
                pointWidth: 12
              }
            }
          });
        })
        .catch(error => console.error(error));
    }
  }
  
      updateChart('0388.HK', '5Min', 5);
      updateAskAndBid('0388.HK');
  
      // Update chart every 10 seconds
      setInterval(function() {
      const currentTime = new Date().getTime();
      if (document.getElementById('type').value === '5Min' && 
      (lastUpdateTime === null || currentTime - lastUpdateTime >= 10000)) {
        updateChart(document.getElementById('symbol').value, document.getElementById('type').value, document.getElementById('mavPeriod').value);
        updateAskAndBid(document.getElementById('symbol').value);
      }
    }, 10000);
  
      document.getElementById('symbol').addEventListener('change', function() {
        updateChart(this.value, document.getElementById('type').value, document.getElementById('mavPeriod').value);
        updateAskAndBid(this.value);
      });
  
      document.getElementById('type').addEventListener('change', function() {
        updateChart(document.getElementById('symbol').value, this.value, document.getElementById('mavPeriod').value);
      });
  
      document.getElementById('mavPeriod').addEventListener('input', function() {
         updateChart(document.getElementById('symbol').value, document.getElementById('type').value, this.value);
      });
});

