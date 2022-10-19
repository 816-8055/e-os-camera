package net.sourceforge.opencamera.camera2

import net.sourceforge.opencamera.camera2.model.CameraModel

abstract class CameraIdentifierAbstract<T : Iterable<CameraModel>>(
    protected val cameraModels: T
) : CameraIDs.Identifier<T>
