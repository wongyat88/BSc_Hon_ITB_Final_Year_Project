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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.EmpModel;
import tools.myFont;

public class UserDetailDialog extends JDialog implements ActionListener{
	Image titleIcon;
	int flag = 0;
	String uid = "";
	
	JPanel m,p1,p2,p3,p4;
	JLabel id,n,e,p,pw,pt;
	JTextField idf,nf,ef,pf,pwf,ptf;
	
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
	
	public UserDetailDialog(Frame owner,String title,boolean modal,int locale,int f,String userid){
		super(owner,title,modal);
		localeCurrent = locale;
		flag = f;
		uid = userid;
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
		
		n = new JLabel(rb.getString("uj_un"));
		n.setFont(myFont.f1);
		nf = new JTextField(20);
		nf.setFont(myFont.f1);
		
		p1.add(id);p1.add(idf);
		p1.add(n);p1.add(nf);
		
		e = new JLabel("  "+rb.getString("uj_email"));
		e.setFont(myFont.f1);
		ef = new JTextField(20);
		ef.setFont(myFont.f1);
		
		p = new JLabel(rb.getString("toad_phone"));
		p.setFont(myFont.f1);
		pf = new JTextField(20);
		pf.setFont(myFont.f1);
		
		p2.add(e);p2.add(ef);
		p2.add(p);p2.add(pf);
		
		pw = new JLabel(rb.getString("login_pw"));
		pw.setFont(myFont.f1);
		pwf = new JTextField(20);
		pwf.setFont(myFont.f1);
		
		pt = new JLabel(rb.getString("uj_pt"));
		pt.setFont(myFont.f1);
		ptf = new JTextField(20);
		ptf.setFont(myFont.f1);
		
		p3.add(pw);p3.add(pwf);
		p3.add(pt);p3.add(ptf);
		
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
		String sql = "select * from fyp_user where user_id = '"+uid+"' ";
		emp.runSql(sql);
		idf.setText(emp.getValueAt(0, 0).toString());
		nf.setText(emp.getValueAt(0, 1).toString());
		ef.setText(emp.getValueAt(0, 2).toString());
		pf.setText(emp.getValueAt(0, 3).toString());
		pwf.setText(emp.getValueAt(0, 4).toString());
		ptf.setText(emp.getValueAt(0, 5).toString());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			EmpModel emp = new EmpModel();
			String sql = "insert into fyp_user values (userid_increase.nextval,?,?,?,?,?) ";
			String[] paras = {nf.getText(),ef.getText(),pf.getText(),pwf.getText(),ptf.getText()};
			emp.updInfo(sql, paras);
			this.dispose();
		}else if(e.getSource() == up) {
			EmpModel emp = new EmpModel();
			String sql = "update fyp_user set user_id=?,user_username=?,user_email=?,user_phone=?, "
					+ "user_password=?,user_point=? where user_id=? ";
			String[] paras = {idf.getText(),nf.getText(),ef.getText(),pf.getText(),pwf.getText(),
					ptf.getText(),idf.getText()};
			emp.updInfo(sql, paras);
			this.dispose();
		}
	}
}
