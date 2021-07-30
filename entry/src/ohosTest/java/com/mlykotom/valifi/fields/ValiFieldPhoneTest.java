package com.mlykotom.valifi.fields;

import com.mlykotom.valifi.ValiFi;
import com.mlykotom.valifi.fields.ValiFieldPhone;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValiFieldPhoneTest {

    private Context mContext;
    private ValiFieldPhone mField;
    private TextField mTextField;
    private Text mErrorText;
    public static final String PHONE_NUMBER_VALID = "9876543210";
    public static final String PHONE_NUMBER_INVALID = "123";
    public static final String FIELD_ERROR_MSG = "field is not valid";

    @Before
    public void prepare() {
        mContext = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        ValiFi.install(mContext);
        mTextField = new TextField(mContext);
        mErrorText = new Text(mContext);
        mField = new ValiFieldPhone(null, FIELD_ERROR_MSG);
        mField.setTextField(mTextField);
        mField.setErrorText(mErrorText);
        mField.init();
    }

    @Test
    public void checkConstructWithValidPhone() {
        mTextField.setText(PHONE_NUMBER_VALID);
        assertTrue(mField.isValid());
    }

    @Test
    public void checkConstructWithInvalidPhone() {
        mTextField.setText(PHONE_NUMBER_INVALID);
        assertFalse(mField.isValid());
        assertEquals(FIELD_ERROR_MSG, mErrorText.getText());
    }
}
