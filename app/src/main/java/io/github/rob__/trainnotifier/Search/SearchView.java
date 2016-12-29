package io.github.rob__.trainnotifier.Search;


import io.github.rob__.trainnotifier.API.Models.Mobile.JourneyData;

interface SearchView {

    void showJourneys(JourneyData journeys);
    void showRecentSearches(String[][] recentSearches);
    void findTrainsClicked();
    void failedGettingJourneys();
    void swapSearchInput();

}
