package com.mlykotom.valifi;

import com.mlykotom.valifi.ValiFi;
import com.mlykotom.valifi.ValiFieldBase;
import com.mlykotom.valifi.fields.ValiFieldText;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValiFiTest {
    public static final String FIELD_ERROR_MSG = "field is not valid";
    private ValiFieldBase<String> mField;

    /**
     * WARNING: installing without context will crash when getting string, only for tests!
     */
    public static void installWithoutContext() {
        ValiFi.install();
    }

    @Before
    public void prepareField() {
        installWithoutContext();
        mField = new ValiFieldText();
    }

    @Test
    public void checkLibraryInstalled() {
        assertNotNull(ValiFi.getInstance());
    }

    @Test
    public void checkLibraryInstalledByAddingValidator() {
        try {
            mField.addCustomValidator(ResourceTable.String_validation_error_email, new ValiFieldBase.PropertyValidator<String>() {
                @Override
                public boolean isValid(@Nullable String value) {
                    // it doesn't matter if it's valid, checking installation
                    return false;
                }
            });
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void checkErrorResourcesMatchLength() {
        int errorResArrCount = ValiFi.getInstance().mParameters.mErrorResources.length;
        assertEquals(errorResArrCount, (ValiFi.Builder.ERROR_RES_COUNT));
    }

    @Test
    public void checkPatternsMatchLength() {
        int patternsArrCount = ValiFi.getInstance().mParameters.mValidators.length;
        assertEquals(patternsArrCount, (ValiFi.Builder.PATTERN_COUNT));
    }

    @Test
    public void checkErrorResourcesMathStrings() {
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_NOT_EMPTY), (ResourceTable.String_validation_error_empty));
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_LENGTH_MIN), (ResourceTable.String_validation_error_min_length));
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_LENGTH_MAX), (ResourceTable.String_validation_error_max_length));
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_LENGTH_RANGE), (ResourceTable.String_validation_error_range_length));
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_LENGTH_EXACT), (ResourceTable.String_validation_error_exact_length));
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_EMAIL), (ResourceTable.String_validation_error_email));
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_PHONE), (ResourceTable.String_validation_error_phone));
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_USERNAME), (ResourceTable.String_validation_error_username));
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_PASSWORD), (ResourceTable.String_validation_error_password));
        assertEquals(mField.getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_YEARS_OLDER_THAN), (ResourceTable.String_validation_error_older_than_years));
    }
}
