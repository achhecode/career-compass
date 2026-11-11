package com.achhecode.careercompass.util;

import com.achhecode.careercompass.constant.AppConstants;

import java.util.Locale;

public final class MoneyUtil {

    private MoneyUtil() {
    }

    public static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /** 1 LPA for one year = 1 lakh rupees, so LPA-years == lakh rupees. */
    public static String readable(double lakh) {
        if (Math.abs(lakh) >= AppConstants.LAKH_PER_CRORE) {
            return String.format(Locale.US, "Rs. %.2f crore", lakh / AppConstants.LAKH_PER_CRORE);
        }
        return String.format(Locale.US, "Rs. %.2f lakh", lakh);
    }
}
