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

public class ShopTableDetailDialog extends JDialog implements ActionListener{
	Image titleIcon;
	int fff = 0;
	
	int oldnof = 0;
	
	String sid = "";
	String tid = "";
	
	JPanel m,p1,p2;
	JLabel top,id,shop,no,now,f;
	JTextField idf,sf,nof,nowf;
	JComboBox<String> ff;
	JButton go,up;
	
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
	
	public ShopTableDetailDialog(Frame owner,String title,boolean modal,int locale,int flag,
			String shopid,String tableid) {
		super(owner,title,modal);
		fff = flag;
		sid = shopid;
		tid = tableid;
		localeCurrent = locale;
		initLocale();
		
		m = new JPanel(new GridBagLayout());
		
		top = new JLabel(rb.getString("st_ready"));
		top.setFont(myFont.f1);
		
		p1 = new JPanel(new FlowLayout());
		p2 = new JPanel(new FlowLayout());
		
		id = new JLabel(rb.getString("shop_id"));
		id.setFont(myFont.f1);
		idf = new JTextField(10);
		idf.setFont(myFont.f1);
		
		shop = new JLabel("  "+rb.getString("shop_sid"));
		shop.setFont(myFont.f1);
		sf = new JTextField(10);
		sf.setText(sid);
		sf.setEnabled(false);
		sf.setFont(myFont.f1);
		
		f = new JLabel("  "+rb.getString("shop_f"));
		f.setFont(myFont.f1);
		String[] StatusStr = { rb.getString("md_av"),rb.getString("st_nf"),rb.getString("md_nav")};
		ff = new JComboBox<String>(StatusStr);
		ff.setFont(myFont.f1);
		ff.setSelectedIndex(0);
		ff.addActionListener(this);
		
		p1.add(id);p1.add(idf);
		p1.add(shop);p1.add(sf);
		p1.add(f);p1.add(ff);
		
		no = new JLabel(rb.getString("shop_no"));
		no.setFont(myFont.f1);
		nof = new JTextField(10);
		nof.setFont(myFont.f1);
		
		now = new JLabel("  "+rb.getString("shop_now"));
		now.setFont(myFont.f1);
		nowf = new JTextField(10);
		nowf.setFont(myFont.f1);
		
		p2.add(no);p2.add(nof);
		p2.add(now);p2.add(nowf);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		m.add(p1,gbc);
		gbc.gridy = 1;
		m.add(p2,gbc);
		
		go = new JButton(rb.getString("common_ok"));
		go.setFont(myFont.f1);
		go.addActionListener(this);
		
		up = new JButton(rb.getString("fj_up"));
		up.setFont(myFont.f1);
		up.addActionListener(this);
		
		top = new JLabel(rb.getString("st_ready"));
		top.setFont(myFont.f1);
		
		this.add(top,"North");
		this.add(m,"Center");
		if(flag==2) {
			updateData();
			this.add(up,"South");
		}else {
			this.add(go,"South");
		}
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setIconImage(titleIcon);
		this.setLocation(w/2-500, h/2-150);
		this.setSize(1000, 300);
		this.setVisible(true);
	}
	
	public void updateData() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_table where table_shop='"+sid+"' and table_id='"+tid+"' ";
		emp.runSql(sql);
		idf.setText(emp.getValueAt(0, 0).toString());
		sf.setText(emp.getValueAt(0, 1).toString());
		oldnof = Integer.parseInt(emp.getValueAt(0, 2).toString());
		nof.setText(emp.getValueAt(0, 2).toString());
		ff.setSelectedIndex(Integer.parseInt(emp.getValueAt(0, 3).toString())-1);
		nowf.setText(emp.getValueAt(0, 4).toString());
	}
	
	public void updateCap() {
		EmpModel emp = new EmpModel();
		String sql = "select shop_capacity from fyp_shop where shop_id='"+sid+"' ";
		emp.runSql(sql);
		int cap = Integer.parseInt(emp.getValueAt(0, 0).toString());
		int ncap = 0;
		if(fff==1) {
			ncap = cap+(Integer.parseInt(nof.getText()));
		}else {
			ncap = cap-oldnof+(Integer.parseInt(nof.getText()));
		}
		sql = "update fyp_shop set shop_capacity=? where shop_id=? ";
		String[] paras = {ncap+"",sid};
		emp.updInfo(sql, paras);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == go) {
			EmpModel emp = new EmpModel();
			String sql = "insert into fyp_table values (?,?,?,?,?) ";
			String[] paras = {idf.getText(),sf.getText(),nof.getText(),
					(ff.getSelectedIndex()+1)+"",nowf.getText()};
			emp.updInfo(sql, paras);
			top.setText(rb.getString("w_sA")+" "+idf.getText());
			updateCap();
		}else if(e.getSource() == up) {
			EmpModel emp = new EmpModel();
			String sql = "update fyp_table set table_id=?,table_shop=?,table_number=?,"
					+ "table_flag=?,table_now=? where table_id=? and table_shop=? ";
			String[] paras = {idf.getText(),sf.getText(),nof.getText(),
					(ff.getSelectedIndex()+1)+"",nowf.getText(),tid,sid};
			emp.updInfo(sql, paras);
			updateCap();
			this.dispose();
		}
	}
}
