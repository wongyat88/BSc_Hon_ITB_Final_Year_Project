package commonView;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import managerView.ManagerMain;
import model.EmpModel;
import tools.myFont;

public class AddFoodFreeDialog extends JDialog implements ActionListener {
	int ff = 0;
	String sid = "";
	String oid = "";
	String ot = "";
	
	JPanel p1;
	JLabel id;
	JTextField idf;
	JButton ok;
	
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
	
	public AddFoodFreeDialog(Frame owner,String title,boolean modal,int locale,
			String shopid,String orderid,int flag) {
		super(owner,title,modal);
		localeCurrent = locale;
		sid = shopid;
		oid = orderid;
		ff = flag;
		initLocale();
		
		EmpModel pSD = new EmpModel();
		String hgf = "select order_time from fyp_order "
				+ "where order_id='"+oid+"' and order_sid='"+sid+"' and order_flag='1' ";
		pSD.runSql(hgf);
		ot = pSD.getValueAt(0, 0).toString();
		
		p1 = new JPanel(new FlowLayout());
		
		id = new JLabel(rb.getString("common_sid"));
		id.setFont(myFont.f1);
		idf = new JTextField(10);
		idf.setFont(myFont.f1);
		p1.add(id);p1.add(idf);
		
		this.add(p1,"Center");
		
		ok = new JButton(rb.getString("common_ok"));
		ok.setFont(myFont.f1);
		ok.addActionListener(this);
		
		this.add(ok,"South");
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-150, h/2-100);
		this.setSize(300, 200);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok) {
			try {
				int cid = Integer.parseInt(idf.getText());
				try {
					Date dd = new Date();
				    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				    String datetime = ft.format(dd);
				    
					EmpModel emp = new EmpModel();
					String sql = "select c_type,c_date,c_flag from fyp_usercoupon where c_id='"+cid+"' ";
					emp.runSql(sql);
					String ctype = emp.getValueAt(0, 0).toString();
					String cdate = emp.getValueAt(0, 1).toString();
					String cflag = emp.getValueAt(0, 2).toString();
					
					SimpleDateFormat foramt = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date d1 = foramt.parse(datetime);
					Date d2 = foramt.parse(cdate);
					long diff = d2.getTime() - d1.getTime();
					long diffDays = diff / (24 * 60 * 60 * 1000);
					
					sql = "select c_fid,c_mon from fyp_coupon where c_type='"+ctype+"' ";
					emp.runSql(sql);
					String fid = emp.getValueAt(0, 0).toString();
					int mon = Integer.parseInt(emp.getValueAt(0, 1).toString());
					
					int j=30;
					if(diffDays<0) {
						diffDays = diffDays*(-1);
					}
					
					BigDecimal b1 = new BigDecimal(diffDays);
	    	        BigDecimal b2 = new BigDecimal(j);
	    	        BigDecimal b3 = b1.divide(b2 , 2, RoundingMode.HALF_UP);
	    	        float sum = Float.parseFloat(b3.toString());
	    	        
	    	        if(cflag.equals("1")) {
	    	        	if(fid.equals("0")) {
							JLabel w2 = new JLabel(rb.getString("w_wInput"));
							w2.setFont(myFont.f1);
							JOptionPane.showMessageDialog(
								this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
						}else {
			    	        if(sum<mon) {
			    	        	//update user coupon flag
			    	        	sql = "update fyp_usercoupon set c_flag=? where c_id=? ";
								String[] paras2 = {2+"",cid+""};
								emp.updInfo(sql, paras2);
								
								// insert into coupon log
								int staffid = ManagerMain.getUid();
								sql = "insert into fyp_couponlog values"
										+ "(clog_increase.nextval,?,?,TO_DATE(SYSDATE),?,?,?) ";
								String[] paras3 = {cid+"","0",sid,staffid+"","3"};
								emp.updInfo(sql, paras3);
								
			    	        	// insert free food to order item
								String area = "";
								if(ff==1) {
									area = "d";
								}else {
									area = "t";
								}
							    sql = "insert into fyp_orderitem values "
										+ "(orderitem_increase.nextval,?,?,?,?,?,?,?,?,?,?,?,?) ";
								String[] paras = {oid,sid,datetime,"f","na","na",fid,area,
										"0","y","1",ot};
								emp.updInfo(sql, paras);
								
								this.dispose();
			    	        }else {
			    	        	JLabel w3 = new JLabel(rb.getString("w_wExpired"));
								w3.setFont(myFont.f1);
								JOptionPane.showMessageDialog(
									this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			    	        }
						}
	    	        }else {
	    	        	JLabel w2 = new JLabel(rb.getString("w_wCoue"));
						w2.setFont(myFont.f1);
						JOptionPane.showMessageDialog(
							this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
	    	        }
				}catch (Exception e3) {
					JLabel w3 = new JLabel(rb.getString("w_wSelect"));
					w3.setFont(myFont.f1);
					JOptionPane.showMessageDialog(
						this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
					e3.printStackTrace();
				}
			}catch (Exception e2) {
				JLabel w2 = new JLabel(rb.getString("w_wInput"));
				w2.setFont(myFont.f1);
				JOptionPane.showMessageDialog(
					this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			}
		}
	}
}
