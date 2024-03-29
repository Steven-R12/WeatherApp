package com.example.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.weatherapp.WeatherFetch.fetchWeatherByCityState;

public class WeatherApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        TextField cityField = new TextField();
        cityField.setPromptText("Enter city");
        TextField stateField = new TextField();
        stateField.setPromptText("Enter state");
        Button fetchButton = new Button("Fetch Weather");
        Label temperatureLabel = new Label();
        temperatureLabel.setText("Temp:");
        Label weatherLabel = new Label();
        weatherLabel.setText("Current Weather: ");
        Label weatherDescriptionLabel = new Label();
        weatherDescriptionLabel.setText("Description: ");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(cityField, 0, 0);
        gridPane.add(stateField, 1, 0);
        gridPane.add(fetchButton, 0, 1, 2 , 1);
        gridPane.add(temperatureLabel, 1,1,2,1);
        gridPane.add(weatherLabel, 0,2,2,1);
        gridPane.add(weatherDescriptionLabel,0,3,2,1);
        Scene scene = new Scene(gridPane, 300,100);
        stage.setScene(scene);
        stage.setTitle("Weather App");
        stage.show();
        fetchButton.setOnAction(actionEvent -> {
            String city = cityField.getText();
            String state = stateField.getText();
            try {
                JSONObject weatherData = fetchWeatherByCityState(city, state);
                double temperature = weatherData
                        .getJSONObject("current")
                        .getDouble("temp");
                temperatureLabel.setText("Temp: " + temperature + "°F");

                String description = weatherData
                        .getJSONObject("current")
                        .getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("description");
                weatherLabel.setText("Current Weather: " + description);
                String currentWeather = weatherData
                        .getJSONObject("current")
                        .getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("main");
                weatherDescriptionLabel.setText("Description: " + currentWeather);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
}

    public static void main(String[] args) {
        launch();
    }
}