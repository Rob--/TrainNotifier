
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.Map;

public class Stop {

    private Location location;
    private Arrival arrival;
    private Departure departure;
    private String callingType;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public String getCallingType() {
        return callingType;
    }

    public void setCallingType(String callingType) {
        this.callingType = callingType;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
