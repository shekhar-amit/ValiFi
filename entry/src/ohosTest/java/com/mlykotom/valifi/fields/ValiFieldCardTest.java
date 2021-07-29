package com.mlykotom.valifi.fields;

import com.mlykotom.valifi.ValiFi;
import com.mlykotom.valifi.fields.ValiFieldCard;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ValiFieldCardTest {
    public static final String VALIDATOR_EMPTY_MESSAGE = "Credit card is not valid";
    private final String inputCard;
    private ValiFieldCard mField;
    private TextField mTextField;
    private Text mErrorText;
    public ValiFieldCardTest(String input) {
        this.inputCard = input;
    }

    @Parameterized.Parameters
    public static Collection<String> creditCards() {
        return Arrays.asList(
                "4242424242424242",
                "4012888888881881",
                "4000056655665556",
                "5555555555554444",
                "5200828282828210",
                "5105105105105100",
                "378282246310005",
                "371449635398431",
                "6011111111111117",
                "6011000990139424",
                "30569309025904",
                "38520000023237",
                "3530111333300000",
                "3566002020360505"
        );
    }

    @Before
    public void prepare() {
        Context mContext = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        ValiFi.install(mContext);
        mField = new ValiFieldCard(0L, VALIDATOR_EMPTY_MESSAGE);
        mTextField = new TextField(mContext);
        mErrorText = new Text(mContext);
        mField.setTextField(mTextField);
        mField.setErrorText(mErrorText);
        mField.init();
    }

    @Test
    public void checkLuhnTestForCard() {
        assertTrue(ValiFieldCard.isLuhnTestValid(inputCard));
    }

    @Test
    public void checkLuhnTestWrongString() {
        assertFalse(ValiFieldCard.isLuhnTestValid("424242424242424x"));
        assertFalse(ValiFieldCard.isLuhnTestValid("assdfg123sf"));
    }

    @Test
    public void checkDefaultCardTypes() {
        mTextField.setText(inputCard);
        assertTrue(mField.isValid());
    }
}
