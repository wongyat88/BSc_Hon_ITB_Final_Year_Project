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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.EmpModel;
import tools.myFont;

public class CouponDetailPanel extends JDialog implements ActionListener{
	Image titleIcon;
	int flag = 0;
	String uid = "";
	
	JPanel m,p1,p2,p3,p4;
	JLabel id,n,nzh,d,dzh,v,fo,mon,img;
	JTextField idf,nf,nzhf,vf,fof,monf,imgf;
	JTextArea df,dzhf;
	JScrollPane scroll,scroll2;
	
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
	
	public CouponDetailPanel(Frame owner,String title,boolean modal,int locale,int f,String userid){
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
		idf = new JTextField(10);
		idf.setFont(myFont.f1);
		
		n = new JLabel("  "+rb.getString("shop_n"));
		n.setFont(myFont.f1);
		nf = new JTextField(20);
		nf.setFont(myFont.f1);
		
		nzh = new JLabel("  "+rb.getString("shop_nzh"));
		nzh.setFont(myFont.f1);
		nzhf = new JTextField(20);
		nzhf.setFont(myFont.f1);
		
		p1.add(id);p1.add(idf);
		p1.add(n);p1.add(nf);
		p1.add(nzh);p1.add(nzhf);
		
		d = new JLabel(rb.getString("c_de"));
		d.setFont(myFont.f1);
		df = new JTextArea(4,20);
		df.setFont(myFont.f1);
		df.setLineWrap(true);
		df.setWrapStyleWord(true);
		scroll = new JScrollPane(df);
		
		dzh = new JLabel("  "+rb.getString("c_de_zh"));
		dzh.setFont(myFont.f1);
		dzhf = new JTextArea(4,20);
		dzhf.setFont(myFont.f1);
		dzhf.setLineWrap(true);
		dzhf.setWrapStyleWord(true);
		scroll2 = new JScrollPane(dzhf);
		
		p2.add(d);p2.add(scroll);
		p2.add(dzh);p2.add(scroll2);
		
		v = new JLabel(rb.getString("cp_v"));
		v.setFont(myFont.f1);
		vf = new JTextField(10);
		vf.setFont(myFont.f1);
		
		fo = new JLabel("  "+rb.getString("md_id"));
		fo.setFont(myFont.f1);
		fof = new JTextField(10);
		fof.setFont(myFont.f1);
		
		mon = new JLabel("  "+rb.getString("cp_mon"));
		mon.setFont(myFont.f1);
		monf = new JTextField(10);
		monf.setFont(myFont.f1);
		
		p3.add(v);p3.add(vf);
		p3.add(fo);p3.add(fof);
		p3.add(mon);p3.add(monf);
		
		img = new JLabel(rb.getString("md_img"));
		img.setFont(myFont.f1);
		imgf = new JTextField(20);
		imgf.setFont(myFont.f1);
		
		p4.add(img);p4.add(imgf);
		
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
		this.setLocation(w/2-800, h/2-200);
		this.setSize(1600, 400);
		this.setVisible(true);
	}
	
	public void update() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_coupon where c_type = '"+uid+"' ";
		emp.runSql(sql);
		idf.setText(emp.getValueAt(0, 0).toString());
		nf.setText(emp.getValueAt(0, 1).toString());
		nzhf.setText(emp.getValueAt(0, 2).toString());
		df.setText(emp.getValueAt(0, 3).toString());
		dzhf.setText(emp.getValueAt(0, 4).toString());
		vf.setText(emp.getValueAt(0, 5).toString());
		fof.setText(emp.getValueAt(0, 6).toString());
		monf.setText(emp.getValueAt(0, 7).toString());
		imgf.setText(emp.getValueAt(0, 8).toString());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			EmpModel emp = new EmpModel();
			String sql = "insert into fyp_coupon values (?,?,?,?,?,?,?,?,?) ";
			String[] paras = {idf.getText(),nf.getText(),nzhf.getText(),df.getText(),dzhf.getText(),
					vf.getText(),fof.getText(),monf.getText(),imgf.getText()};
			emp.updInfo(sql, paras);
			this.dispose();
		}else if(e.getSource() == up) {
			EmpModel emp = new EmpModel();
			String sql = "update fyp_coupon set c_type=?,c_n=?,c_nzh=?,c_d=?,c_dzh=?, "
					+ "c_value=?,c_fid=?,c_mon=?,c_img=? where c_type=? ";
			String[] paras = {idf.getText(),nf.getText(),nzhf.getText(),df.getText(),dzhf.getText(),
					vf.getText(),fof.getText(),monf.getText(),imgf.getText(),idf.getText()};
			emp.updInfo(sql, paras);
			this.dispose();
		}
	}
}

