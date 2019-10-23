package edu.spbspu.amd.morze_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import edu.spbspu.amd.morze_app.receiver.image_processing.ImageProcessing;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class UnitTest {
  @Test
  public void addition_isCorrect()
  {
    assertEquals(4, 2 + 2);
  }

  private static File getFileFromPath(Object obj, String fileName) {
    ClassLoader classLoader = obj.getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    return new File(resource.getPath());
  }

  @Test
  public void testMorzeImageProcessing_isCorrect()
  {
    final String image1FileName = "test_image_processing_1.png";
    final String image2FileName = "test_image_processing_2.png";

    // Get images from files
    File image1File = getFileFromPath(this, image1FileName);
    File image2File = getFileFromPath(this, image2FileName);

    assertThat(image1File, notNullValue());
    assertThat(image2File, notNullValue());

    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPreferredConfig = Bitmap.Config.ARGB_8888;

    // Load images from files to Bitmap
    Bitmap image1Bitmap = null;
    Bitmap image2Bitmap = null;
    try {
      image1Bitmap = BitmapFactory.decodeFile(image1File.getAbsolutePath(), options);
      image2Bitmap = BitmapFactory.decodeFile(image2File.getAbsolutePath(), options);
    } catch (Exception e) {
      fail(e.getMessage());
      return;
    }

    // Processing
    ImageProcessing ip = new ImageProcessing();
    ip.compareWithCurrentFrameImage(image1Bitmap);

    assertTrue(ip.compareWithCurrentFrameImage(image2Bitmap) != 0);
  }
}