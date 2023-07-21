package com.example.weathergui;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private Text lbCels;

    @FXML
    private Text lbCity;

    @FXML
    private Text lbDate;

    @FXML
    private Text lbGrad;

    @FXML
    private Text lbMainMinus;

    @FXML
    private Text lbMaxMinus;

    @FXML
    private Text lbMinMinus;

    @FXML
    private Text lbTemp;

    @FXML
    private Text lbTempMax;

    @FXML
    private Text lbTempMaxSign;

    @FXML
    private Text lbTempMin;

    @FXML
    private Text lbTempMinSign;

    @FXML
    private Text lbTime;

    @FXML
    private Text lbWeather;

    @FXML
    private TextField textBox;

    @FXML
    private Text textInfo;
    private String buildTemp(Integer temperature, String type) {
        if (temperature <= 9 && temperature >= -9) {
            if (temperature < 0) {
                switch (type) {
                    case ("main"):
                        lbMainMinus.setVisible(true);
                        break;

                }
            }
            return "0" + temperature;
        }
        else {
            return temperature.toString();
        }
    }
    @FXML
    void onHelloButtonClick(MouseEvent event) {
        lbWeather.setVisible(true);
        Application.city = textBox.getText();
        System.out.println(Application.city);

        String language = "ru"; //TODO: язык
        String weatherData = Logic.getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + Application.city + "&lang=" + language + "&appid=597fb0b809f7fca16fd9ce2c6dfade75&units=metric");
        JSONObject obj = new JSONObject(weatherData);
        textInfo.setVisible(true);


//        result += "Pressure: " + obj.getJSONObject("main").getInt("pressure") + " hPa" + "\n";
//        result += "Humidity: " + obj.getJSONObject("main").getInt("humidity") + "%" + "\n";
//        result += "Wind: "  + obj.getJSONObject("wind").getInt("speed") + "m/s" + "\n";
//        lbWeather.setText(result);

        //=========== DATE TIME ===========
        //TODO: выровнять текст
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        LocalDateTime currentTime = LocalDateTime.now();
        lbDate.setText(dateFormat.format(currentTime));
        lbTime.setText(timeFormat.format(currentTime));
        lbTime.setVisible(true);
        lbDate.setVisible(true);

        //=========== CITY COUNTRY ===========
        //TODO: выровнять текст
        String tmpFirst = "name\":\"";
        String tmpSecond = "\",\"cod\"";
        lbCity.setText(weatherData.substring(weatherData.indexOf(tmpFirst) + tmpFirst.length(),
            weatherData.indexOf(tmpSecond)) + ", " +obj.getJSONObject("sys").getString("country"));
        lbCity.setVisible(true);

        //=========== WEATHER ===========
        //TODO: выровнять текст
        tmpFirst = "\"description\":\"";
        tmpSecond = "\",\"main\":\"";
        int tmpIndexFirst = obj.get("weather").toString().indexOf(tmpFirst) + tmpFirst.length();
        int tmpIndexSecond = obj.get("weather").toString().indexOf(tmpSecond);
        lbWeather.setText(obj.get("weather").toString().substring(tmpIndexFirst, tmpIndexSecond));
        lbWeather.setVisible(true);

        //=========== TEMPERATURE ===========
        //TODO: выровнять текст
        //TODO: поменять отображение минуса путем приклеивания перед строкой
        Integer temperature = obj.getJSONObject("main").getInt("temp");
        lbTemp.setText(buildTemp(temperature, "main"));
        temperature = obj.getJSONObject("main").getInt("temp_min");
        lbTempMin.setText(buildTemp(temperature, "min"));
        temperature = obj.getJSONObject("main").getInt("temp_max");
        lbTempMax.setText(buildTemp(temperature, "max"));
        lbTemp.setVisible(true);
        lbTempMax.setVisible(true);
        lbTempMin.setVisible(true);



        lbGrad.setVisible(true);
        lbCels.setVisible(true);
        lbTempMaxSign.setVisible(true);
        lbTempMinSign.setVisible(true);
    }

    @FXML
    void initialize() {
        assert button != null : "fx:id=\"button\" was not injected: check your FXML file 'view.fxml'.";
        assert lbCels != null : "fx:id=\"lbCels\" was not injected: check your FXML file 'view.fxml'.";
        assert lbCity != null : "fx:id=\"lbCity\" was not injected: check your FXML file 'view.fxml'.";
        assert lbDate != null : "fx:id=\"lbDate\" was not injected: check your FXML file 'view.fxml'.";
        assert lbGrad != null : "fx:id=\"lbGrad\" was not injected: check your FXML file 'view.fxml'.";
        assert lbMainMinus != null : "fx:id=\"lbMainMinus\" was not injected: check your FXML file 'view.fxml'.";
        assert lbMaxMinus != null : "fx:id=\"lbMaxMinus\" was not injected: check your FXML file 'view.fxml'.";
        assert lbMinMinus != null : "fx:id=\"lbMinMinus\" was not injected: check your FXML file 'view.fxml'.";
        assert lbTemp != null : "fx:id=\"lbTemp\" was not injected: check your FXML file 'view.fxml'.";
        assert lbTempMax != null : "fx:id=\"lbTempMax\" was not injected: check your FXML file 'view.fxml'.";
        assert lbTempMaxSign != null : "fx:id=\"lbTempMaxSign\" was not injected: check your FXML file 'view.fxml'.";
        assert lbTempMin != null : "fx:id=\"lbTempMin\" was not injected: check your FXML file 'view.fxml'.";
        assert lbTempMinSign != null : "fx:id=\"lbTempMinSign\" was not injected: check your FXML file 'view.fxml'.";
        assert lbTime != null : "fx:id=\"lbTime\" was not injected: check your FXML file 'view.fxml'.";
        assert lbWeather != null : "fx:id=\"lbWeather\" was not injected: check your FXML file 'view.fxml'.";
        assert textBox != null : "fx:id=\"textBox\" was not injected: check your FXML file 'view.fxml'.";
        assert textInfo != null : "fx:id=\"textInfo\" was not injected: check your FXML file 'view.fxml'.";

        lbTime.setVisible(false); //TODO: время должно изменяться динамически
        lbDate.setVisible(false);
        lbCity.setVisible(false);
        lbWeather.setVisible(false);
        lbTemp.setVisible(false);
        lbGrad.setVisible(false);
        lbCels.setVisible(false);
        lbTempMaxSign.setVisible(false);
        lbTempMinSign.setVisible(false);
        lbTempMax.setVisible(false);
        lbTempMin.setVisible(false);
        lbMainMinus.setVisible(false);
        lbMaxMinus.setVisible(false);
        lbMinMinus.setVisible(false);

        textInfo.setVisible(false);
        textBox.setStyle("-fx-text-fill: black");
        lbWeather.setVisible(false);
    }

}
