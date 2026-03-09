/*
 * SPDX-FileCopyrightText: 2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package net.sourceforge.opencamera.qr.ext

import android.content.res.Resources.getSystem
import kotlin.math.roundToInt

val Int.px
    get() = (this * getSystem().displayMetrics.density).roundToInt()
