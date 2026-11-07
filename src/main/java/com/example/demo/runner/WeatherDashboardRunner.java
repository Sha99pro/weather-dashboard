package com.example.demo.runner;

import com.example.demo.model.WeatherData;
import com.example.demo.service.WeatherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@ConditionalOnProperty(
        name = "weather.console.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class WeatherDashboardRunner implements CommandLineRunner {

    private final WeatherService weatherService;

    public WeatherDashboardRunner(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public void run(String... args) {

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {

            System.out.println("\n================================");
            System.out.println("       WEATHER DASHBOARD");
            System.out.println("================================");
            System.out.println("1. St. Louis");
            System.out.println("2. Chicago");
            System.out.println("3. Dallas");
            System.out.println("4. Enter another city");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            String city = "";

            switch (choice) {

                case "1":
                    city = "St. Louis";
                    break;

                case "2":
                    city = "Chicago";
                    break;

                case "3":
                    city = "Dallas";
                    break;

                case "4":
                    System.out.print("Enter city name: ");
                    city = scanner.nextLine();
                    break;

                case "5":
                    System.out.println("Thank you for using Weather Dashboard!");
                    running = false;
                    continue;

                default:
                    System.out.println("Invalid choice. Try again.");
                    continue;
            }

            try {

                WeatherData weather =
                        weatherService.getCurrentWeather(city);

                System.out.println("\n--------------------------------");
                System.out.println("Weather for " + weather.getName());
                System.out.println("--------------------------------");

                System.out.println(
                        "Temperature: " +
                                weather.getMain().getTemp() + " °F"
                );

                System.out.println(
                        "Description: " +
                                weather.getWeather()[0].getDescription()
                );

                System.out.println(
                        "Humidity: " +
                                weather.getMain().getHumidity() + "%"
                );

                System.out.println("--------------------------------");

            } catch (Exception e) {

                System.out.println("Error: " + e.getMessage());

            }
        }
    }
}