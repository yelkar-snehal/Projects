import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;


class App
{
	//indicates native methods written in C
    public static native int calcBill(int[] params);
    public static native int calcTax(int result);

    static //static block --run only once --initialised or setting up the class at run time
    {
        try{

         System.loadLibrary("calc"); //libcalc/calc
        }
        catch(Exception e)	//unsatisfied link error
        {
            System.out.println(e);
        }
    }
    
    public static void main( String args[])
    {
    	//login window
        Login mf= new Login();
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//action for close button onthe frame --others--hide,dispose,donothing
        mf.setVisible(true);
    }
}
class AppFrame extends JFrame
{
	//dimensions for the frame
    public static final int DEFAULT_WIDTH =800;
    public static final int DEFAULT_HEIGHT= 600; //like macro

	//hashtable is used to store name and priice of product in key/value fashion for fast retrieval
    private Hashtable<String,Integer> processors;
    private Hashtable<String,Integer> ram;
    private Hashtable<String,Integer> graphicsCard;

    private Hashtable<String,Integer> monitors;
    private Hashtable<String,Integer> CPUcabinets;
    private Hashtable<String,Integer> HardDisk;
    private Hashtable<String,Integer> CacheMemory;



    JLabel resultLabel2,resultLabel3,cL1,cL2,cL3,cL4,cL5,cL6,cL7;

    public AppFrame()
    {
        setTitle("Computer Shopping Portal");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new GridLayout(10,3));
       

        processors= new Hashtable<String,Integer>();//<> indicates generic param
        processors.put("i3",1000);
        processors.put("i5",15000);
        processors.put("i7",20000);

        JPanel panel1 =new JPanel();
        JLabel processorLabel= new JLabel( "Select Processor:");
        panel1.add(processorLabel);

        JPanel panel2 =new JPanel();
        Set<String> processorkeys= processors.keySet();
        JComboBox processorComboBox=new JComboBox(processorkeys.toArray());
        panel2.add(processorComboBox);

        ram= new Hashtable<String,Integer>();
        ram.put("2 GB" ,3000);
        ram.put("4 GB",5000);
        ram.put("8 GB",8000);

        JPanel panel3= new JPanel();
        JLabel ramLabel= new JLabel("Select RAM : ");
        panel3.add(ramLabel);

        JPanel panel4= new JPanel();
        Set<String> ramKeys= ram.keySet();
        JComboBox ramComboBox = new JComboBox(ramKeys.toArray());
        panel4.add(ramComboBox);
        
        graphicsCard=new Hashtable< String,Integer>();
        graphicsCard.put("2 GB",3000);
        graphicsCard.put("4 GB", 5000);
        graphicsCard.put("8 GB ",8000);

        JPanel panel5 =new JPanel();
        JLabel graphicsCardLabel =new JLabel("Select Graphics Card:");
        panel5.add(graphicsCardLabel);

        JPanel panel6=new JPanel();
        Set<String>graphicsCardKeys =graphicsCard.keySet();
        JComboBox graphicsCardComboBox = new JComboBox(graphicsCardKeys.toArray());
        panel6.add(graphicsCardComboBox);


        monitors=new Hashtable< String,Integer>();
        monitors.put("LCD: Upto 19\"",6000 );
        monitors.put("LED: Upto 19\"",8000 );
        monitors.put("LCD: 20-24\"",10000 );
        monitors.put("LED: 20-24\"",12000 );
        monitors.put("LCD: 25\" & more",14000 );
        monitors.put("LED: 25\" & more",16000 );
        
        JPanel nPanel1=new JPanel();
        JLabel monitorLabel=new JLabel("Select Monitor:");
        nPanel1.add(monitorLabel);

        JPanel nPanel2=new JPanel();
        Set<String>monitorKeys=monitors.keySet();
        JComboBox monitorComboBox=new JComboBox(monitorKeys.toArray());
        nPanel2.add(monitorComboBox);

        CPUcabinets= new Hashtable< String,Integer>();
        CPUcabinets.put("Full Tower(76cm)", 18000);
        CPUcabinets.put("Mid Tower(45-60cm)", 6000);
        CPUcabinets.put("Mini Tower(30-45cm)", 2000);
        CPUcabinets.put("Small Form Factor Case", 5000);

