<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vedic Astrology Report</title>
    <style>
        :root {
            --bg-dark: #1a1a2e;
            --bg-panel: #16213e;
            --bg-accent: #0f3460;
            --text-main: #e0e0e0;
            --text-gold: #f9d342;
            --border-color: #2a2a4a;
            --success: #4caf50;
            --danger: #f44336;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--bg-dark);
            color: var(--text-main);
            margin: 0;
            padding: 20px;
            line-height: 1.6;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        
        h1, h2, h3 {
            color: var(--text-gold);
            text-align: center;
        }
        
        h1 {
            border-bottom: 2px solid var(--text-gold);
            padding-bottom: 10px;
            margin-bottom: 30px;
        }
        
        .grid {
            display: grid;
            grid-template-columns: 1fr;
            gap: 20px;
        }
        
        @media(min-width: 768px) {
            .grid-2 { grid-template-columns: 1fr 1fr; }
        }
        
        .panel {
            background-color: var(--bg-panel);
            border: 1px solid var(--border-color);
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.3);
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid var(--border-color);
        }
        
        th {
            background-color: var(--bg-accent);
            color: var(--text-gold);
        }
        
        .chart-container {
            display: flex;
            justify-content: center;
            align-items: center;
            background: #fff;
            padding: 20px;
            border-radius: 4px;
        }
        
        .yoga-card {
            background-color: var(--bg-accent);
            border-left: 4px solid var(--text-gold);
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 4px;
        }
        
        .bar-container {
            width: 100%;
            background-color: var(--bg-dark);
            border-radius: 4px;
            height: 20px;
            margin-top: 5px;
        }
        
        .bar-fill {
            height: 100%;
            border-radius: 4px;
            background-color: var(--success);
        }
        
        .bar-low { background-color: var(--danger); }
        .bar-high { background-color: var(--success); }
    </style>
</head>
<body>
    <div class="container">
        <h1>Vedic Astrology Birth Chart Analysis</h1>
        
        <div class="grid grid-2">
            <div class="panel">
                <h2>Birth Details</h2>
                <table>
                    <tr><th>Name</th><td>Sample Native</td></tr>
                    <tr><th>Date</th><td>01-Jan-2000</td></tr>
                    <tr><th>Time</th><td>12:00:00</td></tr>
                    <tr><th>Location</th><td>New Delhi, India</td></tr>
                    <tr><th>Ayanamsha</th><td>Lahiri</td></tr>
                </table>
            </div>
            
            <div class="panel">
                <h2>North Indian Chart</h2>
                <div class="chart-container">
                    ${northIndianChartSvg!""}
                </div>
            </div>
        </div>
        
        <div class="panel" style="margin-top: 20px;">
            <h2>Planetary Positions</h2>
            <table>
                <thead>
                    <tr>
                        <th>Planet</th>
                        <th>Sign</th>
                        <th>Longitude</th>
                        <th>Nakshatra</th>
                        <th>Pada</th>
                        <th>House</th>
                        <th>Dignity</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- In a real app, we iterate over report.birthChart.planets -->
                    <tr>
                        <td>Sun</td>
                        <td>Aries</td>
                        <td>10° 15'</td>
                        <td>Ashwini</td>
                        <td>1</td>
                        <td>1</td>
                        <td>Exalted</td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <div class="grid grid-2" style="margin-top: 20px;">
            <div class="panel">
                <h2>Vimshottari Dasha</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Mahadasha</th>
                            <th>Start</th>
                            <th>End</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr><td>Venus</td><td>1995</td><td>2015</td></tr>
                        <tr style="background-color: var(--bg-accent);">
                            <td>Sun</td><td>2015</td><td>2021</td>
                        </tr>
                        <tr><td>Moon</td><td>2021</td><td>2031</td></tr>
                    </tbody>
                </table>
            </div>
            
            <div class="panel">
                <h2>Shadbala Strength</h2>
                <div>
                    <p>Sun</p>
                    <div class="bar-container">
                        <div class="bar-fill bar-high" style="width: 120%;"></div>
                    </div>
                </div>
                <div>
                    <p>Moon</p>
                    <div class="bar-container">
                        <div class="bar-fill bar-low" style="width: 80%;"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="panel" style="margin-top: 20px;">
            <h2>Yogas</h2>
            <div class="yoga-card">
                <h3>Ruchaka Yoga</h3>
                <p><strong>Category:</strong> Mahapurusha</p>
                <p><strong>Effects:</strong> Bestows courage, physical strength, and leadership abilities.</p>
            </div>
        </div>
    </div>
</body>
</html>
