package managerView;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.myBorder;
import tools.myColor;
import tools.myFont;

public class MaterialServicePanel extends JPanel implements MouseListener {
	
	CardLayout cardP1;
	JPanel p,m,p1;
	JLabel l1,l2,l3,l4,l5,l6;
	
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
	
	public MaterialServicePanel(int locale) {
		localeCurrent = locale;
		initLocale();
		p = new JPanel(new BorderLayout());
		
		cardP1 = new CardLayout();
		m = new JPanel(this.cardP1);
		
		p1 = new JPanel(new GridLayout(0,4));
		p1.setBackground(myColor.orange);
		
		l1 = new JLabel(rb.getString("ms_ms"),new ImageIcon("images/food/logo/ms.jpg"),JLabel.CENTER);
		l2 = new JLabel(rb.getString("ms_m"),new ImageIcon("images/food/logo/m.jpg"),JLabel.CENTER);
		l3 = new JLabel(rb.getString("ms_c"),new ImageIcon("images/food/logo/com.png"),JLabel.CENTER);
		l4 = new JLabel(rb.getString("ms_buy"),new ImageIcon("images/food/logo/tran.png"),JLabel.CENTER);
		l1.setFont(myFont.f1);
		l2.setFont(myFont.f1);
		l3.setFont(myFont.f1);
		l4.setFont(myFont.f1);
		
		l1.setBorder(myBorder.border2);
		l2.setBorder(myBorder.border2);
		l3.setBorder(myBorder.border2);
		l4.setBorder(myBorder.border2);
		
		l1.setVerticalTextPosition(JLabel.TOP);
		l2.setVerticalTextPosition(JLabel.TOP);
		l3.setVerticalTextPosition(JLabel.TOP);
		l4.setVerticalTextPosition(JLabel.TOP);
		l1.setHorizontalTextPosition(JLabel.CENTER);
		l2.setHorizontalTextPosition(JLabel.CENTER);
		l3.setHorizontalTextPosition(JLabel.CENTER);
		l4.setHorizontalTextPosition(JLabel.CENTER);

		l1.addMouseListener(this);
		l2.addMouseListener(this);
		l3.addMouseListener(this);
		l4.addMouseListener(this);
		
		p1.add(l1);
		p1.add(l2);
		p1.add(l3);
		p1.add(l4);
		
		m.add(p1,"0");
		
		MaterialShopPanel msp = new MaterialShopPanel(localeCurrent);
		m.add(msp,"1");
		
		MaterialPanel mp= new MaterialPanel(localeCurrent);
		m.add(mp,"2");
		
		MaterialComPanel mcp = new MaterialComPanel(localeCurrent);
		m.add(mcp,"3");
		
		MaterialTranPanel mtp = new MaterialTranPanel(localeCurrent);
		m.add(mtp,"4");
		
		p.add(m,"Center");
		this.setLayout(new BorderLayout());
		this.add(p);
		this.setVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == l1) {
			this.cardP1.show(m, "1");
		}else if(e.getSource() == l2) {
			this.cardP1.show(m, "2");
		}else if(e.getSource() == l3) {
			this.cardP1.show(m, "3");
		}else if(e.getSource() == l4) {
			this.cardP1.show(m, "4");
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
