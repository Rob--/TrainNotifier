
package io.github.rob__.trainnotifier.API.Models.Mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JourneyData {

    private String journeySearchId;
    private List<Journey> journeys = null;
    private List<Ticket> tickets = null;
    private List<JourneyTicket> journeyTickets = null;
    private List<RouteRestriction> routeRestrictions = null;
    private BookingHorizon bookingHorizon;
    private final Map<String, Object> additionalProperties = new HashMap();

    /**
     * 
     * @return
     *     The journeySearchId
     */
    public String getJourneySearchId() {
        return journeySearchId;
    }

    /**
     * 
     * @param journeySearchId
     *     The journeySearchId
     */
    public void setJourneySearchId(String journeySearchId) {
        this.journeySearchId = journeySearchId;
    }

    /**
     * 
     * @return
     *     The journeys
     */
    public List<Journey> getJourneys() {
        return journeys;
    }

    /**
     * 
     * @param journeys
     *     The journeys
     */
    public void setJourneys(List<Journey> journeys) {
        this.journeys = journeys;
    }

    /**
     * 
     * @return
     *     The tickets
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * 
     * @param tickets
     *     The tickets
     */
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * 
     * @return
     *     The journeyTickets
     */
    public List<JourneyTicket> getJourneyTickets() {
        return journeyTickets;
    }

    /**
     * 
     * @param journeyTickets
     *     The journeyTickets
     */
    public void setJourneyTickets(List<JourneyTicket> journeyTickets) {
        this.journeyTickets = journeyTickets;
    }

    /**
     * 
     * @return
     *     The routeRestrictions
     */
    public List<RouteRestriction> getRouteRestrictions() {
        return routeRestrictions;
    }

    /**
     * 
     * @param routeRestrictions
     *     The routeRestrictions
     */
    public void setRouteRestrictions(List<RouteRestriction> routeRestrictions) {
        this.routeRestrictions = routeRestrictions;
    }

    /**
     * 
     * @return
     *     The bookingHorizon
     */
    public BookingHorizon getBookingHorizon() {
        return bookingHorizon;
    }

    /**
     * 
     * @param bookingHorizon
     *     The bookingHorizon
     */
    public void setBookingHorizon(BookingHorizon bookingHorizon) {
        this.bookingHorizon = bookingHorizon;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
