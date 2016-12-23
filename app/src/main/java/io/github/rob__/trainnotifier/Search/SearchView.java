package io.github.rob__.trainnotifier.Search;


import io.github.rob__.trainnotifier.API.Models.API;

interface SearchView {

    void showJourneys(API journeys);
    void showRecentSearches(String[][] recentSearches);
    void findTrainsClicked();
    void failedGettingJourneys();
    void swapSearchInput();

}
