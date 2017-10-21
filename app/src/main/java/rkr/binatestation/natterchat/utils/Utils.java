package rkr.binatestation.natterchat.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;

import rkr.binatestation.natterchat.R;


/**
 * Created by RKR on 9/18/2017.
 * Utils
 */

public class Utils {

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void setGlideImage(ImageView imageView, String imageUrl) {
        if (imageView != null) {
            Glide.with(imageView.getContext())
                    .setDefaultRequestOptions(getDefaultRequestOption())
                    .load(imageUrl)
                    .into(imageView);
        }
    }

    public static void setGlideCircleImage(ImageView imageView, String imageUrl) {
        if (imageView != null) {
            Glide.with(imageView.getContext())
                    .setDefaultRequestOptions(getDefaultRequestOption())
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);
        }
    }

    public static void setGlideCircleImageProfile(ImageView imageView, String imageUrl) {
        if (imageView != null) {
            Glide.with(imageView.getContext())
                    .setDefaultRequestOptions(getProfileRequestOption())
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);
        }
    }

    private static RequestOptions getDefaultRequestOption() {
        return getDefaultRequestOption(R.drawable.ic_image_black_alpha_24dp, R.drawable.ic_broken_image_black_alpha_24dp);
    }

    private static RequestOptions getProfileRequestOption() {
        return getDefaultRequestOption(R.drawable.ic_person_black_alpha_24dp, R.drawable.ic_person_black_alpha_24dp);
    }

    private static RequestOptions getDefaultRequestOption(int placeHolder, int error) {
        RequestOptions requestOptions = RequestOptions.errorOf(error);
        requestOptions.placeholder(placeHolder);
        return requestOptions;
    }

    public static String getBase64String(String imagePath) {

        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static long getTimeInMillis(long unixTimeStamp) {
        return unixTimeStamp * 1000;
    }

    public static long getUnixTimeStamp(long timeInMillis) {
        return timeInMillis / 1000;
    }
}
