package com.youth.banner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //鍏蜂綋鏂规硶鍐呭鑷繁鍘婚�夋嫨锛屾鏂规硶鏄负浜嗗噺灏慴anner杩囧鐨勪緷璧栫涓夋柟鍖咃紝鎵�浠ュ皢杩欎釜鏉冮檺寮�鏀剧粰浣跨敤鑰呭幓閫夋嫨
        Glide.with(context.getApplicationContext())
                .load(path)
                .into(imageView);
    }

//    @Override
//    public ImageView createImageView(Context context) {
//        //鍦嗚
//        return new RoundAngleImageView(context);
//    }
}
