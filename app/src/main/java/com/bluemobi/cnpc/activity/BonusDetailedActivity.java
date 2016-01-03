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
import com.bluemobi.cnpc.network.model.BonusItem;
import com.bluemobi.cnpc.view.LoadingPage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyn on 2015/8/9.
 */

@ContentView(R.layout.activity_bonus_detailed)
@PageName(R.string.mine_bonus_number)
public class BonusDetailedActivity extends BaseActivity{

    private BonusItem item;

    @Bind(R.id.img)
    protected TextView amount;

    @Bind(R.id.title)
    protected TextView title;

    @Bind(R.id.time)
    protected TextView time;

    @Bind(R.id.limit)
    protected TextView limit;

    @Bind(R.id.qc_code)
    protected ImageView qc_code;

    @Bind(R.id.one_code)
    protected ImageView one_code;

    @Override
    protected void initBase() {

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.mine_bonus_number, R.drawable.return_arrow, true);
        showLoadingPage(false);
    }

    @Override
    protected void successViewCompleted(View successView) {
        item = (BonusItem)getIntent().getSerializableExtra("item");

        ButterKnife.bind(this, successView);
        if(item != null){
            amount.setText("￥" + item.getBonusDiscount());
            title.setText(item.getBonusName());
            limit.setText("满" + item.getMinOrderAmount() + "元使用");
            time.setText(item.getBonusStartTime() + "至" +
                    item.getBonusEndTime());
            injectImg();
        }else{//TODO:

        }
    }

    private void injectImg() {
        Bitmap qcBmp = null;
        Bitmap oneBmp = null;
        try {
            if (item.getBarcode() != null && !"".equals(item.getBarcode())) {
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


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
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
}
