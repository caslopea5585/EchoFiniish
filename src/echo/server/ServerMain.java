package echo.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerMain extends JFrame implements ActionListener{
	JPanel p_north;
	JTextField t_port;
	JButton bt_start;
	JTextArea area;
	JScrollPane scroll;
	int port =7777;
	
	ServerSocket server;//���Ӱ����� ����
	Thread thread; //���� ������ ������!!
	
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public ServerMain() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port),10);
		bt_start = new JButton("���ӽ���");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		
		p_north.add(t_port);
		p_north.add(bt_start);
		
		add(p_north,BorderLayout.NORTH);
		add(scroll,BorderLayout.CENTER);
		
		bt_start.addActionListener(this);
		
		setVisible(true);
		setBounds(600, 100, 300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	//�������� �� ����
	//������ ���� - checked Exception --> ����ó���� ����... / ������ ����..�����ϵ� �Ҽ�����
	//				- runtime Exception -->����ó���� �������� ����. / ����Ÿ�ӿ� ������ ��Ƴ�.
	public void startServer(){
		bt_start.setEnabled(false); //��ư ��Ȱ��ȭ..�����尡 ������ ������ �ʰ� �ϱ�����
	
		try {
			port = Integer.parseInt(t_port.getText());
			server = new ServerSocket(port);
			area.append("���� �غ��...\n");
			//����
			Socket socket =server.accept(); //����ζ� �Ҹ��� ���ξ������ ����!!!! ���� ������ ���or ���� ���¿� ������ �ؼ��� �ȵȴ�.
								//��??? ����δ� �������� �̺�Ʈ�� �����ϰų� ���α׷��� ��ؾ� �ϹǷ� ���ѷ�����, ��⿡ ������
								// ������ ������ �� �� ���� �ȴ�!
								//����Ʈ�� ���ߺо߿����� �̿� ���� �ڵ�� �̹� �����Ͽ��� ������ ����Ų��.
			
			area.append("���� ����...\n");
			
			//Ŭ���̾�Ʈ�� ��ȭ�� �ϱ� ���� ������ ���̹Ƿ� ������ �Ǵ� ���� ��Ʈ���� �̾ƾ�����!!
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			//Ŭ���̾�Ʈ�� �޽��� �ޱ�
			String data;
			while(true){
				data = buffr.readLine(); //Ŭ���̾�Ʈ�� �޼��� �ޱ�
				area.append("Ŭ���̾�Ʈ�� �� = "+data+"\n");
				
				buffw.write(data+"\n");		//Ŭ���̾�Ʈ�� �޽��� ������
				buffw.flush();
			}
			
			
			
			
		} catch (NumberFormatException e) { //runŸ�� exception...�������� �ʴ� ����..����Ÿ�ӿ� ������ �߻��ϴ� ���� ó��
			JOptionPane.showMessageDialog(this, "��Ʈ�� ���ڷ� �־��!!");
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		
	}
	
	public void actionPerformed(ActionEvent e) {
		thread = new Thread(){			
			public void run() {
				startServer();				
			}
			
		};
		thread.start();
		
	}
	
	public static void main(String[] args) {
		new ServerMain();
	}
}
