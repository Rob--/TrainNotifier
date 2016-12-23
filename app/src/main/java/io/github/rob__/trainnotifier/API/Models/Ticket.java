
package io.github.rob__.trainnotifier.API.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ticket {

    private Integer id;
    private String name;
    private String description;
    private String ticketType;
    private String ticketClass;
    private Boolean throughLondon;
    private Integer totalFare;
    private String routeRestriction;
    private String fareRestrictionCode;
    private String fareOriginNlc;
    private String fareOriginStation;
    private String fareDestinationNlc;
    private String fareDestinationStation;
    private String fareSource;
    private List<Fare> fares = null;
    private OutboundValidity outboundValidity;
    private InboundValidity inboundValidity;
    private Boolean reservationRequired;
    private String fareCategory;
    private Boolean isPromotional;
    private Map<String, Object> additionalProperties = new HashMap();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The ticketType
     */
    public String getTicketType() {
        return ticketType;
    }

    /**
     * 
     * @param ticketType
     *     The ticketType
     */
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * 
     * @return
     *     The ticketClass
     */
    public String getTicketClass() {
        return ticketClass;
    }

    /**
     * 
     * @param ticketClass
     *     The ticketClass
     */
    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    /**
     * 
     * @return
     *     The throughLondon
     */
    public Boolean getThroughLondon() {
        return throughLondon;
    }

    /**
     * 
     * @param throughLondon
     *     The throughLondon
     */
    public void setThroughLondon(Boolean throughLondon) {
        this.throughLondon = throughLondon;
    }

    /**
     * 
     * @return
     *     The totalFare
     */
    public Integer getTotalFare() {
        return totalFare;
    }

    /**
     * 
     * @param totalFare
     *     The totalFare
     */
    public void setTotalFare(Integer totalFare) {
        this.totalFare = totalFare;
    }

    /**
     * 
     * @return
     *     The routeRestriction
     */
    public String getRouteRestriction() {
        return routeRestriction;
    }

    /**
     * 
     * @param routeRestriction
     *     The routeRestriction
     */
    public void setRouteRestriction(String routeRestriction) {
        this.routeRestriction = routeRestriction;
    }

    /**
     * 
     * @return
     *     The fareRestrictionCode
     */
    public String getFareRestrictionCode() {
        return fareRestrictionCode;
    }

    /**
     * 
     * @param fareRestrictionCode
     *     The fareRestrictionCode
     */
    public void setFareRestrictionCode(String fareRestrictionCode) {
        this.fareRestrictionCode = fareRestrictionCode;
    }

    /**
     * 
     * @return
     *     The fareOriginNlc
     */
    public String getFareOriginNlc() {
        return fareOriginNlc;
    }

    /**
     * 
     * @param fareOriginNlc
     *     The fareOriginNlc
     */
    public void setFareOriginNlc(String fareOriginNlc) {
        this.fareOriginNlc = fareOriginNlc;
    }

    /**
     * 
     * @return
     *     The fareOriginStation
     */
    public String getFareOriginStation() {
        return fareOriginStation;
    }

    /**
     * 
     * @param fareOriginStation
     *     The fareOriginStation
     */
    public void setFareOriginStation(String fareOriginStation) {
        this.fareOriginStation = fareOriginStation;
    }

    /**
     * 
     * @return
     *     The fareDestinationNlc
     */
    public String getFareDestinationNlc() {
        return fareDestinationNlc;
    }

    /**
     * 
     * @param fareDestinationNlc
     *     The fareDestinationNlc
     */
    public void setFareDestinationNlc(String fareDestinationNlc) {
        this.fareDestinationNlc = fareDestinationNlc;
    }

    /**
     * 
     * @return
     *     The fareDestinationStation
     */
    public String getFareDestinationStation() {
        return fareDestinationStation;
    }

    /**
     * 
     * @param fareDestinationStation
     *     The fareDestinationStation
     */
    public void setFareDestinationStation(String fareDestinationStation) {
        this.fareDestinationStation = fareDestinationStation;
    }

    /**
     * 
     * @return
     *     The fareSource
     */
    public String getFareSource() {
        return fareSource;
    }

    /**
     * 
     * @param fareSource
     *     The fareSource
     */
    public void setFareSource(String fareSource) {
        this.fareSource = fareSource;
    }

    /**
     * 
     * @return
     *     The fares
     */
    public List<Fare> getFares() {
        return fares;
    }

    /**
     * 
     * @param fares
     *     The fares
     */
    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }

    /**
     * 
     * @return
     *     The outboundValidity
     */
    public OutboundValidity getOutboundValidity() {
        return outboundValidity;
    }

    /**
     * 
     * @param outboundValidity
     *     The outboundValidity
     */
    public void setOutboundValidity(OutboundValidity outboundValidity) {
        this.outboundValidity = outboundValidity;
    }

    /**
     * 
     * @return
     *     The inboundValidity
     */
    public InboundValidity getInboundValidity() {
        return inboundValidity;
    }

    /**
     * 
     * @param inboundValidity
     *     The inboundValidity
     */
    public void setInboundValidity(InboundValidity inboundValidity) {
        this.inboundValidity = inboundValidity;
    }

    /**
     * 
     * @return
     *     The reservationRequired
     */
    public Boolean getReservationRequired() {
        return reservationRequired;
    }

    /**
     * 
     * @param reservationRequired
     *     The reservationRequired
     */
    public void setReservationRequired(Boolean reservationRequired) {
        this.reservationRequired = reservationRequired;
    }

    /**
     * 
     * @return
     *     The fareCategory
     */
    public String getFareCategory() {
        return fareCategory;
    }

    /**
     * 
     * @param fareCategory
     *     The fareCategory
     */
    public void setFareCategory(String fareCategory) {
        this.fareCategory = fareCategory;
    }

    /**
     * 
     * @return
     *     The isPromotional
     */
    public Boolean getIsPromotional() {
        return isPromotional;
    }

    /**
     * 
     * @param isPromotional
     *     The isPromotional
     */
    public void setIsPromotional(Boolean isPromotional) {
        this.isPromotional = isPromotional;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
