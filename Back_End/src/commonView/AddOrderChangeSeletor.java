package commonView;

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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.EmpModel;
import tools.myFont;

public class AddOrderChangeSeletor extends JDialog implements ActionListener {
	JRadioButton[] r = new JRadioButton[10];
	ButtonGroup bg;
	String oiid = "";
	
	JPanel p1;
	JButton go;
	
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
	
	public AddOrderChangeSeletor(Frame owner,String title,boolean modal,int locale,
			String hash,String orderitemid) {
		super(owner,title,modal);
		localeCurrent = locale;
		oiid = orderitemid;
		initLocale();
		
		p1 = new JPanel();
		
		EmpModel emp = new EmpModel();
		bg = new ButtonGroup();
		String[] buff = hash.split("_");
		String sql = "";
		for(int i=0;i<buff.length;i++) {
			if(locale==0) {
				sql = "select food_id, food_name from fyp_food where food_id='"+buff[i]+"' ";
			}else {
				sql = "select food_id, food_name_zh from fyp_food where food_id='"+buff[i]+"' ";
			}
			emp.runSql(sql);
			r[i] = new JRadioButton();
			String myString = rb.getString("af_id")+emp.getValueAt(0, 0).toString()+"<br>"
							 +rb.getString("af_name")+emp.getValueAt(0, 1).toString();
			r[i].setText("<html>"+ myString +"</html>");
			r[i].addActionListener(this);
			r[i].setActionCommand(emp.getValueAt(0, 0).toString());
			r[i].setFont(myFont.f1);
        	bg.add(r[i]);
        	p1.add(r[i]);
		}
		
		this.add(p1,"Center");
		
		go = new JButton(rb.getString("common_ok"));
		go.setFont(myFont.f1);
		go.addActionListener(this);
		
		this.add(go,"South");
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(titleIcon);
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(w/2-500, h/2-150);
		this.setSize(1000,300);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == go) {
			String fid = bg.getSelection().getActionCommand().toString();
			EmpModel emp = new EmpModel();
			String sql = "update fyp_orderitem set oi_fid=? where oi_id=? ";
			String[] paras = {fid,oiid};
			emp.updInfo(sql, paras);
			this.dispose();
		}
	}
}
