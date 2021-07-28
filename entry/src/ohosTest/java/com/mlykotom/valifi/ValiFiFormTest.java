package com.mlykotom.valifi;

import com.mlykotom.valifi.fields.ValiFieldEmailTest;
import com.mlykotom.valifi.ValiFi;
import com.mlykotom.valifi.ValiFiForm;
import com.mlykotom.valifi.fields.ValiFieldEmail;
import com.mlykotom.valifi.fields.ValiFieldText;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.app.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ValiFiFormTest {

    private Context mContext;
    private ValiFiForm mForm;
    private TextField mTextField, mTextField2;
    private Text mErrorText, mErrorText2;

    @Before
    public void prepare() {
        mContext = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        ValiFi.install(mContext);
        mTextField = new TextField(mContext);
        mErrorText = new Text(mContext);
        mTextField2 = new TextField(mContext);
        mErrorText2 = new Text(mContext);
        mForm = new ValiFiForm();
    }

    @Test
    public void checkFieldsValid() {
        ValiFieldText field1 = new ValiFieldText();
        field1.setEmptyAllowed(true);
        field1.setTextField(mTextField);
        field1.setErrorText(mErrorText);
        field1.init();
        mForm.addField(field1);
        ValiFieldEmail field2 = new ValiFieldEmail();
        field2.setTextField(mTextField2);
        field2.setErrorText(mErrorText2);
        field2.init();
        mForm.addField(field2);
        mTextField2.setText(ValiFieldEmailTest.EMAIL_ADDRESS_VALID);
        assertTrue(mForm.isValid());
    }

    @After
    public void clean() {
        mForm.destroy();
    }

}
