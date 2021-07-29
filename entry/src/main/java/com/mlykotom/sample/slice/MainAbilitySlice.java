package com.mlykotom.sample.slice;

import com.mlykotom.sample.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

/**
 * Main Ability Slice of sample app.
 */
public class MainAbilitySlice extends AbilitySlice {

    Button btn_basic_form;
    Button btn_long_form;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        btn_basic_form = (Button) findComponentById(ResourceTable.Id_btn_basic_form);
        btn_long_form = (Button) findComponentById(ResourceTable.Id_btn_long_form);

        btn_basic_form.setClickedListener(listener -> present(new BasicFormSlice(), new Intent()));
        btn_long_form.setClickedListener(listener -> present(new LongFormSlice(), new Intent()));
    }
}

