package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import model.EmpModel;
import tools.myFont;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

public class Login extends JFrame implements ActionListener{
	
	Image titleIcon;
	JTabbedPane jtp;
	JPanel m1,p1,p2,p1p1,p1p2,p3;
	
	JLabel p1l1,p1l2,p1l3,p2l1,p2l2,space,p3l1,p3l2,space2;
	JLabel w1,w2,w3;
	JTextField p1tf1,p2tf1,p3tf1;
	JPasswordField p1tp1,p2tp1,p3tp1;
	JCheckBox p1cb1;
	JButton p1b1,p2b1,mb1,p3b1;
	JComboBox<Item> cb1,cb2;
	Border margin,margin2,margin3;
	
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
	
	public void initText() {
    	this.setTitle (rb.getString("login_title"));
    	p1l1.setText(rb.getString("login_userid"));
    	p1l2.setText(rb.getString("login_pw"));
    	p1l3.setText(rb.getString("login_selectShop"));
    	p2l1.setText(rb.getString("login_userid"));
    	p2l2.setText(rb.getString("login_pw"));
    	p1cb1.setText(rb.getString("login_remember"));
    	p1b1.setText(rb.getString("login_login"));
    	p2b1.setText(rb.getString("login_login"));
    	mb1.setText(rb.getString("login_locale"));
    	w2 = new JLabel(rb.getString("w_wuid"));
    	w3 = new JLabel(rb.getString("w_wpw"));
    	w2.setFont(myFont.f3);
    	w3.setFont(myFont.f3);
    }
	
