package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Map;

public class Route {

    private String from;
    private String to;
    private int distance;
    private Map<Integer, String> roadSigns;

    @JsonCreator
    public Route(@JsonProperty("from") String from,
                 @JsonProperty("to") String to,
                 @JsonProperty("distance") int distance,
                 @JsonProperty("roadsigns") Map<Integer, String> roadSigns) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.roadSigns = roadSigns;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Map<Integer, String> getRoadSigns() {
        return roadSigns;
    }

    public void setRoadSigns(Map<Integer, String> roadSigns) {
        this.roadSigns = roadSigns;
    }
}
