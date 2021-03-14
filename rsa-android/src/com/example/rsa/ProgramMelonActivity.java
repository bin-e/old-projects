package com.example.rsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;

public class ProgramMelonActivity extends Activity {

	ConnectActivity ca;		// TCP ����� ���� Connect ��ü

	/* ��� ��ư */
	Button btn_Melon_Mouse;	// 					���콺ȣ��
	Button btn_Melon_Power; // 					ON / OFF

	ImageView img_Melon;	// 					�߾� �̹���

	/* �ߴ� 5�� ��ư */
	Button btn_Melon_Prev;	// Ctrl + Left		������
	Button btn_Melon_Back;	// Alt + Left		5�� ������
	Button btn_Melon_Play;	// Space			����, �Ͻ�����
	Button btn_Melon_Front;	// Alt + Right		5�� ������
	Button btn_Melon_Next;	// Ctrl + Right		������

	/* �ϴ� ��Ʈ ��ư */
	Button btn_Melon_Mute;	// F6				��Ʈ, ���Ʈ

	/* ���������� ����̽� ������ư���� */
	// MELON_VLUME_UP		// Ctrl + Up		���� ��
	// MELON_VLUME_DOWN		// Ctrl + Down		���� �ٿ�

	boolean state_Melon_Power = false;	// ��� on/off ���� �ľ�
	boolean state_Melon_Mute = false;	// ��� mute/unMute ���� �ľ�
	boolean state_Melon_Play = false;	// ��� play/pause ���� �ľ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program_melon);

		ca = new ConnectActivity();

		/* R���Ϸκ��� �ּҰ� ó�� */
		btn_Melon_Mouse = (Button) findViewById(R.id.button_melon_mouse);
		btn_Melon_Power = (Button) findViewById(R.id.button_melon_on);

		img_Melon = (ImageView) findViewById(R.id.imageView_melon);

		btn_Melon_Prev = (Button) findViewById(R.id.button_melon_prev);
		btn_Melon_Back = (Button) findViewById(R.id.button_melon_back);
		btn_Melon_Play = (Button) findViewById(R.id.button_melon_play);
		btn_Melon_Front = (Button) findViewById(R.id.button_melon_front);
		btn_Melon_Next = (Button) findViewById(R.id.button_melon_next);

		btn_Melon_Mute = (Button) findViewById(R.id.button_melon_mute);

		/* ��� ��ư onClick������ ���� */
		btn_Melon_Mouse.setOnClickListener(onClick);
		btn_Melon_Power.setOnClickListener(onClick);

		btn_Melon_Prev.setOnClickListener(onClick);
		btn_Melon_Back.setOnClickListener(onClick);
		btn_Melon_Play.setOnClickListener(onClick);
		btn_Melon_Front.setOnClickListener(onClick);
		btn_Melon_Next.setOnClickListener(onClick);

		btn_Melon_Mute.setOnClickListener(onClick);
	}

	View.OnClickListener onClick = new View.OnClickListener() {	// onClick ������
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_melon_mouse:		// ���콺 ��ư Ŭ���� 
				final Intent it_MelonMouse = new Intent(ProgramMelonActivity.this, MouseActivity.class);
				startActivity(it_MelonMouse);		// ���콺 ��Ƽ��Ƽ�� ��ȯ
				break;
			case R.id.button_melon_on:				// ���� ��ư Ŭ��
				if(!state_Melon_Power){				// �ʱⰪ�� false( == ������ ���������� )
					ca.sendMessages("MELON_ON");	// ������ �Ѱ�
					state_Melon_Power = true;		// �������� true( == �����ִ� ���� )�� ��ȯ
				} else{								// �׷��� �������
					ca.sendMessages("MELON_OFF");	// ������ ����
					state_Melon_Power = false;		// �������� false( == �����ִ� ����)�� ����ȯ
				}
				break;

			case R.id.button_melon_prev:
				ca.sendMessages("MELON_PREV");
				break;
			case R.id.button_melon_back:
				ca.sendMessages("MELON_BACK");
				break;
			case R.id.button_melon_play:
				if(!state_Melon_Play){
					btn_Melon_Play.setBackgroundResource(R.drawable.button_melon_play);
					state_Melon_Play = true;
				} else {
					btn_Melon_Play.setBackgroundResource(R.drawable.button_melon_pause);
					state_Melon_Play = false;
				}
				ca.sendMessages("MELON_PLAY");
				break;
			case R.id.button_melon_front:
				ca.sendMessages("MELON_FRONT");
				break;
			case R.id.button_melon_next:
				ca.sendMessages("MELON_NEXT");
				break;

			case R.id.button_melon_mute:
				if(!state_Melon_Mute){
					btn_Melon_Mute.setBackgroundResource(R.drawable.button_melon_mute);
					state_Melon_Mute = true;
				} else {
					btn_Melon_Mute.setBackgroundResource(R.drawable.button_melon_unmute);
					state_Melon_Mute = false;
				}
				ca.sendMessages("MELON_MUTE");
				break;
			}
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:			// ����̽� ������ư ��
			ca.sendMessages("MELON_VLUME_UP");
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:			// ����̽� ������ư �ٿ�
			ca.sendMessages("MELON_VLUME_DOWN");
			break;
		case KeyEvent.KEYCODE_BACK:					// ����̽� ��ҹ�ư
			finish();
		}
		return true;
	}
}
