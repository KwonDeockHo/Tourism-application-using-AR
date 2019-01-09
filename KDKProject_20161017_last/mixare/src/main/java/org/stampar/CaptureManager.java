package org.stampar;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.view.Display;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2016-06-28.
 */
public class CaptureManager {
    /*
    전부다 스태틱으로 처리했꼬, REQUEST_CODE와 STORE_DIRECTORY는 MainActivity에서 처리하고,
    값을 설정해주기 때문에, (STORE_DIRECTORY) public으로 처리했다.
    물론 나중에는 좀 더 제대로 구성해야될 듯. 구성이 심각하게 혼란스럽다.
     */
    public static final int REQUEST_CODE = 100;
    public static String STORE_DIRECTORY;

    private static final String VIRTUAL_DISPLAY_NAME = "CaptureScreen";
    private static final String IMAGE_NAME = "test.jpg";
    private static final int VIRTUAL_DISPLAY_FLAGS = DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC | DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY;

    private static MediaProjectionManager sMediaProjectionManager = null;
    private static MediaProjection sMediaProjection = null;
    //private static Handler sHandler = null;
    private static Display sDisplay = null;
    private static ImageReader sImageReader = null;
    private static VirtualDisplay sVirtualDisplay = null;
    private static int sDensity = 0;
    private static int sWidth = 0;
    private static int sHeight = 0;

    public CaptureManager() {

    }

    private CaptureManager(MediaProjectionManager projectionManager, MediaProjection mediaProjection,Display display, int density) {
        /*
        액티비티에서 설정되어진 값들 모두 넣는다. 원래 핸들러 부분이 있었지만, 이용하지 않으므로,
        제거 됬다. 물론 핸들러가 정확히 문제가 있었는지는 모르겠으나, (결국 다른 부분이 문제)
        액티비티에서 만들어진 쓰레드가 액티비티 종료 후 에도 계속 유지될지는 정확히 모르겠다.
         */
        sMediaProjectionManager = projectionManager;
        sMediaProjection = mediaProjection;
        sDisplay = display;
        sDensity = density;
        //sHandler = handler;

        sMediaProjection.registerCallback(new MediaProjectionStopCallback(), null);
    }

    public static CaptureManager initCaptureManager(MediaProjectionManager projectionManager, MediaProjection mediaProjection,Display display, int density){
        /*
        액티비티에서 설정하고, 여기서 생성자를 만든다. 그냥 아싸리 생성자를 불르면 되지 않냐
        싶기도 한데, 그러면 MainActivity에서 필요없는 객체를 보관해야하며, init라고 이름을 명명
        했기 때문에, 이 메소드를 호출함으로써 초기화가 되는거구나...그냥 알게끔만?
        물론 구성이 혼란스럽다.
         */
        return new CaptureManager(projectionManager, mediaProjection,display, density);
    }



    public class ImageAvailableListener implements ImageReader.OnImageAvailableListener {
        /*
        기존 이미지캡처와 크게 다른부분은 없다.
         */
        @Override
        public void onImageAvailable(ImageReader imageReader) {
            synchronized (sMediaProjectionManager) {
                Image image = null;
                FileOutputStream fos = null;
                Bitmap bitmap = null;

                try {
                    image = sImageReader.acquireLatestImage();
                    if (image != null) {
                        Image.Plane[] planes = image.getPlanes();
                        ByteBuffer buffer = planes[0].getBuffer();
                        int pixelStride = planes[0].getPixelStride();
                        int rowStride = planes[0].getRowStride();
                        int rowPadding = rowStride - pixelStride * sWidth;

                        bitmap = Bitmap.createBitmap(sWidth + rowPadding / pixelStride, sHeight, Bitmap.Config.ARGB_8888);
                        bitmap.copyPixelsFromBuffer(buffer);

                        fos = new FileOutputStream(STORE_DIRECTORY + IMAGE_NAME);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }

                    if (bitmap != null) {
                        bitmap.recycle();
                    }

                    if (image != null) {
                        image.close();
                    }
                }

                /*
                여기서 VirtualDisplay와 ImageReader 객체 리소스를 해제하고 이벤트를 해제 한다.
                stop() 메소드를 호출한 것이 문제가 되었었다. stop() 메소드를 호출함으로써
                MediaProjection 객체가 아예 사용 못하게 되면서, 재사용이 되지않아 첫번째 호출 이후
                에러가 났었다. 이 부분이 문제였음. 이 부분전에 핸들러나 디스플레이 객체등등을
                손보았다. (물론 해당 부분에 문제가 있었는지에 대한 것은 정확히 모르겠음.)
                 */
                if (sVirtualDisplay != null) sVirtualDisplay.release();
                if (sImageReader != null) sImageReader.setOnImageAvailableListener(null, null);
                //sMediaProjection.stop();
            }
        }
    }

