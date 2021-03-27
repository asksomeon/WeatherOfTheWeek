package Weather;

public class ThatDay {
    public String day;
    public String hourMaxTemperature;
    public String hourLowestWater;
    public String hourMaxWindSpeed;
    public double temperature;
    public double windSpeed;
    public String windDirection;
    public double amountOfWaterInTheCity;

    public String getDay() {
        return day;
    }

    public String getHourMaxTemperature() {
        return hourMaxTemperature;
    }

    public String getHourLowestWater() {
        return hourLowestWater;
    }

    public String getHourMaxWindSpeed() {
        return hourMaxWindSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public double getAmountOfWaterInTheCity() {
        return amountOfWaterInTheCity;
    }


    public ThatDay() {
        this.day = null;
        this.hourMaxTemperature = null;
        this.hourLowestWater = null;
        this.hourMaxWindSpeed = null;
        this.temperature = -247;
        this.windSpeed = 0;
        this.windDirection = null;
        this.amountOfWaterInTheCity = 100;
    }

    public ThatDay(String day, String hourMaxTemperature, String hourLowestWater, String hourMaxWindSpeed,
                   double temperature, double windSpeed, String windDirection, double amountOfWaterInTheCity) {
        this.day = day;
        this.hourMaxTemperature = hourMaxTemperature;
        this.hourLowestWater = hourLowestWater;
        this.hourMaxWindSpeed = hourMaxWindSpeed;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.amountOfWaterInTheCity = amountOfWaterInTheCity;
    }
}
