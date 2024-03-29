package com.example.weatherapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherFetch {
    public static JSONObject fetchJSON(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");


        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {

                response.append(line);

            }

            reader.close();


            String jsonResponse = response.toString().replaceAll("^\\[|]$", "");

            return new JSONObject(jsonResponse);

        } else {

            throw new IOException("Error fetching data. Response code: " + responseCode);

        }
    }

    private static final String API_KEY = System.getenv("OPENWEATHER_API_KEY");
    private static final String WEATHER_API_BASE_URL = "https://api.openweathermap.org/data/3.0/onecall";

    public static JSONObject fetchWeather(double latitude, double longitude) throws IOException {
        String apiUrl = String.format("%s?lat=%.2f&lon=%.2f&units=imperial&appid=%s",
                WEATHER_API_BASE_URL,
                latitude,
                longitude,
                API_KEY);
        URL url = new URL(apiUrl);
        return fetchJSON(url);
    }

    public static JSONObject convertCityStatetoLatLon(String City, String State, String Country) throws IOException {
        String apiUrl = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s,%s,%s&limit=1&appid=%s",
                City,
                State,
                Country,
                API_KEY);
        URL url = new URL(apiUrl);
        return fetchJSON(url);
    }

    public static JSONObject fetchWeatherByCityState(String City, String State) throws IOException {
        JSONObject x = WeatherFetch.convertCityStatetoLatLon(City, State, "US");
        double y = x.getDouble("lat");
        double z = x.getDouble("lon");
        JSONObject a = WeatherFetch.fetchWeather(y, z);

        return a;
    }
}
