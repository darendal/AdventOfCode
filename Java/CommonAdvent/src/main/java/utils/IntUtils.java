/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package utils;

public class IntUtils {
    public static int tryParse(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
