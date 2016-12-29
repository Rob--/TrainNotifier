
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.Map;

public class RealtimeData {

    private String requestId;
    private Boolean isRealTimeDataAvailable;
    private Service service;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Boolean getIsRealTimeDataAvailable() {
        return isRealTimeDataAvailable;
    }

    public void setIsRealTimeDataAvailable(Boolean isRealTimeDataAvailable) {
        this.isRealTimeDataAvailable = isRealTimeDataAvailable;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
