package commonView;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import model.EmpModel;
import tools.myFont;

public class AddFoodComboSelector extends JDialog implements ActionListener {
	String cid = "";
	String log = "";
	int flag = 0;
	int space = 2;
	
	public static ArrayList<String> a;
	ArrayList<String> b;
	
	JLabel[] labels = new JLabel[10];
	JRadioButton[] labels1 = new JRadioButton[10];
	ButtonGroup[] bg = new ButtonGroup[10];
	JPanel p1;
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
	
	public static ArrayList<String> getA() {
		return a;
	}

	public AddFoodComboSelector(Frame owner,String title,boolean modal,int locale,String comboid) {
		super(owner,title,modal);
		localeCurrent = locale;
		cid = comboid;
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
		this.setLocation(w/2-750, h/2-450);
		this.setSize(1500, 900);
		this.setVisible(true);
	}

	public void mainPanel() {
		p1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		EmpModel emp = new EmpModel();
		String sql = "select c_hash from fyp_combo where c_id = '"+cid+"' ";
		emp.runSql(sql);
		
		log = emp.getValueAt(0, 0).toString();
		a = new ArrayList<String>();
		b = new ArrayList<String>();
		
		String[] buff = log.split(",");
        for(int i=0;i<buff.length;i++){
        	if(buff[i].contains(":")) {
        		b.add(buff[i]);
        	}else {
        		a.add(buff[i]);
        	}
        }
        for(int x=0;x<b.size();x++) {
        	String[] buff2 = ((String)b.get(x)).split(":");
        	labels[x] = new JLabel();
        	labels[x].setText(buff2[0]+" "+rb.getString("af_selectCom"));
        	labels[x].setFont(myFont.f1);
        	gbc.gridx = 0;
        	if(x==0) {
        		gbc.gridy = x;
        	}else {
        		gbc.gridy = x*2;
        	}
        	p1.add(labels[x],gbc);
        	
            for(int g=0;g<buff2.length;g++){
            	if(buff2[g].contains("_")) {
            		String linkkey = buff2[g];
            		String[] buff3 = buff2[g].split("_");
            		bg[flag]=new ButtonGroup();
                    for(int y=0;y<buff3.length;y++){
                    	int foodid = Integer.parseInt(buff3[y]);
                    	labels1[y] = new JRadioButton();
                    	if(localeCurrent==0) {
                    		sql = "select food_name from fyp_food where food_id = "+foodid;
                    	}else {
                    		sql = "select food_name_zh from fyp_food where food_id = "+foodid;
                    	}
                    	emp.runSql(sql);
                    	String myString = rb.getString("af_id")+buff3[y]+"<br>"+rb.getString("af_name")+(String)emp.getValueAt(0, 0);
                    	labels1[y].setText("<html>"+ myString +"</html>");
                    	labels1[y].setFont(myFont.f1);
                    	labels1[y].addActionListener(this);
                    	labels1[y].setActionCommand(buff3[y]+"@"+linkkey);
                    	bg[flag].add(labels1[y]);
                    	gbc.gridx = y;
                    	if(x==0) {
                    		gbc.gridy = 1;
                    	}else {
                    		gbc.gridy = (x*2)+1;
                    	}
                    	p1.add(labels1[y],gbc);
                    }
                    flag = flag+1;
            	}
            }
        }
        final JScrollPane scroll = new JScrollPane(p1);
        this.add(scroll,"Center");
        
        ok = new JButton(rb.getString("common_ok"));
        ok.setFont(myFont.f1);
        ok.addActionListener(this);
        
        this.add(ok,"South");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok) {
			for(int y = 0; y<bg.length;y++) {
				if(bg[y] != null) {
					a.add(bg[y].getSelection().getActionCommand());
				}
			}
			this.dispose();
		}
	}
}
