package com.mlykotom.valifi;

import com.mlykotom.valifi.ValiFi;
import com.mlykotom.valifi.ValiFieldBase;
import com.mlykotom.valifi.fields.ValiFieldText;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.app.Context;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValiFieldBaseTest {

    private Context mContext;
    private TextField mTextField, mTextField2;
    private Text mText, mText2;

    @Before
    public void prepare() {
        mContext = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        ValiFi.install(mContext);
        mTextField = new TextField(mContext);
        mTextField2 = new TextField(mContext);
        mText = new Text(mContext);
        mText2 = new Text(mContext);
    }

    @Test
    public void setGetIsCorrect() {
        ValiFieldBase<String> field = new ValiFieldText();
        field.setTextField(mTextField);
        field.setErrorText(mText);
        field.init();
        mTextField.setText("test_value");
        assertEquals("test_value", field.get());
        assertTrue(field.isValid());
    }

    @Test
    public void checkBoundFields() {
        ValiFieldBase<String> field = new ValiFieldText();
        ValiFieldBase<String> boundField = new ValiFieldText();
        field.addVerifyFieldValidator("fields must be same", boundField);
        field.setTextField(mTextField);
        field.setErrorText(mText);
        field.init();
        boundField.setTextField(mTextField2);
        boundField.setErrorText(mText2);
        boundField.init();
        mTextField.setText("val_2");
        mTextField2.setText("val_1");
        assertFalse(field.isValid());
        mTextField.setText("val_1");
        assertTrue(field.isValid());
    }

    @Test
    public void checkAddCustomValidator1Invalid() {
        ValiFieldBase<String> field = new ValiFieldText();
        field.addCustomValidator(new ValiFieldBase.PropertyValidator<String>() {
            @Override
            public boolean isValid(@Nullable String value) {
                return false;
            }
        });
        field.setTextField(mTextField);
        field.setErrorText(mText);
        field.init();
        assertFalse(field.isValid());
    }

    @Test
    public void checkReset() {
        ValiFieldText field = new ValiFieldText();
        field.addExactLengthValidator("invalid_length_3", 3);
        field.setTextField(mTextField);
        field.setErrorText(mText);
        field.init();
        mTextField.setText("1");
        assertNotEquals("",mText.getText());
        assertFalse(field.isValid());

        mTextField.setText("123");
        assertEquals("",mText.getText());
        assertTrue(field.isValid());

        field.reset();

        assertNotEquals("",mText.getText());
        assertFalse(field.isValid());
    }
}
