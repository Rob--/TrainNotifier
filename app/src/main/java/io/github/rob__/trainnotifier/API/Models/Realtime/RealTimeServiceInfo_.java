
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.Map;

public class RealTimeServiceInfo_ {

    private Boolean hasDeparted;
    private String realTime;
    private String realTimePlatform;
    private String realTimeFlag;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Boolean getHasDeparted() {
        return hasDeparted;
    }

    public void setHasDeparted(Boolean hasDeparted) {
        this.hasDeparted = hasDeparted;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public String getRealTimePlatform() {
        return realTimePlatform;
    }

    public void setRealTimePlatform(String realTimePlatform) {
        this.realTimePlatform = realTimePlatform;
    }

    public String getRealTimeFlag() {
        return realTimeFlag;
    }

    public void setRealTimeFlag(String realTimeFlag) {
        this.realTimeFlag = realTimeFlag;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
