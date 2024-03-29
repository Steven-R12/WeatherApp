import com.example.weatherapp.WeatherFetch;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class testWeatherFetch {
    @Test
    void testFetchWeather(){
        try {
            double latitude = 40.7128;
            double longitude = -74.0060;

            JSONObject weatherData = WeatherFetch.fetchWeather(latitude, longitude);

            assertNotNull(weatherData, "Weather data should not be null");
            assertEquals(weatherData.getDouble("lat"), latitude, 0.1);
            assertEquals(weatherData.getDouble("lon"), longitude, 0.1);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    @Test
    void testconvertCityStatetoLatLon(){
        try{
            String City = "Boston";
            String State = "Massachusetts";
            String Country = "US";

            JSONObject weatherData = WeatherFetch.convertCityStatetoLatLon(City,State,Country);

            assertNotNull(weatherData, "Weather Data Should Not be Null");
            assertEquals(weatherData.getString("name"),City);
            assertEquals(weatherData.getString("state"),State);
            assertEquals(weatherData.getString("country"),Country);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFetchLocation() {
        try {
            double latitude = 42.3554334;
            double longitude = -71.060511;
            JSONObject weatherData = WeatherFetch.fetchWeatherByCityState("Boston", "MA");
            assertNotNull(weatherData);
            assertEquals(weatherData.getDouble("lat"), latitude, 0.1);
            assertEquals(weatherData.getDouble("lon"), longitude, 0.1);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
