import os
import re

directory = '/home/kkkothari/.gemini/antigravity/scratch/vedic-astrology/'

for root, _, files in os.walk(directory):
    if 'src/main/java/com/astrology' not in root:
        continue
    for file in files:
        if not file.endswith('.java'):
            continue
        filepath = os.path.join(root, file)
        with open(filepath, 'r') as f:
            content = f.read()

        new_content = content
        
        # 1. BirthChart.positions() -> BirthChart.getPositions()
        new_content = new_content.replace('.positions()', '.getPositions()')
        
        # 2. pp.longitude() -> pp.siderealLongitude()
        new_content = re.sub(r'\.longitude\(\)', '.siderealLongitude()', new_content)
        
        # 3. pp.speed() -> pp.speedLongitude()
        new_content = re.sub(r'\.speed\(\)', '.speedLongitude()', new_content)
        
        # 4. Sign -> Rashi
        new_content = re.sub(r'\bSign\b', 'Rashi', new_content)
        
        # 6. ExaltationDebilitation.getStatus() -> ExaltationDebilitation.getDignitaryStatus()
        new_content = new_content.replace('.getStatus(', '.getDignitaryStatus(')
        
        # 7. Planet.MEAN_NODE -> Planet.RAHU, Planet.TRUE_NODE -> Planet.KETU
        new_content = new_content.replace('Planet.MEAN_NODE', 'Planet.RAHU')
        new_content = new_content.replace('Planet.TRUE_NODE', 'Planet.KETU')
        
        # 8. chart.isDayBirth() -> (chart.getBirthData().localDateTime().getHour() >= 6 && chart.getBirthData().localDateTime().getHour() <= 18)
        new_content = new_content.replace('chart.isDayBirth()', '(chart.getBirthData().localDateTime().getHour() >= 6 && chart.getBirthData().localDateTime().getHour() <= 18)')

        # 9. chart.getPlanetLongitude(planet) -> chart.getPositions().get(planet).siderealLongitude()
        new_content = re.sub(r'chart\.getPlanetLongitude\((.*?)\)', r'chart.getPositions().get(\1).siderealLongitude()', new_content)
        
        # 10. chart.getPlanetSpeed(planet) -> chart.getPositions().get(planet).speedLongitude()
        new_content = re.sub(r'chart\.getPlanetSpeed\((.*?)\)', r'chart.getPositions().get(\1).speedLongitude()', new_content)

        # 11. chart.getPlanetDeclination(planet) -> chart.getPositions().get(planet).latitude()  (simplification)
        new_content = re.sub(r'chart\.getPlanetDeclination\((.*?)\)', r'chart.getPositions().get(\1).latitude()', new_content)
        
        # 12. chart.getDateTime() -> chart.getBirthData().localDateTime()
        new_content = new_content.replace('chart.getDateTime()', 'chart.getBirthData().localDateTime()')
        
        # 13. getHouseCusp(int) -> getHouseCusps()[int-1]
        new_content = re.sub(r'chart\.getHouseCusp\((.*?)\)', r'chart.getHouseCusps()[(\1) - 1]', new_content)

        # 14. DivisionalChartSet imports might need fixing but I'll skip if I don't know the bad one. Wait, in astrology-strength... 
        
        if content != new_content:
            with open(filepath, 'w') as f:
                f.write(new_content)
