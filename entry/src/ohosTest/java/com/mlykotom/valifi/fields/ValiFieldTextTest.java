package com.mlykotom.valifi.fields;

import com.mlykotom.valifi.ValiFi;
import com.mlykotom.valifi.exceptions.ValiFiValidatorException;
import com.mlykotom.valifi.fields.ValiFieldText;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValiFieldTextTest {

    private Context mContext;
    private ValiFieldText mField;
    private TextField mTextField;
    private Text mErrorText;
    private static final String VALIDATOR_EMPTY_MESSAGE = "field can't be empty";
    private static final String TEST_STRING3 = "123";
    private static final String TEST_STRING4 = "1234";
    private static final String TEST_STRING5 = "12345";

    @Before
    public void prepare() {
        mContext = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        ValiFi.install(mContext);
        mTextField = new TextField(mContext);
        mErrorText = new Text(mContext);
        mField = new ValiFieldText();
        mField.setTextField(mTextField);
        mField.setErrorText(mErrorText);
        mField.init();
    }

    @Test(expected = ValiFiValidatorException.class)
    public void checkEmptyOrNotEmpty() {
        mField.setEmptyAllowed(true);
        mField.addNotEmptyValidator(VALIDATOR_EMPTY_MESSAGE);
    }

    @Test(expected = ValiFiValidatorException.class)
    public void checkEmptyOrNotEmptyInverse() {
        mField.addNotEmptyValidator(VALIDATOR_EMPTY_MESSAGE);
        mField.setEmptyAllowed(true);
    }

    @Test
    public void checkNotEmptyWhenNull() {
        mField.addNotEmptyValidator(VALIDATOR_EMPTY_MESSAGE);
        mTextField.setText(null);
        assertFalse(mField.isValid());
    }

    @Test
    public void checkNotEmpty() {
        mField.addNotEmptyValidator(VALIDATOR_EMPTY_MESSAGE);
        mTextField.setText("");
        assertFalse(mField.isValid());
    }

    // ------------------ MIN LENGTH VALIDATOR ------------------ //

    @Test
    public void checkMinLengthInvalid() {
        mTextField.setText(TEST_STRING3);
        mField.addMinLengthValidator("must be longer than 4 characters", 4);
        assertFalse(mField.isValid());
    }

    @Test
    public void checkMinLengthValid() {
        mTextField.setText(TEST_STRING4);
        mField.addMinLengthValidator("must be longer than 4 characters", 4);
        assertTrue(mField.isValid());
    }

    // ------------------ EXACT LENGTH VALIDATOR ------------------ //

    @Test
    public void checkExactLength5Invalid() {
        mTextField.setText(TEST_STRING4);
        mField.addExactLengthValidator("must be exactly 5 characters long", 5);
        assertFalse(mField.isValid());
    }

    @Test
    public void checkExactLength5Valid() {
        mTextField.setText(TEST_STRING5);
        mField.addExactLengthValidator("must be exactly 5 characters long", 5);
        assertTrue(mField.isValid());
    }

    // ------------------ MAX LENGTH VALIDATOR ------------------ //

    @Test
    public void checkMaxLengthInvalid() {
        mTextField.setText(TEST_STRING4);
        mField.addMaxLengthValidator("must be shorter than 4 characters", 4);
        assertTrue(mField.isValid());
    }

    @Test
    public void checkMaxLengthValid() {
        mTextField.setText(TEST_STRING5);
        mField.addMaxLengthValidator("must be shorter than 4 characters", 4);
        assertFalse(mField.isValid());
    }


    // ------------------ RANGE VALIDATOR ------------------ //

    @Test
    public void checkRangeLengthMin4Max6Invalid7() {
        mTextField.setText("1234567");
        mField.addRangeLengthValidator("must be between 4 and 6", 4, 6);
        assertFalse(mField.isValid());
    }

    @Test
    public void checkRangeLengthMin4Max6Valid5() {
        mTextField.setText(TEST_STRING5);
        mField.addRangeLengthValidator("must be between 4 and 6", 4, 6);
        assertTrue(mField.isValid());
    }
}
