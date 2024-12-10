package commonView;

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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import model.EmpModel;
import tools.myFont;

public class TableOrderAddDialog extends JDialog implements ActionListener {
	
	String tid;
	String sid;
	int left;
	Image titleIcon;
	
	JPanel p1,p2;
	JLabel l1,l2;
	JTextField tf1,tf2;
	JButton b1;
	
	Border margin;
	
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
	
	public TableOrderAddDialog(Frame owner,String title,boolean modal,
			int locale,String tableid,String shopid,int sLeft) {
		super(owner,title,modal);
		localeCurrent = locale;
		initLocale();
		
		tid = tableid;
		sid = shopid;
		left = sLeft;
		
		EmpModel emp = new EmpModel();
		String sql = "select order_id from fyp_order "
				+ "where order_flag = '1' and order_sid = '"+sid+"' "
				+ "and order_id like '"+tableid+"%' order by order_id ";
		emp.runSql(sql);
		String yy = "";
		for(int i=0;i<emp.getRowCount();i++) {
			String x = (String)emp.getValueAt(i, 0);
			String y = x.substring(3);
			yy = yy+y;
		}
		
		if(yy.equals("")) {
			yy = "a";
			newOid = tid+yy;
		}else {
			int y = 0;
			int flag = 0;
			char s[]=yy.toCharArray();
			int x = 97;
			for(int i=0;i<s.length;i++){
				y = s[i];
				if(y!=x) {
					char newletter=(char)x;
					newOid = tid+newletter;
					flag = 1;
					break;
				}else {
					x++;
				}
			}
			if(flag == 0) {
				char newletter=(char)x;  
				newOid = tid+newletter;
			}
		}
		
		p1 = new JPanel(new GridLayout(2,2));
		margin = new EmptyBorder(30, 10, 30, 10);
		p1.setBorder(margin);
		l1 = new JLabel(rb.getString("toad_oid"));
		l1.setFont(myFont.f1);
		tf1 = new JTextField(newOid);
		tf1.setFont(myFont.f1);
		tf1.setEnabled(false);
		l2 = new JLabel(rb.getString("toad_ppl"));
		l2.setFont(myFont.f1);
		tf2 = new JTextField();
		tf2.setFont(myFont.f1);
		
		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		
		p2 = new JPanel();
		b1 = new JButton(rb.getString("common_ok"));
		b1.setFont(myFont.f1);
		b1.addActionListener(this);
		p2.add(b1);
		
		this.add(p1,"Center");
		this.add(p2,"South");
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-125, h/2- 125);
		this.setSize(250, 250);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int ppl = Integer.parseInt(tf2.getText());
			if(ppl>left) {
				JLabel w1 = new JLabel(rb.getString("w_tooManyppl"));
		    	w1.setFont(myFont.f3);
				JOptionPane.showMessageDialog(
						this,w1,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}else {
				int flag = 2;
				if(ppl==left) {
					flag = 3;
				}
				try {
					EmpModel emp = new EmpModel();
					String sql = "update fyp_table set table_flag=?, table_now=? "+
								 " where table_id=? and table_shop=?";
					String[] paras = {flag+"", (left-ppl)+"", tid, sid};
					emp.updInfo(sql, paras);
					
					EmpModel emp2 = new EmpModel();
					String sql2 = "insert into fyp_order values (?,?,TO_DATE(SYSDATE),"
							+ "TO_CHAR(SYSDATE,'DD-MM-YYYY HH24:MI:SS'),?,?,?,?,?) ";
					String[] paras2 = {newOid, sid, ppl+"", 1+"", 1+"", 0+"", "na"};
					emp2.updInfo(sql2, paras2);
					
					this.dispose();
				}catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}catch (Exception e2) {
			JLabel w1 = new JLabel(rb.getString("w_wInput"));
	    	w1.setFont(myFont.f3);
			JOptionPane.showMessageDialog(
					this,w1,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
		}
	}
}
