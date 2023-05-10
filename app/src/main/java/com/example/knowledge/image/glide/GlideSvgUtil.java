package com.example.knowledge.image.glide;

import static com.example.knowledge.AppApplication.context;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.PictureDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

public class GlideSvgUtil {

    //显示网络中的svg文件
    public static void showSvgUrl(ImageView imageView, String url) {
        Glide.with(context)
                .as(PictureDrawable.class)
                .listener(new SvgSoftwareLayerSetter())
                .load(url)
                .into(imageView);
    }

    //把svg放入到raw中，通过rawid来显示
    public static void showSvgRes(ImageView imageView, int rawId) {
        try {
            SVG svg = SVG.getFromResource(imageView.getContext(), rawId);
            if (svg.getDocumentWidth() != -1) {
                Bitmap newBM = Bitmap.createBitmap((int) Math.ceil(svg.getDocumentWidth()),
                        (int) Math.ceil(svg.getDocumentHeight()),
                        Bitmap.Config.ARGB_8888);
                Canvas bmcanvas = new Canvas(newBM);

                // Clear background to white
                bmcanvas.drawRGB(255, 255, 255);

                // Render our document onto our canvas
                svg.renderToCanvas(bmcanvas);
                imageView.setImageBitmap(newBM);
            }
        } catch (SVGParseException e) {
        }
    }

    //把svg的XML加载到字符串中来显示
    public static void showSvgContent(ImageView imageView, String svgContent) {
//        Glide.with(imageView.getContext()).as(PictureDrawable.class).listener(new SvgSoftwareLayerSetter()).load(svgContent.getBytes()).into(imageView);
    }
}
