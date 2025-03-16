/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package csci6461;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.BitSet;
// import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class MainFrame extends JFrame {
	
//	 Initialise memory
	private static Simulator simulator = Simulator.getInstance();
	private static Memory memory = Memory.getInstance();

	static int mar;
	static int mbr;
	static int pc = 10;
	static int ir;
	static String ir_binary;
	static byte OPCODE;
	static byte R;
	static byte IX;
	static byte I;
	static int ADDR;
	static int pcx;
	
	
	private JPanel contentPane;
	private static JTextField register0TextField;
	private static JTextField register1TextField;
	private static JTextField register2TextField;
	private static JTextField inputTextField;
	private static JTextField register3TextField;
	private static JTextField ixr1TextField;
	private static JTextField ixr2TextField;
	private static JTextField ixr3TextField;
	private static JTextField pcTextField;
	private static JTextField marTextField;
	private static JTextField mbrTextField;
	private static JTextField irTextField;
	 
	private static JTextField mfrTextField;
	private static JTextField PriviledgedTextField;
	private static JTextField CCTextField;
	private static JTextArea KeyboardTextField;
	private static JTextArea PrinterTextField;
	private static JTextArea TagTextField;
	private static JTextArea TagValueTextField;
	private JTextField textField_FR0;
	private JTextField textField_FR1;
	
	/*
	 * Method to get the text field object for respective registers
	 */
	
	
	private static JTextField getRegisterGUI(String regStr) {
		if (regStr == "R0") {
			return register0TextField;
		}
		if (regStr == "R1") {
			return register1TextField;
		}
		if (regStr == "R2") {
			return register2TextField;
		}
		if (regStr == "R3") {
			return register3TextField;
		}
		if (regStr == "X1") {
			return ixr1TextField;
		}
		if (regStr == "X2") {
			return ixr2TextField;
		}
		if (regStr == "X3") {
			return ixr3TextField;
		}
		if (regStr == "PC") {
			return pcTextField;
		}
		if (regStr == "MAR") {
			return marTextField;
		}
		if (regStr == "MBR") {
			return mbrTextField;
		}
		if (regStr == "IR") {
			return irTextField;
		}
		if (regStr == "CC") {
			return CCTextField;
		}
		return null;
	}
	
	
	/**
	 * Method to update Registers display on UI
	 * 
	 * @param regStr
	 * @param value
	 * @param length
	 */
	public static void updateRegisterDisplay(String regStr, BitSet value, int length) {
		JTextField reg = getRegisterGUI(regStr);
		// no update if no corresponding GUI element
		if (reg == null) {
			return;
		}
		String result = Integer.toBinaryString(Util.bitSet2Int(value));
		int zeros = length - result.length();
		for (int i = 0; i < zeros; i++) {
			result = "0" + result;
		}
		reg.setText(result);
	}
	
	/**
	 * Method to update Cache on UI
	 * 
	 * @param tag
	 * @param value
	 */
	public static void updateCacheUI(ArrayList<Integer> tag, ArrayList<Word> value) {
		String strTag = "";
		String strVal = "";
		for (int i = 0; i < tag.size(); i++) {
			strTag += tag.get(i).toString() + '\n';
			strVal += Util.bitSet2Int(value.get(i)) + "\n";
		}
		TagTextField.setText(strTag);
		TagValueTextField.setText(strVal);
	}
	
	/**
	 * Method to Get input from UI keyboard
	 * @return
	 */
	public static int getKeyboard() {
		String text = KeyboardTextField.getText();
		String token = text.split(",")[0]; // get first element
		int result;
		try {
			result = Integer.parseInt(token);
		} catch (NumberFormatException n) {
			result = Integer.parseInt(String.valueOf(token.charAt(0)));
		}
		if (text.indexOf(",") != -1)
			text = text.substring(text.indexOf(",") + 1);
		else text = "";
		KeyboardTextField.setText(text);
		return result;
	}
	
	/**
	 * Method to set printer on UI using integer output
	 * @param output
	 */
	public static void setPrinter(int output) {
		PrinterTextField.setText(PrinterTextField.getText() + output);
	}
	/**
	 * Method to set printer on UI using string output
	 * @param output
	 */
	public static void setPrinter(String output) {
		PrinterTextField.setText(PrinterTextField.getText() + output);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		System.out.println(pc);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Simulator UI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);


		// Set background color
        contentPane.setBackground(Color.LIGHT_GRAY); // Adjust color as needed
		
		JLabel lblNewLabel = new JLabel("GPR 0");
		lblNewLabel.setBounds(25, 32, 63, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("GPR 1");
		lblNewLabel_1.setBounds(25, 82, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("GPR 2");
		lblNewLabel_2.setBounds(25, 118, 45, 13);
		contentPane.add(lblNewLabel_2);
		
		register0TextField = new JTextField();
		register0TextField.setEditable(false);
		register0TextField.setBounds(136, 35, 245, 19);
		register0TextField.setText("0000000000000000");
		contentPane.add(register0TextField);
		register0TextField.setColumns(10);
		
		register1TextField = new JTextField();
		register1TextField.setEditable(false);
		register1TextField.setBounds(136, 79, 245, 19);
		register1TextField.setText("0000000000000000");
		contentPane.add(register1TextField);
		register1TextField.setColumns(10);
		
		register2TextField = new JTextField();
		register2TextField.setEditable(false);
		register2TextField.setBounds(136, 115, 245, 19);
		register2TextField.setText("0000000000000000");
		contentPane.add(register2TextField);
		register2TextField.setColumns(10);
		
		JButton GPR0_load = new JButton("LD");
		GPR0_load.setForeground(Color.BLUE); // Set foreground color to blue
		GPR0_load.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("R0", inputTextField.getText());

			}
		});
		GPR0_load.setBounds(391, 35, 85, 21);
		contentPane.add(GPR0_load);
		
		JButton GPR1_load = new JButton("LD");
		GPR1_load.setForeground(Color.BLUE); // Set foreground (text) color to blue
		GPR1_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("R1", inputTextField.getText());
			}
		});
		GPR1_load.setBounds(391, 79, 85, 21);
		contentPane.add(GPR1_load);
		
		JButton GPR2_load = new JButton("LD");
		GPR2_load.setForeground(Color.BLUE); // Set foreground (text) color to blue
		GPR2_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("R2", inputTextField.getText());
			}
		});
		GPR2_load.setBounds(391, 115, 85, 21);
		contentPane.add(GPR2_load);
		
		inputTextField = new JTextField();
		inputTextField.setBounds(90, 410, 358, 19);
		contentPane.add(inputTextField);
		inputTextField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Input");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(90, 437, 358, 13);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2_1 = new JLabel("GPR 3");
		lblNewLabel_2_1.setBounds(25, 155, 45, 13);
		contentPane.add(lblNewLabel_2_1);
		
		register3TextField = new JTextField();
		register3TextField.setEditable(false);
		register3TextField.setColumns(10);
		register3TextField.setBounds(136, 152, 245, 19);
		register3TextField.setText("0000000000000000");
		contentPane.add(register3TextField);
		
		JButton GPR3_load = new JButton("LD");
		
		GPR3_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("R3", inputTextField.getText());
			}
		});
		GPR3_load.setForeground(Color.BLUE); // Set foreground (text) color to blue
		GPR3_load.setBounds(391, 152, 85, 21);
		contentPane.add(GPR3_load);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("IXR 1");
		
		lblNewLabel_2_1_1.setBounds(25, 196, 45, 13);
		contentPane.add(lblNewLabel_2_1_1);
		
		ixr1TextField = new JTextField();
		ixr1TextField.setEditable(false);
		ixr1TextField.setColumns(10);
		ixr1TextField.setBounds(136, 193, 245, 19);
		ixr1TextField.setText("0000000000000000");
		contentPane.add(ixr1TextField);
		
		JButton IXR1_load = new JButton("LD");
		IXR1_load.setForeground(Color.RED); // Set foreground (text) color to red
		IXR1_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("X1", inputTextField.getText());
			}
		});
		IXR1_load.setBounds(391, 193, 85, 21);
		contentPane.add(IXR1_load);

		ixr2TextField = new JTextField();
		ixr2TextField.setEditable(false);
		ixr2TextField.setColumns(10);
		ixr2TextField.setBounds(136, 230, 245, 19);
		ixr2TextField.setText("0000000000000000");

		contentPane.add(ixr2TextField);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("IXR 2");
		IXR1_load.setForeground(Color.RED); // Set foreground (text) color to red
		lblNewLabel_2_1_1_1.setBounds(25, 233, 45, 13);
		contentPane.add(lblNewLabel_2_1_1_1);
		
		JButton IXR2_load = new JButton("LD");
		IXR2_load.setForeground(Color.RED); // Set foreground (text) color to red
		IXR2_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("X2", inputTextField.getText());
			}
		});
		IXR2_load.setBounds(391, 230, 85, 21);
		contentPane.add(IXR2_load);
		
		ixr3TextField = new JTextField();
		ixr3TextField.setEditable(false);
		ixr3TextField.setBounds(136, 272, 245, 19);
		ixr3TextField.setText("0000000000000000");

		contentPane.add(ixr3TextField);
		
		JLabel lblNewLabel_2_1_1_2 = new JLabel("IXR 3");
		lblNewLabel_2_1_1_2.setBounds(25, 272, 45, 13);
		contentPane.add(lblNewLabel_2_1_1_2);
		
		ixr3TextField = new JTextField();
		ixr3TextField.setEditable(false);
		ixr3TextField.setColumns(10);
		ixr3TextField.setBounds(136, 272, 245, 19);
		ixr3TextField.setText("0000000000000000");
		contentPane.add(ixr3TextField);
		
		JButton IXR3_load = new JButton("LD");
		IXR3_load.setForeground(Color.RED); // Set foreground (text) color to red
		IXR3_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("X3", inputTextField.getText());
			}
		});
		IXR3_load.setBounds(391, 272, 85, 21);
		contentPane.add(IXR3_load);
		
		
		JLabel lblPc = new JLabel("PC");
		lblPc.setBounds(673, 32, 63, 24);
		contentPane.add(lblPc);
		
		pcTextField = new JTextField();
		pcTextField.setEditable(false);
		pcTextField.setColumns(10);
		pcTextField.setBounds(784, 35, 313, 19);
		pcTextField.setText("000000000000");
		
		contentPane.add(pcTextField);
		
		JButton PC_load = new JButton("LD");
		PC_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("PC", inputTextField.getText());
			}
		});
		PC_load.setBounds(1107, 35, 85, 21);
		contentPane.add(PC_load);
		
		JLabel lblNewLabel_4_1 = new JLabel("MAR");
		lblNewLabel_4_1.setBounds(673, 82, 63, 24);
		contentPane.add(lblNewLabel_4_1);
		
		marTextField = new JTextField();
		marTextField.setEditable(false);
		marTextField.setColumns(10);
		marTextField.setBounds(784, 85, 313, 19);
		marTextField.setText("000000000000");

		contentPane.add(marTextField);
		
		JButton MAR_load = new JButton("LD");
		MAR_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("MAR", inputTextField.getText());
			}
		});
		MAR_load.setBounds(1107, 85, 85, 21);
		contentPane.add(MAR_load);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("MBR");
		lblNewLabel_4_1_1.setBounds(673, 118, 63, 24);
		contentPane.add(lblNewLabel_4_1_1);
		
		mbrTextField = new JTextField();
		mbrTextField.setEditable(false);
		mbrTextField.setColumns(10);
		mbrTextField.setBounds(784, 121, 313, 19);
		mbrTextField.setText("0000000000000000");
		contentPane.add(mbrTextField);
		
		JButton MBR_load = new JButton("LD");
		MBR_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("MBR", inputTextField.getText());
			}
		});
		MBR_load.setBounds(1107, 121, 85, 21);
		contentPane.add(MBR_load);
		
		JLabel lblNewLabel_4_1_1_1 = new JLabel("IR");
		lblNewLabel_4_1_1_1.setBounds(673, 160, 63, 24);
		contentPane.add(lblNewLabel_4_1_1_1);
		
		irTextField = new JTextField();
		irTextField.setEditable(false);
		irTextField.setColumns(10);
		irTextField.setBounds(784, 163, 313, 19);
		irTextField.setText("0000000000000000");
		contentPane.add(irTextField);
		
		JButton btnNewButton_3 = new JButton("Load");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.load();
			}
		});
		btnNewButton_3.setBounds(673, 353, 74, 37);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Store");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.store();
			}
		});
		btnNewButton_4.setBounds(673, 396, 74, 37);
		
		contentPane.add(btnNewButton_4);
		
		JButton SS_button = new JButton("SS");
		SS_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.singleStep();
			}
		});
		SS_button.setBounds(854, 365, 94, 59);
		contentPane.add(SS_button);
		
		JButton Run_button = new JButton("Run");
		Run_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.runProgram();
			}
		});
		Run_button.setBounds(958, 365, 94, 59);
		contentPane.add(Run_button);
		
		JButton Init_Button = new JButton("INIT");
		Init_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.init("./src/main/java/csci6461/textFiles/boot.txt");
			}
		});
		Init_Button.setBounds(754, 376, 74, 37);
		contentPane.add(Init_Button);
		
		JLabel lblNewLabel_4_1_1_2 = new JLabel("MFR");
		lblNewLabel_4_1_1_2.setBounds(673, 196, 63, 24);
		contentPane.add(lblNewLabel_4_1_1_2);
		
		mfrTextField = new JTextField();
		mfrTextField.setText("0000");
		mfrTextField.setEditable(false);
		mfrTextField.setColumns(10);
		mfrTextField.setBounds(784, 199, 313, 19);
		contentPane.add(mfrTextField);
		
		JLabel lblNewLabel_4_1_1_3 = new JLabel("Priviledged");
		lblNewLabel_4_1_1_3.setBounds(673, 238, 85, 24);
		contentPane.add(lblNewLabel_4_1_1_3);
		
		PriviledgedTextField = new JTextField();
		PriviledgedTextField.setText("0");
		PriviledgedTextField.setEditable(false);
		PriviledgedTextField.setColumns(10);
		PriviledgedTextField.setBounds(784, 241, 313, 19);
		contentPane.add(PriviledgedTextField);
		
		JLabel lblNewLabel_4_1_1_3_1 = new JLabel("CC");
		lblNewLabel_4_1_1_3_1.setBounds(673, 288, 85, 24);
		contentPane.add(lblNewLabel_4_1_1_3_1);
		
		CCTextField = new JTextField();
		CCTextField.setText("0000");
		CCTextField.setEditable(false);
		CCTextField.setColumns(10);
		CCTextField.setBounds(784, 291, 313, 19);
		contentPane.add(CCTextField);
		
		JButton CC_Button = new JButton("LD");
		CC_Button.setBounds(1107, 290, 85, 21);
		CC_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("CC", inputTextField.getText());
			}
		});
		contentPane.add(CC_Button);
		
		KeyboardTextField = new JTextArea();
		KeyboardTextField.setLineWrap(true);
		KeyboardTextField.setBounds(673, 479, 223, 183);
		contentPane.add(KeyboardTextField);
		KeyboardTextField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Keyboard Console");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(673, 672, 223, 13);
		contentPane.add(lblNewLabel_4);
		
		PrinterTextField = new JTextArea();
		JScrollPane scrollPane = new JScrollPane (PrinterTextField, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		PrinterTextField.setEditable(false);
		PrinterTextField.setLineWrap(true);
		PrinterTextField.setColumns(10);
		scrollPane.setBounds(939, 479, 223, 183);
		contentPane.add(scrollPane);

		
		JLabel lblNewLabel_4_2 = new JLabel("Printer Display");
		lblNewLabel_4_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_2.setBounds(939, 670, 223, 17);
		contentPane.add(lblNewLabel_4_2);
		
		TagTextField = new JTextArea();
		JScrollPane scrollPaneTag = new JScrollPane (TagTextField, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		TagTextField.setBackground(Color.WHITE);
		TagTextField.setEditable(false);
		TagTextField.setColumns(10);
		scrollPaneTag.setBounds(25, 483, 223, 183);
		contentPane.add(scrollPaneTag);
		
		TagValueTextField = new JTextArea();
		JScrollPane scrollPaneTagValue = new JScrollPane (TagValueTextField, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		TagValueTextField.setBackground(Color.WHITE);
		TagValueTextField.setEditable(false);
		TagValueTextField.setColumns(10);
		scrollPaneTagValue.setBounds(279, 483, 223, 183);
		contentPane.add(scrollPaneTagValue);
		
		
		JLabel lblNewLabel_5 = new JLabel("CACHE");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(158, 676, 223, 23);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Tag");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(25, 460, 223, 13);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_6_1 = new JLabel("Value");
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6_1.setBounds(279, 460, 223, 13);
		contentPane.add(lblNewLabel_6_1);
		
		JButton Program1_Button = new JButton("Program 1");
		Program1_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.init("./src/main/java/csci6461/textFiles/program1.txt");
				if (KeyboardTextField.getText().isEmpty()) {
					setPrinter("using default value...\n");
					KeyboardTextField.setText("17827,8255,23642,1412,870,3177,2639,231,2899,18,1109,31,88,973,100,1281,1241,713,119,240,5000");
				}
					
				String msg = "Array of 20 integers:\n";
				setPrinter(msg);
				int k;
				// write array to memory and print in console output
				for (int i = 0; i < 20; i++) {
					k = getKeyboard();
					memory.write(Util.int2Word(k), 800 + i + 1);
					setPrinter(k);
					if (i < 19) setPrinter(","); 
					else setPrinter("\n");
				}
				// write search number to memory and print in console output
				k = getKeyboard();
				memory.write(Util.int2Word(k), 400);
				msg = "Search number: ";
				setPrinter(msg);
				setPrinter(k);
				setPrinter("\n");
			}
		});
		Program1_Button.setBounds(1075, 353, 157, 21);
		contentPane.add(Program1_Button);
		
		JLabel lblNewLabel_2_1_1_1_1_1 = new JLabel("FR 0");
		
		lblNewLabel_2_1_1_1_1_1.setBounds(25, 315, 45, 13);
		contentPane.add(lblNewLabel_2_1_1_1_1_1);
		
		textField_FR0 = new JTextField();
		textField_FR0.setText("0000000000000000");
		textField_FR0.setEditable(false);
		textField_FR0.setColumns(10);
		textField_FR0.setBounds(136, 312, 245, 19);
		contentPane.add(textField_FR0);
		
		JButton FR0_load = new JButton("LD");
		FR0_load.setForeground(Color.MAGENTA); // Set foreground (text) color to PINK
		FR0_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("X3", inputTextField.getText());
			}
		});
		FR0_load.setBounds(391, 312, 85, 21);
		contentPane.add(FR0_load);
		
		JLabel lblNewLabel_2_1_1_1_1_2 = new JLabel("FR 1");
		FR0_load.setForeground(Color.MAGENTA); // Set foreground (text) color to PINK
		lblNewLabel_2_1_1_1_1_2.setBounds(25, 353, 45, 13);
		contentPane.add(lblNewLabel_2_1_1_1_1_2);
		
		textField_FR1 = new JTextField();
		textField_FR1.setText("0000000000000000");
		textField_FR1.setEditable(false);
		textField_FR1.setColumns(10);
		textField_FR1.setBounds(136, 350, 245, 19);
		contentPane.add(textField_FR1);
		
		JButton FR1_load = new JButton("LD");
		FR1_load.setForeground(Color.MAGENTA); // Set foreground (text) color to PINK
		FR1_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.loadRegisterFromInput("X3", inputTextField.getText());
			}
		});
		FR1_load.setBounds(391, 350, 85, 21);
		contentPane.add(FR1_load);
		
		simulator.init("./src/main/java/csci6461/textFiles/boot.txt");
	}


	public static void clearPrinter() {
		PrinterTextField.setText("");
	}
}

