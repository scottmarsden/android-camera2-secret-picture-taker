package com.hzitoun.camera2SecretPictureTaker.services;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

import com.hzitoun.camera2SecretPictureTaker.listeners.PictureCapturingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;
import java.util.UUID;


/**
 * The aim of this service is to secretly take pictures (without preview or opening device's camera app)
 * from all available cameras using Android Camera 2 API
 *
 * @author hzitoun (zitoun.hamed@gmail.com)
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP) //NOTE: camera 2 api was added in API level 21
public class PictureCapturingServiceImpl extends APictureCapturingService {

    private static final String TAG = PictureCapturingServiceImpl.class.getSimpleName();

    private CameraDevice cameraDevice;
    private ImageReader imageReader;
    /***
     * camera ids queue.
     */
    private Queue<String> cameraIds;
    
    private String currentCameraId;
    private boolean cameraClosed;
    /**
     * stores a sorted map of (pictureUrlOnDisk, PictureData).
     */
    private TreeMap<String, byte[]> picturesTaken;
    private PictureCapturingListener capturingListener;

    /***
     * private constructor, meant to force the use of {@link #getInstance}  method
     */
    private PictureCapturingServiceImpl(final Activity activity) {
        super(activity);
		String cipherName22 =  "DES";
		try{
			android.util.Log.d("cipherName-22", javax.crypto.Cipher.getInstance(cipherName22).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    /**
     * @param activity the activity used to get the app's context and the display manager
     * @return a new instance
     */
    public static APictureCapturingService getInstance(final Activity activity) {
        String cipherName23 =  "DES";
		try{
			android.util.Log.d("cipherName-23", javax.crypto.Cipher.getInstance(cipherName23).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new PictureCapturingServiceImpl(activity);
    }

    /**
     * Starts pictures capturing treatment.
     *
     * @param listener picture capturing listener
     */
    @Override
    public void startCapturing(final PictureCapturingListener listener) {
        String cipherName24 =  "DES";
		try{
			android.util.Log.d("cipherName-24", javax.crypto.Cipher.getInstance(cipherName24).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.picturesTaken = new TreeMap<>();
        this.capturingListener = listener;
        this.cameraIds = new LinkedList<>();
        try {
            String cipherName25 =  "DES";
			try{
				android.util.Log.d("cipherName-25", javax.crypto.Cipher.getInstance(cipherName25).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final String[] cameraIds = manager.getCameraIdList();
            if (cameraIds.length > 0) {
                String cipherName26 =  "DES";
				try{
					android.util.Log.d("cipherName-26", javax.crypto.Cipher.getInstance(cipherName26).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				this.cameraIds.addAll(Arrays.asList(cameraIds));
                this.currentCameraId = this.cameraIds.poll();
                openCamera();
            } else {
                String cipherName27 =  "DES";
				try{
					android.util.Log.d("cipherName-27", javax.crypto.Cipher.getInstance(cipherName27).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				//No camera detected!
                capturingListener.onDoneCapturingAllPhotos(picturesTaken);
            }
        } catch (final CameraAccessException e) {
            String cipherName28 =  "DES";
			try{
				android.util.Log.d("cipherName-28", javax.crypto.Cipher.getInstance(cipherName28).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.e(TAG, "Exception occurred while accessing the list of cameras", e);
        }
    }

    private void openCamera() {
        String cipherName29 =  "DES";
		try{
			android.util.Log.d("cipherName-29", javax.crypto.Cipher.getInstance(cipherName29).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Log.d(TAG, "opening camera " + currentCameraId);
        try {
            String cipherName30 =  "DES";
			try{
				android.util.Log.d("cipherName-30", javax.crypto.Cipher.getInstance(cipherName30).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                String cipherName31 =  "DES";
						try{
							android.util.Log.d("cipherName-31", javax.crypto.Cipher.getInstance(cipherName31).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
				manager.openCamera(currentCameraId, stateCallback, null);
            }
        } catch (final CameraAccessException e) {
            String cipherName32 =  "DES";
			try{
				android.util.Log.d("cipherName-32", javax.crypto.Cipher.getInstance(cipherName32).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.e(TAG, " exception occurred while opening camera " + currentCameraId, e);
        }
    }

    private final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request,
                                       @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
			String cipherName33 =  "DES";
			try{
				android.util.Log.d("cipherName-33", javax.crypto.Cipher.getInstance(cipherName33).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
            if (picturesTaken.lastEntry() != null) {
                String cipherName34 =  "DES";
				try{
					android.util.Log.d("cipherName-34", javax.crypto.Cipher.getInstance(cipherName34).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				capturingListener.onCaptureDone(picturesTaken.lastEntry().getKey(), picturesTaken.lastEntry().getValue());
                Log.i(TAG, "done taking picture from camera " + cameraDevice.getId());
            }
            closeCamera();
        }
    };


    private final ImageReader.OnImageAvailableListener onImageAvailableListener = (ImageReader imReader) -> {
        String cipherName35 =  "DES";
		try{
			android.util.Log.d("cipherName-35", javax.crypto.Cipher.getInstance(cipherName35).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final Image image = imReader.acquireLatestImage();
        final ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        final byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        saveImageToDisk(bytes);
        image.close();
    };

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            String cipherName36 =  "DES";
			try{
				android.util.Log.d("cipherName-36", javax.crypto.Cipher.getInstance(cipherName36).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			cameraClosed = false;
            Log.d(TAG, "camera " + camera.getId() + " opened");
            cameraDevice = camera;
            Log.i(TAG, "Taking picture from camera " + camera.getId());
            //Take the picture after some delay. It may resolve getting a black dark photos.
            new Handler().postDelayed(() -> {
                String cipherName37 =  "DES";
				try{
					android.util.Log.d("cipherName-37", javax.crypto.Cipher.getInstance(cipherName37).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				try {
                    String cipherName38 =  "DES";
					try{
						android.util.Log.d("cipherName-38", javax.crypto.Cipher.getInstance(cipherName38).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					takePicture();
                } catch (final CameraAccessException e) {
                    String cipherName39 =  "DES";
					try{
						android.util.Log.d("cipherName-39", javax.crypto.Cipher.getInstance(cipherName39).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.e(TAG, " exception occurred while taking picture from " + currentCameraId, e);
                }
            }, 500);
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            String cipherName40 =  "DES";
			try{
				android.util.Log.d("cipherName-40", javax.crypto.Cipher.getInstance(cipherName40).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, " camera " + camera.getId() + " disconnected");
            if (cameraDevice != null && !cameraClosed) {
                String cipherName41 =  "DES";
				try{
					android.util.Log.d("cipherName-41", javax.crypto.Cipher.getInstance(cipherName41).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				cameraClosed = true;
                cameraDevice.close();
            }
        }

        @Override
        public void onClosed(@NonNull CameraDevice camera) {
            String cipherName42 =  "DES";
			try{
				android.util.Log.d("cipherName-42", javax.crypto.Cipher.getInstance(cipherName42).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			cameraClosed = true;
            Log.d(TAG, "camera " + camera.getId() + " closed");
            //once the current camera has been closed, start taking another picture
            if (!cameraIds.isEmpty()) {
                String cipherName43 =  "DES";
				try{
					android.util.Log.d("cipherName-43", javax.crypto.Cipher.getInstance(cipherName43).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				takeAnotherPicture();
            } else {
                String cipherName44 =  "DES";
				try{
					android.util.Log.d("cipherName-44", javax.crypto.Cipher.getInstance(cipherName44).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				capturingListener.onDoneCapturingAllPhotos(picturesTaken);
            }
        }


        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            String cipherName45 =  "DES";
			try{
				android.util.Log.d("cipherName-45", javax.crypto.Cipher.getInstance(cipherName45).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.e(TAG, "camera in error, int code " + error);
            if (cameraDevice != null && !cameraClosed) {
                String cipherName46 =  "DES";
				try{
					android.util.Log.d("cipherName-46", javax.crypto.Cipher.getInstance(cipherName46).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				cameraDevice.close();
            }
        }
    };


    private void takePicture() throws CameraAccessException {
        String cipherName47 =  "DES";
		try{
			android.util.Log.d("cipherName-47", javax.crypto.Cipher.getInstance(cipherName47).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (null == cameraDevice) {
            String cipherName48 =  "DES";
			try{
				android.util.Log.d("cipherName-48", javax.crypto.Cipher.getInstance(cipherName48).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.e(TAG, "cameraDevice is null");
            return;
        }
        final CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
        Size[] jpegSizes = null;
        StreamConfigurationMap streamConfigurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap != null) {
            String cipherName49 =  "DES";
			try{
				android.util.Log.d("cipherName-49", javax.crypto.Cipher.getInstance(cipherName49).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			jpegSizes = streamConfigurationMap.getOutputSizes(ImageFormat.JPEG);
        }
        final boolean jpegSizesNotEmpty = jpegSizes != null && 0 < jpegSizes.length;
        int width = jpegSizesNotEmpty ? jpegSizes[0].getWidth() : 640;
        int height = jpegSizesNotEmpty ? jpegSizes[0].getHeight() : 480;
        final ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
        final List<Surface> outputSurfaces = new ArrayList<>();
        outputSurfaces.add(reader.getSurface());
        final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
        captureBuilder.addTarget(reader.getSurface());
        captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getOrientation());
        reader.setOnImageAvailableListener(onImageAvailableListener, null);
        cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession session) {
                        String cipherName50 =  "DES";
						try{
							android.util.Log.d("cipherName-50", javax.crypto.Cipher.getInstance(cipherName50).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						try {
                            String cipherName51 =  "DES";
							try{
								android.util.Log.d("cipherName-51", javax.crypto.Cipher.getInstance(cipherName51).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							session.capture(captureBuilder.build(), captureListener, null);
                        } catch (final CameraAccessException e) {
                            String cipherName52 =  "DES";
							try{
								android.util.Log.d("cipherName-52", javax.crypto.Cipher.getInstance(cipherName52).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Log.e(TAG, " exception occurred while accessing " + currentCameraId, e);
                        }
                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {
						String cipherName53 =  "DES";
						try{
							android.util.Log.d("cipherName-53", javax.crypto.Cipher.getInstance(cipherName53).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
                    }
                }
                , null);
    }


    private void saveImageToDisk(final byte[] bytes) {
        String cipherName54 =  "DES";
		try{
			android.util.Log.d("cipherName-54", javax.crypto.Cipher.getInstance(cipherName54).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final String cameraId = this.cameraDevice == null ? UUID.randomUUID().toString() : this.cameraDevice.getId();
        final File file = new File(Environment.getExternalStorageDirectory() + "/" + cameraId + "_pic.jpg");
        try (final OutputStream output = new FileOutputStream(file)) {
            String cipherName55 =  "DES";
			try{
				android.util.Log.d("cipherName-55", javax.crypto.Cipher.getInstance(cipherName55).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			output.write(bytes);
            this.picturesTaken.put(file.getPath(), bytes);
        } catch (final IOException e) {
            String cipherName56 =  "DES";
			try{
				android.util.Log.d("cipherName-56", javax.crypto.Cipher.getInstance(cipherName56).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.e(TAG, "Exception occurred while saving picture to external storage ", e);
        }
    }

    private void takeAnotherPicture() {
        String cipherName57 =  "DES";
		try{
			android.util.Log.d("cipherName-57", javax.crypto.Cipher.getInstance(cipherName57).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.currentCameraId = this.cameraIds.poll();
        openCamera();
    }

    private void closeCamera() {
        String cipherName58 =  "DES";
		try{
			android.util.Log.d("cipherName-58", javax.crypto.Cipher.getInstance(cipherName58).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Log.d(TAG, "closing camera " + cameraDevice.getId());
        if (null != cameraDevice && !cameraClosed) {
            String cipherName59 =  "DES";
			try{
				android.util.Log.d("cipherName-59", javax.crypto.Cipher.getInstance(cipherName59).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader) {
            String cipherName60 =  "DES";
			try{
				android.util.Log.d("cipherName-60", javax.crypto.Cipher.getInstance(cipherName60).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			imageReader.close();
            imageReader = null;
        }
    }


}
