
package io.github.rob__.trainnotifier.API.Models.Mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {

    private Integer adults;
    private Integer children;
    private String destination;
    private String journeyType;
    private String origin;
    private OutboundJourney outboundJourney;
    private List<Railcard> railcards = null;
    private Boolean showCancelledTrains;
    private Map<String, Object> additionalProperties = new HashMap();

    /**
     * 
     * @return
     *     The adults
     */
    public Integer getAdults() {
        return adults;
    }

    /**
     * 
     * @param adults
     *     The adults
     */
    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    /**
     * 
     * @return
     *     The children
     */
    public Integer getChildren() {
        return children;
    }

    /**
     * 
     * @param children
     *     The children
     */
    public void setChildren(Integer children) {
        this.children = children;
    }

    /**
     * 
     * @return
     *     The destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * 
     * @param destination
     *     The destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * 
     * @return
     *     The journeyType
     */
    public String getJourneyType() {
        return journeyType;
    }

    /**
     * 
     * @param journeyType
     *     The journeyType
     */
    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    /**
     * 
     * @return
     *     The origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * 
     * @param origin
     *     The origin
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * 
     * @return
     *     The outboundJourney
     */
    public OutboundJourney getOutboundJourney() {
        return outboundJourney;
    }

    /**
     * 
     * @param outboundJourney
     *     The outboundJourney
     */
    public void setOutboundJourney(OutboundJourney outboundJourney) {
        this.outboundJourney = outboundJourney;
    }

    /**
     * 
     * @return
     *     The railcards
     */
    public List<Railcard> getRailcards() {
        return railcards;
    }

    /**
     * 
     * @param railcards
     *     The railcards
     */
    public void setRailcards(List<Railcard> railcards) {
        this.railcards = railcards;
    }

    /**
     * 
     * @return
     *     The showCancelledTrains
     */
    public Boolean getShowCancelledTrains() {
        return showCancelledTrains;
    }

    /**
     * 
     * @param showCancelledTrains
     *     The showCancelledTrains
     */
    public void setShowCancelledTrains(Boolean showCancelledTrains) {
        this.showCancelledTrains = showCancelledTrains;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
