
package io.github.rob__.trainnotifier.API.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusyData {

    private List<Object> coaches = null;
    private Map<String, Object> additionalProperties = new HashMap();

    /**
     * 
     * @return
     *     The coaches
     */
    public List<Object> getCoaches() {
        return coaches;
    }

    /**
     * 
     * @param coaches
     *     The coaches
     */
    public void setCoaches(List<Object> coaches) {
        this.coaches = coaches;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
