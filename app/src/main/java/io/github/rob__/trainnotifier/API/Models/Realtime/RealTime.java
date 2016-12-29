
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.Map;

public class RealTime {

    private RealTimeServiceInfo realTimeServiceInfo;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public RealTimeServiceInfo getRealTimeServiceInfo() {
        return realTimeServiceInfo;
    }

    public void setRealTimeServiceInfo(RealTimeServiceInfo realTimeServiceInfo) {
        this.realTimeServiceInfo = realTimeServiceInfo;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
