package managerView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.EmpModel;
import tools.myFont;

public class MaterialPanel extends JPanel implements ActionListener{
	JFrame parent;
	
	JPanel m,p1,p2;
	JLabel search;
	JTextField sf;
	JButton b,sgo,add,up,del;
	
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
	
	public MaterialPanel(int locale) {
		localeCurrent = locale;
		initLocale();
		
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		b = new JButton(rb.getString("common_exit"));
		b.setFont(myFont.f1);
		b.addActionListener(this);
		
		search = new JLabel(rb.getString("hr_search"));
		search.setFont(myFont.f1);
		
		sf = new JTextField(20);
		sf.setFont(myFont.f1);
		
		sgo = new JButton(rb.getString("common_search"));
		sgo.setFont(myFont.f1);
		sgo.addActionListener(this);
		
		p1.add(b);
		p1.add(search);
		p1.add(sf);
		p1.add(sgo);
		
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_material order by m_id ASC ";
		emp.runSql(sql);
		jt = new JTable(emp);
		jt.setAutoCreateRowSorter(true);
		jt.setRowHeight(30);
		jt.setFont(myFont.f1);
		jsp = new JScrollPane(jt);
		
		add = new JButton(rb.getString("fj_add"));
		up = new JButton(rb.getString("fj_up"));
		del = new JButton(rb.getString("fj_del"));
		add.setFont(myFont.f1);
		up.setFont(myFont.f1);
		del.setFont(myFont.f1);
		add.addActionListener(this);
		up.addActionListener(this);
		del.addActionListener(this);
		
		p2 = new JPanel();
		p2.add(add);
		p2.add(up);
		p2.add(del);
		
		this.setLayout(new BorderLayout());
		this.add(p1,"North");
		this.add(jsp,"Center");
		this.add(p2,"South");
	}
	
	public void reFresh() {
		EmpModel emp = new EmpModel();
		String sql = "select * from fyp_material order by m_id ASC ";
		emp.runSql(sql);
		jt.setModel(emp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b) {
			this.setVisible(false);
		}else if(e.getSource() == sgo) {
			String input = sf.getText();
			String sql = "select * from fyp_material where "
				+ "(lower(m_n) like lower('%"+input+"%') or "
				+ "lower(m_nzh) like lower('%"+input+"%')) "
				+ "order by m_id ASC ";
			EmpModel emp = new EmpModel();
			emp.runSql(sql);
			jt.setModel(emp);
		}else if(e.getSource() == add) {
			new MaterialDetailDialog(parent,rb.getString("material_title"),true,localeCurrent,1,"0");
			reFresh();
		}else if(e.getSource() == up) {
			try {
				String mid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
				new MaterialDetailDialog(parent,rb.getString("material_title"),true,localeCurrent,2,mid);
				reFresh();
			}catch (Exception e2) {}
		}else if(e.getSource() == del) {
			try {
				String mid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
				int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	String del = "delete from fyp_material where m_id=? ";
		        	EmpModel emp = new EmpModel();
		        	String[] paras = {mid};
		        	emp.updInfo(del, paras);
		        }
		        reFresh();
			}catch (Exception e2) {}
		}
	}
}
