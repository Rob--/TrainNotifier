
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.Map;

public class RealTimeServiceInfo {

    private Boolean hasArrived;
    private String realTime;
    private String realTimePlatform;
    private String realTimeFlag;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Boolean getHasArrived() {
        return hasArrived;
    }

    public void setHasArrived(Boolean hasArrived) {
        this.hasArrived = hasArrived;
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
