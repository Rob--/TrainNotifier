
package io.github.rob__.trainnotifier.API.Models;

import java.util.HashMap;
import java.util.Map;

public class BookingHorizon {

    private Integer kiosk;
    private Map<String, Object> additionalProperties = new HashMap();

    /**
     * 
     * @return
     *     The kiosk
     */
    public Integer getKiosk() {
        return kiosk;
    }

    /**
     * 
     * @param kiosk
     *     The Kiosk
     */
    public void setKiosk(Integer kiosk) {
        this.kiosk = kiosk;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
