
package io.github.rob__.trainnotifier.API.Models.Mobile;

import java.util.HashMap;
import java.util.Map;

public class Fare {

    private String code;
    private Integer price;
    private Integer numberOfPassengers;
    private String fareType;
    private String passengerType;
    private Map<String, Object> additionalProperties = new HashMap();

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The numberOfPassengers
     */
    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    /**
     * 
     * @param numberOfPassengers
     *     The numberOfPassengers
     */
    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    /**
     * 
     * @return
     *     The fareType
     */
    public String getFareType() {
        return fareType;
    }

    /**
     * 
     * @param fareType
     *     The fareType
     */
    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    /**
     * 
     * @return
     *     The passengerType
     */
    public String getPassengerType() {
        return passengerType;
    }

    /**
     * 
     * @param passengerType
     *     The passengerType
     */
    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