        JPanel nPanel3=new JPanel();
        JLabel cabinetLabel=new JLabel("Select CPU cabinets:");
        nPanel3.add(cabinetLabel);

        JPanel nPanel4=new JPanel();
        Set<String>cabinetKeys=CPUcabinets.keySet();
        JComboBox cabinetComboBox=new JComboBox(cabinetKeys.toArray());
        nPanel4.add(cabinetComboBox);

        HardDisk= new Hashtable< String,Integer>();
        HardDisk.put("Seagate SATA HDD(500GB)", 2000);
        HardDisk.put("Seagate SATA HDD(1TB)", 4000);
        HardDisk.put("Samsung PCIe SSD(256GB)", 10000);
        HardDisk.put("Samsung PCIe SSD(512GB)", 18000);
        
        JPanel nPanel5=new JPanel();
        JLabel HDDLabel=new JLabel("Select Hard Disk Drive:");
        nPanel5.add(HDDLabel);

        JPanel nPanel6=new JPanel();
        Set<String> HDDKeys=HardDisk.keySet();
        JComboBox HDDComboBox=new JComboBox(HDDKeys.toArray());
        nPanel6.add(HDDComboBox);

        CacheMemory =new Hashtable< String,Integer>();
        CacheMemory.put("Level 1(L1): 2KB-64KB", 4000);
        CacheMemory.put("Level 2(L2): 256KB-512KB", 2000);
        CacheMemory.put("Level 1(L3): 1MB-8MB", 1000);

        JPanel nPanel7=new JPanel();
        JLabel CacheLabel=new JLabel("Select Cache Memory:");
        nPanel7.add(CacheLabel);

        JPanel nPanel8=new JPanel();
        Set<String> cacheKeys=CacheMemory.keySet();
        JComboBox cacheComboBox=new JComboBox(cacheKeys.toArray());
        nPanel8.add(cacheComboBox);
       
        JPanel panel7 =new JPanel();
        JButton calculateButton =new JButton( "Calculate Bill");
        calculateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                int processorPrice =processors.get((String)processorComboBox.getSelectedItem());
                int ramPrice= ram.get((String)ramComboBox.getSelectedItem());
                //interrupted by dialogue at least gigs for i5 and above
                if((processorPrice>=15000)&&(ramPrice<5000))
                {
                    JOptionPane.showMessageDialog(panel4,"Select at least 4GB ram for appropriate processors");
                    
                }
                int graphicsCardPrice= graphicsCard.get((String)graphicsCardComboBox.getSelectedItem());
                int monitorPrice=monitors.get((String)monitorComboBox.getSelectedItem());
                int cabinetPrice=CPUcabinets.get((String)cabinetComboBox.getSelectedItem());
                int HDDPrice=HardDisk.get((String)HDDComboBox.getSelectedItem());
                int cachePrice=CacheMemory.get((String)cacheComboBox.getSelectedItem());


                int params[]=new int[7];
                params[0]=processorPrice;
                params[1]=ramPrice;
                params[2]=graphicsCardPrice;
                params[3]=monitorPrice;
                params[4]=cabinetPrice;
                params[5]=HDDPrice;
                params[6]=cachePrice;

                cL1.setText("Rs." + params[0]);
                cL2.setText("Rs." + params[1]);
                cL3.setText("Rs." + params[6]);
                cL4.setText("Rs." + params[2]);
                cL5.setText("Rs." + params[3]);
                cL6.setText("Rs." + params[4]);
                cL7.setText("Rs." + params[5]);

				//native methods
                int result = App.calcBill(params);
                resultLabel2.setText("Rs." + result);

