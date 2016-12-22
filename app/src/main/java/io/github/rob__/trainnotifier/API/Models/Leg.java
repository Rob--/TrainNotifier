
package io.github.rob__.trainnotifier.API.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leg {

    private Integer id;
    private Origin origin;
    private Destination destination;
    private String transportMode;
    private String reservationFlag;
    private String retailTrainIdentifier;
    private String trainId;
    private String serviceProviderCode;
    private String serviceProviderName;
    private String seatingClass;
    private Boolean isCancelled;
    private List<String> finalDestinations = null;
    private BusyData busyData;
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
    public Origin getOrigin() {
        return origin;
    }

    /**
     * 
     * @param origin
     *     The origin
     */
    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    /**
     * 
     * @return
     *     The destination
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * 
     * @param destination
     *     The destination
     */
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    /**
     * 
     * @return
     *     The transportMode
     */
    public String getTransportMode() {
        return transportMode;
    }

    /**
     * 
     * @param transportMode
     *     The transportMode
     */
    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    /**
     * 
     * @return
     *     The reservationFlag
     */
    public String getReservationFlag() {
        return reservationFlag;
    }

    /**
     * 
     * @param reservationFlag
     *     The reservationFlag
     */
    public void setReservationFlag(String reservationFlag) {
        this.reservationFlag = reservationFlag;
    }

    /**
     * 
     * @return
     *     The retailTrainIdentifier
     */
    public String getRetailTrainIdentifier() {
        return retailTrainIdentifier;
    }

    /**
     * 
     * @param retailTrainIdentifier
     *     The retailTrainIdentifier
     */
    public void setRetailTrainIdentifier(String retailTrainIdentifier) {
        this.retailTrainIdentifier = retailTrainIdentifier;
    }

    /**
     * 
     * @return
     *     The trainId
     */
    public String getTrainId() {
        return trainId;
    }

    /**
     * 
     * @param trainId
     *     The trainId
     */
    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    /**
     * 
     * @return
     *     The serviceProviderCode
     */
    public String getServiceProviderCode() {
        return serviceProviderCode;
    }

    /**
     * 
     * @param serviceProviderCode
     *     The serviceProviderCode
     */
    public void setServiceProviderCode(String serviceProviderCode) {
        this.serviceProviderCode = serviceProviderCode;
    }

    /**
     * 
     * @return
     *     The serviceProviderName
     */
    public String getServiceProviderName() {
        return serviceProviderName;
    }

    /**
     * 
     * @param serviceProviderName
     *     The serviceProviderName
     */
    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    /**
     * 
     * @return
     *     The seatingClass
     */
    public String getSeatingClass() {
        return seatingClass;
    }

    /**
     * 
     * @param seatingClass
     *     The seatingClass
     */
    public void setSeatingClass(String seatingClass) {
        this.seatingClass = seatingClass;
    }

    /**
     * 
     * @return
     *     The isCancelled
     */
    public Boolean getIsCancelled() {
        return isCancelled;
    }

    /**
     * 
     * @param isCancelled
     *     The isCancelled
     */
    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    /**
     * 
     * @return
     *     The finalDestinations
     */
    public List<String> getFinalDestinations() {
        return finalDestinations;
    }

    /**
     * 
     * @param finalDestinations
     *     The finalDestinations
     */
    public void setFinalDestinations(List<String> finalDestinations) {
        this.finalDestinations = finalDestinations;
    }

    /**
     * 
     * @return
     *     The busyData
     */
    public BusyData getBusyData() {
        return busyData;
    }

    /**
     * 
     * @param busyData
     *     The busyData
     */
    public void setBusyData(BusyData busyData) {
        this.busyData = busyData;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
