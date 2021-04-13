import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BackProblemJFrame extends JFrame implements ActionListener {
	private JLabel backVolumeJL,goodsVolumeJL,goodsWorthJL,bestVolumeJL,groupJL,
                    groupGoodsVolumeJL,groupGoodsWorthJL;
	private JTextField backVolumeJTF,goodsVolumeJTF,goodsWorthJTF,bestVolumeJTF,groupJTF,
	                   groupGoodsVolumeJTF,groupGoodsWorthJTF;
	private JPanel jp;
	private JButton ok,reset;
	BackProblemJFrame (String s){
		super(s);
		backProblemJFrame();
		
	}
	
	private void backProblemJFrame() {
		// TODO 主界面方法
		this.setLayout(null);//设置为空布局  默认的是边界布局
		backVolumeJL = new JLabel("请输入背包的容量：");
		backVolumeJL.setBounds(30,5,125,25);
		this.add(backVolumeJL);
		
		backVolumeJTF = new JTextField(10);
		backVolumeJTF.setBounds(160,5,80,25);
		backVolumeJTF.addActionListener(this);
		this.add(backVolumeJTF);
		
		goodsVolumeJL = new JLabel("请输入所有物品的质量（个数不限，以逗号隔开）");
		goodsVolumeJL.setBounds(5,35,300,25);
		this.add(goodsVolumeJL);
		
		goodsVolumeJTF = new JTextField(40);
		goodsVolumeJTF.setBounds(10,65,270,25);
		goodsVolumeJTF.addActionListener(this);
	    this.add(goodsVolumeJTF);
	    
	    goodsWorthJL = new JLabel("请输入物品的价值（以逗号隔开，默认为零）");
		goodsWorthJL.setBounds(10,95,300,25);
		this.add(goodsWorthJL);
		
		goodsWorthJTF = new JTextField(40);
		goodsWorthJTF.setBounds(10,125,270,25);
		goodsWorthJTF.addActionListener(this);
	    this.add(goodsWorthJTF);
	    
	    ok = new JButton("确         定");
	    ok.setBounds(10,155,120,25);
	    ok.addActionListener(this);
	    this.add(ok);
	    
	    reset = new JButton("重           置");
	    reset.setBounds(160,155,120,25);
	    reset.addActionListener(this);
	    this.add(reset);
	    
	    jp = new JPanel();
	    jp.setBorder(BorderFactory.createTitledBorder("结果"));
	    jp.setBounds(10,185,270,150);
	    jp.setLayout(null);
	    this.add(jp);
		
	    bestVolumeJL = new JLabel("最优值：");
	    bestVolumeJL.setBounds(10,20,70,25);
	    jp.add(bestVolumeJL);
	    
	    bestVolumeJTF = new JTextField();
	    bestVolumeJTF.setBounds(80, 20, 180, 25);
	    bestVolumeJTF.setEditable(false);
	    jp.add(bestVolumeJTF);
	    
	    groupJL = new JLabel("组合方式：");
	    groupJL.setBounds(10,50,70,25);
	    jp.add(groupJL);
	    
	    groupJTF = new JTextField();
	    groupJTF.setBounds(80,50,180,25);
	    groupJTF.setEditable(false);
	    jp.add(groupJTF);
	    
	    groupGoodsVolumeJL = new JLabel("物品质量：");
	    groupGoodsVolumeJL.setBounds(10,80,70,25);
	    jp.add(groupGoodsVolumeJL);
	    
	    groupGoodsVolumeJTF = new JTextField();
	    groupGoodsVolumeJTF.setBounds(80,80,180,25);
	    groupGoodsVolumeJTF.setEditable(false);
	    jp.add(groupGoodsVolumeJTF);
	    
	    groupGoodsWorthJL = new JLabel("物品价值：");
	    groupGoodsWorthJL.setBounds(10,110,70,25);
	    jp.add(groupGoodsWorthJL);
	    
	    groupGoodsWorthJTF = new JTextField();
	    groupGoodsWorthJTF.setBounds(80,110,180,25);
	    groupGoodsWorthJTF.setEditable(false);
	    jp.add(groupGoodsWorthJTF);
	    //界面的设置
		Toolkit tk = getToolkit();
		Dimension dim = tk.getScreenSize(); 
	    this.setResizable(false);
		this.setBounds(dim.width/2-150, dim.height/2-200,300,380);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