	public Login(int locale) {
		localeCurrent = locale;
		margin = new EmptyBorder(30, 10, 60, 10);
		
		m1 = new JPanel();
		mb1 = new JButton("Chinese");
		mb1.setFont(myFont.f3);
		mb1.addActionListener(this);
		m1.add(mb1);
		
		jtp = new JTabbedPane();
		
		p1 = new JPanel(new GridLayout(1,2));
		p1.setBorder(margin);
		
		p1p1 = new JPanel(new GridLayout(6,1));
		p1p2 = new JPanel(new GridLayout(6,1));
		
		p1l1 = new JLabel("User ID :");
		p1l1.setFont(myFont.f3);
		p1tf1 = new JTextField();
		p1l2 = new JLabel("Password :");
		p1l2.setFont(myFont.f3);
		p1tp1 = new JPasswordField();
		p1cb1 = new JCheckBox("Remeber me");
		p1cb1.setFont(myFont.f3);
		p1b1 = new JButton("Login");
		p1b1.setFont(myFont.f3);
		p1b1.addActionListener(this);
		
		p1p1.add(p1l1);
		p1p1.add(p1tf1);
		p1p1.add(p1l2);
		p1p1.add(p1tp1);
		p1p1.add(p1cb1);
		p1p1.add(p1b1);
		
		p1l3 = new JLabel("Select a shop");
		p1l3.setFont(myFont.f3);
		
		Vector<Item> model = new Vector<Item>();
		String sql="";
		if(localeCurrent==0) {
			sql = "select shop_id, shop_name from fyp_shop";
		}else {
			sql = "select shop_id, shop_name_zh from fyp_shop";
		}
		
		EmpModel emp = new EmpModel();
		emp.runSql(sql);
		for(int i = 0; i<emp.getRowCount(); i++){
			model.addElement( new Item(emp.getValueAt(i, 0).toString(), emp.getValueAt(i, 1).toString()));
		}
		cb1 = new JComboBox<Item>(model);
		cb1.setFont(myFont.f3);
		cb1.setSelectedIndex(0);
		
		p1p2.add(p1l3);
		p1p2.add(cb1);
		
		p1.add(p1p1);
		p1.add(p1p2);
		
		margin2 = new EmptyBorder(30, 150, 60, 150);
		p2 = new JPanel(new GridLayout(6,1));
		p2.setBorder(margin2);
		
		p2l1 = new JLabel("User ID :");
		p2l1.setFont(myFont.f3);
		p2tf1 = new JTextField();
		p2l2 = new JLabel("Password :");
		p2l2.setFont(myFont.f3);
		p2tp1 = new JPasswordField();
		space = new JLabel(" ");
		p2b1 = new JButton("Login");
		p2b1.setFont(myFont.f3);
		p2b1.addActionListener(this);
		
		p2.add(p2l1);
		p2.add(p2tf1);
		p2.add(p2l2);
		p2.add(p2tp1);
		p2.add(space);
		p2.add(p2b1);
		
		margin3 = new EmptyBorder(30, 150, 60, 150);
		p3 = new JPanel(new GridLayout(8,0));
		p3.setBorder(margin3);
		
		p3l1 = new JLabel("User ID :");
		p3l1.setFont(myFont.f3);
		p3tf1 = new JTextField();
		p3l2 = new JLabel("Password :");
		p3l2.setFont(myFont.f3);
		p3tp1 = new JPasswordField();
		space2 = new JLabel(" ");
		cb2 = new JComboBox<Item>(model);
		cb2.setFont(myFont.f3);
		cb2.setSelectedIndex(0);
		JLabel space3 = new JLabel(" ");
		p3b1 = new JButton("Login");
		p3b1.setFont(myFont.f3);
		p3b1.addActionListener(this);
		
		p3.add(p3l1);
		p3.add(p3tf1);
		p3.add(p3l2);
		p3.add(p3tp1);
		p3.add(space2);
		p3.add(cb2);
		p3.add(space3);
		p3.add(p3b1);
		
		try {
			titleIcon = ImageIO.read(new File("images/logo/logo.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initLocale();
		initText();
		
		jtp.add(p1,rb.getString("login_common"));
		jtp.add(p2,rb.getString("login_manager"));
		jtp.add(p3,rb.getString("login_kic"));
		jtp.setFont(myFont.f3);
		
		this.add(jtp,BorderLayout.CENTER);
		this.add(m1,BorderLayout.SOUTH);
		
		this.setSize(600,400);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width/2-300, height/2-200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(titleIcon);
		this.setResizable(false);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Login login = new Login(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==p1b1){
			int userid = 0;
			try {
				userid = Integer.parseInt(p1tf1.getText());
				String pw= String.valueOf(p1tp1.getPassword());
				String sql = "select staff_pw from fyp_staff where staff_id = " + userid;
				EmpModel emp = new EmpModel();
				emp.runSql(sql);
				String pw2 = "";
				try {
					pw2 = emp.getValueAt(0, 0).toString();
					if(pw2.equals(pw)) {
						Item item = (Item)cb1.getSelectedItem();
						LoadingScene ls = new LoadingScene("c",localeCurrent,userid,item.getId());
						Thread x = new Thread(ls);
						x.start();
						this.dispose();
					}else {
						JOptionPane.showMessageDialog(
								this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
					}
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(
							this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
				}
			}catch (Exception e1) {
				JOptionPane.showMessageDialog(
						this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}
		}else if(e.getSource()==p2b1) {
			int userid = 0;
			try {
				userid = Integer.parseInt(p2tf1.getText());
				String pw= String.valueOf(p2tp1.getPassword());
				String sql = "select staff_pw from fyp_staff where staff_permit = 2 and staff_id = " + userid;
				EmpModel emp = new EmpModel();
				emp.runSql(sql);
				String pw2 = "";
				try {
					pw2 = emp.getValueAt(0, 0).toString();
					if(pw2.equals(pw)) {
						LoadingScene ls = new LoadingScene("m",localeCurrent,userid,"x");
						Thread x = new Thread(ls);
						x.start();
						this.dispose();
					}else {
						JOptionPane.showMessageDialog(
								this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
					}
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(
							this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
				}
			}catch (Exception e1) {
				JOptionPane.showMessageDialog(
						this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}
		}else if(e.getSource()==mb1) {
			int x = 0;
			if(localeCurrent == 0) {
				x = 1;
			}else {
				x = 0;
			}
			ResourceBundle.clearCache();
			this.dispose();
			Login login = new Login(x);
		}else if(e.getSource()==p3b1){
			int userid = 0;
			try {
				userid = Integer.parseInt(p3tf1.getText());
				String pw= String.valueOf(p3tp1.getPassword());
				String sql = "select staff_pw from fyp_staff where staff_id = " + userid;
				EmpModel emp = new EmpModel();
				emp.runSql(sql);
				String pw2 = "";
				try {
					pw2 = emp.getValueAt(0, 0).toString();
					if(pw2.equals(pw)) {
						Item item = (Item)cb2.getSelectedItem();
						LoadingScene ls = new LoadingScene("k",localeCurrent,userid,item.getId());
						Thread x = new Thread(ls);
						x.start();
						this.dispose();
					}else {
						JOptionPane.showMessageDialog(
								this,w3,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
					}
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(
							this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
				}
			}catch (Exception e1) {
				JOptionPane.showMessageDialog(
						this,w2,rb.getString("w_w"),JOptionPane.ERROR_MESSAGE);
			}
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
