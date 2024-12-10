package commonView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import model.EmpModel;
import tools.myColor;
import tools.myFont;

public class TakeOutOrderListDialog extends JDialog implements ActionListener {
	
	JPanel p1,p2,p3,p4,p5;
	JLabel p1_lab1,p3_lab1;
	JTextField p1_jtf1;
	JButton p1_jb1,p4_jb1,p4_jb2,p4_jb3,p4_jb4;
	
	JTable jtable;
	JScrollPane jsp;
	
	String sid;
	
	String sql = "select order_id, order_time, order_flag, order_price, order_phone from fyp_order "
			+ "where order_id like 'to%' and order_sid = '"+sid+"' "
			+ "order by length(order_id) DESC, order_id DESC ";
	
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

	public TakeOutOrderListDialog(Frame owner,String title,boolean modal,int locale,String shopid) {
		super(owner,title,modal);
		localeCurrent = locale;
		sid = shopid;
		initLocale();
		
		MenuInfo();
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-450, h/2- 250);
		this.setSize(900, 500);
		this.setVisible(true);
	}
	
	public void refresh() {
		EmpModel emp = new EmpModel();
		String sql = "select order_id, order_time, order_flag, order_price, order_phone from fyp_order "
			+ "where order_id like 'to%' and order_sid = '"+sid+"' "
			+ "order by length(order_id) DESC, order_id DESC";
		emp.runSql(sql);
		jtable.setModel(emp);
		p3_lab1.setText(emp.getRowCount() +" "+ rb.getString("toold_records"));
	}
	
	public void MenuInfo() {
		//North
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p1_lab1 = new JLabel(rb.getString("common_toid"));
		p1_lab1.setFont(myFont.f1);
		
		p1_jtf1 = new JTextField(20);
		p1_jtf1.setPreferredSize(new Dimension(120,30));
		p1_jtf1.setFont(myFont.f1);
		
		p1_jb1 = new JButton(rb.getString("common_search"));
		p1_jb1.setFont(myFont.f1);
		p1_jb1.addActionListener(this);
		
		p1.add(p1_lab1);
		p1.add(p1_jtf1);
		p1.add(p1_jb1);
		
		//Middle
		EmpModel em = new EmpModel();
		String sql2 = "select order_id, order_time, order_flag, order_price, order_phone from fyp_order "
				+ "where order_id like 'to%' and order_sid = '"+sid+"' "
				+ "order by length(order_id) DESC, order_id DESC";
		em.runSql(sql2);
		
		jtable = new JTable(em) {
			@Override

		    public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
		        Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);
		        
		        Object value = getModel().getValueAt(rowIndex,columnIndex);
		        if(columnIndex == 2){
		        	if(value.equals("1")) {
		        		componenet.setBackground(myColor.yellow);
		        	}else {
		        		componenet.setBackground(myColor.green);
		        	}
		        }else {
		        	componenet.setBackground(Color.WHITE);
		            componenet.setForeground(Color.BLACK);
		        }
		        return componenet;
		    }
		};
		jtable.setRowHeight(30);
		jtable.setFont(myFont.f3);
		
		p2 = new JPanel(new BorderLayout());
		jsp = new JScrollPane(jtable);
		p2.add(jsp);
		
		//South
		p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p3_lab1 = new JLabel(em.getRowCount() +" "+ rb.getString("toold_records"));
		p3_lab1.setFont(myFont.f1);
		p3.add(p3_lab1);
		
		p4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p4_jb1 = new JButton(rb.getString("common_refresh"));
		p4_jb1.setFont(myFont.f1);
		p4_jb1.addActionListener(this);
		
		p4.add(p4_jb1);
		
		p5 = new JPanel(new BorderLayout());
		p5.add(p3,"West");
		p5.add(p4,"East");
		
		this.setLayout(new BorderLayout());
		this.add(p1,"North");
		this.add(p2,"Center");
		this.add(p5,"South");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == p4_jb1) {
			refresh();
			
		}else if(e.getSource() == p1_jb1) {
			try {
				int x = Integer.parseInt(p1_jtf1.getText());
				String sql = "select order_id, order_time, order_flag, order_price, order_phone from fyp_order "
						+ "where order_id like 'to"+x+"%' and order_sid = '"+sid+"' "
						+ "order by length(order_id) DESC, order_id DESC";
				EmpModel emp = new EmpModel();
				emp.runSql(sql);
				jtable.setModel(emp);
				p3_lab1.setText(emp.getRowCount() +" "+ rb.getString("toold_records"));
				
			}catch (Exception e2){
				JLabel w1 = new JLabel(rb.getString("w_wInput"));
		    	w1.setFont(myFont.f3);
				JOptionPane.showMessageDialog(
						this,w1,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
