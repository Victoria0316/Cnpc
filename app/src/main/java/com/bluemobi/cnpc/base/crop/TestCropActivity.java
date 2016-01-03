package com.bluemobi.cnpc.base.crop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.bluemobi.cnpc.R;
import com.bluemobi.cnpc.base.BaseActivity;
import com.bluemobi.cnpc.view.LoadingPage;

import java.io.File;

/**
 * Created by wangzhijun on 2015/7/2.
 */
public class TestCropActivity extends BaseActivity {
    private ImageView resultView;
    private Uri outputFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testcrop);
        resultView = (ImageView) findViewById(R.id.result_image);
    }

    @Override
    protected void initBase() {

    }

    @Override
    protected void successViewCompleted(View successView) {

    }



    @Override
    protected LoadingPage.LoadResult load() {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_select) {
            resultView.setImageDrawable(null);
            Crop.pickImage(this);
            return true;
        }
        if (item.getItemId() == R.id.action_select_camera) {
            resultView.setImageDrawable(null);
//            Crop.pickImage(this);
//            File file = new File(Environment.getExternalStorageDirectory(),
//                    "cmrp" + ".jpg");
//            outputFileUri = Uri.fromFile(file);
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//            startActivityForResult(intent, Crop.REQUEST_CAMERA);
            Crop.pickImageFromCamera(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        } else if (requestCode == Crop.REQUEST_CAMERA && resultCode == RESULT_OK) {
            beginCrop(Crop.outputFileUri);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            resultView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
