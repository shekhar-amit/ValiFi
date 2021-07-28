package com.mlykotom.valifi.fields.number;

import org.jetbrains.annotations.Nullable;

/**
 * ValiFieldDouble class to parse double.
 */
public class ValiFieldDouble extends ValiFieldNumber<Double> {
    public ValiFieldDouble() {
        super();
    }

    public ValiFieldDouble(@Nullable Double defaultValue) {
        super(defaultValue);
    }

    @Override
    protected Double parse(@Nullable String value) throws NumberFormatException {
        return Double.parseDouble(value);
    }
}