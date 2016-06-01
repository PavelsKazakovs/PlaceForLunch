package com.pavel.placeforlunch.util.exception;

import com.pavel.placeforlunch.util.EntityName;

public class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static <T> T check(T object, EntityName entityName, int id) {
        return check(object, entityName, "id=" + id);
    }

    @SuppressWarnings("WeakerAccess")
    public static <T> T check(T object, String fullMsg) {
        check(object != null, fullMsg);
        return object;
    }

    public static <T> T check(T object, EntityName entityName, String msg) {
        check(object != null, entityName + " with " + msg);
        return object;
    }

    public static void check(boolean found, EntityName entityName, int id) {
        check(found, entityName + " with id=" + id);
    }

    public static void check(boolean found, EntityName entityName, String msg) {
        check(found, entityName + " with " + msg);
    }

    public static <T> T check(T object, EntityName entityName, int id, EntityName forEntity, int forId) {
        return check(object, String.format("%s with id=%s for %s with id=%s",
                entityName, id, forEntity, forId));
    }

    public static void check(boolean found, EntityName entityName, int id, EntityName forEntity, int forId) {
        check(found, String.format("%s with id=%s for %s with id=%s",
                entityName, id, forEntity, forId));
    }

    @SuppressWarnings("WeakerAccess")
    public static void check(boolean found, String msg) {
        if (!found) {
            throw new ResourceNotFoundException("Not found " + msg);
        }
    }
}
