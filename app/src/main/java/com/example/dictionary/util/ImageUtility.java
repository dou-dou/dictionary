package com.example.dictionary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.dictionary.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by doudou on 2016/5/21.
 */
public class ImageUtility {

    public static DisplayImageOptions imageLoader(Context context){
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(context);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);


        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }


}
