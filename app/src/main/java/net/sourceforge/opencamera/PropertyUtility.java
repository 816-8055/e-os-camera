/*
 * Copyright (C) 2022  E FOUNDATION
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.sourceforge.opencamera;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;

public class PropertyUtility {

    private static final String TAG = PropertyUtility.class.getName();

    /**
     * Retrieve the property value from systemProperties
     * @param key the property key
     * @return value of the property, null if the value is not present
     */
    @Nullable
    public static String getProperty(String key) {
        String value = null;
        try {
            @SuppressLint("PrivateApi") Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);

            value = (String) get.invoke(c, key);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return value;
    }

    private PropertyUtility() {
    }
}
