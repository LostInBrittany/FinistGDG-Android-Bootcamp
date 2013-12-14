package org.finistgdg.bootcamp.android.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by stephane on 13/12/2013.
 */
public class ImageHelper {

    public static String convertBmpToBase64(String picturePath){
        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        //added lines
        bm.recycle();
        bm = null;
        //added lines
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
