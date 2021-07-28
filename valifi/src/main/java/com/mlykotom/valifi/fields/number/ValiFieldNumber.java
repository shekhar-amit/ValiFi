package com.mlykotom.valifi.fields.number;

import com.mlykotom.valifi.fields.ValiFieldText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ValiFieldNumber abstract class to parse various number types.
 */
public abstract class ValiFieldNumber<NumberType extends Number & Comparable<NumberType>> extends ValiFieldText {

    /**
     * NumberValidator interface.
     */
    public interface NumberValidator<T> {
        boolean isValid(@NotNull T value);
    }

    protected ValiFieldNumber() {
        super();
    }

    protected ValiFieldNumber(@Nullable NumberType defaultValue) {
        super(getStringOrNull(defaultValue));
    }

    /**
     * Helper for returning either string version or @null.
     *
     * @param value anything which can be made toString
     * @return string or null (because .toString() represents null as "null" string)
     */
    @Nullable
    protected static String getStringOrNull(Object value) {
        return value != null ? value.toString() : null;
    }

    /**
     * Parses the number from string. If not possible, throws exception which will be marked as invalid validation.
     *
     * @param value to be parsed (actual value in field)
     * @return parsed value (e.g Long)
     * @throws NumberFormatException will be thrown if number can't be parsed
     */
    protected abstract NumberType parse(@Nullable String value) throws NumberFormatException;

    /**
     * Tries to parse value to number, if not possible returns @null.
     */
    @Nullable
    public NumberType getNumber() {
        try {
            return parse(get());
        } catch (NumberFormatException exc) {
            return null;
        }
    }

    /**
     * Sets the value of the number.
     *
     * @param value which will be set
     */
    public void setNumber(@Nullable NumberType value) {
        set(getStringOrNull(value));
    }

    public ValiFieldNumber<NumberType> addNumberValidator(@Nullable NumberValidator<NumberType> validator) {
        return addNumberValidator(null, validator);
    }

    public ValiFieldNumber<NumberType> addNumberValidator(int errorResource,
                                                          @Nullable NumberValidator<NumberType> validator) {
        String errorMessage = getString(errorResource);
        return addNumberValidator(errorMessage, validator);
    }

    /**
     * Adds custom validator which first tries to parse number from string and later applies validation.
     * This is similar as {@link #addCustomValidator(String, PropertyValidator)} but adds parsing to number.
     *
     * @param errorMessage to be shown if field does not meet this validation
     * @param validator implementation of validation (with number)
     * @return this, so validators can be chained
     */
    public ValiFieldNumber<NumberType> addNumberValidator(String errorMessage,
                                                          @Nullable final NumberValidator<NumberType> validator) {
        addCustomValidator(errorMessage, value -> {
            try {
                // 1) first tried to parse the value
                NumberType numberValue = parse(value);
                // 2) applies validator
                return validator == null || validator.isValid(numberValue);
            } catch (NumberFormatException ignored) {
                return false;
            }
        });

        return this;
    }
}