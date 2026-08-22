package com.master.DBHandler;

public final class DBConstants {

    private DBConstants() {
        // Utility class
    }

    public static final class SQL {

        private SQL() {
        }

        public static final String DB_MAXIMUM_POOL_SIZE = "5";

        public static final String DB_MINIMUM_IDLE = "1";

        public static final String DB_CONNECTION_TIMEOUT = "10000";

        public static final String DB_IDLE_TIMEOUT = "60000";

        public static final String DB_MAX_LIFETIME = "1800000";

        public static final String DB_VALIDATION_TIMEOUT = "5";
    }


    public static final class REDIS {

        private REDIS() {
        }

        public static final String REDIS_USE_SSL = "false";

        public static final String REDIS_CONNECTION_TIMEOUT = "10000";

        public static final String REDIS_MAX_TOTAL = "20";

        public static final String REDIS_MAX_IDLE = "10";

        public static final String REDIS_MIN_IDLE = "2";

        public static final String REDIS_TEST_ON_BORROW = "true";

        public static final String REDIS_TEST_ON_RETURN = "true";

        public static final String REDIS_TEST_WHILE_IDLE = "true";

        public static final String REDIS_NUM_TESTS_PER_EVICTION_RUN = "3";

        public static final String REDIS_BLOCK_WHEN_EXHAUSTED = "true";
    }
}