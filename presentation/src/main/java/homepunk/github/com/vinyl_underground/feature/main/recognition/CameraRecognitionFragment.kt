package homepunk.github.com.vinyl_underground.feature.main.recognition

import android.annotation.SuppressLint
import android.hardware.camera2.*
import android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
import android.hardware.camera2.params.StreamConfigurationMap
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.util.SparseIntArray
import android.view.*
import com.tbruyelle.rxpermissions2.RxPermissions
import homepunk.github.com.vinyl_underground.R
import homepunk.github.com.vinyl_underground.base.BaseFragment
import homepunk.github.com.vinyl_underground.databinding.FragmentCameraRecognitionBinding
import timber.log.Timber
import java.util.*

class CameraRecognitionFragment : BaseFragment<CameraRecognitionFragmentViewModel, FragmentCameraRecognitionBinding>() {
    private val orientations = SparseIntArray()
    private var cameraId: String? = null

    private var previewCaptureRequest: CaptureRequest? = null
    private var previewCaptureRequestBuilder: CaptureRequest.Builder? = null

    private var cameraCaptureSession: CameraCaptureSession? = null

    private var cameraDevice: CameraDevice? = null
    private var cameraManager: CameraManager? = null

    private var surfaceView: SurfaceView? = null

    private var cameraBackgroundHandler: Handler? = null
    private var cameraBackgroundHandlerThread: HandlerThread? = null
    private var mPreviewSize: Size? = null

    private lateinit var rxPermissions: RxPermissions

    override fun getLayoutResId() = R.layout.fragment_camera_recognition

    override fun createViewModel() = CameraRecognitionFragmentViewModel(context!!)

    override fun onPreInflate() {
//        activity!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        activity!!.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun init() {
        orientations.append(Surface.ROTATION_0, 0)
        orientations.append(Surface.ROTATION_90, 90)
        orientations.append(Surface.ROTATION_180, 180)
        orientations.append(Surface.ROTATION_270, 270)

        rxPermissions = RxPermissions(activity!!)
    }

