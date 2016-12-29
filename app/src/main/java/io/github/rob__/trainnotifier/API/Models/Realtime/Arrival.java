
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.Map;

public class Arrival {

    private Boolean notApplicable;
    private Scheduled scheduled;
    private RealTime realTime;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Boolean getNotApplicable() {
        return notApplicable;
    }

    public void setNotApplicable(Boolean notApplicable) {
        this.notApplicable = notApplicable;
    }

    public Scheduled getScheduled() {
        return scheduled;
    }

    public void setScheduled(Scheduled scheduled) {
        this.scheduled = scheduled;
    }

    public RealTime getRealTime() {
        return realTime;
    }

    public void setRealTime(RealTime realTime) {
        this.realTime = realTime;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
