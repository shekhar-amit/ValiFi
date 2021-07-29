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
import com.mlykotom.valifi.ValiFiForm;
import com.mlykotom.valifi.fields.ValiFieldCard;
import com.mlykotom.valifi.fields.ValiFieldDate;
import com.mlykotom.valifi.fields.ValiFieldText;
import com.mlykotom.valifi.fields.ValiFieldUsername;
import com.mlykotom.valifi.fields.number.ValiFieldLong;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;

import java.util.regex.Pattern;

public class LongFormSlice extends AbilitySlice {


    TextField tf_date;
    TextField tf_minvalue;
    TextField tf_user;
    TextField tf_rangelen;
    TextField tf_exactlen;
    TextField tf_minlen;
    TextField tf_pattern;
    TextField tf_card;
    Text err_date;
    Text err_minvalue;
    Text err_user;
    Text err_rangelen;
    Text err_exactlen;
    Text err_minlen;
    Text err_pattern;
    Text err_card;
    Button btn_submit2;

    public final ValiFieldDate vf_date = new ValiFieldDate();
    public final ValiFieldLong vf_minvalue = new ValiFieldLong();
    public final ValiFieldUsername vf_username = new ValiFieldUsername();
    public final ValiFieldText vf_rangelen = new ValiFieldText();
    public final ValiFieldText vf_exactlen = new ValiFieldText();
    public final ValiFieldText vf_minlen = new ValiFieldText();
    public final ValiFieldText vf_pattern = new ValiFieldText();
    public final ValiFieldCard vf_card = new ValiFieldCard();
    public final ValiFiForm form = new ValiFiForm(vf_date, vf_minvalue, vf_username, vf_card,
            vf_rangelen, vf_exactlen, vf_minlen, vf_pattern);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_long_form);

        tf_date = (TextField) findComponentById(ResourceTable.Id_tf_date);
        tf_minvalue = (TextField) findComponentById(ResourceTable.Id_tf_minvalue);
        tf_user = (TextField) findComponentById(ResourceTable.Id_tf_user);
        tf_rangelen = (TextField) findComponentById(ResourceTable.Id_tf_rangelen);
        tf_exactlen = (TextField) findComponentById(ResourceTable.Id_tf_exactlen);
        tf_minlen = (TextField) findComponentById(ResourceTable.Id_tf_minlen);
        tf_pattern = (TextField) findComponentById(ResourceTable.Id_tf_pattern);
        tf_card = (TextField) findComponentById(ResourceTable.Id_tf_card);

        err_date = (Text) findComponentById(ResourceTable.Id_err_date);
        err_minvalue = (Text) findComponentById(ResourceTable.Id_err_mivalue);
        err_user = (Text) findComponentById(ResourceTable.Id_err_user);
        err_rangelen = (Text) findComponentById(ResourceTable.Id_err_rangelen);
        err_exactlen = (Text) findComponentById(ResourceTable.Id_err_exactlen);
        err_minlen = (Text) findComponentById(ResourceTable.Id_err_minlen);
        err_pattern = (Text) findComponentById(ResourceTable.Id_err_pattern);
        err_card = (Text) findComponentById(ResourceTable.Id_err_card);

        btn_submit2 = (Button) findComponentById(ResourceTable.Id_btn_submit2);

        vf_date.setTextField(tf_date);
        vf_minvalue.setTextField(tf_minvalue);
        vf_username.setTextField(tf_user);
        vf_rangelen.setTextField(tf_rangelen);
        vf_exactlen.setTextField(tf_exactlen);
        vf_minlen.setTextField(tf_minlen);
        vf_pattern.setTextField(tf_pattern);
        vf_card.setTextField(tf_card);

        vf_date.setErrorText(err_date);
        vf_minvalue.setErrorText(err_minvalue);
        vf_username.setErrorText(err_user);
        vf_rangelen.setErrorText(err_rangelen);
        vf_exactlen.setErrorText(err_exactlen);
        vf_minlen.setErrorText(err_minlen);
        vf_pattern.setErrorText(err_pattern);
        vf_card.setErrorText(err_card);

        vf_date.addOlderThanYearsValidator(1);
        vf_rangelen.addRangeLengthValidator(2, 4);
        vf_exactlen.addExactLengthValidator(3);
        vf_minlen.addMinLengthValidator(3);
        Pattern test_pattern = Pattern.compile(".{5,}");
        vf_pattern.addPatternValidator("Text should be atleast 5 characters long", test_pattern);

        form.init();

        btn_submit2.setClickedListener(component -> showToast(form.isValid() ? "This field is valid" : "This field is not valid"));

        setupNumberValidator();
    }

    private void setupNumberValidator() {
        final long requiredMinNumber = 13;
        vf_minvalue.addNumberValidator("This number must be greater than 13", value -> {
            if (value == null) {
                return false;
            }
            return value > requiredMinNumber;
        });
    }

    private void showToast(String msg) {
        new ToastDialog(this).setDuration(1000).setText(msg).show();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
