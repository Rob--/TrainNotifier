
package io.github.rob__.trainnotifier.API.Models;

import java.util.HashMap;
import java.util.Map;

public class Destination {

    private String stationCode;
    private String scheduledTime;
    private String realTime;
    private String realTimeStatus;
    private String platform;
    private String platformStatus;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The stationCode
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * 
     * @param stationCode
     *     The stationCode
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * 
     * @return
     *     The scheduledTime
     */
    public String getScheduledTime() {
        return scheduledTime;
    }

    /**
     * 
     * @param scheduledTime
     *     The scheduledTime
     */
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     *
     * @return
     *     The realTime
     */
    public String getRealTime() {
        return realTime;
    }

    /**
     *
     * @param realTime
     *     The realTime
     */
    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    /**
     * 
     * @return
     *     The realTimeStatus
     */
    public String getRealTimeStatus() {
        return realTimeStatus;
    }

    /**
     * 
     * @param realTimeStatus
     *     The realTimeStatus
     */
    public void setRealTimeStatus(String realTimeStatus) {
        this.realTimeStatus = realTimeStatus;
    }

    /**
     * 
     * @return
     *     The platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 
     * @param platform
     *     The platform
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * 
     * @return
     *     The platformStatus
     */
    public String getPlatformStatus() {
        return platformStatus;
    }

    /**
     * 
     * @param platformStatus
     *     The platformStatus
     */
    public void setPlatformStatus(String platformStatus) {
        this.platformStatus = platformStatus;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
