package com.ljstudio.android.magicdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ljstudio.android.magicdemo.common.Direction;
import com.ljstudio.android.magicdemo.common.MagicActivity;
import com.ljstudio.android.magicdemo.updater.MacWindowAnimUpdater;
import com.ljstudio.android.magicdemo.updater.VortexAnimUpdater;
import com.ljstudio.android.magicsurfaceview.MagicScene;
import com.ljstudio.android.magicsurfaceview.MagicSurface;
import com.ljstudio.android.magicsurfaceview.MagicUpdater;
import com.ljstudio.android.magicsurfaceview.MagicUpdaterListener;
import com.ljstudio.android.magicsurfaceview.MagicSurfaceView;

public class VortexAnimActivity extends MagicActivity {

    private MagicSurfaceView mSurfaceView;
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vortex_anim);
        setTitle("VortexAnim");

        mSurfaceView = (MagicSurfaceView) findViewById(R.id.surface_view);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        findViewById(R.id.view_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTvContent.getVisibility() == View.VISIBLE) {
                    hide();
                } else {
                    show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSurfaceView.onDestroy();
    }

    private void show() {
        VortexAnimUpdater updater = new VortexAnimUpdater(false);
        updater.addListener(new MagicUpdaterListener() {
            @Override
            public void onStart() {
                mTvContent.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onStop() {
                mTvContent.setVisibility(View.VISIBLE);
                mSurfaceView.setVisibility(View.GONE);
                // 释放资源
                mSurfaceView.release();
            }
        });
        MagicSurface s = new MagicSurface(mTvContent)
                .setGrid(60, 60)
                .drawGrid(false)
                .setModelUpdater(updater);
        mSurfaceView.setVisibility(View.VISIBLE);
        mSurfaceView.render(s);
    }

    private void hide() {
        VortexAnimUpdater updater = new VortexAnimUpdater(true);
        updater.addListener(new MagicUpdaterListener() {
            @Override
            public void onStart() {
                mTvContent.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onStop() {
                mSurfaceView.setVisibility(View.GONE);
                // 释放场景资源
                mSurfaceView.release();
            }
        });
        MagicSurface s = new MagicSurface(mTvContent)
                .setGrid(60, 60)
                .drawGrid(false)
                .setModelUpdater(updater);
        mSurfaceView.setVisibility(View.VISIBLE);
        mSurfaceView.render(s);
    }

    // 设置Page转场动画
    @Override
    protected MagicUpdater getPageUpdater(boolean isHide) {
        return new VortexAnimUpdater(isHide);
    }

    @Override
    protected int pageAnimRowCount() {
        return 60;
    }

    @Override
    protected int pageAnimColCount() {
        return 60;
    }
}
