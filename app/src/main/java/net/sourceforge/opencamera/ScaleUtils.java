package net.sourceforge.opencamera;

import android.content.Context;

public class ScaleUtils {

    private ScaleUtils() {
    }

    public static float convertPxToDp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
