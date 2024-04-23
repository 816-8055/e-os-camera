/*
 * Copyright MURENA SAS 2024
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
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.sourceforge.opencamera.preview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.core.content.ContextCompat;

import net.sourceforge.opencamera.R;

public class OverlayQRCodeView extends View {
    private Drawable qrcode;
    // Do not draw the additional resource if something goes wrong
    private boolean isValid = true;

    public OverlayQRCodeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        qrcode = ContextCompat.getDrawable(this.getContext(), R.drawable.scan_area);
        DisplayMetrics displayMetrics =this.getContext().getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;

        final int left = (screenWidth - qrcode.getIntrinsicWidth()) / 2;
        final int top = (screenHeight - qrcode.getIntrinsicHeight()) / 2;
        final int right = left + qrcode.getIntrinsicWidth();
        final int bottom = top + qrcode.getIntrinsicHeight();

        if (left <= 0 || top <= 0 || right <= 0 || bottom <= 0) {
            isValid = false;
        } else {
            qrcode.setBounds(left, top, right, bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isValid) {
            qrcode.draw(canvas);
        }
    }
}
