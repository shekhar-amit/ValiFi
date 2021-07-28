package com.mlykotom.valifi.fields;

import com.mlykotom.valifi.ValiFi;
import org.jetbrains.annotations.Nullable;

/**
 * ValiFieldEmail class for email address validation.
 */
public class ValiFieldEmail extends ValiFieldText {
    public ValiFieldEmail() {
        super();
        addEmailValidator(getString(getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_EMAIL)));
    }

    public ValiFieldEmail(@Nullable String defaultValue) {
        super(defaultValue);
        addEmailValidator(getString(getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_EMAIL)));
    }

    public ValiFieldEmail(@Nullable String defaultValue, boolean markAsChanged) {
        super(defaultValue, markAsChanged);
        addEmailValidator(getString(getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_EMAIL)));
    }

    public ValiFieldEmail(int errorResource) {
        super();
        addEmailValidator(getString(errorResource));
    }

    public ValiFieldEmail(@Nullable String defaultValue, int errorResource) {
        super(defaultValue);
        addEmailValidator(getString(errorResource));
    }

    public ValiFieldEmail(@Nullable String defaultValue, int errorResource, boolean markAsChanged) {
        super(defaultValue, markAsChanged);
        addEmailValidator(getString(errorResource));
    }

    public ValiFieldEmail(@Nullable String defaultValue, String errorMessage) {
        super(defaultValue);
        addEmailValidator(errorMessage);
    }

    public ValiFieldEmail(@Nullable String defaultValue, String errorMessage, boolean markAsChanged) {
        super(defaultValue, markAsChanged);
        addEmailValidator(errorMessage);
    }
}