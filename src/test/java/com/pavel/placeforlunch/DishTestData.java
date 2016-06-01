package com.pavel.placeforlunch;

import com.pavel.placeforlunch.model.Dish;
import com.pavel.placeforlunch.util.TestUtil;

import java.util.Arrays;
import java.util.List;

public class DishTestData {

    public static final TestUtil.ToStringMatcher<Dish> MATCHER = new TestUtil.ToStringMatcher<>(Dish.class);

    public static final Dish DISH1 = new Dish(1, "Slow-Cooked Mint & Mustard Turkey", 374);
    public static final Dish DISH2 = new Dish(2, "Marinated Lime-Coated Horse", 355);
    private static final Dish DISH3 = new Dish(3, "Steamed Coconut Frog", 994);
    private static final Dish DISH4 = new Dish(4, "Basted Sour Clams", 431);
    private static final Dish DISH5 = new Dish(5, "Grilled Mountain Risotto", 569);
    public static final Dish DISH6 = new Dish(6, "Brined Mustard & Garlic Spring Greens", 408);
    private static final Dish DISH7 = new Dish(7, "Coconut and Banana Pound Cake", 558);
    private static final Dish DISH8 = new Dish(8, "Vanilla and Date Wafer", 720);
    public static final Dish DISH9 = new Dish(9, "Praline Ice Cream", 219);
    private static final Dish DISH10 = new Dish(10, "White Chocolate Snacks", 693);

    public static final List<Dish> RESTAURANT2_DISHES = Arrays.asList(DISH2, DISH6, DISH9);
    public static final List<Dish> RESTAURANT2_DISHES_WITHOUT_ONE = Arrays.asList(DISH6, DISH9);

    public static final Dish RESTAURANT1_DISH = DISH1;
    public static final Dish RESTAURANT2_DISH = DISH2;
    public static final Dish RESTAURANT2_DISH_UPDATED = new Dish(2, "Marinated UPDATED Horse", 111);
    public static final Dish DISH_NEW = new Dish("Created Test Dish", 545);

    public static final Dish DISH_NOT_EXIST = new Dish(99, "Not Existing ID", 999);
    public static final int RESTAURANT1_DISH_ID = 1;
    public static final int RESTAURANT2_DISH_ID = 2;
    public static final int RESTAURANT2_ID = 2;

    public static final List<Dish> ALL_DISHES = Arrays.asList(
            DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10);

    public static Dish getCreated() {
        return new Dish("Created Test Dish", 545);
    }
}
