package com.mlykotom.sample;

import com.mlykotom.sample.slice.LongFormSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class LongForm extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(LongFormSlice.class.getName());
    }
}
