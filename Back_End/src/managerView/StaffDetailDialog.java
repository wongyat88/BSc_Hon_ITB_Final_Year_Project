package managerView;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.EmpModel;
import tools.myFont;

public class StaffDetailDialog extends JDialog implements ActionListener{
	Image titleIcon;
	int flag = 0;
	String sid = "";
	
	JPanel m,p1,p2,p3,p4;
	JLabel id,g,p,n,nzh,r,rzh,pw;
	JTextField idf,nf,nzhf,rf,rzhf,pwf;
	JComboBox<String> gf,pf;
	
	JButton add,up;
	
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
	
	public StaffDetailDialog(Frame owner,String title,boolean modal,int locale,int f,String staffid){
		super(owner,title,modal);
		localeCurrent = locale;
		flag = f;
		sid = staffid;
		initLocale();
		
		p1 = new JPanel(new FlowLayout());
		p2 = new JPanel(new FlowLayout());
		p3 = new JPanel(new FlowLayout());
		p4 = new JPanel(new FlowLayout());
		
		id = new JLabel(rb.getString("shop_id"));
		id.setFont(myFont.f1);
		idf = new JTextField(20);
		idf.setEnabled(false);
		idf.setFont(myFont.f1);
		
		g = new JLabel("  "+rb.getString("hr_g"));
		g.setFont(myFont.f1);
		String[] gg = {rb.getString("hr_m"),rb.getString("hr_f")};
		gf = new JComboBox<String>(gg);
		gf.setFont(myFont.f1);
		
		p = new JLabel("  "+rb.getString("hr_p"));
		p.setFont(myFont.f1);
		String[] pp = {rb.getString("hr_l"),rb.getString("hr_h")};
		pf = new JComboBox<String>(pp);
		pf.setFont(myFont.f1);
		
		p1.add(id);p1.add(idf);
		p1.add(g);p1.add(gf);
		p1.add(p);p1.add(pf);
		
		n = new JLabel(rb.getString("shop_n"));
		n.setFont(myFont.f1);
		nf = new JTextField(20);
		nf.setFont(myFont.f1);
		
		nzh = new JLabel("  "+rb.getString("shop_nzh"));
		nzh.setFont(myFont.f1);
		nzhf = new JTextField(20);
		nzhf.setFont(myFont.f1);
		
		p2.add(n);p2.add(nf);
		p2.add(nzh);p2.add(nzhf);
		
		r = new JLabel(rb.getString("hr_r"));
		r.setFont(myFont.f1);
		rf = new JTextField(20);
		rf.setFont(myFont.f1);
		
		rzh = new JLabel("  "+rb.getString("hr_rzh"));
		rzh.setFont(myFont.f1);
		rzhf = new JTextField(20);
		rzhf.setFont(myFont.f1);
		
		p3.add(r);p3.add(rf);
		p3.add(rzh);p3.add(rzhf);
		
		pw = new JLabel(rb.getString("login_pw"));
		pw.setFont(myFont.f1);
		pwf = new JTextField(20);
		pwf.setFont(myFont.f1);
		
		p4.add(pw);p4.add(pwf);
		
		add = new JButton(rb.getString("fj_add"));
		add.setFont(myFont.f1);
		add.addActionListener(this);
		up = new JButton(rb.getString("fj_up"));
		up.setFont(myFont.f1);
		up.addActionListener(this);
		
		m = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		m.add(p1,gbc);
		gbc.gridy = 1;
		m.add(p2,gbc);
		gbc.gridy = 2;
		m.add(p3,gbc);
		gbc.gridy = 3;
		m.add(p4,gbc);
		
		this.add(m,"North");
		if(flag==1) {
			this.add(add,"South");
		}else {
			update();
			this.add(up,"South");
		}
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setIconImage(titleIcon);
		this.setLocation(w/2-600, h/2-150);
		this.setSize(1200, 300);
		this.setVisible(true);
	}
	
	public void update() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_staff where staff_id = '"+sid+"' ";
		emp.runSql(sql);
		idf.setText(emp.getValueAt(0, 0).toString());
		nf.setText(emp.getValueAt(0, 1).toString());
		nzhf.setText(emp.getValueAt(0, 2).toString());
		pwf.setText(emp.getValueAt(0, 3).toString());
		if(emp.getValueAt(0, 4).toString().equals("m")) {
			gf.setSelectedIndex(0);
		}else {
			gf.setSelectedIndex(1);
		}
		rf.setText(emp.getValueAt(0, 5).toString());
		rzhf.setText(emp.getValueAt(0, 6).toString());
		pf.setSelectedIndex(Integer.parseInt(emp.getValueAt(0, 7).toString())-1);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			EmpModel emp = new EmpModel();
			String sql = "insert into fyp_staff values (staffid_increase.nextval,?,?,?,?,?,?,?) ";
			int b = gf.getSelectedIndex();
			String G = "";
			if(b==0) {
				G = "m";
			}else {
				G = "f";
			}
			String[] paras = {nf.getText(),nzhf.getText(),pwf.getText(),G,rf.getText(),rzhf.getText(),
					(pf.getSelectedIndex()+1)+""};
			emp.updInfo(sql, paras);
			this.dispose();
		}else if(e.getSource() == up) {
			EmpModel emp = new EmpModel();
			String sql = "update fyp_staff set staff_id=?,staff_name=?,staff_name_zh=?,staff_pw=?, "
					+ "staff_gender=?,staff_role=?,staff_role_zh=?,staff_permit=? where staff_id=? ";
			int b = gf.getSelectedIndex();
			String G = "";
			if(b==0) {
				G = "m";
			}else {
				G = "f";
			}
			String[] paras = {idf.getText(),nf.getText(),nzhf.getText(),pwf.getText(),G,
					rf.getText(),rzhf.getText(),(pf.getSelectedIndex()+1)+"",idf.getText()};
			emp.updInfo(sql, paras);
			this.dispose();
		}
	}
}
