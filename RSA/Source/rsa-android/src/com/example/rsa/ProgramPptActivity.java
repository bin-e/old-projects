package com.example.rsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ProgramPptActivity extends Activity {

	ConnectActivity ca;			// TCP ����� ���� Connect ��ü

	/* ��� 3��ư */
	Button btn_Ppt_File_Open;	// Ctrl + O		���Ϻҷ�����
	Button btn_Ppt_Mouse;		// 				���콺 ȣ��
	Button btn_Ppt_Power;		//				on / off
	
	/* �ߴ� */
	ImageView img_Ppt;			//				�߾� �̹���
	Button btn_Ppt_Show_Start;	// F5(! ESC)	Show�� ������, (���۵��ִ°�� ������)

	/* �ϴ� Show ��Ʈ�� 4��ư */
	Button btn_Ppt_Show_Home;	// Home			Show�� ó�����ΰ�
	Button btn_Ppt_Show_Back;	// PageUp		Show�� ��ĭ�ڷ�
	Button btn_Ppt_Show_Front;	// PageDown		Show�� ��ĭ������
	Button btn_Ppt_Show_End;	// End			Show�� �ǵڷ�
	
	boolean state_Ppt_File_Open = false;	// PPT ����â�� ����������
	boolean state_Ppt_Power = false;		// PPT on / off ���� �ľ�
	boolean state_Ppt_Show = false;		// PPT �����̵�� start / exit ��� �ľ�
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program_ppt);

		ca = new ConnectActivity();

		/* R���Ϸκ��� �ּҰ� ó�� */
		btn_Ppt_File_Open = (Button) findViewById(R.id.button_ppt_file_open);
		btn_Ppt_Mouse = (Button) findViewById(R.id.button_ppt_mouse);
		btn_Ppt_Power = (Button) findViewById(R.id.button_ppt_on);

		img_Ppt = (ImageView) findViewById(R.id.imageView_ppt);
		btn_Ppt_Show_Start = (Button) findViewById(R.id.button_ppt_show_start);

		btn_Ppt_Show_Home = (Button) findViewById(R.id.button_ppt_show_home);
		btn_Ppt_Show_Back = (Button) findViewById(R.id.button_ppt_show_back);
		btn_Ppt_Show_Front = (Button) findViewById(R.id.button_ppt_show_front);
		btn_Ppt_Show_End = (Button) findViewById(R.id.button_ppt_show_end);
		
		/* ��� ��ư onClick������ ���� */
		btn_Ppt_File_Open.setOnClickListener(onClick);
		btn_Ppt_Mouse.setOnClickListener(onClick);
		btn_Ppt_Power.setOnClickListener(onClick);

		btn_Ppt_Show_Start.setOnClickListener(onClick);

		btn_Ppt_Show_Home.setOnClickListener(onClick);
		btn_Ppt_Show_Back.setOnClickListener(onClick);
		btn_Ppt_Show_Front.setOnClickListener(onClick);
		btn_Ppt_Show_End.setOnClickListener(onClick);
	}

	View.OnClickListener onClick = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_ppt_file_open:
				if(!state_Ppt_File_Open){
					ca.sendMessages("PPT_FILE_OPEN");
					state_Ppt_File_Open = true;
					btn_Ppt_File_Open.setText("Cancel");
				} else {
					ca.sendMessages("PPT_SHOW_EXIT");
					state_Ppt_File_Open = false;
					btn_Ppt_File_Open.setText("Open");
				}
				break;
			case R.id.button_ppt_mouse:
				final Intent it_PptMouse = new Intent(ProgramPptActivity.this, MouseActivity.class);
				startActivity(it_PptMouse);		// ���콺 ��Ƽ��Ƽ�� ��ȯ
				break;
			case R.id.button_ppt_on:
				if (!state_Ppt_Power) {
					ca.sendMessages("PPT_ON");
					state_Ppt_Power = true;
					btn_Ppt_Power.setText("off");
				} else {
					ca.sendMessages("PPT_OFF");
					state_Ppt_Power = false;
					btn_Ppt_Power.setText("on");
				}
				break;

			case R.id.button_ppt_show_start:
				if (!state_Ppt_Show) {
					ca.sendMessages("PPT_SHOW_START");
					state_Ppt_Show = true;
					btn_Ppt_Show_Start.setText("show - exit");
				} else {
					ca.sendMessages("PPT_SHOW_EXIT");
					state_Ppt_Show = false;
					btn_Ppt_Show_Start.setText("show - start");
				}
				break;

			case R.id.button_ppt_show_home:
				ca.sendMessages("PPT_SHOW_HOME");
				break;
			case R.id.button_ppt_show_back:
				ca.sendMessages("PPT_SHOW_BACK");
				break;
			case R.id.button_ppt_show_front:
				ca.sendMessages("PPT_SHOW_FRONT");
				break;
			case R.id.button_ppt_show_end:
				ca.sendMessages("PPT_SHOW_END");
				break;
			}
		}
	};
}