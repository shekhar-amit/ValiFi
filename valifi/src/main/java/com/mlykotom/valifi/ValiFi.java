package com.mlykotom.valifi;

import com.mlykotom.valifi.exceptions.ValiFiException;
import com.mlykotom.valifi.exceptions.ValiFiValidatorException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import ohos.app.Context;
import ohos.utils.CommonPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

/**
 * ValiFi main class.
 */

@SuppressWarnings("unused")
public class ValiFi {
    static String TAG = ValiFi.class.getSimpleName();
    private static ValiFi ourInstance;
    public final ValiFiConfig mParameters;
    private final Context mAppContext;

    /**
     * Constructor for ValiFi.
     *
     * @param appContext nullable for tests
     * @param config configuration of valifi (patterns, errors)
     */
    private ValiFi(@Nullable Context appContext, @NotNull ValiFiConfig config) {
        mAppContext = appContext;
        mParameters = config;
    }

    /**
     * Installs validation with specified settings.
     * This should be called in Application's onCreate.
     *
     * @param appContext for requesting resources, etc.
     * @param config overriden parameters, built by {@link Builder}
     */
    public static void install(@NotNull Context appContext, @NotNull ValiFiConfig config) {
        ourInstance = new ValiFi(appContext.getApplicationContext(), config);
    }

    /**
     * Installs validation with default settings.
     * This should be called in Application's onCreate.
     *
     * @param appContext for requesting resources, etc.
     */
    public static void install(@NotNull Context appContext) {
        install(appContext.getApplicationContext(), new Builder().build());
    }

    /**
     * Installer for tests without context.
     */
    @TestOnly
    public static void install() {
        ourInstance = new ValiFi(null, new Builder().build());
    }

    /**
     * Installer for tests without context.
     *
     * @param config overriden parameters, built by {@link Builder}
     */
    @TestOnly
    public static void install(@NotNull ValiFiConfig config) {
        ourInstance = new ValiFi(null, config);
    }

    /**
     * Helper for destroying all specified fields.
     *
     * @param fields to be destroyed
     */
    public static void destroyFields(ValiFiValidable... fields) {
        for (ValiFiValidable field : fields) {
            field.destroy();
        }
    }

    /**
     * Returns installed known card types.
     *
     * @return installed known card types (MASTERCARD, VISA and AMERICAN_EXPRESS as default).
     */
    @NotNull
    public static Set<ValiFiCardType> getCreditCardTypes() {
        return getInstance().mParameters.mKnownCardTypes;
    }

    static int getErrorRes(Builder.ERROR_RES field) {
        return getInstance().mParameters.mErrorResources[Builder.getErrorResNum(field)];
    }

    @NotNull
    static ValiFieldBase.PropertyValidator<String> getValidator(Builder.PATTERN field) {
        return getInstance().mParameters.mValidators[Builder.getPatternNum(field)];
    }

    static long getErrorDelay() {
        return getInstance().mParameters.mErrorDelay;
    }

    static long getAsyncValidationDelay() {
        return getInstance().mParameters.mAsyncValidationDelay;
    }

    /**
     * Returns instance of ValiFi.
     */
    public static ValiFi getInstance() {
        if (ourInstance == null) {
            throw new ValiFiException("ValiFi must be installed in Application.onCreate()!");
        }

        return ourInstance;
    }

    static Context getContext() {
        if (getInstance().mAppContext == null) {
            throw new ValiFiException("ValiFi was installed without Context!");
        }
        return getInstance().mAppContext;
    }

    static String getString(int stringRes, Object... formatArgs) {
        @Nullable Context context = getInstance().mAppContext;
        if (context == null) {
            // tests may be initialized without context, so will use placeholder string
            return "string-" + stringRes;
        }
        return context.getString(stringRes, formatArgs);
    }

    /**
     * Configuration for validation library.
     * Should be built by {@link Builder}
     */
    public static class ValiFiConfig {
        public final int[] mErrorResources;
        public final ValiFieldBase.PropertyValidator<String>[] mValidators;
        final long mErrorDelay;
        final long mAsyncValidationDelay;
        final Set<ValiFiCardType> mKnownCardTypes;

        ValiFiConfig(ValiFieldBase.PropertyValidator<String>[] validators, int[] errorResources,
                     long errorDelay, long asyncValidationDelay, @NotNull Set<ValiFiCardType> knownCardTypes) {
            mValidators = validators;
            mErrorResources = errorResources;
            mErrorDelay = errorDelay;
            mAsyncValidationDelay = asyncValidationDelay;
            mKnownCardTypes = knownCardTypes;
        }
    }

