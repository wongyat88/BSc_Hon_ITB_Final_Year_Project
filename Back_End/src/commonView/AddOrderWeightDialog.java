package commonView;

import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import tools.myFont;

public class AddOrderWeightDialog extends JDialog implements ActionListener {
	public static float weight = 0;
	
	JLabel l1;
	JTextField tf1;
	JButton go;
	
	Image titleIcon;
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

	public static float getWeight() {
		return weight;
	}

	public AddOrderWeightDialog(Frame owner,String title,boolean modal,int locale) {
		super(owner,title,modal);
		localeCurrent = locale;
		initLocale();
		
		l1 = new JLabel(rb.getString("afo_weight"));
		l1.setFont(myFont.f1);
		
		tf1 = new JTextField();
		tf1.setFont(myFont.f1);
		
		go = new JButton(rb.getString("common_ok"));
		go.setFont(myFont.f1);
		go.addActionListener(this);
		
		this.add(l1,"North");
		this.add(tf1,"Center");
		this.add(go,"South");
		
		try {  
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-100, h/2-100);
		this.setSize(200,200);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			weight = Float.parseFloat(tf1.getText());
			this.dispose();
		}catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
