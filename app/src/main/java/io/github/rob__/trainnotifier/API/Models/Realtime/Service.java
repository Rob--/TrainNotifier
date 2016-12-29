
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {

    private String serviceUid;
    private String serviceOperator;
    private String transportMode;
    private List<String> serviceOrigins = null;
    private List<String> serviceDestinations = null;
    private List<Stop> stops = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getServiceUid() {
        return serviceUid;
    }

    public void setServiceUid(String serviceUid) {
        this.serviceUid = serviceUid;
    }

    public String getServiceOperator() {
        return serviceOperator;
    }

    public void setServiceOperator(String serviceOperator) {
        this.serviceOperator = serviceOperator;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public List<String> getServiceOrigins() {
        return serviceOrigins;
    }

    public void setServiceOrigins(List<String> serviceOrigins) {
        this.serviceOrigins = serviceOrigins;
    }

    public List<String> getServiceDestinations() {
        return serviceDestinations;
    }

    public void setServiceDestinations(List<String> serviceDestinations) {
        this.serviceDestinations = serviceDestinations;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
