package com.master.DBHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.master.DBHandler.Chache.CacheConfig;
import com.master.DBHandler.SQL.DBConfig;


public final class ConfigLoader {

    private ConfigLoader() {
    }

    public static DBConfig loadDbConfig(String filePath) {

        Properties properties = loadProperties(filePath);

        DBConfig config = new DBConfig();

        // Required properties
        config.setJdbcUrl(
                getRequired(
                        properties,
                        ConfigKeys.SQL.DB_URL));

        config.setUsername(
                getRequired(
                        properties,
                        ConfigKeys.SQL.DB_USERNAME));

        config.setPassword(
                getRequired(
                        properties,
                        ConfigKeys.SQL.DB_PASSWORD));

        // Optional properties with defaults
        config.setMaximumPoolSize(
                getInt(
                        properties,
                        ConfigKeys.SQL.DB_MAX_POOL_SIZE,
                        DBConstants.SQL.DB_MAXIMUM_POOL_SIZE));

        config.setMinimumIdle(
                getInt(
                        properties,
                        ConfigKeys.SQL.DB_MIN_IDLE,
                        DBConstants.SQL.DB_MINIMUM_IDLE));

        config.setConnectionTimeout(
                getLong(
                        properties,
                        ConfigKeys.SQL.DB_CONNECTION_TIMEOUT,
                        DBConstants.SQL.DB_CONNECTION_TIMEOUT));

        config.setIdleTimeout(
                getLong(
                        properties,
                        ConfigKeys.SQL.DB_IDLE_TIMEOUT,
                        DBConstants.SQL.DB_IDLE_TIMEOUT));

        config.setMaxLifetime(
                getLong(
                        properties,
                        ConfigKeys.SQL.DB_MAX_LIFETIME,
                        DBConstants.SQL.DB_MAX_LIFETIME));

        config.setValidationtime(
                getInt(
                        properties,
                        ConfigKeys.SQL.DB_VALIDATION_TIMEOUT,
                        DBConstants.SQL.DB_VALIDATION_TIMEOUT));

        return config;
    }

    public static CacheConfig loadCacheConfig(String filePath) {

        Properties properties = loadProperties(filePath);

        CacheConfig config = new CacheConfig();

        // Required properties

        config.setHost(
                getRequired(
                        properties,
                        ConfigKeys.REDIS.REDIS_HOST));

        config.setPort(
                getInt(
                        properties,
                        ConfigKeys.REDIS.REDIS_PORT,
                        "6379"));

        // Password can be optional depending on Redis configuration

        config.setPassword(
                properties.getProperty(
                        ConfigKeys.REDIS.REDIS_PASSWORD));

        // Optional properties with defaults

        config.setUseSSL(
                getBoolean(
                        properties,
                        ConfigKeys.REDIS.REDIS_USE_SSL,
                        DBConstants.REDIS.REDIS_USE_SSL));

        config.setConnectionTimeout(
                getInt(
                        properties,
                        ConfigKeys.REDIS.REDIS_CONNECTION_TIMEOUT,
                        DBConstants.REDIS.REDIS_CONNECTION_TIMEOUT));

        config.setMaxTotal(
                getInt(
                        properties,
                        ConfigKeys.REDIS.REDIS_MAX_TOTAL,
                        DBConstants.REDIS.REDIS_MAX_TOTAL));

        config.setMaxIdle(
                getInt(
                        properties,
                        ConfigKeys.REDIS.REDIS_MAX_IDLE,
                        DBConstants.REDIS.REDIS_MAX_IDLE));

        config.setMinIdle(
                getInt(
                        properties,
                        ConfigKeys.REDIS.REDIS_MIN_IDLE,
                        DBConstants.REDIS.REDIS_MIN_IDLE));

        config.setTestOnBorrow(
                getBoolean(
                        properties,
                        ConfigKeys.REDIS.REDIS_TEST_ON_BORROW,
                        DBConstants.REDIS.REDIS_TEST_ON_BORROW));

        config.setTestOnReturn(
                getBoolean(
                        properties,
                        ConfigKeys.REDIS.REDIS_TEST_ON_RETURN,
                        DBConstants.REDIS.REDIS_TEST_ON_RETURN));

        config.setTestWhileIdle(
                getBoolean(
                        properties,
                        ConfigKeys.REDIS.REDIS_TEST_WHILE_IDLE,
                        DBConstants.REDIS.REDIS_TEST_WHILE_IDLE));

        config.setNumTestsPerEvictionRun(
                getInt(
                        properties,
                        ConfigKeys.REDIS.REDIS_NUM_TESTS_PER_EVICTION_RUN,
                        DBConstants.REDIS.REDIS_NUM_TESTS_PER_EVICTION_RUN));

        config.setBlockWhenExhausted(
                getBoolean(
                        properties,
                        ConfigKeys.REDIS.REDIS_BLOCK_WHEN_EXHAUSTED,
                        DBConstants.REDIS.REDIS_BLOCK_WHEN_EXHAUSTED));

        return config;
    }

    private static Properties loadProperties(String filePath) {

        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException(
                    "Configuration file path cannot be null or empty");
        }

        Path path = Path.of(filePath);

        if (!Files.exists(path)) {
            throw new IllegalStateException(
                    "Configuration file does not exist: " + filePath);
        }

        if (!Files.isRegularFile(path)) {
            throw new IllegalStateException(
                    "Configuration path is not a file: " + filePath);
        }

        Properties properties = new Properties();

        try (InputStream inputStream = Files.newInputStream(path)) {

            properties.load(inputStream);

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Unable to load configuration file: " + filePath,
                    e);
        }

        return properties;
    }

    private static String getRequired(
            Properties properties,
            String key) {

        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {

            throw new IllegalStateException(
                    "Missing required configuration property: " + key);
        }

        return value.trim();
    }

    private static int getInt(
            Properties properties,
            String key,
            String defaultValue) {

        String value = properties.getProperty(
                key,
                defaultValue).trim();

        try {

            return Integer.parseInt(value);

        } catch (NumberFormatException e) {

            throw new IllegalStateException(
                    "Invalid integer value for property "
                            + key
                            + ": "
                            + value,
                    e);
        }
    }

    private static long getLong(
            Properties properties,
            String key,
            String defaultValue) {

        String value = properties.getProperty(
                key,
                defaultValue).trim();

        try {

            return Long.parseLong(value);

        } catch (NumberFormatException e) {

            throw new IllegalStateException(
                    "Invalid long value for property "
                            + key
                            + ": "
                            + value,
                    e);
        }
    }

    private static boolean getBoolean(
            Properties properties,
            String key,
            String defaultValue) {

        String value = properties.getProperty(
                key,
                defaultValue).trim();

        if (!value.equalsIgnoreCase("true")
                && !value.equalsIgnoreCase("false")) {

            throw new IllegalStateException(
                    "Invalid boolean value for property "
                            + key
                            + ": "
                            + value);
        }

        return Boolean.parseBoolean(value);
    }

}
