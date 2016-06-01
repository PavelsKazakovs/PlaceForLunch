package com.pavel.placeforlunch;

import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.to.RestaurantWithVotes;
import com.pavel.placeforlunch.util.TestUtil;

import java.util.*;

public class RestaurantTestData {

    public static final TestUtil.ToStringMatcher<Restaurant> MATCHER =
            new TestUtil.ToStringMatcher<>(Restaurant.class);

    public static final TestUtil.ToStringMatcher<RestaurantWithVotes> MATCHER_WITH_VOTES =
            new TestUtil.ToStringMatcher<>(RestaurantWithVotes.class);

    public static final Restaurant RESTAURANT1 = new Restaurant(1, "The Vintage Chef");
    public static final Restaurant RESTAURANT2 = new Restaurant(2, "Roadhouse");
    public static final Restaurant RESTAURANT3 = new Restaurant(3, "The Rose");
    public static final Restaurant RESTAURANT4 = new Restaurant(4, "Meadows");

    public static final Restaurant RESTAURANT2_UPDATED = new Restaurant(2, "UPDATED Roadhouse");
    public static final Restaurant RESTAURANT_NOT_EXIST = new Restaurant(99, "Not existing ID");
    public static final List<Restaurant> ALL_RESTAURANTS = Arrays.asList(
            RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4);

    public static final List<RestaurantWithVotes> RESTAURANTS_WITH_VOTES = new ArrayList<>();
    public static final List<RestaurantWithVotes> RESTAURANTS_WITH_VOTES_ONLY_ID = new ArrayList<>();
    public static final Map<Restaurant, Integer> VOTES_BY_RESTAURANT = new LinkedHashMap<>();

    static {
        VOTES_BY_RESTAURANT.put(RESTAURANT4, 3);
        VOTES_BY_RESTAURANT.put(RESTAURANT1, 2);
        VOTES_BY_RESTAURANT.put(RESTAURANT2, 2);
        VOTES_BY_RESTAURANT.put(RESTAURANT3, 1);
        VOTES_BY_RESTAURANT.put(null, 2);

        VOTES_BY_RESTAURANT.entrySet().stream()
                .forEachOrdered(e -> RESTAURANTS_WITH_VOTES
                        .add(new RestaurantWithVotes(e.getKey(), e.getValue())));

        // id is null for those who didn't vote
        VOTES_BY_RESTAURANT.entrySet().stream()
                .forEachOrdered(e -> RESTAURANTS_WITH_VOTES_ONLY_ID.add(new RestaurantWithVotes(
                        e.getKey() == null ? null : e.getKey().getId(), e.getValue())));
    }

    public static Restaurant getCreated() {
        return new Restaurant("New test restaurant");
    }
}
