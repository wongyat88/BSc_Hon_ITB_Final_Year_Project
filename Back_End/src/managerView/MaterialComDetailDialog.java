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

public class MaterialComDetailDialog extends JDialog implements ActionListener{
	Image titleIcon;
	int flag = 0;
	String cid = "";
	
	JPanel m,p1,p2,p3,p4;
	JLabel id,n,e,t,mon,addV;
	JTextField idf,nf,ef,tf,monf,addVf;
	
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
	
	public MaterialComDetailDialog(Frame owner,String title,boolean modal,int locale,int f,String comid){
		super(owner,title,modal);
		localeCurrent = locale;
		flag = f;
		cid = comid;
		initLocale();
		
		p1 = new JPanel(new FlowLayout());
		p2 = new JPanel(new FlowLayout());
		p3 = new JPanel(new FlowLayout());
		p4 = new JPanel(new FlowLayout());
		
		id = new JLabel(rb.getString("shop_id"));
		id.setFont(myFont.f1);
		idf = new JTextField(5);
		idf.setFont(myFont.f1);
		
		n = new JLabel(rb.getString("md_name"));
		n.setFont(myFont.f1);
		nf = new JTextField(20);
		nf.setFont(myFont.f1);
		
		p1.add(id);p1.add(idf);
		p1.add(n);p1.add(nf);
		
		e = new JLabel(rb.getString("uj_email"));
		e.setFont(myFont.f1);
		ef = new JTextField(20);
		ef.setFont(myFont.f1);
		
		t = new JLabel(rb.getString("toad_phone"));
		t.setFont(myFont.f1);
		tf = new JTextField(10);
		tf.setFont(myFont.f1);
		
		p2.add(e);p2.add(ef);
		p2.add(t);p2.add(tf);
		
		mon = new JLabel(rb.getString("mc_cost"));
		mon.setFont(myFont.f1);
		monf = new JTextField(10);
		monf.setFont(myFont.f1);
		
		addV = new JLabel(rb.getString("cp_v"));
		addV.setFont(myFont.f1);
		addVf = new JTextField(10);
		addVf.setFont(myFont.f1);
		
		p3.add(mon);p3.add(monf);
		p3.add(addV);p3.add(addVf);
		
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
		String sql = "select * from fyp_materialcom where c_id='"+cid+"'order by length(c_id),c_id ASC ";
		emp.runSql(sql);
		idf.setText(emp.getValueAt(0, 0).toString());
		nf.setText(emp.getValueAt(0, 1).toString());
		ef.setText(emp.getValueAt(0, 2).toString());
		tf.setText(emp.getValueAt(0, 3).toString());
		monf.setText(emp.getValueAt(0, 4).toString());
		addVf.setText(emp.getValueAt(0, 5).toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			EmpModel emp = new EmpModel();
			String sql = "insert into fyp_materialCom values (?,?,?,?,?,?) ";
			String[] paras = {idf.getText(),nf.getText(),ef.getText(),tf.getText(),
					monf.getText(),addVf.getText()};
			emp.updInfo(sql, paras);
			this.dispose();
		}else if(e.getSource() == up) {
			EmpModel emp = new EmpModel();
			String sql = "update fyp_materialCom set c_id=?,c_n=?,c_email=?,c_tel=?,c_money=?,"
						+ "c_add=? where c_id=? ";
			String[] paras = {idf.getText(),nf.getText(),ef.getText(),tf.getText(),
					monf.getText(),addVf.getText(),idf.getText()};
			emp.updInfo(sql, paras);
			this.dispose();
		}
	}
}
