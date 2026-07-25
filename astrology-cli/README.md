# astrology-cli

Production-quality Vedic Astrology Engine CLI.

## Usage

```bash
# Full chart analysis
java -jar target/astrology-cli.jar chart --date 1990-07-15 --time 14:30:00 --tz Asia/Kolkata --lat 28.61 --lon 77.20 --name "Sample Person"

# Vimshottari Dasha for next 30 years
java -jar target/astrology-cli.jar dasha --date 1990-07-15 --time 14:30:00 --tz Asia/Kolkata --lat 28.61 --lon 77.20 --years 30

# Detect all yogas
java -jar target/astrology-cli.jar yoga --date 1990-07-15 --time 14:30:00 --tz Asia/Kolkata --lat 28.61 --lon 77.20

# Generate PDF report
java -jar target/astrology-cli.jar report --date 1990-07-15 --time 14:30:00 --tz Asia/Kolkata --lat 28.61 --lon 77.20 --format PDF --output my-chart
```
