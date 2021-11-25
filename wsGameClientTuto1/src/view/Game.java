package view;
import webservices.GameProxy;
import webservices.GameSoapBindingStub;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.BorderFactory;

import control.SudokuWS;
import model.Sudoku;

public class Game extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int n = 9, m = 9;
	public JButton btnRestart = new JButton();
	public JButton btn[][] = new JButton[n][m];
	public JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
	public int currentValue; 
	public int value[][] = new int[n][m];
	private String[] gameList = new String[]{"061490020280007050003108007600704031000250074090600000000010008570000206800906000",
	        "608900050000320190010000300400073600570260000003105020080000064020090507047008001",
	        "000700900004300527010006084800094053040001200962080070100869000700020130059000000",
	        "680905000003000508402108703390720800000000010045006900060804002001002075700013000",
	        "600837001089004700102000400000450020030609005040000860908006070700098010005100930",
	        "000067430800009150500003009007000010001806304940350020009010502608200700400708000",
	        "063700401400000000700091300092076030004500260035000100509040820087010609000003000",
	        "000340002006082073700100450082005014000098300670000005140700000905030020030000806",
	        "008070600960001405402000010200830090600790103007004026500900307030020500000310089",
	        "346179258187523964529648371965832417472916835813754629798261543631485792254397180"};

	JPanel panelNorth = new JPanel();
	JPanel panelSouth = new JPanel();
	JPanel panelCenter = new JPanel();

	public JFrame frame = new JFrame();

	public Game() {
		super("Video call app");
		currentValue = new Integer(0);
		frame.setLayout(new BorderLayout());

		panelNorth.setBackground(Color.gray);
		panelSouth.setBackground(Color.gray);

		JLabel lbHeader = new JLabel("GAME SUDOKU");

		panelNorth.add(lbHeader);

		btnRestart = new JButton("New game");

		btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				newGame();
			}
		});
		panelSouth.add(btnRestart);
		btn1 = new JButton("1");
		btn2 = new JButton("2");
		btn3 = new JButton("3");
		btn4 = new JButton("4");
		btn5 = new JButton("5");
		btn6 = new JButton("6");
		btn7 = new JButton("7");
		btn8 = new JButton("8");
		btn9 = new JButton("9");
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentValue = 1;
				btn1.setBackground(Color.lightGray);
				btn2.setBackground(Color.white);
				btn3.setBackground(Color.white);
				btn4.setBackground(Color.white);
				btn5.setBackground(Color.white);
				btn6.setBackground(Color.white);
				btn7.setBackground(Color.white);
				btn8.setBackground(Color.white);
				btn9.setBackground(Color.white);
			}
		});
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentValue = 2;
				btn1.setBackground(Color.white);
				btn2.setBackground(Color.lightGray);
				btn3.setBackground(Color.white);
				btn4.setBackground(Color.white);
				btn5.setBackground(Color.white);
				btn6.setBackground(Color.white);
				btn7.setBackground(Color.white);
				btn8.setBackground(Color.white);
				btn9.setBackground(Color.white);
			}
		});
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentValue = 3;
				btn1.setBackground(Color.white);
				btn2.setBackground(Color.white);
				btn3.setBackground(Color.lightGray);
				btn4.setBackground(Color.white);
				btn5.setBackground(Color.white);
				btn6.setBackground(Color.white);
				btn7.setBackground(Color.white);
				btn8.setBackground(Color.white);
				btn9.setBackground(Color.white);
			}
		});
		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentValue = 4;
				btn1.setBackground(Color.white);
				btn2.setBackground(Color.white);
				btn3.setBackground(Color.white);
				btn4.setBackground(Color.lightGray);
				btn5.setBackground(Color.white);
				btn6.setBackground(Color.white);
				btn7.setBackground(Color.white);
				btn8.setBackground(Color.white);
				btn9.setBackground(Color.white);
			}
		});
		btn5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentValue = 5;
				btn1.setBackground(Color.white);
				btn2.setBackground(Color.white);
				btn3.setBackground(Color.white);
				btn4.setBackground(Color.white);
				btn5.setBackground(Color.lightGray);
				btn6.setBackground(Color.white);
				btn7.setBackground(Color.white);
				btn8.setBackground(Color.white);
				btn9.setBackground(Color.white);
			}
		});
		btn6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentValue = 6;
				btn1.setBackground(Color.white);
				btn2.setBackground(Color.white);
				btn3.setBackground(Color.white);
				btn4.setBackground(Color.white);
				btn5.setBackground(Color.white);
				btn6.setBackground(Color.lightGray);
				btn7.setBackground(Color.white);
				btn8.setBackground(Color.white);
				btn9.setBackground(Color.white);
			}
		});
		btn7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentValue = 7;
				btn1.setBackground(Color.white);
				btn2.setBackground(Color.white);
				btn3.setBackground(Color.white);
				btn4.setBackground(Color.white);
				btn5.setBackground(Color.white);
				btn6.setBackground(Color.white);
				btn7.setBackground(Color.lightGray);
				btn8.setBackground(Color.white);
				btn9.setBackground(Color.white);
			}
		});
		btn8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentValue = 8;
				btn1.setBackground(Color.white);
				btn2.setBackground(Color.white);
				btn3.setBackground(Color.white);
				btn4.setBackground(Color.white);
				btn5.setBackground(Color.white);
				btn6.setBackground(Color.white);
				btn7.setBackground(Color.white);
				btn8.setBackground(Color.lightGray);
				btn9.setBackground(Color.white);
			}
		});
		btn9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				currentValue = 9;
				btn1.setBackground(Color.white);
				btn2.setBackground(Color.white);
				btn3.setBackground(Color.white);
				btn4.setBackground(Color.white);
				btn5.setBackground(Color.white);
				btn6.setBackground(Color.white);
				btn7.setBackground(Color.white);
				btn8.setBackground(Color.white);
				btn9.setBackground(Color.lightGray);
			}
		});

		panelSouth.add(btn1);
		panelSouth.add(btn2);
		panelSouth.add(btn3);
		panelSouth.add(btn4);
		panelSouth.add(btn5);
		panelSouth.add(btn6);
		panelSouth.add(btn7);
		panelSouth.add(btn8);
		panelSouth.add(btn9);

		frame.add(panelNorth, BorderLayout.NORTH);
		frame.add(panelSouth, BorderLayout.SOUTH);
		frame.add(panelCenter); 
		panelCenter.setLayout(new GridLayout(n, m));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				// pos[i][j] = num;
				// num++;
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				btn[i][j] = new JButton();
				btn[i][j].addActionListener(this);
													
				panelCenter.add(btn[i][j]);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				btn[i][j].setBackground(Color.white);
			}
		}
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		panelCenter.setSize(500, 500);
		frame.setSize(550, 550);
		frame.setResizable(false);
		for (int i = 0; i < 9; i += 3) {
			for (int j = 0; j < 9; j += 3) {
				btn[i][j].setBorder(BorderFactory.createMatteBorder(2, 2, 1, 1,
						Color.GRAY));
				btn[i][j + 2].setBorder(BorderFactory.createMatteBorder(2, 1,
						1, 2, Color.GRAY));
				btn[i + 2][j + 2].setBorder(BorderFactory.createMatteBorder(1,
						1, 2, 2, Color.GRAY));
				btn[i + 2][j].setBorder(BorderFactory.createMatteBorder(1, 2,
						2, 1, Color.GRAY));
				btn[i][j + 1].setBorder(BorderFactory.createMatteBorder(2, 1,
						1, 1, Color.GRAY));
				btn[i + 1][j + 2].setBorder(BorderFactory.createMatteBorder(1,
						1, 1, 2, Color.GRAY));
				btn[i + 2][j + 1].setBorder(BorderFactory.createMatteBorder(1,
						1, 2, 1, Color.GRAY));
				btn[i + 1][j].setBorder(BorderFactory.createMatteBorder(1, 2,
						1, 1, Color.GRAY));
				btn[i + 1][j + 1].setBorder(BorderFactory.createMatteBorder(1,
						1, 1, 1, Color.GRAY));
			}
		}
		newGame();
	}

	public void newGame() {
		Random rand = new Random();
		btn1.setBackground(Color.white);
		btn2.setBackground(Color.white);
		btn3.setBackground(Color.white);
		btn4.setBackground(Color.white);
		btn5.setBackground(Color.white);
		btn6.setBackground(Color.white);
		btn7.setBackground(Color.white);
		btn8.setBackground(Color.white);
		btn9.setBackground(Color.white);
		int x = rand.nextInt(10);
		 String game = gameList[x];
//		String game = "346179258187523964529648371965832417472916835813754629798261543631485792254397100";
		
		int index = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				value[i][j] = 0;
				btn[i][j].setBackground(Color.white);
				btn[i][j].setText("");
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Integer.parseInt(Character.toString(game.charAt(index))) > 0) {

					btn[i][j].setText(Character.toString(game.charAt(index)));
					btn[i][j].setBackground(Color.lightGray);
					value[i][j] = Integer.parseInt(Character.toString(game
							.charAt(index)));

				}
				index++;

			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		JButton btnClicked = (JButton) e.getSource();
		if (btnClicked.equals(btn1)) {
			currentValue = 1;
		} else {
			for (int i = 0; i < n; i++) {

				for (int j = 0; j < m; j++) {
					if (e.getSource() == btn[i][j] && btn[i][j].getText() == "") {
						try {
//							webservices.Game service = new GameProxy();
//							GameSoapBindingStub service  = new GameSoapBindingStub();
							SudokuWS service = new SudokuWS();
							 Sudoku checkValidData = new Sudoku();
							 checkValidData.setArrayValue(value);
							 checkValidData.setLastValue(currentValue);
							 checkValidData.setLastX(i);
							 checkValidData.setLastY(j);
							if (service.remoteCheckValid(checkValidData) == false) {
								JOptionPane.showMessageDialog(this, "Invalid!");
							} else {
								if (service.remoteCheckWin(checkValidData) == true) {
									JOptionPane.showMessageDialog(this,"You win!");
									newGame();
								} else {
									btn[i][j].setText(currentValue + "");
									value[i][j] = currentValue;
								}
							}
						} catch (Exception ee) {
							ee.printStackTrace();
						}
						// Sudoku checkValidData = new Sudoku();
						// checkValidData.setArrayValue(value);
						// checkValidData.setLastValue(currentValue);
						// checkValidData.setLastX(i);
						// checkValidData.setLastY(j);
						// mySocket.sendData(new
						// ObjectWrapper(ObjectWrapper.CHECK_VALID,
						// checkValidData));
						//
						// ObjectWrapper data = mySocket.receiveData();
						// if (data.getPerformative() ==
						// ObjectWrapper.REPLY_CHECK_VALID) {
						// String result = (String) data.getData();
						// System.out.println(result);
						// if (result.equals("invalid")) {
						// JOptionPane.showMessageDialog(this, "Invalid!");
						// } else if (result.equals("valid")) {
						// btn[i][j].setText(currentValue + "");
						// value[i][j] = currentValue;
						// } else {
						// JOptionPane.showMessageDialog(this, "You win!");
						// newGame();
						// }
						//
						// }
					}
					//
				}
			}
		}

	}

}
