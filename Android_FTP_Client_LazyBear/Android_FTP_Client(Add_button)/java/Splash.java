package com.ftp;

import com.ftp.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends Activity {

	Thread thd_Splash;

	// UI
	TextView txv_Wait;
	ProgressBar pgb_Splash;
	int progress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// R���ϰ� ����
		txv_Wait = (TextView) findViewById(R.id.txv_wait);
		pgb_Splash = (ProgressBar) findViewById(R.id.pgb_splash);

		// ���μ��� �ʱ�ȭ
		pgb_Splash.setProgress(0);

		// ���÷��� �ϸ��� ���� ������ ����
		thd_Splash = new Thread(new Runnable() {
			public void run() {
				progress = 0;
				pgb_Splash.setVisibility(View.VISIBLE);
				txv_Wait.setVisibility(View.VISIBLE);

				while (progress < 100) {
					progress += 34; // �Ѿ �ð�����
					handler.sendMessage(handler.obtainMessage());
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				/** ���÷��� �� �Ѿ�� �κ� */
				Intent endSplash = new Intent(Splash.this,	MainActivity.class);
				startActivity(endSplash);
				finish();
			}
		});
		thd_Splash.start();

		/*
		// Handler.postDelayed �� �̿��Ͽ� ������ �ð��� �༭ �Ѿ
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

			}
		}, 3000);
		 */
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			pgb_Splash.incrementProgressBy(progress);
		}
	};
}
