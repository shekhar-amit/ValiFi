package com.mlykotom.valifi;

import org.jetbrains.annotations.Nullable;

/**
 * Interface which serves for composite design pattern.
 * This means that form and field may be used just as validable and be validated the same way
 */
public interface ValiFiValidable {
    /**
     * Whether field which implements it is valid.
     */
    boolean isValid();

    void init();

    /**
     * So that form or field can be destroyed the same way.
     */
    void destroy();

    /**
     * Clears the state of the field (e.g. after submit of form).
     * Sets value to null.
     */
    void reset();

    /**
     * If you want to manually show error for the field.
     */
    void validate();

    /**
     * Parent form of this field/form.
     *
     * @param form to be notified when this validable changes
     */
    void setFormValidation(@Nullable ValiFiForm form);

    /**
     * Manually show error on field - set error first + this will notify UI.
     */
    void refreshError();
}