    /**
     * Builder for overriding error resources or patterns for default validation.
     */
    public static class Builder {
        // ----- Error Resources
        public static final int ERROR_RES_NOT_EMPTY = 0;
        public static final int ERROR_RES_LENGTH_MIN = 1;
        public static final int ERROR_RES_LENGTH_MAX = 2;
        public static final int ERROR_RES_LENGTH_RANGE = 3;
        public static final int ERROR_RES_LENGTH_EXACT = 4;
        public static final int ERROR_RES_EMAIL = 5;
        public static final int ERROR_RES_PHONE = 6;
        public static final int ERROR_RES_USERNAME = 7;
        public static final int ERROR_RES_PASSWORD = 8;
        public static final int ERROR_RES_YEARS_OLDER_THAN = 9;
        public static final int ERROR_RES_CREDIT_CARD = 10;
        // ------ COUNT OF PARAMETERS
        public static final int ERROR_RES_COUNT = ERROR_RES_CREDIT_CARD + 1;
        // ----- Patterns
        public static final int PATTERN_EMAIL = 0;
        public static final int PATTERN_PHONE = 1;
        public static final int PATTERN_PASSWORD = 2;
        public static final int PATTERN_USERNAME = 3;
        // ------ COUNT OF PARAMETERS
        public static final int PATTERN_COUNT = PATTERN_USERNAME + 1;
        // ----- other
        private static final long DEFAULT_ERROR_DELAY_MILLIS = 500;
        private static final long DEFAULT_ASYNC_VALIDATION_DELAY_MILLIS = 300;
        private ValiFieldBase.PropertyValidator<String>[] mValidators;
        private int[] mErrorResources;
        private Set<ValiFiCardType> mKnownCardTypes;
        private long mErrorDelay = DEFAULT_ERROR_DELAY_MILLIS;
        private long mAsyncValidationDelay = DEFAULT_ASYNC_VALIDATION_DELAY_MILLIS;

        /**
         * Enumerator for error resources.
         */
        public enum ERROR_RES {
            ERROR_RES_NOT_EMPTY,
            ERROR_RES_LENGTH_MIN,
            ERROR_RES_LENGTH_MAX,
            ERROR_RES_LENGTH_RANGE,
            ERROR_RES_LENGTH_EXACT,
            ERROR_RES_EMAIL,
            ERROR_RES_PHONE,
            ERROR_RES_USERNAME,
            ERROR_RES_PASSWORD,
            ERROR_RES_YEARS_OLDER_THAN,
            ERROR_RES_CREDIT_CARD
        }

        /**
         * Returns integer from the enumerator.
         *
         * @param field error resource
         */
        public static int getErrorResNum(ERROR_RES field) {
            switch (field) {
                case ERROR_RES_NOT_EMPTY:
                    return 0;
                case ERROR_RES_LENGTH_MIN:
                    return 1;
                case ERROR_RES_LENGTH_MAX:
                    return 2;
                case ERROR_RES_LENGTH_RANGE:
                    return 3;
                case ERROR_RES_LENGTH_EXACT:
                    return 4;
                case ERROR_RES_EMAIL:
                    return 5;
                case ERROR_RES_PHONE:
                    return 6;
                case ERROR_RES_USERNAME:
                    return 7;
                case ERROR_RES_PASSWORD:
                    return 8;
                case ERROR_RES_YEARS_OLDER_THAN:
                    return 9;
                case ERROR_RES_CREDIT_CARD:
                    return 10;
                default:
                    return -1;
            }
        }

        /**
         * Enumerator for regex pattern resources.
         */
        public enum PATTERN {
                PATTERN_EMAIL,
                PATTERN_PHONE,
                PATTERN_PASSWORD,
                PATTERN_USERNAME
        }

        /**
         * Returns integer from the enumerator.
         *
         * @param field regex pattern resource
         */
        public static int getPatternNum(PATTERN field) {
            switch (field) {
                case PATTERN_EMAIL:
                    return 0;
                case PATTERN_PHONE:
                    return 1;
                case PATTERN_PASSWORD:
                    return 2;
                case PATTERN_USERNAME:
                    return 3;
                default:
                    return -1;
            }
        }

        /**
         * Builder class for ValiFi.
         */
        public Builder() {
            //noinspection unchecked
            mValidators = new ValiFieldBase.PropertyValidator[PATTERN_COUNT];
            mErrorResources = new int[ERROR_RES_COUNT];
            mKnownCardTypes = ValiFiCardType.getDefaultTypes();

            setupResources();
            setupPatterns();
        }

        /**
         * You may override any resource when specifying string resource for it.
         *
         * @param field one of error resources in library
         * @param value string resource used as default. Some errors may require PARAMETERS
         * @return builder for chaining
         */
        public Builder setErrorResource(ERROR_RES field, int value) {
            mErrorResources[getErrorResNum(field)] = value;
            return this;
        }