    public MediaProjectionManager getMediaProjectionManager() {
        if (sMediaProjectionManager == null){
            return null;
        }

        return sMediaProjectionManager;
    }

    private class MediaProjectionStopCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            /*
            핸들러 부분을 바깥으로 꺼냈다. 그리고 unregister 부분을 없앴다. 물론 이부분이
            문제가 있었는지는 모르겠으나, 서비스가 종료되서 아예 사라지면, 시스템이 자체적으로
            리소스를 해제할테니 그냥 없애버렸다. 해당 코드 수정당시에는 에러가 나지 않는 것이
            주요목표 였다.
             */
            if (sVirtualDisplay != null) sVirtualDisplay.release();
            if (sImageReader != null)
                sImageReader.setOnImageAvailableListener(null, null);
            //sMediaProjection.unregisterCallback(MediaProjectionStopCallback.this);

            /*sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (sVirtualDisplay != null) sVirtualDisplay.release();
                    if (sImageReader != null)
                        sImageReader.setOnImageAvailableListener(null, null);
                    sMediaProjection.unregisterCallback(MediaProjectionStopCallback.this);
                }
            });*/
        }
    }

    public void createVirtualDisplay(Display display) {
        /*
            init 메소드가 호출되지 않으면, 해당 객체 값은 null 값을 유지 한다 즉
            초기화 하지 않고 사용할 경우에는 기능을 수행하지 않고 종료 한다.
         */
        if (sMediaProjectionManager == null || sMediaProjection == null) return;

        /*
        여기서 Resource 및 Display객체를 가져올 수 없으므로, dpi는 처음실행한 액티비티에서
        초기화 하면서 가져오고, Display 객체는 서비스에서 매개변수로 건내준것을 받는다.
        서비스의 WindowManaegr 멤버 변수의 Display 객체다.
         */

        //DisplayMetrics metrics = resources.getDisplayMetrics();
        //sDensity = metrics.densityDpi;


        //sMediaProjection.registerCallback(new MediaProjectionStopCallback(), sHandler);
        //sMediaProjection.registerCallback(new MediaProjectionStopCallback(), null);


        /*
        사이즈 설정. 이 부분을 일단 남겨두었다. 혹시나 가로,세로 화면 전환시에 적용이 될까해서
        물론 테스트를 안해보았지만, 둘째치고 생각해보니 가로,세로 화면이 전환되면 dpi 값도
        달라지지 않나...비율이 달라지니까 음.. dpi 값만 설정하면 적용이 될려나... 나중에
        테스트 해봐야겠다.
         */
        Point size = new Point();
        display.getSize(size);
        sWidth = size.x;
        sHeight = size.y;

        /*
        핸들러부분을 제거 했다. 물론 이 때문에 에러가 난 것은 아니었지만,지운 뒤라서
        지운 상태로 일단 남겨두었다. 핸들러의 사용 여부와 MediaProjection을 좀 더 제대로
        학습하는 것도 중요하다고 생각한다.
         */
        sImageReader = ImageReader.newInstance(sWidth, sHeight, PixelFormat.RGBA_8888, 2);
        //sVirtualDisplay = sMediaProjection.createVirtualDisplay(VIRTUAL_DISPLAY_NAME, sWidth, sHeight, sDensity, VIRTUAL_DISPLAY_FLAGS, sImageReader.getSurface(), null, sHandler);
        //sImageReader.setOnImageAvailableListener(new ImageAvailableListener(), sHandler);
        sVirtualDisplay = sMediaProjection.createVirtualDisplay(VIRTUAL_DISPLAY_NAME, sWidth, sHeight, sDensity, VIRTUAL_DISPLAY_FLAGS, sImageReader.getSurface(), null, null);
        sImageReader.setOnImageAvailableListener(new ImageAvailableListener(), null);
    }
}
