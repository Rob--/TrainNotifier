
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.Map;

public class Scheduled_ {

    private String scheduledTime;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
