package com.master.DBHandler;

public final class ConfigKeys {

        private ConfigKeys() {
        }

        public static final class SQL {
                private SQL() {
                }

                public static final String DB_URL = "DB_URL";

                public static final String DB_USERNAME = "DB_USERNAME";

                public static final String DB_PASSWORD = "DB_PASSWORD";

                public static final String DB_MAX_POOL_SIZE = "DB_MAX_POOL_SIZE";

                public static final String DB_MIN_IDLE = "DB_MIN_IDLE";

                public static final String DB_CONNECTION_TIMEOUT = "DB_CONNECTION_TIMEOUT";

                public static final String DB_IDLE_TIMEOUT = "DB_IDLE_TIMEOUT";

                public static final String DB_MAX_LIFETIME = "DB_MAX_LIFETIME";

                public static final String DB_VALIDATION_TIMEOUT = "DB_VALIDATION_TIMEOUT";
        }

        // =========================
        // REDIS
        // =========================

        public static final class REDIS {
                private REDIS() {

                }

                public static final String REDIS_HOST = "REDIS_HOST";

                public static final String REDIS_PORT = "REDIS_PORT";

                public static final String REDIS_PASSWORD = "REDIS_PASSWORD";

                public static final String REDIS_USE_SSL = "REDIS_USE_SSL";

                public static final String REDIS_CONNECTION_TIMEOUT = "REDIS_CONNECTION_TIMEOUT";

                public static final String REDIS_MAX_TOTAL = "REDIS_MAX_TOTAL";

                public static final String REDIS_MAX_IDLE = "REDIS_MAX_IDLE";

                public static final String REDIS_MIN_IDLE = "REDIS_MIN_IDLE";

                public static final String REDIS_TEST_ON_BORROW = "REDIS_TEST_ON_BORROW";

                public static final String REDIS_TEST_ON_RETURN = "REDIS_TEST_ON_RETURN";

                public static final String REDIS_TEST_WHILE_IDLE = "REDIS_TEST_WHILE_IDLE";

                public static final String REDIS_NUM_TESTS_PER_EVICTION_RUN = "REDIS_NUM_TESTS_PER_EVICTION_RUN";

                public static final String REDIS_BLOCK_WHEN_EXHAUSTED = "REDIS_BLOCK_WHEN_EXHAUSTED";
        }
}
