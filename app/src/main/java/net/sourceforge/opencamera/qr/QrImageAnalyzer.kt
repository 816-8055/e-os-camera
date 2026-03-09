/*
 * SPDX-FileCopyrightText: 2022-2025 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package net.sourceforge.opencamera.qr

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.sourceforge.opencamera.MyDebug
import net.sourceforge.opencamera.qr.models.QrResult
import zxingcpp.BarcodeReader

class QrImageAnalyzer(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
) : ImageAnalysis.Analyzer {
    fun interface QrResultListener {
        fun onQrResult(qrResult: QrResult)
    }

    private val tag = "QrImageAnalyzer"

    // QR
    private val reader by lazy {
        BarcodeReader().apply {
            options.tryInvert = true
            options.tryRotate = true
        }
    }

    private val qrTextClassifier by lazy { QrTextClassifier(context) }

    private val _qrResult = MutableStateFlow<QrResult?>(null)
    val qrResult = _qrResult.filterNotNull()
    @Volatile
    private var qrResultListener: QrResultListener? = null

    fun isQrDialogShowing(): Boolean {
        return _qrResult.value != null
    }

    override fun analyze(image: ImageProxy) {
        image.use {
            if (_qrResult.value != null) {
                return
            }

            processResults(reader.read(image))
        }
    }

    fun readImage(bitmap: Bitmap, rotation: Int) {
        try {
            // We want to scan the full bitmap
            val results: List<BarcodeReader.Result> = reader.read(
                bitmap, Rect(0, 0, bitmap.width, bitmap.height), rotation
            )

            if (MyDebug.LOG) {
                Log.d(tag, "Find QRCode qrcodeContent = $results")
            }

            results.firstOrNull()?.let {
                processResults(results)
            }
        } catch (ignored: Exception) { }
    }

    fun dismissResult() {
        _qrResult.value = null
    }

    fun setQrResultListener(listener: QrResultListener?) {
        qrResultListener = listener
    }

    private fun processResults(results: List<BarcodeReader.Result>) {
        results.firstOrNull()?.let { result ->
            coroutineScope.launch(Dispatchers.IO) {
                // Classify message
                val qrResult = qrTextClassifier.classify(result)

                _qrResult.emit(qrResult)
                withContext(Dispatchers.Main) {
                    qrResultListener?.onQrResult(qrResult)
                }
            }
        }
    }
}
