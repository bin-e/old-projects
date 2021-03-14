import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TcpIpServer {
	public static void main(String args[]) throws Exception {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(7777);
			System.out.println(getTime() + "������ �غ�Ǿ����ϴ�.");
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		while (true) {
			try {
				System.out.println(getTime() + "���� ��û�� ��ٸ��ϴ�.");
				Socket socket = serverSocket.accept();
				System.out.println(getTime() + socket.getInetAddress()
						+ " <�� ���� �����û�� ���Խ��ϴ�.");

				System.out.println("getPort() : " + socket.getPort());
				System.out.println("getLocalPort() : " + socket.getLocalPort());
				System.out.println(socket.getLocalAddress().toString());

				DataInputStream dInput = new DataInputStream(
						socket.getInputStream());
				DataOutputStream dOutput = new DataOutputStream(
						socket.getOutputStream());

				dOutput.writeUTF("������ ��");
				System.out.println(getTime() + "'������ ��' �� �����߽��ϴ�.");

				dInput.close();
				dOutput.close();
				socket.close();

			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	private static void MelonRun() {

		// ��Ȯ�� ��� ������ ���� "ProcessBuilder" �� ����Ѵ�.

		ProcessBuilder process = new ProcessBuilder();
		Map<String, String> environment = process.environment();
		process.redirectErrorStream(true);

		// ���� ��θ� �����Ѵ�.

		// process.directory(new
		// File("C:\\Program Files (x86)\\MelOn Player4\\"));
		// environment.put("name", "var");

		// ������� �κп��� ������ ������ �����Ѵ�.

		process.command("C:\\Program Files (x86)\\MelOn Player4\\Melon.exe");

		// process.

		try {
			// ������ ��ü�� ����(start()) �����ָ� �ȴ�.

			Process p = process.start();
			BufferedReader output = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = output.readLine()) != null)
				System.out.println(line);

			// The process should be done now, but wait to be sure.
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}
}
