package managerView;

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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EmpModel;
import tools.myFont;

public class ShopTableDialog extends JDialog implements ActionListener{
	Image titleIcon;
	
	JFrame parent;
	String sid = "";
	
	JPanel p1;
	JButton add,up,del;
	JTable jt;
	JScrollPane jsp;
	
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
	
	public ShopTableDialog(Frame owner,String title,boolean modal,int locale,String shopid) {
		super(owner,title,modal);
		sid = shopid;
		localeCurrent = locale;
		initLocale();
		
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_table where table_shop='"+sid+"' order by length(table_id), table_id ";
		emp.runSql(sql);
		jt = new JTable(emp);
		jt.setRowHeight(30);
		jt.setFont(myFont.f1);
		jt.setAutoCreateRowSorter(true);
		jsp = new JScrollPane(jt);
		
		this.add(jsp,"Center");
		
		p1 = new JPanel();
		add = new JButton(rb.getString("fj_add"));
		up = new JButton(rb.getString("fj_up"));
		del = new JButton(rb.getString("fj_del"));
		add.setFont(myFont.f1);
		up.setFont(myFont.f1);
		del.setFont(myFont.f1);
		add.addActionListener(this);
		up.addActionListener(this);
		del.addActionListener(this);
		p1.add(add);
		p1.add(up);
		p1.add(del);
		
		this.add(p1,"South");
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setIconImage(titleIcon);
		this.setLocation(w/2-400, h/2-300);
		this.setSize(800, 600);
		this.setVisible(true);
	}
	
	public void reFresh() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_table where table_shop='"+sid+"' order by length(table_id), table_id ";
		emp.runSql(sql);
		jt.setModel(emp);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			new ShopTableDetailDialog(parent,rb.getString("shopTableDetailDialog_title")+" "+rb.getString("fj_add"),
					true,localeCurrent,1,sid,"0");
			reFresh();
		}else if(e.getSource() == up) {
			String tid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
			new ShopTableDetailDialog(parent,rb.getString("shopTableDetailDialog_title")+" "+rb.getString("fj_up"),
					true,localeCurrent,2,sid,tid);
			reFresh();
		}else if(e.getSource() == del) {
			String tid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
			int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	String del = "delete from fyp_table where table_shop=? and table_id=? ";
	        	EmpModel emp = new EmpModel();
	        	String[] paras = {sid,tid};
	        	emp.updInfo(del, paras);
	        	
	        	String sql = "select shop_capacity from fyp_shop where shop_id='"+sid+"' ";
	    		emp.runSql(sql);
	    		int cap = Integer.parseInt(emp.getValueAt(0, 0).toString());
	    		
	    		int ncap = 0;
	        	ncap = cap-Integer.parseInt(jt.getValueAt(jt.getSelectedRow(), 2).toString());
	    		sql = "update fyp_shop set shop_capacity=? where shop_id=? ";
	    		String[] paras2 = {ncap+"",sid};
	    		emp.updInfo(sql, paras2);
	        }
			reFresh();
		}
	}
}
