/*
 * This is an example test project created for students work
 * project : Knack Pask 2D
 *
 *
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 *
 * @author Vladislav Shubnikov
 *
 */

package edu.spbspu.amd.morze_app;

// imports
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import edu.spbspu.amd.morze_app.morzeCoder.MorzeСoder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


// class
@RunWith(AndroidJUnit4.class)
public class morzeTests
{
  @Test
  public void testMorzeAlgoDecode(){
    MorzeСoder morzeDecoder = new MorzeСoder();

    //encode
    String strToEncode = "aem kek lol";
    char[] encodedStr = MorzeСoder.encode(strToEncode);
    System.out.println(encodedStr);

    //decode
    StringBuffer decodeResStr = new StringBuffer();
    for (char elem : encodedStr){
      try {
        morzeDecoder.appendSym(elem);
        if (morzeDecoder.canDecode()){
          decodeResStr.append(morzeDecoder.getDecodedSym());
        }
      }
      catch (Exception e){
        fail(e.getMessage());
      }
    }
    assertEquals(decodeResStr.toString(), strToEncode);
  }
}
