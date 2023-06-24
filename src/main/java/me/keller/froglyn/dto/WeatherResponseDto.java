package me.keller.froglyn.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResponseDto {
    private CoordDto coord;
    private List<WeatherDto> weather;
    private String base;
    private MainDto main;
    private int visibility;
    private WindDto wind;
    private CloudsDto clouds;
    private long dt;
    private SysDto sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

    @Data
    public static class CoordDto {
        private double lon;
        private double lat;
    }

    @Data
    public static class WeatherDto {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class MainDto {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
    }

    @Data
    public static class WindDto {
        private double speed;
        private int deg;
        private int gust;
    }

    @Data
    public static class CloudsDto {
        private int all;
    }

    @Data
    public static class SysDto {
        private int type;
        private int id;
        private String country;
        private long sunrise;
        private long sunset;
    }
}
