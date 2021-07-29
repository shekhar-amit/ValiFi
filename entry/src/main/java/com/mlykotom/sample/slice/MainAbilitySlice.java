/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

