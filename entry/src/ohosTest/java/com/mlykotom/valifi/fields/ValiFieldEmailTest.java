package com.mlykotom.valifi.fields;

import com.mlykotom.valifi.ValiFi;
import com.mlykotom.valifi.fields.ValiFieldEmail;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValiFieldEmailTest {

    private Context mContext;
    private ValiFieldEmail mField;
    private TextField mTextField;
    private Text mErrorText;
    public static final String EMAIL_ADDRESS_VALID = "test@email.com";
    public static final String EMAIL_ADDRESS_INVALID = "this_is_not_ok";
    public static final String FIELD_ERROR_MSG = "field is not valid";

    @Before
    public void prepare() {
        mContext = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        ValiFi.install(mContext);
        mTextField = new TextField(mContext);
        mErrorText = new Text(mContext);
        mField = new ValiFieldEmail(null, FIELD_ERROR_MSG);
        mField.setTextField(mTextField);
        mField.setErrorText(mErrorText);
        mField.init();
    }

    @Test
    public void checkConstructWithValidEmail() {
        mTextField.setText(EMAIL_ADDRESS_VALID);
        assertTrue(mField.isValid());
    }

    @Test
    public void checkConstructWithInvalidEmail() {
        mTextField.setText(EMAIL_ADDRESS_INVALID);
        assertFalse(mField.isValid());
        assertEquals(FIELD_ERROR_MSG, mErrorText.getText());
    }
}
