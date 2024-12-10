package commonView;

import java.awt.FlowLayout;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import model.EmpModel;
import tools.myFont;

public class ChangeTableDialog extends JDialog implements ActionListener {
	String ot = "";
	String sid;
	String oid;
	int ppl = 0;
	
	JPanel m;
	JButton b;
	JComboBox jb;
	
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
	
	public ChangeTableDialog(Frame owner,String title,boolean modal,
			int locale,String shopid,String orderid,int people) {
		super(owner,title,modal);
		localeCurrent = locale;
		sid = shopid;
		oid = orderid;
		ppl = people;
		
		initLocale();
		
		EmpModel emp = new EmpModel();
		String sql = "select table_id from fyp_table where table_shop = '"+sid+"' "
				+ "and table_flag != 3 and table_now >= "+ppl+" ";
		emp.runSql(sql);
		jb = new JComboBox();
		for(int i=0;i<emp.getRowCount();i++) {
			jb.addItem(emp.getValueAt(i, 0).toString());
		}
		jb.setFont(myFont.f1);
		m = new JPanel(new FlowLayout());
		m.add(jb);
		
		b = new JButton(rb.getString("common_ok"));
		b.setFont(myFont.f1);
		b.addActionListener(this);
		m.add(b);
		
		this.add(m);
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-150, h/2-50);
		this.setSize(300, 100);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b) {
			String newOid = "";
			String nt = jb.getSelectedItem().toString();
			
			// Delete Old Records
			int flag = 2;
			EmpModel emp = new EmpModel();
			String sql = "select table_number, table_now from fyp_table "
					+ "where table_id = '"+oid.substring(0, 3)+"' and table_shop='"+sid+"' ";
			emp.runSql(sql);
			int canSit = Integer.parseInt(emp.getValueAt(0, 0).toString());
			int left = Integer.parseInt(emp.getValueAt(0, 1).toString());
			int finallySit = left+ppl;
			if(finallySit==canSit) {
				flag = 1;
			}
			
			String sql2 = "update fyp_table set table_flag=?, table_now=? "
					+ "where table_shop=? and table_id=? ";
			String[] paras = {flag+"", finallySit+"", sid, oid.substring(0, 3)};
			emp.updInfo(sql2, paras);
			
			sql2 = "select order_time from fyp_order "
					+ "where order_id='"+oid+"' and order_flag = '1' and order_sid = '"+sid+"' ";
			emp.runSql(sql2);
			
			ot = emp.getValueAt(0, 0).toString();
			
			// Add new Records
			String sql3 = "select order_id from fyp_order "
					+ "where order_flag = '1' and order_sid = '"+sid+"' "
					+ "and order_id like '"+nt+"%' order by order_id ";
			emp.runSql(sql3);
			
			String yy = "";
			for(int i=0;i<emp.getRowCount();i++) {
				String x = (String)emp.getValueAt(i, 0);
				String y = x.substring(3);
				yy = yy+y;
			}
			
			if(yy.equals("")) {
				yy = "a";
				newOid = nt+yy;
			}else {
				int y = 0;
				int flag2 = 0;
				char s[]=yy.toCharArray();
				int x = 97;
				for(int i=0;i<s.length;i++){
					y = s[i];
					if(y!=x) {
						char newletter=(char)x;
						newOid = nt+newletter;
						flag2 = 1;
						break;
					}else {
						x++;
					}
				}
				if(flag2 == 0) {
					char newletter=(char)x;  
					newOid = nt+newletter;
				}
			}
			
			String sql4 = "update fyp_order set order_id=? "
					+ "where order_id=? and order_sid=? and order_flag=1 and order_time=?";
			String[] paras2 = {newOid,oid,sid,ot};
			emp.updInfo(sql4, paras2);
			
			String sql5 = "select table_flag, table_now from fyp_table "
					+ "where table_id = '"+nt+"' and table_shop = '"+sid+"' ";
			emp.runSql(sql5);
			int nFlag = Integer.parseInt(emp.getValueAt(0, 0).toString());
			int nLeft = Integer.parseInt(emp.getValueAt(0, 1).toString());
			int nFinal = nLeft-ppl;
			if(nFinal==0) {
				nFlag=3;
			}else {
				nFlag=2;
			}
			String sql6 = "update fyp_table set table_now=?, table_flag=? where table_id=? and table_shop=? ";
			String[] paras3 = {nFinal+"",nFlag+"",nt+"",sid};
			emp.updInfo(sql6, paras3);
			
			sql6 = "update fyp_orderitem set oi_oid=? where oi_oid=? and oi_sid=? and oi_ptime=? ";
			String[] paras4 = {newOid,oid,sid,ot};
			emp.updInfo(sql6, paras4);
			
			this.dispose();
		}
	}
}
