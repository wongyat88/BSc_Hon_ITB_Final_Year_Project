package managerView;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.EmpModel;
import tools.myFont;

public class FoodSizePriceDialog extends JDialog implements ActionListener{
	Image titleIcon;
	
	JTable jt;
	JScrollPane jsp;
	JButton b1;
	JPanel m;
	
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
	
	public FoodSizePriceDialog(Frame owner,String title,boolean modal,int locale) {
		super(owner,title,modal);
		localeCurrent = locale;
		initLocale();
		
		m = new JPanel(new BorderLayout());
		
		JLabel head = new JLabel(rb.getString("spd_intro"), JLabel.CENTER);
		head.setFont(myFont.f1);
		m.add(head,"North");
		
		DefaultTableModel tableModel = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return column == 1 || column==2 || column==3 || column==4 ? true : false;
		    }
		};
		
		tableModel.addColumn("Size");
		tableModel.addColumn("Price");
		tableModel.addColumn("Price Morn.");
		tableModel.addColumn("Price Tea");
		tableModel.addColumn("Price Holiday");
		
        EmpModel emp = new EmpModel();
		String sql = "select * from fyp_size ";
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
		
		b1 = new JButton(rb.getString("fj_up"));
		b1.setFont(myFont.f1);
		b1.addActionListener(this);
		m.add(b1,"South");
		
		this.add(m);
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setIconImage(titleIcon);
		this.setLocation(w/2-400, h/2-150);
		this.setSize(800, 300);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b1) {
			try {
				float pp = 0;
				for(int y=0;y<jt.getRowCount();y++) {
					for(int x=0;x<jt.getColumnCount();x++) {
						if(x!=0) {
							pp += Float.parseFloat(jt.getValueAt(y, x).toString());
						}
					}
				}
				try {
					Vector<String> cc = new Vector<String>();
					cc.addElement("l");
					cc.addElement("m");
					cc.addElement("b");
					cc.addElement("s");
					EmpModel emp = new EmpModel();
					String sql = "update fyp_size set s_price=?, s_morn=?, s_tea=?, s_hoilday=? where s_id=? ";
					for(int y=0;y<jt.getRowCount();y++) {
						String[] paras = {jt.getValueAt(y, 1).toString(),jt.getValueAt(y, 2).toString(),
								jt.getValueAt(y, 3).toString(),jt.getValueAt(y, 4).toString(),cc.get(y)};
						emp.updInfo(sql, paras);
					}
					JLabel w2 = new JLabel(rb.getString("w_sA"));
					w2.setFont(myFont.f1);
					JOptionPane.showMessageDialog(
							this,w2,rb.getString("w_s"),JOptionPane.INFORMATION_MESSAGE);
				}catch (Exception e3) {
					// TODO: handle exception
				}
			}catch (Exception e2) {
				JLabel w2 = new JLabel(rb.getString("w_wInput"));
				w2.setFont(myFont.f1);
				JOptionPane.showMessageDialog(
						this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
