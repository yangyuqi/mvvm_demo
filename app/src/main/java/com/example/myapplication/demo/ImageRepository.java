package com.example.myapplication.demo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Description:
 * @Author: ch
 * @CreateDate: 2019/7/25 21:23
 */
public class ImageRepository {

    private static final String TAG = "ImageRepository";

    private NetUtil netUtil = new NetUtil();
    private MutableLiveData<ImageBean> imageBean1 = new MutableLiveData<>();

    private int idx = 1;

    /**
     * 获取图片
     *
     * @return
     */
    public MutableLiveData<ImageBean> getBingImage() {
        netUtil.getBingImage("js", idx, 1)
//                .enqueue(new Callback<ImageBean>() {
//            @Override
//            public void onResponse(Call<ImageBean> call, Response<ImageBean> response) {
//                if (response.body() != null) {
//                    Log.d(TAG, "onResponse: " + idx);
//                    imageBean1.setValue(new BaseData<>(response.body(), null));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ImageBean> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.toString());
//            }
//        });
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        imageBean1.setValue(imageBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return imageBean1;
    }

    /**
     * 获取下一张图片
     */
    public MutableLiveData<ImageBean> getNextImage() {
        if (idx > 6) {
            imageBean1.setValue(new ImageBean());
            return imageBean1;
        }
        ++idx;
        return getBingImage();
    }

    /**
     * 获取上一张图片
     */
    public MutableLiveData<ImageBean> getPreviousImage() {
        if (idx < 1) {
            imageBean1.setValue(new ImageBean());
            return imageBean1;
        }
        --idx;
        return getBingImage();
    }
}
