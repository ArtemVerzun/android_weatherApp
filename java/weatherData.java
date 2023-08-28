package com.example.weather;

import org.json.JSONObject;

public class weatherData {

    private String mTemp,mIcon,mCity,mWeatherType;
    private int mCondition;

    public static weatherData fromJson(JSONObject jsonObject){

        try{
            weatherData weatherD = new weatherData();
            weatherD.mCity=jsonObject.getString("name");
            //weatherD.mCondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            //weatherD.mCity=updateWeatherIcon(weatherD.mCondition);
            return weatherD;
    } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static String updateWeatherIcon(int condition){
        if(condition>=0 && condition<=300){

            return "thunderstorm";
        }
        else if(condition>=300 && condition<=500){

            return "lightrain";
        }
        else if(condition>=500 && condition<=600){

            return "shower";
        }
        else if(condition>=600 && condition<=700){

            return "snow";
        }
        else if(condition>=701 && condition<=771){

            return "fog";
        }
        else if(condition>=772 && condition<=800){

            return "overcast";
        }
        return "dunno";
    }

    public String getmCity(){
        return mCity;
    }
}
