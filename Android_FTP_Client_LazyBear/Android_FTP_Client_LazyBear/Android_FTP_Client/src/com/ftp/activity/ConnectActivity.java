package com.ftp.activity;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftp.R;

public class ConnectActivity extends Activity {
	
	// Debugging
	private final String TAG = "TAG_Connect";
	
	// ArrayAdapter
	private ArrayAdapter<String> m_Adapter_Files;
	
	// FTP Member
	private FTPClient ftp_Client = null;
	private FTPFile[] ftp_Dirs = null;
	private FTPFile[] ftp_Files = null;
	private int ftp_Reply = 0;

	// FTP User Infomation
	private String ftp_Address = null;		// 210.115.226.214
	private int ftp_port = 0;				// 21
	private String ftp_Id = null;			// pi
	private String ftp_Password = null;	// koo
	private String ftp_Folder = new String();		//
	
	// Flag
	private boolean ftp_Login_Flag = false;
	
	Intent intent_getIntent;
	
	// UI
	private TextView txv_Ftp_Folder;
	private ListView lsv_Ftp_Folder;
	private Button btn_UpLoad;
	private Button btn_DownLoad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect);
		
		// R���ϰ� ����
		txv_Ftp_Folder = (TextView) findViewById(R.id.txv_ftp_folder);
		lsv_Ftp_Folder = (ListView) findViewById(R.id.lsv_ftp_folder);
		btn_UpLoad = (Button) findViewById(R.id.btn_upload);
		btn_DownLoad = (Button) findViewById(R.id.btn_download);
		
		// ������ ����
		btn_UpLoad.setOnClickListener(onClick_Connect);
		btn_DownLoad.setOnClickListener(onClick_Connect);
		
		// �ʱ�ȭ�� Ŀ�ؼ�
		init();
		ftp_Connect();
	}
	
	// �ʱ�ȭ
	private void init(){

		// get intent
		intent_getIntent = getIntent();
		ftp_Address = intent_getIntent.getExtras().getString("FTP_ADDRESS");
		ftp_port = intent_getIntent.getExtras().getInt("FTP_PORT");
		ftp_Id = intent_getIntent.getExtras().getString("FTP_ID");
		ftp_Password = intent_getIntent.getExtras().getString("FTP_PASSWORD");
		
		// init
		m_Adapter_Files = new ArrayAdapter<String>(this , R.layout.list_ftp_item);
		lsv_Ftp_Folder.setAdapter(m_Adapter_Files);
		lsv_Ftp_Folder.setOnItemClickListener(onItemClick_Connect);
	}

	// onClick ������
	View.OnClickListener onClick_Connect = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_upload:
				break;
			case R.id.btn_download:
				break;
			default:

			}
			
		}
	};
	
	// ����Ʈ�信 ����� OnItemClick������
	private OnItemClickListener onItemClick_Connect = new OnItemClickListener() {
		@Override
		// ������ Ŭ����
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			try {
				// ����Ʈ ����
				m_Adapter_Files.clear();
				m_Adapter_Files.notifyDataSetChanged();
				// Ŭ���� ���丮�� ��ġ�� ����
				ftp_Client.changeWorkingDirectory(((TextView)view).getText().toString());
				// �佺Ʈ ���
				Toast.makeText(getApplicationContext(),"FTP Directory ==>"+ftp_Client.printWorkingDirectory(), Toast.LENGTH_LONG).show();
				// ���� ���� �ѷ��ֱ�
				ftp_Folder_In();
			} catch (Exception e) {
				Log.e(TAG, ""+e.getMessage());
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			try {
				// ����Ʈ ����
				m_Adapter_Files.clear();
				m_Adapter_Files.notifyDataSetChanged();
				
				String changeFolder = new String(ftp_Client.printWorkingDirectory());
				String out = changeFolder.split("/")[(changeFolder.split("/").length)-1];
				ftp_Client.changeWorkingDirectory(changeFolder.substring(0, (changeFolder.length()-out.length())));
				
				// �佺Ʈ ���
				Toast.makeText(getApplicationContext(),"FTP Directory ==>"+ftp_Client.printWorkingDirectory(), Toast.LENGTH_LONG).show();
				// ���� ���� �ѷ��ֱ�
				ftp_Folder_In();
			} catch(ArrayIndexOutOfBoundsException aioobe){
				Toast.makeText(getApplicationContext(),"������ ���Դϴ�.", Toast.LENGTH_LONG).show();
				finish();
			} catch (Exception e) {
				Log.e(TAG + "_onKeyDown", "" + e);
			}
			
			break;
		}
		return ftp_Login_Flag;
	}

	// FTP ����
	public boolean ftp_Connect(){
		try{
			ftp_Client = new FTPClient();	// �����
			
			ftp_Client.setConnectTimeout(1000);	// Ŀ�ؼ� �ƿ�Ÿ������
			ftp_Client.connect(ftp_Address, ftp_port);	// Ŀ�ؼ�
			ftp_Login_Flag = ftp_Client.login(ftp_Id, ftp_Password);	// �α���
			ftp_Client.setFileType(FTP.BINARY_FILE_TYPE);
			ftp_Client.setControlEncoding("UTF-8");
			ftp_Client.enterLocalPassiveMode();
			ftp_Reply = ftp_Client.getReplyCode();

			// ������ �Ǿ����� Ȯ��
			if (FTPReply.isPositiveCompletion(ftp_Reply)) {
				Toast.makeText(getApplicationContext(),"FTP Connecting is Successed", Toast.LENGTH_LONG).show();
				ftp_Folder_In();
				return true;
			} else {
				ftp_Client.disconnect();
				return false;
			}
			// ����ó��
		} catch (Exception e) {
			Log.e(TAG, ""+e.getMessage());
		}
		return false;
	}

	// ����Ʈ��
	private void ftp_Folder_In() throws Exception {
		if (ftp_Login_Flag) {
			txv_Ftp_Folder.setText(ftp_Client.printWorkingDirectory());
		/*	
			String str_file = new String();
			ftp_Dirs = ftp_Client.listDirectories();
			for (int i = 0; i < ftp_Dirs.length; i++) {
//				str_file = ftp_Dirs[0].getName();
				m_Adapter_Files.add(ftp_Dirs[i].getName());
			}
*/
			ftp_Files = ftp_Client.listFiles(ftp_Folder);
			for (int i = 0; i < ftp_Files.length; i++) {
				m_Adapter_Files.add(ftp_Files[i].getName());
			}
		}
	}
	
	// �ɼǸ޴� ����
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.reconnect, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/* �翬�� */
		case R.id.menu_item_reconnect:
			Intent intent_Reconnect = new Intent(this, MainActivity.class);
			startActivity(intent_Reconnect);
			finish();
			return true;
		}
		return false;
	}
}
