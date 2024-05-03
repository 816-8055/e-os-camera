package net.sourceforge.opencamera.qr

import android.os.Bundle
import net.sourceforge.opencamera.MainActivity

class QrScannerActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preview.setQRCode(true)
    }
}
