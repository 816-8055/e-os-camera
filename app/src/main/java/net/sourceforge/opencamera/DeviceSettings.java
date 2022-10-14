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

import android.os.Build;

import java.util.Locale;

public class DeviceSettings {

    /** Returns whether the the device uses fake flash in Camera API2.
     *  Some older device have issues in low light.
     */
    public static boolean deviceUsingFakeFlash() {
        final boolean is_xiaomi = Build.MANUFACTURER.toLowerCase(Locale.US).contains("xiaomi");
        final boolean is_fairphone = Build.MANUFACTURER.toLowerCase(Locale.US).contains("fairphone");
        final boolean is_GS290 = Build.DEVICE != null && Build.DEVICE.equals("GS290");
        return is_fairphone || is_xiaomi || is_GS290;
    }
}
