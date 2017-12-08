package com.zhang.box.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * Created by lilei on 2017/11/12.
 */

public class QRCodeUtil {

    /** 生成创建二维码 */
    public static Bitmap createQRImage(String url) {
        Bitmap bitmap;
        int QR_WIDTH = 300;
        int QR_HEIGHT = 300;
        try {
            // 判断URL合法性
            if (url != null && !"".equals(url) && url.length() > 0) {
                Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                // 图像数据转换，使用了矩阵转换
                BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                        BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
                int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
                // 下面这里按照二维码的算法，逐个生成二维码的图片，
                // 两个for循环是图片横列扫描的结果
                for (int y = 0; y < QR_HEIGHT; y++) {
                    for (int x = 0; x < QR_WIDTH; x++) {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * QR_WIDTH + x] = 0xff000000;
                        } else {
                            // pixels[y * QR_WIDTH + x] = 0xffffffff;
                        }
                    }
                }
                // 生成二维码图片的格式，使用ARGB_8888
                bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                        Bitmap.Config.ARGB_8888);
                bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
                return bitmap;
            }else{
                return null;
            }
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

}
