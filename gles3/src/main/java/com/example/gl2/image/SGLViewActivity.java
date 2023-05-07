/*
 *
 * SGLViewActivity.java
 *
 * Created by Wuwang on 2016/10/15
 */
package com.example.gl2.image;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gl2.R;
import com.example.gl2.image.filter.ColorFilter;
import com.example.gl2.image.filter.ContrastColorFilter;

/**
 * Description:
 */
public class SGLViewActivity extends AppCompatActivity {

    private SGLView mGLView;
    private boolean isHalf = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        mGLView = (SGLView) findViewById(R.id.glView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.mDeal) {
            isHalf = !isHalf;
            if (isHalf) {
                item.setTitle("处理一半");
            } else {
                item.setTitle("全部处理");
            }
            mGLView.getRender().refresh();
        } else if (itemId == R.id.mDefault) {
            mGLView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.NONE));
        } else if (itemId == R.id.mGray) {
            mGLView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.GRAY));
        } else if (itemId == R.id.mCool) {
            mGLView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.COOL));
        } else if (itemId == R.id.mWarm) {
            mGLView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.WARM));
        } else if (itemId == R.id.mBlur) {
            mGLView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.BLUR));
        } else if (itemId == R.id.mMagn) {
            mGLView.setFilter(new ContrastColorFilter(this, ColorFilter.Filter.MAGN));
        }
        mGLView.getRender().getFilter().setHalf(isHalf);
        mGLView.requestRender();
        return super.onOptionsItemSelected(item);
    }
}
