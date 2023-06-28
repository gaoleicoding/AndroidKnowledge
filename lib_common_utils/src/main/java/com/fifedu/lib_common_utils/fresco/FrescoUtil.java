package com.fifedu.lib_common_utils.fresco;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by gaolei on 2023/6/1.
 */
public class FrescoUtil {
    /**
     * 加载本地图片（drawable图片）
     */
    public static void loadResPic(Context context, SimpleDraweeView simpleDraweeView, int id) {
        Uri uri = Uri.parse("res://" +
                context.getPackageName() +
                "/" + id);
        simpleDraweeView.setImageURI(uri);
    }

    public static void loadUrlPicNormal_radius(Context context, int radius, SimpleDraweeView simpleDraweeView, String url) {

        if (!TextUtils.isEmpty(url) && simpleDraweeView != null) {
            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setCornersRadius(radius);//这里是设置你希望的圆角的值
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
            GenericDraweeHierarchy hierarchy = builder.build();
            hierarchy.setRoundingParams(roundingParams);
            simpleDraweeView.setHierarchy(hierarchy);//一定要先设置Hierarchy，再去加载图片，否则会加载不出来图片

            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .build();

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(simpleDraweeView.getController())
                    .setControllerListener(new BaseControllerListener<ImageInfo>())
                    .setImageRequest(request).build();
            simpleDraweeView.setController(controller);
        }
    }


    /**
     * 加载本地图片（drawable图片）
     */
    public static void loadResPicWithSize(Context context, SimpleDraweeView simpleDraweeView, int id, int width, int height) {
        Uri uri = Uri.parse("res://" +
                context.getPackageName() +
                "/" + id);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) simpleDraweeView.getLayoutParams();
        params.width = width;
        params.height = height;
        simpleDraweeView.setLayoutParams(params);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载网络图片（不压缩加载，防止图片模糊）
     */
    public static void loadUrlPicNormal(SimpleDraweeView simpleDraweeView, String url) {
        if (!TextUtils.isEmpty(url) && simpleDraweeView != null) {
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .build();

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(simpleDraweeView.getController())
                    .setControllerListener(new BaseControllerListener<ImageInfo>())
                    .setImageRequest(request).build();
            simpleDraweeView.setController(controller);
        }
    }

    /**
     * 加载本地图片（drawable）
     *
     * @param ratio 宽高比
     */
    public static void loadResPicWithRatio(Context context, SimpleDraweeView simpleDraweeView, int id, float ratio) {
        Uri uri = Uri.parse("res://" +
                context.getPackageName() +
                "/" + id);
        simpleDraweeView.setImageURI(uri);
        simpleDraweeView.setAspectRatio(ratio);
    }

