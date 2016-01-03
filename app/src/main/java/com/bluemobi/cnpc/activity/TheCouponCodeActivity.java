package com.bluemobi.cnpc.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.annotation.ContentView;
import com.bluemobi.cnpc.annotation.PageName;
import com.bluemobi.cnpc.app.TitleBarManager;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.network.model.CouponItem;
import com.bluemobi.cnpc.util.StringUtils;
import com.bluemobi.cnpc.view.LoadingPage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liufy on 2015/7/28.
 * P10_10_1优惠券详细
 */
@ContentView(R.layout.activity_coupon_code)
@PageName(R.string.s_the_coupon_code)
public class TheCouponCodeActivity extends BaseActivity
{

    private CouponItem item;

    @Bind(R.id.tv_name)
    protected TextView tv_name;

    @Bind(R.id.tv_money)
    protected TextView tv_money;

    @Bind(R.id.activity)
    protected TextView activity;

    @Bind(R.id.time)
    protected TextView time;

    @Bind(R.id.min_buy)
    protected TextView min_buy;

    @Bind(R.id.oil_limit_value)
    protected TextView oil_limit_value;

    @Bind(R.id.qc_code)
    protected ImageView qc_code;

    @Bind(R.id.one_code)
    protected ImageView one_code;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.s_the_coupon_code, R.drawable.return_arrow, true);
        showLoadingPage(false);

    }

    @Override
    protected void successViewCompleted(View successView) {
        ButterKnife.bind(this, successView);
        item = (CouponItem)getIntent().getSerializableExtra("item");
        if(item != null){
            tv_name.setText(item.getDeptName());//TODO: to sure this right?
            tv_money.setText(item.getCouponDiscount() + "元");
            activity.setText(item.getCouponName());//TODO: to sure this right?
            min_buy.setText(item.getMinOrderAmount() + "元");
            time.setText(item.getCouponStartTime() + "至" +
                    item.getCouponEndTime());
            oil_limit_value.setText(item.getDeptName());//TODO: to sure this right?
            injectImg();
        }
    }

    /**
     * 不可含有中文
     * 09-13 16:35:57.212: E/AndroidRuntime(5879):
     * Caused by: java.lang.IllegalArgumentException:
     * Requested contents should be less than 80 digits long,
     * but got 1456
     */
    private void injectImg() {
        Bitmap qcBmp = null;
        Bitmap oneBmp = null;
        try {
            if (StringUtils.isNotEmpty(item.getBarcode())) {
                qcBmp = createTwoDCode(item.getBarcode());
                oneBmp = createOneDCode(item.getBarcode());
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (qcBmp != null) {
            qc_code.setImageBitmap(qcBmp);
        }
        if (oneBmp != null) {
            one_code.setImageBitmap(oneBmp);
        }
    }

    /**
     * 将指定的内容生成成二维码
     *
     * @param content 将要生成二维码的内容
     * @return 返回生成好的二维码事件
     * @throws WriterException WriterException异常
     */
    public Bitmap createTwoDCode(String content) throws WriterException {
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, 300, 300);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 用于将给定的内容生成成一维码 注：目前生成内容为中文的话将直接报错，要修改底层jar包的内容
     *
     * @param content 将要生成一维码的内容
     * @return 返回生成好的一维码bitmap
     * @throws WriterException WriterException异常
     */
    public Bitmap createOneDCode(String content) throws WriterException {
        // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.CODE_128, 500, 200);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    @Override
    protected LoadingPage.LoadResult load() {

        return LoadingPage.LoadResult.success;

    }



}
