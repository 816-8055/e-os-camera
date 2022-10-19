package net.sourceforge.opencamera.camera2

import android.hardware.camera2.CameraCharacteristics
import net.sourceforge.opencamera.camera2.model.CameraModel

interface CameraIDs {
    interface Finder<T : Iterable<CameraModel>> {
        fun init()
        fun getCameraCharacteristics(cameraId: Int): CameraCharacteristics?
        val cameraModels: T
        val apiCameraIdList: List<String>
        val allCameraIdList: List<String>
    }

    interface Identifier<T : Iterable<CameraModel>> {
        fun init()
        fun identifyCamera(cameraModels: T)
    }
}