    /**
     * 加载本地图片（assets图片）
     *
     * @param nameWithSuffix 带后缀的名称（如test.png）
     */
    public static void loadAssetsPic(SimpleDraweeView simpleDraweeView, String nameWithSuffix) {
        Uri uri = Uri.parse("asset:///" +
                nameWithSuffix);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载本地图片（本地文件图片）
     */
    public static void loadFilePic(SimpleDraweeView simpleDraweeView, String filePath) {
        Uri uri = Uri.parse("file://" + filePath);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载资源图片 自适应高度（宽度为最大宽度）
     */
    public static void loadResPicWithWidth(Context context, final SimpleDraweeView simpleDraweeView, int res, final int maxWidth) {
        final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                int width = imageInfo.getWidth();
                int height = imageInfo.getHeight();
                layoutParams.width = maxWidth;
                layoutParams.height = maxWidth * height / width;
                simpleDraweeView.setLayoutParams(layoutParams);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        Uri uri = Uri.parse("res://" +
                context.getPackageName() +
                "/" + res);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(maxWidth, 100))
                //支持图片渐进式加载
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).setImageRequest(request).build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 加载本地图片（本地文件图片）自适应高度
     */
    public static void loadFilePicWithWidth(final SimpleDraweeView simpleDraweeView, String filePath, final int maxWidth) {
        final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                int width = imageInfo.getWidth();
                int height = imageInfo.getHeight();
                if (width > maxWidth) {
                    layoutParams.width = maxWidth;
                    layoutParams.height = maxWidth * height / width;
                } else {
                    layoutParams.width = width;
                    layoutParams.height = height;
                }
                simpleDraweeView.setLayoutParams(layoutParams);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        Uri uri = Uri.parse("file://" + filePath);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(maxWidth, 100))
                //支持图片渐进式加载
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).setImageRequest(request).build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 加载本地图片（本地文件图片）自适应高度
     */
    public static void loadFilePicWithStaticWidth(final SimpleDraweeView simpleDraweeView, String filePath, final int maxWidth) {
        final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                int width = imageInfo.getWidth();
                int height = imageInfo.getHeight();
                layoutParams.width = maxWidth;
                layoutParams.height = maxWidth * height / width;
                simpleDraweeView.setLayoutParams(layoutParams);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        Uri uri = Uri.parse("file://" + filePath);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(maxWidth, 100))
                //支持图片渐进式加载
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).setImageRequest(request).build();
        simpleDraweeView.setController(controller);
    }


    /**
     * 加载本地图片（本地文件图片）
     */
    public static void loadFilePicWithSize(SimpleDraweeView simpleDraweeView, String filePath, int width, int height) {
        Uri uri = Uri.parse("file://" + filePath);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) simpleDraweeView.getLayoutParams();
        params.width = width;
        params.height = height;
        simpleDraweeView.setLayoutParams(params);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 带回调的加载
     */
    public static void loadPicWithCallBack(SimpleDraweeView simpleDraweeView, String url, final PicCallBack picCallBack) {
        if (!TextUtils.isEmpty(url) && simpleDraweeView != null) {
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .disableDiskCache()
                    .setResizeOptions(new ResizeOptions(500, 500))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(simpleDraweeView.getController())
                    .setControllerListener(new BaseControllerListener<ImageInfo>() {
                        @Override
                        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                            super.onFinalImageSet(id, imageInfo, animatable);
                            // 加载完成后
                            if (picCallBack != null) {
                                picCallBack.onSuccess();
                            }
                        }

                        @Override
                        public void onFailure(String id, Throwable throwable) {
                            super.onFailure(id, throwable);
//                            加载失败
                            if (picCallBack != null) {
                                picCallBack.onFailure();
                            }
                        }
                    })
                    .setImageRequest(request).build();
            simpleDraweeView.setController(controller);
        }
    }

    /**
     * 加载网络图片
     */
    public static void loadUrlPic(SimpleDraweeView simpleDraweeView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(100, 100))
                    .build();

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(simpleDraweeView.getController())
                    .setControllerListener(new BaseControllerListener<ImageInfo>())
                    .setImageRequest(request).build();
            simpleDraweeView.setController(controller);
        }
    }

    /**
     * 加载网络图片(根据宽度自适应)
     */
    public static void loadUrlPicWithWidth(final SimpleDraweeView simpleDraweeView, String url, final int maxWidth) {
        if (!TextUtils.isEmpty(url)) {
            final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                    if (imageInfo == null) {
                        return;
                    }
                    int width = imageInfo.getWidth();
                    int height = imageInfo.getHeight();
                    if (width > maxWidth) {
                        layoutParams.width = maxWidth;
                        layoutParams.height = maxWidth * height / width;
                    } else {
                        layoutParams.width = width;
                        layoutParams.height = height;
                    }
                    simpleDraweeView.setLayoutParams(layoutParams);
                }

                @Override
                public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    throwable.printStackTrace();
                }
            };
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.
                    newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(maxWidth, 100))
                    //支持图片渐进式加载
                    .setProgressiveRenderingEnabled(true)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder().setAutoPlayAnimations(true).setControllerListener(controllerListener).setImageRequest(request).build();
            simpleDraweeView.setController(controller);
        }
    }

    public static class PicCallBack {
        public void onSuccess() {
        }

        public void onFailure() {
        }
    }
}