    override fun onRestart() {

    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()
        startCameraBackgroundThread()
        rxPermissions.request(android.Manifest.permission.CAMERA)
                .subscribe { isGranted ->
                    if (isGranted!!) {
                        mDataBinding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
                            override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
                                Timber.i("surfaceCreated")

                            }

                            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                                Timber.i("surfaceChanged w: $width h: $height")
                                val frame = holder.surfaceFrame
                                Timber.i("size of frame w: " + frame.width() + " h: " + frame.height())
                                setupCamera(width, height)
                                openCamera()
                            }

                            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
                                Timber.i("surfaceDestroyed")
                            }
                        })

                    }
                }
    }

    override fun onPause() {
        closeCamera()
        stopCameraBackgroundThread()
        super.onPause()
    }

    @SuppressLint("NewApi", "MissingPermission")
    private fun openCamera() {
        try {
            cameraManager?.openCamera(cameraId!!, object : CameraDevice.StateCallback() {
                @SuppressLint("NewApi")
                override fun onOpened(cameraDevice: CameraDevice) {
                    this@CameraRecognitionFragment.cameraDevice = cameraDevice
                    Timber.i("CameraDevice.StateCallback onOpened " + cameraDevice.id)
                    createCameraPreviewSession()
                }

                override fun onDisconnected(cameraDevice: CameraDevice) {
                    Timber.i("CameraDevice.StateCallback onDisconnected")
                    cameraDevice.close()
                    this@CameraRecognitionFragment.cameraDevice = null
                }

                override fun onError(cameraDevice: CameraDevice, i: Int) {
                    Timber.i("CameraDevice.StateCallback onError")
                    cameraDevice.close()
                    this@CameraRecognitionFragment.cameraDevice = null
                }
            }, cameraBackgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

    private fun closeCamera() {
        cameraDevice?.close()
        cameraDevice = null
    }

    @SuppressLint("NewApi")
    private fun setupCamera(width: Int, height: Int) {
        try {
            cameraManager?.let {
                for (cameraId in it.cameraIdList) {
                    val characteristics = it.getCameraCharacteristics(cameraId)
                    // We don't use a front facing camera
                    if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                        continue
                    }
                    val map = characteristics.get<StreamConfigurationMap>(SCALER_STREAM_CONFIGURATION_MAP)
                    val sizes = map!!.getOutputSizes(SurfaceHolder::class.java)

                    val displayRotation = activity!!.windowManager.defaultDisplay.rotation
                    val totalRotation = getDeviceRottation(characteristics, displayRotation)
                    val swapRotation = totalRotation == 90 || totalRotation == 270
                    var rotatedWidth = width
                    var rotatedHeight = height
                    if (mPreviewSize == null && swapRotation) {
                        rotatedWidth = height
                        rotatedHeight = width
                    }

                    Timber.i("w: $rotatedWidth h: $rotatedHeight")
                    mPreviewSize = chooseBigEnough(sizes, rotatedWidth, rotatedHeight)
                    surfaceView?.holder?.setFixedSize(mPreviewSize!!.width, mPreviewSize!!.height)

                    this.cameraId = cameraId
                    return
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

    @SuppressLint("NewApi")
    private fun createCameraPreviewSession() {
        try {
            surfaceView?.holder?.surface?.let { surface ->
                previewCaptureRequestBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                previewCaptureRequestBuilder!!.addTarget(surface)
                cameraDevice?.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        if (cameraDevice == null) {
                            return
                        }
                        try {
                            previewCaptureRequest = previewCaptureRequestBuilder!!.build()
                            this@CameraRecognitionFragment.cameraCaptureSession = cameraCaptureSession
                            this@CameraRecognitionFragment.cameraCaptureSession!!.setRepeatingRequest(previewCaptureRequest!!,
                                    object : CameraCaptureSession.CaptureCallback() {

                                    }, null)
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }

                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                        Timber.e("onConfigureFailed")
                    }
                }, cameraBackgroundHandler)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun startCameraBackgroundThread() {
        cameraBackgroundHandlerThread = HandlerThread("VinylRecognizerCameraBackgroundThread")
        cameraBackgroundHandlerThread!!.start()
        cameraBackgroundHandler = Handler(cameraBackgroundHandlerThread!!.looper)
    }

    private fun stopCameraBackgroundThread() {
        cameraBackgroundHandlerThread?.quitSafely()
        try {
            cameraBackgroundHandlerThread?.join()
            cameraBackgroundHandlerThread = null
            cameraBackgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    /**
     * Given `choices` of `Size`s supported by a camera, chooses the smallest one whose
     * width and height are at least as large as the respective requested values.
     *
     * @param choices The list of sizes that the camera supports for the intended output class
     * @param width   The minimum desired width
     * @param height  The minimum desired height
     * @return The optimal `Size`, or an arbitrary one if none were big enough
     */
    @SuppressLint("NewApi")
    private fun chooseBigEnough(choices: Array<Size>, width: Int, height: Int): Size {
        // Collect the supported resolutions that are at least as big as the preview Surface
        val bigEnough = ArrayList<Size>()
        for (option in choices) {
            val optionWidth = option.width
            val optionHeight = option.height
            if (optionWidth >= width && optionHeight >= height) {
                bigEnough.add(option)
            }
        }
        // Pick the smallest of those, assuming we found any
        if (bigEnough.size > 0) {
            return Collections.min(bigEnough, CompareSizesByArea())
        } else {
            Timber.i("Couldn't find any suitable preview size")
            return choices[0]
        }
    }

    @SuppressLint("NewApi")
    private fun getDeviceRottation(characteristics: CameraCharacteristics, deviceOrientation: Int): Int {
        var deviceOrientation = deviceOrientation
        val sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
        deviceOrientation = orientations[deviceOrientation]
        return (sensorOrientation + deviceOrientation + 360) % 360
    }

    @SuppressLint("NewApi")
    internal class CompareSizesByArea : Comparator<Size> {
        override fun compare(lhs: Size, rhs: Size): Int {
            // We cast here to ensure the multiplications won't overflow
            return java.lang.Long.signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)
        }
    }
}