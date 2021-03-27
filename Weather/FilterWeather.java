package Weather;

import java.io.*;
import java.util.*;


public class FilterWeather {
    String town;
    double temperature;
    double water;
    double speed;


    public static void main(String[] args) {
        FilterWeather filt = new FilterWeather();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        filt.createAWeather(str);//"C:\\Pleeease\\Maaaaybe.txt"
    }

    private void createAWeather(String str) {
        HashSet<ThatDay> temp = getWeather(str);
        try {
            File file = new File("Weather.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Город: " + town);
            bufferedWriter.newLine();
            bufferedWriter.write("Средняя температура " + temperature);
            bufferedWriter.newLine();
            bufferedWriter.write("Средняя влажность " + water);
            bufferedWriter.newLine();
            bufferedWriter.write("Средняя скорость ветра " + speed);
            bufferedWriter.newLine();
            bufferedWriter.write("День с самой высокой температурой " + out(dayAndHourOfMaxTemperature(temp)));
            bufferedWriter.newLine();
            bufferedWriter.write("День с самой низкой влажностью " + out(dayAndHourOfMinWater(temp)));
            bufferedWriter.newLine();
            bufferedWriter.write("День с самой высокой скоростью " + out(dayAndHourOfMaxWindSpeed(temp)));
            bufferedWriter.newLine();
            bufferedWriter.write("Наиболее частое направление ветра: " + dayAndHourOfMostWindDirection(temp));
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Попытайтесь снова");
            e.getStackTrace();
        }
    }

    private String out(String dayAndHourOfMaxTemperature) {
       return dayAndHourOfMaxTemperature.substring(0, 4) + " года " +
                dayAndHourOfMaxTemperature.substring(4, 6) +
                " месяца " + dayAndHourOfMaxTemperature.substring(6, 8) + " числа в " +
                dayAndHourOfMaxTemperature.substring(9, 11)+":"+
               dayAndHourOfMaxTemperature.substring(11, 13);
    }


    private String dayAndHourOfMostWindDirection(HashSet<ThatDay> weather) {
        int countNorth = 0;
        int countSouth = 0;
        int countWest = 0;
        int countEast = 0;
        for (ThatDay direction : weather) {
            if (direction.getWindDirection() == "North") {
                countNorth++;
            } else if (direction.getWindDirection() == "East") {
                countEast++;
            } else if (direction.getWindDirection() == "South") {
                countSouth++;
            } else {
                countWest++;
            }
        }
        int max = 0;
        String direction = new String();
        if (countNorth > max) {
            max = countNorth;
            direction = "Север";
        }
        if (countEast > max) {
            max = countEast;
            direction = "Восток";
        }
        if (countSouth > max) {
            max = countSouth;
            direction = "Юг";
        }
        if (countWest > max) {
            max = countWest;
            direction = "Запад";
        }
        return direction;
    }

    private String dayAndHourOfMaxWindSpeed(HashSet<ThatDay> weather) {
        ThatDay neededDay = new ThatDay();
        for (ThatDay day : weather) {
            if (day.getWindSpeed() > neededDay.getWindSpeed()) {
                neededDay = day;
            }
        }
        return neededDay.getDay() + " " + neededDay.getHourMaxTemperature();
    }

    private String dayAndHourOfMinWater(HashSet<ThatDay> weather) {
        ThatDay neededDay = new ThatDay();
        for (ThatDay day : weather) {
            if (day.getAmountOfWaterInTheCity() < neededDay.getAmountOfWaterInTheCity()) {
                neededDay = day;
            }
        }
        return neededDay.getDay() + " " + neededDay.getHourMaxTemperature();
    }

    private String dayAndHourOfMaxTemperature(HashSet<ThatDay> weather) {
        ThatDay neededDay = new ThatDay();
        for (ThatDay day : weather) {
            if (day.getTemperature() > neededDay.getTemperature()) {
                neededDay = day;
            }
        }
        return neededDay.getDay() + " " + neededDay.getHourMaxTemperature();
    }


    private HashSet<ThatDay> getWeather(String str) {
        HashSet<ThatDay> setOfDays = new HashSet<>();
        try {
            FileReader fileReader = new FileReader(str);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            town = bufferedReader.readLine();
            town = town.substring(town.lastIndexOf(",") + 1, town.length());
            for (int i = 0; i < 9; i++) {
                bufferedReader.readLine();
            }
            for (int i = 0; i < 8; i++) {

                double maxTemperature = -247;
                double lowAmountOfWaterInTheCity = 100;
                double maxWindSpeed = 0;
                ArrayList<Double> middleWindDirection = new ArrayList<>();
                String hourMaxTemp = new String();
                String hourLowWater = new String();
                String hourMaxWiSpeed = new String();
                String day = new String();
                for (int j = 0; j < 24; j++) {
                    String temp[] = bufferedReader.readLine().split(",");
                    if (Double.parseDouble(temp[1]) > maxTemperature) {
                        maxTemperature = Double.parseDouble(temp[1]);
                        hourMaxTemp = temp[0].substring(temp[0].indexOf("T") + 1, temp[0].length());
                    }
                    if (Double.parseDouble(temp[2]) < lowAmountOfWaterInTheCity) {
                        lowAmountOfWaterInTheCity = Double.parseDouble(temp[2]);
                        hourLowWater = temp[0].substring(temp[0].indexOf("T") + 1, temp[0].length());
                    }
                    if (Double.parseDouble(temp[3]) > maxWindSpeed) {
                        maxWindSpeed = Double.parseDouble(temp[3]);
                        hourMaxWiSpeed = temp[0].substring(temp[0].indexOf("T") + 1, temp[0].length());
                    }
                    middleWindDirection.add(Double.valueOf(temp[4]));
                    day = temp[0].substring(0, temp[0].indexOf("T"));
                    temperature += Double.parseDouble(temp[1]);
                    water += Double.parseDouble(temp[2]);
                    speed += Double.parseDouble(temp[3]);
                }

                setOfDays.add(new ThatDay(day, hourMaxTemp, hourLowWater,
                        hourMaxWiSpeed, maxTemperature, maxWindSpeed, findCommonWindDirection(middleWindDirection), lowAmountOfWaterInTheCity));

            }
            temperature = temperature / 192;
            water = water / 192;
            speed = speed / 192;
            bufferedReader.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return setOfDays;
    }

    public String findCommonWindDirection(ArrayList<Double> middleWindDirection) {
        int countNorth = 0;
        int countSouth = 0;
        int countWest = 0;
        int countEast = 0;
        for (Double direction : middleWindDirection) {
            if (direction >= 315 || direction < 45) {
                countNorth++;
            } else if (direction >= 45 && direction < 135) {
                countEast++;
            } else if (direction >= 135 && direction < 225) {
                countSouth++;
            } else {
                countWest++;
            }
        }
        int max = 0;
        String direction = new String();
        if (countNorth > max) {
            max = countNorth;
            direction = "North";
        }
        if (countEast > max) {
            max = countEast;
            direction = "East";
        }
        if (countSouth > max) {
            max = countSouth;
            direction = "South";
        }
        if (countWest > max) {
            max = countWest;
            direction = "West";
        }
        return direction;
    }
}
