package com.mlykotom.sample;

import com.mlykotom.valifi.ValiFi;
import ohos.aafwk.ability.AbilityPackage;

/**
 * Main application class of sample app.
 */
public class MyApplication extends AbilityPackage {
    @Override
    public void onInitialize() {
        super.onInitialize();
        ValiFi.install(getContext());
    }
}