        /**
         * You may override any pattern with specific validator.
         *
         * @param field one of patterns in library
         * @param validator Generic validation (global) in your app. If you need to specify custom validation,
         *                 check {@link ValiFieldBase#addCustomValidator(ValiFieldBase.PropertyValidator)}
         * @return builder for chaining
         */
        public Builder setValidator(PATTERN field, ValiFieldBase.PropertyValidator<String> validator) {
            mValidators[getPatternNum(field)] = validator;
            return this;
        }

        /**
         * You may override any pattern when specifying pattern for it.
         *
         * @param field one of patterns in library
         * @param pattern compiled pattern used as default
         * @return builder for chaining
         */
        public Builder setPattern(PATTERN field, final Pattern pattern) {
            return setValidator(field, value -> value != null && pattern.matcher(value).matches());
        }

        /**
         * Setups error delay for either never or immediate.
         * When set, it will be used in all fields by default (if some field does not override it).
         *
         * @param delayType either never or immediate
         * @return builder for chaining
         * @see #setErrorDelay(long)  if you want to set exact time
         */
        public Builder setErrorDelay(ValiFiErrorDelay delayType) {
            mErrorDelay = delayType.delayMillis;
            return this;
        }

        /**
         * Setups error delay (default is {@link #DEFAULT_ERROR_DELAY_MILLIS}).
         * When set, it will be used in all fields by default (if some field does not override it).
         *
         * @param millis how long till error will be shown.
         * @return builder for chaining
         * @see #setErrorDelay(ValiFiErrorDelay) for immediate or manual mode
         */
        public Builder setErrorDelay(long millis) {
            if (millis <= 0) {
                throw new ValiFiValidatorException("Error delay must be positive");
            }
            mErrorDelay = millis;
            return this;
        }

        /**
         * Asynchronous validation for all fields will start after the delay specified here.
         * Default value is {@link #DEFAULT_ASYNC_VALIDATION_DELAY_MILLIS}.
         *
         * @param millis can be milliseconds 0+
         * @return builder for chaining
         * @see ValiFieldBase#setAsyncValidationDelay(long) for overriding for specified field
         */
        public Builder setAsyncValidationDelay(long millis) {
            if (millis < 0) {
                throw new ValiFiValidatorException("Asynchronous delay must be positive or immediate");
            }

            mAsyncValidationDelay = millis;
            return this;
        }

        /**
         * Clears known card types and sets new types.
         *
         * @param types to be set (will clear previously set). If @null, only clears the types
         * @return builder for chaining
         */
        public Builder setKnownCardTypes(@Nullable ValiFiCardType... types) {
            mKnownCardTypes = new HashSet<>();
            if (types != null) {
                Collections.addAll(mKnownCardTypes, types);
            }
            return this;
        }

        public ValiFiConfig build() {
            return new ValiFiConfig(mValidators, mErrorResources, mErrorDelay, mAsyncValidationDelay, mKnownCardTypes);
        }

        private void setupPatterns() {
            setPattern(PATTERN.PATTERN_EMAIL, CommonPattern.getEmailAddress());
            setPattern(PATTERN.PATTERN_PHONE, Pattern.compile(
                    "^\\+420 ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$" + "|"
                            + "^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$"));            // phone czech | phone en-US
            setPattern(PATTERN.PATTERN_USERNAME, Pattern.compile(".{4,}"));
            setPattern(PATTERN.PATTERN_PASSWORD, Pattern.compile(".{8,}"));
        }

        private void setupResources() {
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_NOT_EMPTY)]
                    = ResourceTable.String_validation_error_empty;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_LENGTH_MIN)]
                    = ResourceTable.String_validation_error_min_length;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_LENGTH_MAX)]
                    = ResourceTable.String_validation_error_max_length;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_LENGTH_RANGE)]
                    = ResourceTable.String_validation_error_range_length;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_LENGTH_EXACT)]
                    = ResourceTable.String_validation_error_exact_length;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_EMAIL)]
                    = ResourceTable.String_validation_error_email;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_PHONE)]
                    = ResourceTable.String_validation_error_phone;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_USERNAME)]
                    = ResourceTable.String_validation_error_username;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_PASSWORD)]
                    = ResourceTable.String_validation_error_password;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_YEARS_OLDER_THAN)]
                    = ResourceTable.String_validation_error_older_than_years;
            mErrorResources[getErrorResNum(ERROR_RES.ERROR_RES_CREDIT_CARD)]
                    = ResourceTable.String_validation_error_credit_card;
        }
    }
}
