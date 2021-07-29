package com.mlykotom.sample;

import com.mlykotom.sample.slice.BasicFormSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * Test Ability of sample app.
 */
public class BasicForm extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(BasicFormSlice.class.getName());
    }
}
