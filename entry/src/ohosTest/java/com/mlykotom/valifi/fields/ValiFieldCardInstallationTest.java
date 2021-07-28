package com.mlykotom.valifi.fields;

import com.mlykotom.valifi.ValiFi;
import com.mlykotom.valifi.ValiFiCardType;
import com.mlykotom.valifi.fields.ValiFieldCard;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.app.Context;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValiFieldCardInstallationTest {

    private Context mContext;
    private ValiFieldCard mField;
    private TextField mTextField;
    private Text mErrorText;

    public void prepare(boolean mDefault) {
        mContext = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        if (mDefault) {
            ValiFi.install(mContext, new ValiFi.Builder()
                    .setKnownCardTypes() //no default known types
                    .build());
        } else {
            ValiFi.install(mContext, new ValiFi.Builder()
                    .setKnownCardTypes(new ValiFiCardType("Visa Custom", "^4[0-9]{6,}$")) //no default known types
                    .build());
        }
        mTextField = new TextField(mContext);
        mErrorText = new Text(mContext);
        mField = new ValiFieldCard(null, ValiFieldCardTest.VALIDATOR_EMPTY_MESSAGE);
        mField.setTextField(mTextField);
        mField.setErrorText(mErrorText);
        mField.init();
    }

    @Test
    public void checkCardOnlyLuhn() {
        prepare(true);
        mTextField.setText("4265773072920733");
        assertThat(mField.isValid(), is(true));
    }

    @Test
    public void checkCardOnlyLuhnFailed() {
        prepare(true);
        mTextField.setText("4265773020733");
        assertThat(mField.isValid(), is(false));
    }

    @Test
    public void checkCustomCard() {
        prepare(false);
        mTextField.setText("4265773072920733");
        assertThat(mField.isValid(), is(true));
    }
}
