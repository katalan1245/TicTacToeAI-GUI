package ttt;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Game {
	
	Position position = new Position();
	boolean hasWinner = false;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Tic Tac Toe");
				final Game game = new Game();
				final JButton[] buttons = new JButton[9];
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new GridLayout(3,3));
				JMenu menu = new JMenu("File");
				JMenuBar menuBar = new JMenuBar();
				
				JMenuItem newGame = new JMenuItem("New Game");
				newGame.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						for(int i=0;i<9;i++) {
							buttons[i].setText("");
						}
						game.position = new Position();
						game.hasWinner = false;
					}
				});
				JMenuItem quit = new JMenuItem("Quit");
				quit.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				menu.add(newGame);
				menu.add(quit);
				menuBar.add(menu);
				frame.setJMenuBar(menuBar);
				
				for(int i=0; i<9; i++) {
					final int idx = i;
					final JButton button = new JButton();
					buttons[i] = button;
					button.setPreferredSize(new Dimension(150, 150));
					button.setOpaque(true);
					button.setFont(new Font(null, Font.BOLD, 100));
					button.addMouseListener(new MouseListener() {
						public void mouseReleased(MouseEvent e) {}
						public void mousePressed(MouseEvent e) {}
						public void mouseExited(MouseEvent e) {}
						public void mouseEntered(MouseEvent e) {}
						
						@Override
						public void mouseClicked(MouseEvent e) {
							if(game.hasWinner || button.getText() != "")
								return;
							button.setText(game.position.currentPlayer + "");
							game.move(idx);
							if(!game.position.gameEnd()) {
								int best = game.position.bestMove();
								buttons[best].setText(game.position.currentPlayer + "");
								game.move(best);
							}
							if(game.position.gameEnd()) {
								String message = "";
								if(game.position.win('x'))
									message = "You Won!";
								else if(game.position.win('o'))
									message = "Computer Won!";
								else
									message = "Draw!";
								JOptionPane.showMessageDialog(null, message);
								game.hasWinner = true;
							}
						}
					});
					frame.add(button);
				}
				frame.pack();
				frame.setVisible(true);
			}
			
		});
	}

	protected void move(int idx) {
		position = position.move(idx);
	}
}
