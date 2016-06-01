package com.pavel.placeforlunch;

import com.pavel.placeforlunch.model.Vote;
import com.pavel.placeforlunch.util.TestUtil;

import java.util.Arrays;
import java.util.List;

import static com.pavel.placeforlunch.RestaurantTestData.*;
import static com.pavel.placeforlunch.UserTestData.*;

public class VoteTestData {

    public static final TestUtil.ToStringMatcher<Vote> MATCHER = new TestUtil.ToStringMatcher<>(Vote.class);

    private static final Vote VOTE1 = new Vote(USER1, RESTAURANT3);
    private static final Vote VOTE2 = new Vote(USER2, null);
    private static final Vote VOTE3 = new Vote(USER3, RESTAURANT4);
    private static final Vote VOTE4 = new Vote(USER4, RESTAURANT1);
    private static final Vote VOTE5 = new Vote(USER5, RESTAURANT2);
    private static final Vote VOTE5_ONLY_RESTAURANTID = new Vote(); // init in static block
    private static final Vote VOTE5_NO_USER = new Vote(); // init in static block
    private static final Vote VOTE6 = new Vote(USER6, RESTAURANT1);
    private static final Vote VOTE7 = new Vote(USER7, null);
    private static final Vote VOTE8 = new Vote(USER8, RESTAURANT4);
    private static final Vote VOTE9 = new Vote(USER9, RESTAURANT2);
    private static final Vote VOTE10 = new Vote(USER10, RESTAURANT4);

    public static final Vote USER_VOTE_WITH_RESTAURANT = VOTE5;
    public static final Vote USER_VOTE_UPDATED = new Vote(USER5, RESTAURANT1);
    public static final Vote USER_VOTE_DELETED = new Vote(USER5, null);
    public static final Vote USER_VOTE_UPDATED_ONLY_RESTAURANTID = new Vote(); // init in static block
    public static final Vote USER_VOTE_UPDATED_WITH_RESTAURANT_NO_USER = new Vote(); // init in static block
    public static final Vote USER_VOTE_WITH_RESTAURANT_NO_USER = VOTE5_NO_USER; // needed for JSON test
    public static final Vote USER_VOTE_ONLY_RESTAURANTID = VOTE5_ONLY_RESTAURANTID;
    public static final Vote NEW_VOTE = new Vote(USER_WITH_EMPTY_VOTE, RESTAURANT2);
    public static final List<Vote> ALL_VOTES = Arrays.asList(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5,
            VOTE6, VOTE7, VOTE8, VOTE9, VOTE10);

    static {
        VOTE5_NO_USER.setRestaurant(VOTE5.getRestaurant());
        VOTE5_ONLY_RESTAURANTID.setRestaurantId(VOTE5.getRestaurant().getId());
        USER_VOTE_UPDATED_ONLY_RESTAURANTID.setRestaurantId(RESTAURANT1.getId()); // JSON test -> only restaurant ID
        USER_VOTE_UPDATED_WITH_RESTAURANT_NO_USER.setRestaurant(RESTAURANT1);
    }
}
