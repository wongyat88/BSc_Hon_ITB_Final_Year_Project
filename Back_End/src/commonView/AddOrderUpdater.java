package commonView;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.EmpModel;
import tools.myFont;

public class AddOrderUpdater extends JDialog implements ActionListener, MouseListener {
	String oiid = "";
	String oc = "";
	String sid = "";
	String oid = "";
	String ot = "";
	
	JPanel m1,m2,p1,p2,pp1,pp2,pp3,pp4;
	JLabel l1,l2,id,a,n;
	JTextField idf,nf;
	JComboBox<String> af;
	JButton change,up;
	JTable jt1,jt2;
	JScrollPane jsp1,jsp2;
	DefaultTableModel tm;
	
	JFrame parent;
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
	
	public AddOrderUpdater(Frame owner,String title,boolean modal,int locale,String orderItemID,
			String shopid, String orderid) {
		super(owner,title,modal);
		localeCurrent = locale;
		oiid = orderItemID;
		oid = orderid;
		sid = shopid;
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
		this.setLocation(w/2-600, h/2-400);
		this.setSize(1200,800);
		this.setVisible(true);
	}
	
	public void mainPanel() {
		this.setLayout(new GridLayout(0,2));
		m1 = new JPanel(new BorderLayout());
		m2 = new JPanel(new GridLayout(2,1));
		p1 = new JPanel(new BorderLayout());
		p2 = new JPanel(new GridBagLayout());
		pp1 = new JPanel();
		pp2 = new JPanel();
		pp3 = new JPanel();
		pp4 = new JPanel();
		
		l1 = new JLabel(rb.getString("af_clickadd"), JLabel.CENTER);
		l1.setFont(myFont.f1);
		
		EmpModel pSD = new EmpModel();
		String hgf = "select order_time from fyp_order "
				+ "where order_id='"+oid+"' and order_sid='"+sid+"' and order_flag='1' ";
		pSD.runSql(hgf);
		ot = pSD.getValueAt(0, 0).toString();
		
		EmpModel emp = new EmpModel();
		String sql = "select oi_fid,oi_area,oi_change from fyp_orderitem "
				+ "where oi_id='"+oiid+"' and oi_ptime='"+ot+"' ";
		emp.runSql(sql);
		String fid = emp.getValueAt(0, 0).toString();
		String area = emp.getValueAt(0, 1).toString();
		oc = emp.getValueAt(0, 2).toString();
		if(localeCurrent==0) {
			sql = "select food_cat,food_name from fyp_food where food_id = '"+fid+"' ";
		}else {
			sql = "select food_cat,food_name_zh from fyp_food where food_id = '"+fid+"' ";
		}
		emp.runSql(sql);
		String cat = emp.getValueAt(0, 0).toString();
		String foodname = emp.getValueAt(0, 1).toString();
		if(localeCurrent==0) {
			sql = "select SI_ID,SI_NAME,SI_PRICE from fyp_sitem where "
					+ " (si_cat = 'com' or si_cat = '"+cat+"') and SI_FLAG='1' ";
		}else {
			sql = "select SI_ID,SI_NAME_ZH,SI_PRICE from fyp_sitem where "
					+ " (si_cat = 'com' or si_cat = '"+cat+"') and SI_FLAG='1' ";
		}
		emp.runSql(sql);
		jt1 = new JTable(emp);
		jt1.setAutoCreateRowSorter(true);
		jt1.setRowHeight(30);
		jt1.setFont(myFont.f1);
		jt1.addMouseListener(this);
		jsp1 = new JScrollPane(jt1);
		
		m1.add(l1,"North");
		m1.add(jsp1,"Center");
		this.add(m1);
		
		l2 = new JLabel(rb.getString("af_clickdel"), JLabel.CENTER);
		l2.setFont(myFont.f1);
		
		tm = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		tm.addColumn("SO_ID");
		tm.addColumn("SO_O_ID");
		tm.addColumn("SO_SI_ID");
		tm.addColumn("SO_PRICE");
		
		EmpModel emp2 = new EmpModel();
		sql = "select * from fyp_sorder where so_o_id = '"+oiid+"' ";
		emp2.runSql(sql);
		for(int y=0;y<emp2.getRowCount();y++) {
			Vector<String> vv = new Vector<String>();
			for(int x=0;x<emp2.getColumnCount();x++) {
				vv.addElement(emp2.getValueAt(y, x).toString());
			}
			tm.addRow(vv);
		}
		
		jt2 = new JTable(tm);
		jt2.setAutoCreateRowSorter(true);
		jt2.setRowHeight(30);
		jt2.setFont(myFont.f1);
		jt2.addMouseListener(this);
		jsp2 = new JScrollPane(jt2);
		
		p1.add(l2,"North");
		p1.add(jsp2,"Center");
		
		id = new JLabel(rb.getString("af_id"));
		id.setFont(myFont.f1);
		idf = new JTextField(5);
		idf.setText(fid);
		idf.setEditable(false);
		idf.setFont(myFont.f1);
		
		a = new JLabel(rb.getString("af_area"));
		a.setFont(myFont.f1);
		String[] StatusStr = { rb.getString("af_dine"),rb.getString("af_take")};
		af = new JComboBox<String>(StatusStr);
		af.setFont(myFont.f1);
		af.addActionListener(this);
		if(area.equals("d")) {
			af.setSelectedIndex(0);
		}else {
			af.setSelectedIndex(1);
		}
		
		af.setFont(myFont.f1);
		
		pp1.add(id);pp1.add(idf);
		pp1.add(a);pp1.add(af);
		
		n = new JLabel(rb.getString("md_name"));
		n.setFont(myFont.f1);
		nf = new JTextField(20);
		nf.setText(foodname);
		nf.setEditable(false);
		nf.setFont(myFont.f1);
		
		pp2.add(n);pp2.add(nf);
		
		change = new JButton(rb.getString("afo_chB"));
		change.setFont(myFont.f1);
		change.addActionListener(this);
		pp3.add(change);
		
		if(oc.equals("na")) {
			change.setEnabled(false);
		}
		
		up = new JButton(rb.getString("fj_up"));
		up.setFont(myFont.f1);
		up.addActionListener(this);
		pp4.add(up);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		p2.add(pp1,gbc);
		gbc.gridy = 1;
		p2.add(pp2,gbc);
		gbc.gridy = 2;
		p2.add(pp3,gbc);
		gbc.gridy = 3;
		p2.add(pp4,gbc);
		
		m2.add(p1);
		m2.add(p2);
		this.add(m2);
	}
	
