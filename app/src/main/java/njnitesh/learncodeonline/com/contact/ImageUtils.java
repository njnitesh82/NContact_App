package njnitesh.learncodeonline.com.contact;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public byte[] image2byteArray(ImageView image){
        Bitmap bitmap=((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,stream);
        return stream.toByteArray();
    }

    public Bitmap byteArray2image(byte[] im){
        Bitmap bit=null;

            bit=BitmapFactory.decodeByteArray(im,0,im.length);

        return bit;
    }

    public byte[] image2byteArray(Bitmap bn){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bn.compress(Bitmap.CompressFormat.JPEG,40,stream);
        return stream.toByteArray();
    }
}
