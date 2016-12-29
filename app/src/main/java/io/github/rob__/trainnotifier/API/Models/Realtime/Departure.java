
package io.github.rob__.trainnotifier.API.Models.Realtime;

import java.util.HashMap;
import java.util.Map;

public class Departure {

    private Scheduled_ scheduled;
    private RealTime_ realTime;
    private Boolean notApplicable;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Scheduled_ getScheduled() {
        return scheduled;
    }

    public void setScheduled(Scheduled_ scheduled) {
        this.scheduled = scheduled;
    }

    public RealTime_ getRealTime() {
        return realTime;
    }

    public void setRealTime(RealTime_ realTime) {
        this.realTime = realTime;
    }

    public Boolean getNotApplicable() {
        return notApplicable;
    }

    public void setNotApplicable(Boolean notApplicable) {
        this.notApplicable = notApplicable;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