	public void update() {
		EmpModel emp2 = new EmpModel();
		String sql = "select * from fyp_sorder where so_o_id = '"+oiid+"' ";
		emp2.runSql(sql);
		tm.setRowCount(0);
		for(int y=0;y<emp2.getRowCount();y++) {
			Vector<String> vv = new Vector<String>();
			for(int x=0;x<emp2.getColumnCount();x++) {
				vv.addElement(emp2.getValueAt(y, x).toString());
			}
			tm.addRow(vv);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == up) {
			EmpModel emp = new EmpModel();
			String sql = "update fyp_orderitem set oi_area=? where oi_id=? and oi_ptime=?";
			String x = "";
			if(af.getSelectedIndex()==0) {
				x="d";
			}else {
				x="t";
			}
			String[] paras = {x,oiid,ot};
			emp.updInfo(sql, paras);
			this.dispose();
		}else if(e.getSource() == change) {
			new AddOrderChangeSeletor(parent,rb.getString("addFood_title"),true,localeCurrent,oc,oiid);
			try {
				String sid = jt2.getValueAt(0, 1).toString();
				System.out.println(sid);
				EmpModel emp = new EmpModel();
				String sql = "delete from fyp_sorder where so_o_id=? ";
				String[] paras = {sid};
				emp.updInfo(sql, paras);
				update();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
			this.dispose();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == jt1) {
			int sid = Integer.parseInt(jt1.getValueAt(jt1.getSelectedRow(), 0).toString());
			String sp = jt1.getValueAt(jt1.getSelectedRow(), 2).toString();
			EmpModel emp = new EmpModel();
			String sql = "insert into fyp_sorder values (soid_increase.nextval,?,?,?) ";
			String[] paras = {oiid,sid+"",sp};
			emp.updInfo(sql, paras);
			update();
		}else if(e.getSource() == jt2) {
			String sid = jt2.getValueAt(jt2.getSelectedRow(), 0).toString();
			EmpModel emp = new EmpModel();
			String sql = "delete from fyp_sorder where so_id=? ";
			String[] paras = {sid};
			emp.updInfo(sql, paras);
			update();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
