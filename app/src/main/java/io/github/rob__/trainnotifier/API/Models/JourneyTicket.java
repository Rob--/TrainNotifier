
package io.github.rob__.trainnotifier.API.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JourneyTicket {

    private Integer journeyId;
    private Integer ticketId;
    private String seatAvailabilityCode;
    private List<String> deliveryOptions = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The journeyId
     */
    public Integer getJourneyId() {
        return journeyId;
    }

    /**
     * 
     * @param journeyId
     *     The journeyId
     */
    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }

    /**
     * 
     * @return
     *     The ticketId
     */
    public Integer getTicketId() {
        return ticketId;
    }

    /**
     * 
     * @param ticketId
     *     The ticketId
     */
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * 
     * @return
     *     The seatAvailabilityCode
     */
    public String getSeatAvailabilityCode() {
        return seatAvailabilityCode;
    }

    /**
     * 
     * @param seatAvailabilityCode
     *     The seatAvailabilityCode
     */
    public void setSeatAvailabilityCode(String seatAvailabilityCode) {
        this.seatAvailabilityCode = seatAvailabilityCode;
    }

    /**
     * 
     * @return
     *     The deliveryOptions
     */
    public List<String> getDeliveryOptions() {
        return deliveryOptions;
    }

    /**
     * 
     * @param deliveryOptions
     *     The deliveryOptions
     */
    public void setDeliveryOptions(List<String> deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
