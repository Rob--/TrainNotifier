
package io.github.rob__.trainnotifier.API.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Journey {

    private Integer id;
    private String origin;
    private String destination;
    private String departureDateTime;
    private String arrivalDateTime;
    private String direction;
    private List<Leg> legs = null;
    private String walkUpFareCategory;
    private String journeyStatus;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The departureDateTime
     */
    public String getDepartureDateTime() {
        return departureDateTime;
    }

    /**
     * 
     * @param departureDateTime
     *     The departureDateTime
     */
    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    /**
     * 
     * @return
     *     The arrivalDateTime
     */
    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    /**
     * 
     * @param arrivalDateTime
     *     The arrivalDateTime
     */
    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    /**
     * 
     * @return
     *     The direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 
     * @param direction
     *     The direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * 
     * @return
     *     The legs
     */
    public List<Leg> getLegs() {
        return legs;
    }

    /**
     * 
     * @param legs
     *     The legs
     */
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    /**
     * 
     * @return
     *     The walkUpFareCategory
     */
    public String getWalkUpFareCategory() {
        return walkUpFareCategory;
    }

    /**
     * 
     * @param walkUpFareCategory
     *     The walkUpFareCategory
     */
    public void setWalkUpFareCategory(String walkUpFareCategory) {
        this.walkUpFareCategory = walkUpFareCategory;
    }

    /**
     * 
     * @return
     *     The journeyStatus
     */
    public String getJourneyStatus() {
        return journeyStatus;
    }

    /**
     * 
     * @param journeyStatus
     *     The journeyStatus
     */
    public void setJourneyStatus(String journeyStatus) {
        this.journeyStatus = journeyStatus;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
