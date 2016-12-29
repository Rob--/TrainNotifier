
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.Map;

public class Location {

    private String crs;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCrs() {
        return crs;
    }

    public void setCrs(String crs) {
        this.crs = crs;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
