package managerView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.EmpModel;
import tools.myFont;

public class CatPanel extends JPanel implements ActionListener{
	
	JPanel m,p1,p2;
	JButton exit,add,up,del;
	
	JTable jt;
	JScrollPane jsp;
	
	JFrame parent;
	
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
	
	public CatPanel(int locale) {
		localeCurrent = locale;
		initLocale();
		
		m = new JPanel(new BorderLayout());
		
		p2 = new JPanel(new BorderLayout());
		
		exit = new JButton(rb.getString("common_exit"));
		exit.setFont(myFont.f1);
		exit.addActionListener(this);
		p2.add(exit,"West");
		
		JLabel l = new JLabel(rb.getString("spd_intro"), JLabel.CENTER);
		l.setFont(myFont.f1);
		p2.add(l,"Center");
		
		m.add(p2,"North");
		
		DefaultTableModel tableModel = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return column == 1 || column==2 || column==3 ? true : false;
		    }
		};
		
		tableModel.addColumn("Cat_ID");
		tableModel.addColumn("Name");
		tableModel.addColumn("Name_zh");
		tableModel.addColumn("Flag");
		
        EmpModel emp = new EmpModel();
		String sql = "select * from fyp_cat ";
		emp.runSql(sql);
		for(int y=0;y<emp.getRowCount();y++) {
			Vector<String> vv = new Vector<String>();
			for(int x=0;x<emp.getColumnCount();x++) {
				vv.addElement(emp.getValueAt(y, x).toString());
			}
			tableModel.addRow(vv);
		}
		jt = new JTable(tableModel);
		jt.setFont(myFont.f1);
		jt.setRowHeight(30);
		jt.setAutoCreateRowSorter(true);
		jsp = new JScrollPane(jt);
		m.add(jsp,"Center");
		
		add = new JButton(rb.getString("fj_add"));
		up = new JButton(rb.getString("fj_up"));
		del = new JButton(rb.getString("fj_del"));
		add.setFont(myFont.f1);
		up.setFont(myFont.f1);
		del.setFont(myFont.f1);
		add.addActionListener(this);
		up.addActionListener(this);
		del.addActionListener(this);
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p1.add(add);
		p1.add(up);
		p1.add(del);
		
		m.add(p1,"South");
		
		this.setLayout(new BorderLayout());
		this.add(m,"Center");
	}
	
	public void reFresh() {
		DefaultTableModel tableModel2 = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return column == 1 || column==2 || column==3 ? true : false;
		    }
		};
		
		tableModel2.addColumn("Cat_ID");
		tableModel2.addColumn("Name");
		tableModel2.addColumn("Name_zh");
		tableModel2.addColumn("Flag");
        EmpModel emp = new EmpModel();
		String sql = "select * from fyp_cat ";
		emp.runSql(sql);
		for(int y=0;y<emp.getRowCount();y++) {
			Vector<String> vv = new Vector<String>();
			for(int x=0;x<emp.getColumnCount();x++) {
				vv.addElement(emp.getValueAt(y, x).toString());
			}
			tableModel2.addRow(vv);
		}
		jt.setModel(tableModel2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit) {
			this.setVisible(false);
		}else if(e.getSource() == add) {
			new CatAddDialog(parent,rb.getString("CatAddDialog_title"),true,localeCurrent);
			reFresh();
		}else if(e.getSource() == up) {
			try {
				EmpModel emp = new EmpModel();
				String sql = "update fyp_cat set cat_name=?, cat_name_zh=?, cat_flag=? where cat_id=? ";
				for(int y=0;y<jt.getRowCount();y++) {
					String[] paras = {jt.getValueAt(y, 1).toString(),jt.getValueAt(y, 2).toString(),
							jt.getValueAt(y, 3).toString(),jt.getValueAt(y, 0).toString()};
					emp.updInfo(sql, paras);
				}
				JLabel w2 = new JLabel(rb.getString("w_sA"));
				w2.setFont(myFont.f1);
				JOptionPane.showMessageDialog(
						this,w2,rb.getString("w_s"),JOptionPane.INFORMATION_MESSAGE);
			}catch (Exception e3) {
				e3.printStackTrace();
			}finally {
				reFresh();
			}
		}else if(e.getSource() == del) {
			try {
				int reply = JOptionPane.showConfirmDialog(null,rb.getString("mess_confirm"),rb.getString("mess_confirmTitle"),JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
					String cid = jt.getValueAt(jt.getSelectedRow(), 0).toString();
					String sql = "delete from fyp_cat where cat_id=? ";
					String[] paras = {cid};
					EmpModel emp = new EmpModel();
					emp.updInfo(sql, paras);
					reFresh();
		        }
			}catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}
