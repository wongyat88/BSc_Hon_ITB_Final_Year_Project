package managerView;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*; 

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import model.EmpModel;
import tools.myFont;

public class ComboSelectFoodDialog extends JDialog implements ActionListener{
	
	JPanel jp1,jp2;
	JTable jt1;
	JLabel jl1;
	JTextField jtf1;
	JButton jb1,jb2,jb3,jb4;
	JScrollPane jsp;
	EmpModel emp;
	
	static ArrayList<String> a;
	
	String sql = "select food_id, cat_name, cat_name_zh,"
			+ "food_name, food_name_zh, food_flag "
			+ "from ( select pf.* , pc.* from fyp_food pf "
			+ "JOIN fyp_cat pc ON pf.food_cat = pc.cat_id ) "
			+ "order by length(food_id), food_id";
	
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
	
	public ComboSelectFoodDialog(Frame owner2,String title,boolean modal,int locale) {
		super(owner2,title,modal);
		localeCurrent = locale;
		initLocale();
		
		a = new ArrayList<String>();
		a.clear();
		
		jp2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jl1 = new JLabel(rb.getString("common_search"));
		jl1.setFont(myFont.f1);
		
		jtf1 = new JTextField(20);
		jtf1.setFont(myFont.f1);
		
		jb2 = new JButton(rb.getString("common_search"));
		jb2.setFont(myFont.f1);
		jb2.addActionListener(this);
		
		jb3 = new JButton(rb.getString("common_refresh"));
		jb3.setFont(myFont.f1);
		jb3.addActionListener(this);
		
		jp2.add(jl1);
		jp2.add(jtf1);
		jp2.add(jb2);
		jp2.add(jb3);
		
		jp1 = new JPanel(new BorderLayout());
		EmpModel em = new EmpModel();
		em.runSql(sql);
		jt1 = new JTable(em);
		jt1.setRowHeight(30);
		jt1.setFont(myFont.f1);
		setTable();
		jsp = new JScrollPane(jt1);
		jp1.add(jsp,"Center");
		
		JPanel m = new JPanel();
		jb1 = new JButton(rb.getString("fj_add"));
		jb1.setFont(myFont.f1);
		jb1.addActionListener(this);
		
		jb4 = new JButton(rb.getString("common_ok"));
		jb4.setFont(myFont.f1);
		jb4.addActionListener(this);
		
		m.add(jb1);m.add(jb4);
		jp1.add(m,"South");
		
		this.add(jp2,BorderLayout.NORTH);
		this.add(jp1,BorderLayout.CENTER);
		
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		this.setBackground(Color.black);
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		this.setLocation(w/2-500, h/2-250);
		this.setSize(1100, 500);
		
		this.setVisible(true);
	}
	
	public static ArrayList<String> getA() {
		return a;
	}
	
	public void setTable() {
		jt1.getColumnModel().getColumn(0).setPreferredWidth(50);
		jt1.getColumnModel().getColumn(1).setPreferredWidth(100);
		jt1.getColumnModel().getColumn(2).setPreferredWidth(100);
		jt1.getColumnModel().getColumn(3).setPreferredWidth(400);
		jt1.getColumnModel().getColumn(4).setPreferredWidth(200);
		jt1.getColumnModel().getColumn(5).setPreferredWidth(50);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jb2) {
			try {
				String input=this.jtf1.getText();
				String sql2 = "select food_id, cat_name, cat_name_zh,"
						+ "food_name, food_name_zh, food_flag "
						+ "from ( select pf.* , pc.* from fyp_food pf "
						+ "JOIN fyp_cat pc ON pf.food_cat = pc.cat_id ) "
						+ "where (lower(food_name) like lower('%"+input+"%') or "
						+ "lower(food_name_zh) like lower('%"+input+"%')) "
						+ "order by length(food_id), food_id";
				emp = new EmpModel();
				emp.runSql(sql2);
				jt1.setModel(emp);
				setTable();
			}catch (Exception e2){}
		}else if(e.getSource() == jb3) {
			emp = new EmpModel();
			emp.runSql(sql);
			jt1.setModel(emp);
			setTable();
		}else if(e.getSource() == jb1) {
			try {
				String fid = jt1.getValueAt(jt1.getSelectedRow(), 0).toString();
				a.add(fid);
				JLabel w2 = new JLabel(rb.getString("w_sA"));
				w2.setFont(myFont.f1);
				JOptionPane.showMessageDialog(
						this,w2,rb.getString("w_s"),JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e2) {}
		}else if(e.getSource() == jb4) {
			this.dispose();
		}
	}
}
