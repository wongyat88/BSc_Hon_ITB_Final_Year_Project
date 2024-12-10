package managerView;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.EmpModel;
import tools.myFont;

public class CatAddDialog extends JDialog implements ActionListener{
	Image titleIcon;
	
	JPanel p1,p2,p3,p4,p5;
	JLabel l1,l2,l3,l4;
	JTextField tf1,tf2,tf3,tf4;
	JButton b;
	
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
	
	public CatAddDialog(Frame owner,String title,boolean modal,int locale) {
		super(owner,title,modal);
		localeCurrent = locale;
		initLocale();
		
		mainPanel();
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setIconImage(titleIcon);
		this.setLocation(w/2-250, h/2-150);
		this.setSize(500, 300);
		this.setVisible(true);
	}

	public void mainPanel() {
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		l1 = new JLabel(rb.getString("cad_id"),JLabel.CENTER);
		l1.setFont(myFont.f1);
		tf1 = new JTextField(10);
		tf1.setFont(myFont.f1);
		p1.add(l1);
		p1.add(tf1);
		
		l2 = new JLabel(rb.getString("cad_name"),JLabel.CENTER);
		l2.setFont(myFont.f1);
		tf2 = new JTextField(10);
		tf2.setFont(myFont.f1);
		p2.add(l2);
		p2.add(tf2);
		
		l3 = new JLabel(rb.getString("cad_nameZh"),JLabel.CENTER);
		l3.setFont(myFont.f1);
		tf3 = new JTextField(10);
		tf3.setFont(myFont.f1);
		p3.add(l3);
		p3.add(tf3);
		
		l4 = new JLabel(rb.getString("cad_flag"),JLabel.CENTER);
		l4.setFont(myFont.f1);
		tf4 = new JTextField(10);
		tf4.setFont(myFont.f1);
		p4.add(l4);
		p4.add(tf4);
		
		b = new JButton(rb.getString("common_ok"));
		b.setFont(myFont.f1);
		b.addActionListener(this);
		p5.add(b);
		
		this.setLayout(new GridLayout(5,1));
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
		this.add(p5);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b) {
			try {
				EmpModel emp = new EmpModel();
				String sql = "insert into fyp_cat values (?,?,?,?) ";
				String[] paras = {tf1.getText(),tf2.getText(),tf3.getText(),tf4.getText()};
				emp.updInfo(sql, paras);
				this.dispose();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
