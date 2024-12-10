package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import commonView.CommonMain;
import kitchenView.KitchenMain;
import managerView.ManagerMain;
import tools.myFont;

public class LoadingScene extends JWindow implements Runnable {
	
	JPanel p1;
	JLabel l1,l2,l3;
	Border margin;
	int userid = 0;
	String shopid = "";
	String permit = "";
	
	ResourceBundle rb;
	int localeCurrent = 0;
	
	public void initLocale(){
    	if(localeCurrent == 0) {
    		this.setLocale(Locale.getDefault());
    		rb = ResourceBundle.getBundle("translation/my", Locale.getDefault());
    	}else {
    		this.setLocale(new Locale("zh", "TW"));
    		rb = ResourceBundle.getBundle("translation/my", new Locale("zh", "TW"));
    		}
    }
	
	public LoadingScene(String position, int locale, int uid, String sid) {
		localeCurrent = locale;
		initLocale();
		
		userid = uid;
		shopid = sid;
		permit = position;
		
		margin = new EmptyBorder(20, 50, 20, 50);
		p1 = new JPanel(new BorderLayout());
		l1 = new JLabel(rb.getString("ls_load"));
		l1.setFont(myFont.f1);
		l1.setHorizontalAlignment(JLabel.CENTER);
		l1.setVerticalAlignment(JLabel.CENTER);
		l2 = new JLabel(new ImageIcon("images/loading/loading.gif"));
		l3 = new JLabel(rb.getString("ls_percent"));
		l3.setFont(myFont.f1);
		l3.setHorizontalAlignment(JLabel.CENTER);
		l3.setVerticalAlignment(JLabel.CENTER);
		
		p1.add(l1,BorderLayout.NORTH);
		p1.add(l2,BorderLayout.CENTER);
		p1.add(l3,BorderLayout.SOUTH);
		p1.setBorder(margin);
		p1.setBackground(new Color(241, 242, 244));
		
		start();
		
		this.add(p1);
		
		this.setSize(300,300);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width/2-150, height/2-150);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		LoadingScene loadingScene = new LoadingScene("c",1,1,"shop1");
		Thread x = new Thread(loadingScene);
		x.start();
	}
	
	private void start() {
		SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				for(int x=0; x<101; x++) {
					try {
						Thread.sleep(40);
						l3.setText(Integer.toString(x)+"%");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		};
		worker.execute();
	}

	@Override
	public void run() {
		this.toFront();
		try {
			Thread.sleep(1000);
			if(permit.equals("c")) {
				CommonMain cm = new CommonMain(localeCurrent,userid,shopid);
				Thread t = new Thread(cm);
				t.start();
			}else if(permit.equals("m")){
				new ManagerMain(localeCurrent,userid);
			}else if(permit.equals("k")){
				KitchenMain km = new KitchenMain(localeCurrent,userid,shopid);
				Thread upT = new Thread(km);
				upT.start();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.dispose();
	}
}
