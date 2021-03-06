
package io.github.rob__.trainnotifier.API.Models.Mobile;

import java.util.HashMap;
import java.util.Map;

public class OutboundValidity {

    private Integer days;
    private Integer months;
    private Integer years;
    private Map<String, Object> additionalProperties = new HashMap();

    /**
     * 
     * @return
     *     The days
     */
    public Integer getDays() {
        return days;
    }

    /**
     * 
     * @param days
     *     The days
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * 
     * @return
     *     The months
     */
    public Integer getMonths() {
        return months;
    }

    /**
     * 
     * @param months
     *     The months
     */
    public void setMonths(Integer months) {
        this.months = months;
    }

    /**
     * 
     * @return
     *     The years
     */
    public Integer getYears() {
        return years;
    }

    /**
     * 
     * @param years
     *     The years
     */
    public void setYears(Integer years) {
        this.years = years;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
