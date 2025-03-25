package com.admany.peacefuldays.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModLogger {
    private static final Logger LOGGER = LogManager.getLogger(PeacefulDaysMod.MOD_ID);

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void error(String message, Throwable e) {
        LOGGER.error(message, e);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }
}