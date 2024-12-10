package commonView;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.EmpModel;
import tools.myFont;

public class ChangePhoneDialog extends JDialog implements ActionListener {
	
	static int nP = 0;
	
	String oid;
	String sid;
	String oPhone = "";
	String nPhone = "";
	
	JPanel m,p1,p2,p3;
	JLabel l1,l2;
	JTextField t1,t2;
	JButton b1;
	
	Image titleIcon;
	ResourceBundle rb;
	int localeCurrent = 0;
	
	String newOid;
	
	public void initLocale(){
    	if(localeCurrent == 0) {
    		this.setLocale(Locale.getDefault());
    		rb = ResourceBundle.getBundle("translation/my", Locale.getDefault());
    	}else {
    		this.setLocale(new Locale("zh", "TW"));
    		rb = ResourceBundle.getBundle("translation/my", new Locale("zh", "TW"));
    	}
    }
	
	public void MainPanel() {
		
		m = new JPanel(new GridLayout(3,0));
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		EmpModel emp = new EmpModel();
		String sql = "select order_phone from fyp_order "
				+ " where order_id = '"+oid+"' and order_sid = '"+sid+"' and order_flag=1 ";
		emp.runSql(sql);
		oPhone = emp.getValueAt(0, 0).toString();
		
		l1 = new JLabel(rb.getString("cpd_oPhone"));
		l1.setFont(myFont.f1);
		l2 = new JLabel(rb.getString("cpd_nPhone"));
		l2.setFont(myFont.f1);
		t1 = new JTextField(oPhone,8);
		t1.setEnabled(false);
		t1.setFont(myFont.f1);
		t2 = new JTextField(8);
		t2.setFont(myFont.f1);
		b1 = new JButton(rb.getString("common_ok"));
		b1.setFont(myFont.f1);
		b1.addActionListener(this);
		
		p1.add(l1);
		p1.add(t1);
		p2.add(l2);
		p2.add(t2);
		p3.add(b1);
		
		m.add(p1);
		m.add(p2);
		m.add(p3);
		
		this.add(m);
	}
	
	public ChangePhoneDialog(Frame owner,String title,boolean modal,
			int locale,String shopid,String orderid) {
		super(owner,title,modal);
		localeCurrent = locale;
		initLocale();
		
		oid = orderid;
		sid = shopid;
		
		MainPanel();
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-205, h/2- 125);
		this.setSize(400, 250);
		this.setVisible(true);
	}
	
	public static int newPhoneGetValue() {
		return nP;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b1) {
			try {
				int newPhone = Integer.parseInt(t2.getText());
				EmpModel emp = new EmpModel();
				String sql = "update fyp_order set order_phone=? "
						+ "where order_id=? and order_sid=? and order_flag=1 ";
				String[] paras = {newPhone+"", oid, sid};
				emp.updInfo(sql, paras);
				nP = newPhone;
				this.dispose();
				
			}catch (Exception e2) {
				JLabel w1 = new JLabel(rb.getString("w_wInput"));
		    	w1.setFont(myFont.f3);
				JOptionPane.showMessageDialog(
						this,w1,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