                int tax=App.calcTax(result);
                resultLabel3.setText("Rs." + tax);
            }
        });   

       

        panel7.add(calculateButton);

        JPanel panel8 = new JPanel();
        JPanel panel20 = new JPanel();
        JPanel panel21 = new JPanel();
        JPanel panel22 = new JPanel();
       
       

        JPanel panel9 = new JPanel();
        JLabel resultLabel =new JLabel("Your estimated amount :");
        panel9.add(resultLabel);

        JPanel panel10 =new JPanel();
        resultLabel2 = new JLabel("");
        panel10.add(resultLabel2);

        JPanel panel11 = new JPanel();
        JLabel resultLabel4 =new JLabel("Total amount with GST :");
        panel11.add(resultLabel4);

        JPanel panel12 =new JPanel();
        resultLabel3 = new JLabel("");
        panel12.add(resultLabel3);

        JPanel panel13 =new JPanel();
        cL1 = new JLabel("");
        panel13.add(cL1);

        JPanel panel14 =new JPanel();
        cL2 = new JLabel("");
        panel14.add(cL2);

        JPanel panel15 =new JPanel();
        cL3 = new JLabel("");
        panel15.add(cL3);

        JPanel panel16 =new JPanel();
        cL4 = new JLabel("");
        panel16.add(cL4);

        JPanel panel17 =new JPanel();
        cL5 = new JLabel("");
        panel17.add(cL5);

        JPanel panel18 =new JPanel();
        cL6 = new JLabel("");
        panel18.add(cL6);

        JPanel panel19 =new JPanel();
        cL7 = new JLabel("");
        panel19.add(cL7);

        add(panel1);
        add(panel2); 
        add(panel13);
        add(panel3); 
        add(panel4); 
        add(panel14);
        add(nPanel7);
        add(nPanel8);
        add(panel15);
        add(panel5);
        add(panel6);
        add(panel16);
        add(nPanel1);
        add(nPanel2);
        add(panel17);
        add(nPanel3);
        add(nPanel4);
        add(panel18);
        add(nPanel5);
        add(nPanel6);
        add(panel19);
        add(panel7); 
        add(panel8);
        add(panel20);
        add(panel9);
        add(panel21);
        add(panel10);
        add(panel11);
        add(panel22);
        add(panel12);
    
    }
}

class Login extends JFrame implements ActionListener	
{
		//frame components
        JLabel l1,l2;//name, pw
        JTextField tf1;//name ip
        JPasswordField tf2;//pw ip
        JButton b;//submit button
	
		//ststic count for no of attempts
        public static int Cnt=3;

		//constructor
        public Login()
        {
        	//title for the frame
            super(" E Shopping Portal");
			
			//background image
            setContentPane(new JLabel(new ImageIcon("/home/snehal/Desktop/Practice/Projects/JNI/ShoppingPortal/logol.jpg")));
            

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            
            l1=new JLabel("Username");
            l1.setBounds(50,70,150,20);	//x, y, width, height -- xand y are the coordinates from the upper left corner of the frame

            tf1 =new JTextField();
            tf1.setBounds(50,100,200,20);
            tf1.setToolTipText("Enter your username");

            l2=new JLabel("Password ");
            l2.setBounds(53,130,150,20);

            tf2=new JPasswordField();
            tf2.setBounds(50,160,200,20);
            tf2.setToolTipText("Enter your Password");

            b=new JButton("Login");
            b.setBounds(90,200,80,30);
            b.addActionListener(this);

            add(l1); 
            add(tf1);
            add(l2);
            add(tf2);
            add(b);

            setSize(300,300);
           
            setVisible(true);
        }
    
    	//abstract method given defn invoked when registered component is clicked in this case the button b
        public void actionPerformed( ActionEvent e)
        {
            CheckAuthentication();
        }

		//fn that handles auth 
        public void CheckAuthentication()
        {
            String username =tf1.getText();
            String password =tf2.getText();

			//if user is valid
            if((username.equals ("user")) && (password.equals("user@123")))
            {
                AppFrame af=new AppFrame();
                af.setDefaultCloseOperation(EXIT_ON_CLOSE);
                af.setVisible(true);
                dispose();//close this frame
            }
            else
            {
                --Cnt;
                if(Cnt==0)
                {
                    JOptionPane.showMessageDialog(this,"Number of attempts finished.");
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(this," Authentication failed.");
                    tf1.setText("");
                    tf2.setText("");
                }
            }
        }
}
  


/*Java Language Type	Native Type	Description
boolean

jboolean

8 bits, unsigned

byte

jbyte

8 bits, signed

char

jchar

16 bits, unsigned

double

jdouble

64 bits

float

jfloat

32 bits

int

jint

32 bits, signed

long

jlong

64 bits, signed

short

jshort

16 bits, signed

void

void

N/A*/
