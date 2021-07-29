package com.mlykotom.valifi.fields;

import com.mlykotom.valifi.ValiFi;
import com.mlykotom.valifi.ValiFieldBase;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Calendar;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ValiFieldDate class for date validation.
 */
public class ValiFieldDate extends ValiFieldBase<Calendar> {

    static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MY_TAG");

    public ValiFieldDate() {
        super();
    }

    /**
     * Checking for specific type if value is empty.
     * Used for checking if empty is allowed.
     *
     * @param actualValue value when checking
     * @return true when value is empty, false when values is not empty (e.g for String, use isEmpty())
     */
    @Override
    protected boolean whenThisFieldIsEmpty(@NotNull Calendar actualValue) {
        return !actualValue.isSet(Calendar.YEAR);
    }

    @Override
    protected String convertValueToString(@NotNull Calendar value) {
        return DateFormat.getDateInstance().format(value.getTime());
    }


    @Override
    @Nullable
    protected Calendar convertStringToValue(@Nullable String value) {
        if (value == null) {
            return null;
        }
        String[] arrval = value.split("[-/]");
        if (arrval.length != 3) {
            return null;
        }
        int yr = Integer.parseInt(arrval[2]);
        int mo = Integer.parseInt(arrval[1]);;
        int day = Integer.parseInt(arrval[0]);;
        Calendar cal = null;
        try {
            cal = new Calendar.Builder().setDate(yr, mo, day).build();
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            HiLog.error(LABEL, e.toString());
        }
        return cal;
    }


    // ------------------ OLDER THAN VALIDATOR ------------------ //

    public ValiFieldDate addOlderThanYearsValidator(int amount) {
        return addOlderThanValidator(
                getErrorRes(ValiFi.Builder.ERROR_RES.ERROR_RES_YEARS_OLDER_THAN), Calendar.YEAR, amount);
    }

    public ValiFieldDate addOlderThanValidator(int errorResource, int calendarField, int amount) {
        String errorMessage = getString(errorResource, amount);
        return addOlderThanValidator(errorMessage, calendarField, amount);
    }

    /**
     * Older than date validator.
     *
     * @param errorMessage error message string
     * @param calendarField calendar
     * @param amount amount
     * @return ValiFieldDate validator
     */
    public ValiFieldDate addOlderThanValidator(String errorMessage, int calendarField, int amount) {
        final Calendar wantedDate = Calendar.getInstance();
        wantedDate.add(calendarField, -amount);

        addCustomValidator(errorMessage, value -> value != null && value.compareTo(wantedDate) < 0);

        return this;
    }
}

