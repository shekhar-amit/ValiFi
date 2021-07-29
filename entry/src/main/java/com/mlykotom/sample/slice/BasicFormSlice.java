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
import com.mlykotom.valifi.fields.*;
import com.mlykotom.valifi.fields.number.ValiFieldLong;
import com.mlykotom.valifi.fields.number.ValiFieldNumber;
import java.util.regex.Pattern;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * Test Ability Slice of sample app.
 */
public class BasicFormSlice extends AbilitySlice {

    TextField tf_email;
    TextField tf_password;
    TextField tf_password2;
    TextField tf_phone;
    Text err_email;
    Text err_password;
    Text err_password2;
    Text err_phone;
    Button btn_submit;

    public final ValiFieldEmail email = new ValiFieldEmail();
    public final ValiFieldPassword password = new ValiFieldPassword();
    public final ValiFieldPassword password2 = new ValiFieldPassword();
    public final ValiFieldPhone phone = new ValiFieldPhone();

    public final ValiFiForm passwordForm = new ValiFiForm(password, password2);
    public final ValiFiForm form = new ValiFiForm(email, passwordForm, phone);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_basic_form);

        phone.setEmptyAllowed(true);

        tf_email = (TextField) findComponentById(ResourceTable.Id_tf_email);
        tf_password = (TextField) findComponentById(ResourceTable.Id_tf_password);
        tf_password2 = (TextField) findComponentById(ResourceTable.Id_tf_password2);
        tf_phone = (TextField) findComponentById(ResourceTable.Id_tf_phone);

        err_email = (Text) findComponentById(ResourceTable.Id_err_email);
        err_password = (Text) findComponentById(ResourceTable.Id_err_password);
        err_password2 = (Text) findComponentById(ResourceTable.Id_err_password2);
        err_phone = (Text) findComponentById(ResourceTable.Id_err_phone);

        btn_submit = (Button) findComponentById(ResourceTable.Id_btn_submit);

        password2.addVerifyFieldValidator("Passwords must be the same", password);

        email.setTextField(tf_email);
        password.setTextField(tf_password);
        password2.setTextField(tf_password2);
        phone.setTextField(tf_phone);

        email.setErrorText(err_email);
        password.setErrorText(err_password);
        password2.setErrorText(err_password2);
        phone.setErrorText(err_phone);

        form.init();

        btn_submit.setClickedListener(component -> showToast(form.isValid() ? "This field is valid" : "This field is not valid"));
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
