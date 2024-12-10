package commonView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import model.EmpModel;
import tools.myColor;
import tools.myFont;

public class Billo8Dialog extends JDialog implements ActionListener {
	String oid = "";
	String sid = "";
	String ot = "";
	float sum = 0;
	int flag = 0;
	int ppl = 0;
	int area = 0;
	
	JPanel p1,pp1,h;
	JLabel sl,ml,charL;
	JComboBox<Item> method;
	JButton go,exit;
	JTextField input;
	
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
	
	public Billo8Dialog(Frame owner,String title,boolean modal,int locale, int ff,
			String shopid,String orderid, String ptime, float fsum, int people, int aarea) {
		super(owner,title,modal);
		localeCurrent = locale;
		sid = shopid;
		oid = orderid;
		ot = ptime;
		sum = fsum;
		flag = ff;
		ppl = people;
		area = aarea;
		initLocale();
		
		mainPanel();
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-200, h/2-125);
		this.setSize(400, 250);
		this.setVisible(true);
	}
	
	public void mainPanel() {
		p1 = new JPanel(new BorderLayout());
		
		pp1 = new JPanel(new FlowLayout());
		
		DecimalFormat df = new DecimalFormat("#.#");
		sl = new JLabel(rb.getString("in_finalSum")+" : "+df.format(sum),JLabel.CENTER);
		sl.setFont(myFont.f1);
		p1.add(sl,"North");
		
		go = new JButton(rb.getString("common_ok"));
		go.setFont(myFont.f1);
		go.addActionListener(this);
		
		ml = new JLabel(rb.getString("b_wait"),JLabel.CENTER);
		ml.setFont(myFont.f1);
		ml.setForeground(myColor.blood);
		
		charL = new JLabel("",JLabel.CENTER);
		charL.setFont(myFont.f1);
		
		exit = new JButton(rb.getString("common_exit"));
		exit.setFont(myFont.f1);
		exit.addActionListener(this);
		
		if(flag==1) {
			EmpModel emp = new EmpModel();
			String sql = "";
			if(localeCurrent==0) {
				sql="select p_id, p_n from fyp_paymethod where p_flag='1' and p_id!=1 ";
			}else {
				sql="select p_id, p_nzh from fyp_paymethod where p_flag='1' and p_id!=1 ";
			}
			emp.runSql(sql);
			Vector<Item> model = new Vector<Item>();
			for(int i=0;i<emp.getRowCount();i++) {
				model.addElement(new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
			}
			method = new JComboBox<Item>(model);
			method.setFont(myFont.f1);
			
			pp1.add(method);
			pp1.add(go);
			
			p1.add(ml,"Center");
			p1.add(pp1,"South");
		}else {
			h = new JPanel(new FlowLayout());
			input = new JTextField(10);
			input.setFont(myFont.f1);
			h.add(input);
			
			pp1.add(go);
			p1.add(h,"Center");
			p1.add(pp1,"South");
		}
		this.add(p1,"Center");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == go) {
			if(flag==1) {
				Item item = (Item)method.getSelectedItem();
				String mid = item.getId();
				
				int cardno = 12341234;
				LocalTime timenow = LocalTime.now();
				
				EmpModel emp = new EmpModel();
				String sql = "insert into fyp_profit values"
						+ "(pro_increase.nextval,?,?,?,?,?,?,?,TO_DATE(SYSDATE),?,?)";
				String[] paras = {oid,sid,ot,sum+"","shop",mid,cardno+"",timenow+"","s"};
				emp.updInfo(sql, paras);
				
				ml.setText(rb.getString("b_ok"));
				ml.setForeground(myColor.dgreen);
				
				p1.remove(pp1);
				
				p1.add(exit,"South");
				this.validate();
				this.repaint();
			}else {
				float getM = Float.parseFloat(input.getText());
				BigDecimal give = new BigDecimal(getM);
				BigDecimal need = new BigDecimal(sum);
				BigDecimal charge = give.subtract(need);
				DecimalFormat df = new DecimalFormat("#.#");
				
				charL.setText(rb.getString("b_charge")+" : "+df.format(charge).toString());
				
				LocalTime timenow = LocalTime.now();
				
				EmpModel emp = new EmpModel();
				String sql = "insert into fyp_profit values"
						+ "(pro_increase.nextval,?,?,?,?,?,?,?,TO_DATE(SYSDATE),?,?)";
				String[] paras = {oid,sid,ot,sum+"","shop","1",0+"",timenow+"","s"};
				emp.updInfo(sql, paras);
				
				p1.remove(h);
				p1.remove(pp1);
				
				p1.add(charL,"Center");
				p1.add(exit,"South");
				this.validate();
				this.repaint();
			}
			EmpModel emp = new EmpModel();
			String sql = "update fyp_order set order_flag='2', ORDER_PRICE=? "
        			+ "where order_id=? and order_sid=? and order_time=? ";
        	String[] paras2 = {sum+"",oid,sid,ot};
        	emp.updInfo(sql, paras2);
        	
        	if(area==2) {
        		;
        	}else {
        		String tid = oid.substring(0,3);
            	
            	sql = "select table_number, table_flag, table_now from fyp_table "
            			+ "where table_id='"+tid+"' and table_shop='"+sid+"'";
            	emp.runSql(sql);
            	
            	int tno = Integer.parseInt(emp.getValueAt(0, 0).toString());
            	int tf = Integer.parseInt(emp.getValueAt(0, 1).toString());
            	int tnow = Integer.parseInt(emp.getValueAt(0, 2).toString());
            	
            	if((tno-tnow-ppl)==0) {
            		tf=1;
            	}else {
            		tf=2;
            	}
            	tnow = tnow+ppl;
            	
            	sql = "update fyp_table set table_flag=?, table_now=? "
            			+ "where table_id=? and table_shop=? ";
            	String[] paras3 = {tf+"",tnow+"",tid,sid};
            	emp.updInfo(sql, paras3);
        	}
		}else if(e.getSource() == exit) {
			this.dispose();
		}
	}
	
    class ItemRenderer extends BasicComboBoxRenderer{
        public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus){
            super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
 
            if (value != null){
                Item item = (Item)value;
                setText( item.getDescription().toUpperCase() );
            }
 
            if (index == -1){
                Item item = (Item)value;
                setText( "" + item.getId());
            }
            return this;
        }
    }
 
    class Item{
        private String id;
        private String description;
 
        public Item(String id, String description){
            this.id = id;
            this.description = description;
        }
 
        public String getId(){
            return id;
        }
 
        public String getDescription(){
            return description;
        }
 
        public String toString(){
            return description;
        }
    }
}
