package edu.spbspu.amd.morze_app.receiver;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import edu.spbspu.amd.morze_app.ActivityMain;
import edu.spbspu.amd.morze_app.morzeCoder.MorzeСoder;
import edu.spbspu.amd.morze_app.receiver.image_processing.ImageProcessing;
import edu.spbspu.amd.morze_app.receiver.image_processing.*;
import edu.spbspu.amd.morze_app.sender.AppSender;


public class ViewReceiver extends View implements TextureView.SurfaceTextureListener {
    private TextureView textureView;
    private Camera      camera;
    static public Queue<Bitmap> m_queue;

    private ImageProcessing ip;
    private Bitmap          curCameraImage = null;
    private TextView        outputText;

    private Timer   timer;
    private Handler h;

    private ActivityMain  m_ctx;

    private int sv_width, sv_height;

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        setCameraDisplayOrientation(0);
        try {
            camera.setPreviewTexture(surface);
        } catch (Exception e) {
            e.printStackTrace();
        }

        camera.startPreview();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        sv_height = height;
        sv_width = width;

        camera.stopPreview();
        setCameraDisplayOrientation(0);
        try {
            camera.setPreviewTexture(surfaceTexture);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        camera.stopPreview();
        camera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @SuppressLint("HandlerLeak")
    public ViewReceiver(ActivityMain context, TextureView textureSurfaceView,
                        TextView outputTextView, Camera cam) {
        super(context);
        m_ctx = context;
        camera = cam;

        textureView = textureSurfaceView;
        textureView.setSurfaceTextureListener(this);

        outputText = outputTextView;

        timer = new Timer("Receiver Timer");

        TimerTask taskSaveImage = new TimerTask() {
            @Override
            public void run() {
                curCameraImage = textureView.getBitmap();
                m_queue.offer(curCameraImage);
            }
        };

        timer.scheduleAtFixedRate(taskSaveImage, AppSender.delay, AppSender.m_point_time / 3);
    }

    public void onResume(Camera cam) {
        camera = cam;
        setPreviewSize(false);
    }

    public void onPause() {
        if (camera != null)
            camera.release();
        camera = null;
    }

    void setPreviewSize(boolean fullScreen) {
        // получаем размеры экрана
        boolean widthIsMax = sv_width > sv_height;

        // определяем размеры превью камеры
        Camera.Size size = camera.getParameters().getPreviewSize();

        RectF rectDisplay = new RectF();
        RectF rectPreview = new RectF();

        // RectF экрана, соотвествует размерам экрана
        rectDisplay.set(0, 0, sv_width, sv_height);

        // RectF первью
        if (widthIsMax) {
            // превью в горизонтальной ориентации
            rectPreview.set(0, 0, size.width, size.height);
        } else {
            // превью в вертикальной ориентации
            rectPreview.set(0, 0, size.height, size.width);
        }

        Matrix matrix = new Matrix();
        // подготовка матрицы преобразования
        if (!fullScreen) {
            // если превью будет "втиснут" в экран (второй вариант из урока)
            matrix.setRectToRect(rectPreview, rectDisplay, Matrix.ScaleToFit.START);
        } else {
            // если экран будет "втиснут" в превью (третий вариант из урока)
            matrix.setRectToRect(rectDisplay, rectPreview, Matrix.ScaleToFit.START);
            matrix.invert(matrix);
        }
        // преобразование
        matrix.mapRect(rectPreview);

        // установка размеров surface из получившегося преобразования
        textureView.getLayoutParams().height = (int) (rectPreview.bottom);
        textureView.getLayoutParams().width = (int) (rectPreview.right);
    }

    void setCameraDisplayOrientation(int cameraId) {
        // определяем насколько повернут экран от нормального положения
        int rotation = m_ctx.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result = 0;

        // получаем инфо по камере cameraId
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        result = ((360 - degrees) + info.orientation);
        result = result % 360;
        camera.setDisplayOrientation(result);
    }
}