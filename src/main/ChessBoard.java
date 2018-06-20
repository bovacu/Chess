package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import pieces.Piece;
import pieces.PieceType;

/**
 * This is the main class that joins the pieces and the players and makes it all work
 * @author bovacu
 *
 */

public class ChessBoard extends JPanel{
	
	/**
	 * This is a reference to the location of the image
	 */
	private final String GIVE_UP_DIR = "/graphics/giveup.png";
	
	/**
	 * This is a reference to the location of the image
	 */
	private final String CHECK_DIR = "/graphics/jaque.png";
	
	/**
	 * This is a reference to the location of the image
	 */
	private final String CHECKMATE_DIR = "/graphics/jaquemate.png";
	
	/**
	 * This is a reference to the location of the image
	 */
	private final String REMATCH_DIR = "/graphics/rematch.png";
	
	/**
	 * This is a reference to the location of the image
	 */
	private final String PRINT_LOG_DIR = "/graphics/printlog.png";
	
	private final int GIVE_UP_X = 128;
	private final int GIVE_UP_WIDTH = 64;
	private final int GIVE_UP_HEIGHT = 32;
	private final int GIVE_UP_Y = (64 - GIVE_UP_HEIGHT) / 2;
	
	private final int CHECK_X = GIVE_UP_X + GIVE_UP_WIDTH + GIVE_UP_HEIGHT / 2;
	private final int CHECK_Y = (64 - GIVE_UP_HEIGHT) / 2;
	private final int CHECK_WIDTH = 64;
	private final int CHECK_HEIGHT = 32;
	
	private final int CHECKMATE_X = CHECK_X + CHECK_WIDTH + GIVE_UP_HEIGHT / 2;
	private final int CHECKMATE_Y = (64 - GIVE_UP_HEIGHT) / 2;
	private final int CHECKMATE_WIDTH = 64;
	private final int CHECKMATE_HEIGHT = 32;
	
	private final int REMATCH_X = 128;
	private final int REMATCH_WIDTH = 64;
	private final int REMATCH_HEIGHT = 32;
	private final int REMATCH_Y = ((64 - GIVE_UP_HEIGHT) / 2) + 8 * Piece.HEIGHT + Piece.HEIGHT;
	
	private final int PRINT_LOG_X = REMATCH_X + REMATCH_WIDTH + REMATCH_HEIGHT / 2;
	private final int PRINT_LOG_WIDTH = 64;
	private final int PRINT_LOG_HEIGHT = 32;
	private final int PRINT_LOG_Y = REMATCH_Y;
	
	private Image giveUp;
	private Image check;
	private Image checkMate;
	private Image rematch;
	private Image printLog;

	private final int STRING_SEPARATION = (64 - GIVE_UP_HEIGHT) / 2;
	private final Font font = new Font("Dialog", Font.BOLD, 35);
	
	private static final long serialVersionUID = -4087245011944815890L;
	private JFrame f;
	
	public static int BOARD_WIDTH_AND_HEIGHT = 8;
	public static final int SQUARE_WIDTH_HEIGHT = 64;
	
	private final int XOFFSET = 2 * ChessBoard.SQUARE_WIDTH_HEIGHT;
	private final int YOFFSET = ChessBoard.SQUARE_WIDTH_HEIGHT;
	
	public static PieceType matrix[][];
	private Player players[];
	private List<Integer[]> posibleMovementsList;
	private boolean matchEnded;
	
	private int numberOfTurns;
	private String log;
	
	private Piece selectedPiece;
	
	public ChessBoard(JFrame f, int numberOfHumanPlayers) {
		this.f = f;
		Insets insets = f.getInsets();
		int addedWidth = insets.left + insets.right;
        int addedHeight = insets.top + insets.bottom; 
        
        this.giveUp = getImage(GIVE_UP_DIR);
		this.check = getImage(CHECK_DIR);
		this.checkMate = getImage(CHECKMATE_DIR);
		this.rematch = getImage(REMATCH_DIR);
		this.printLog = getImage(PRINT_LOG_DIR);
        
		this.f.setSize(new Dimension(ChessBoard.BOARD_WIDTH_AND_HEIGHT * ChessBoard.SQUARE_WIDTH_HEIGHT + 2 * ChessBoard.SQUARE_WIDTH_HEIGHT + 
				2 * ChessBoard.SQUARE_WIDTH_HEIGHT + addedWidth, 
				ChessBoard.BOARD_WIDTH_AND_HEIGHT * ChessBoard.SQUARE_WIDTH_HEIGHT + 2 * ChessBoard.SQUARE_WIDTH_HEIGHT + addedHeight));
		
		ChessBoard.matrix = new PieceType[BOARD_WIDTH_AND_HEIGHT][BOARD_WIDTH_AND_HEIGHT];
		this.generateMatrix();
		this.matchEnded = false;
		this.log = "";
		
		this.players = new Player[numberOfHumanPlayers];
		for(int i = 0; i < numberOfHumanPlayers; i++)
			this.players[i] = new Player();

		super.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				checkMouseDragged(e);
			}
		});
		
		super.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				checkMouseReleased(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				checkMouseClicked(e, numberOfHumanPlayers);
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.drawBoard(g);
		this.drawPositions(g);
		this.drawButtons(g);
		for(Player p : this.players) {
			p.drawPlayerPieces(g);
			p.drawEatenPieces(g);
		}
		this.drawPlayersTurn(g);
		this.drawWinner(g);
	}
	
	/**
	 * This method checks if there is selectedPiece or not, it there's not, then it is assigned to the dragged piece and
	 * the possible movements are calculated.
	 * <p>
	 * Once the selectedPiece isn't null, the previous process isn't done any more until the piece is released and another
	 * one is selected.
	 * <p>
	 * While the piece is being dragged, it's position it's the mouse position
	 * @param e MouseEvent to check mouse position
	 */
	private void checkMouseDragged(MouseEvent e) {
		if(!matchEnded) {
			if(selectedPiece == null)
				for(Player p : players) {
					if(p.isTurnActive()) {
						for(Piece piece : p.getPieces()) {
							if(piece.mouseOver(e.getX(), e.getY())) {
								selectedPiece = p.getPiece(e.getX(), e.getY());
								posibleMovementsList = selectedPiece.getValidPositionsToMove();
							}
						}
					}
				}
			else
				selectedPiece.setPosition(e.getX() - Piece.WIDTH / 2, e.getY() - Piece.HEIGHT / 2);
			
			repaint();
			e.consume();
		}
	}

	/**
	 * If the selectedPiece is not null, dx and dy (position in matrix coordinates of the mouse) are calculated, then it is checked 
	 * if the position calculated is legal (if is in the possible movements list), it it is, the matrix is updated setting the previous
	 * position to NONE, and the movedPosition to the moved piece type.
	 * <p>
	 * The piece is centered on the right square of the board and it's position is updated. Finally the turn is passed.
	 * <p>
	 * If the position is not legal, then the piece is set back to it's initial position in this turn.
	 * @param e MouseEvent to check mouse position
	 */
	private void checkMouseReleased(MouseEvent e) {
		if(selectedPiece != null && !matchEnded) {
			int dx = (int)Math.floor((e.getX() - XOFFSET) / ChessBoard.SQUARE_WIDTH_HEIGHT);
			int dy = (int)Math.floor((e.getY() - YOFFSET) / ChessBoard.SQUARE_WIDTH_HEIGHT);
			
			if(isMovementLegal(dx, dy)) {
				int initX = (int)Math.floor((selectedPiece.getInitPosition()[0] - XOFFSET) / ChessBoard.SQUARE_WIDTH_HEIGHT);
				int initY = (int)Math.floor((selectedPiece.getInitPosition()[1] - YOFFSET) / ChessBoard.SQUARE_WIDTH_HEIGHT);
				updateLog(initX, initY, dx, dy, selectedPiece.getPieceType(), getActivePlayer().getPlayerId(), eatPiece(e.getX(), e.getY(), dx, dy));
				updateMatrix(dx, dy, initX, initY, selectedPiece.getPieceType());
				selectedPiece.centerPiece(e.getX(), e.getY(), 2 * SQUARE_WIDTH_HEIGHT, SQUARE_WIDTH_HEIGHT, BOARD_WIDTH_AND_HEIGHT);
				selectedPiece.updatePosition();
				passTurn();
			}else {
				selectedPiece.goToLastPosition();
				selectedPiece.setFirstMove(true);
			}

			reset();
			repaint();
			e.consume();
		}
	}
	
	/**
	 * This method is called only when the game is finished and it checks whether the re match button or the print log button are pressed.
	 * <p>
	 * If re match is selected, the idPlayer (which is assigned by static variable) is reset.
	 * <p>
	 * If print log is selected, then it creates a window with the log of the game. 
	 * @param e MouseEvent to check mouse position
	 * @param numberOfHumanPlayers 
	 */
	private void checkMouseClicked(MouseEvent e, int numberOfHumanPlayers) {
		if(matchEnded) {
			if(mouseOverRegion(e.getX(), e.getY(), REMATCH_X, REMATCH_Y, REMATCH_WIDTH + REMATCH_X, REMATCH_Y + REMATCH_HEIGHT)) {
				getActivePlayer().resetId();
				f.setContentPane(new ChessBoard(f, numberOfHumanPlayers));
				f.invalidate();
				f.validate();
			}else if(mouseOverRegion(e.getX(), e.getY(), PRINT_LOG_X, PRINT_LOG_Y, PRINT_LOG_WIDTH + PRINT_LOG_X, PRINT_LOG_Y + PRINT_LOG_HEIGHT)) {
				printLog();
			}
		}
	}
	
	/**
	 * Sets to default values variables: selectedPiece, posibleMovementsList
	 */
	private void reset() {
		this.selectedPiece.resetMovementsList();
		this.posibleMovementsList = null;
		this.selectedPiece = null;
	}
	
	/**
	 * This method creates the window where the log is displayed when pressing the print log button.
	 */
	private void printLog() {
		JPanel middlePanel = new JPanel();
	    middlePanel.setBorder(new TitledBorder(new EtchedBorder (), "Game Log"));
	    
	    JTextArea display = new JTextArea(16, 58);
	    display.setText(this.log);
	    display.setEditable(false);
	    JScrollPane scroll = new JScrollPane(display);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	    middlePanel.add(scroll);
	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.add(middlePanel);
	    frame.pack();
	    frame.setLocationRelativeTo (null);
	    frame.setResizable(false);
	    frame.setVisible(true);

	}
	
	/**
	 * This method draws the white and grey squares of the board.
	 * @param g Graphics object used to paint on screen.
	 */
	private void drawBoard(Graphics g) {
		for(int i = 0; i < BOARD_WIDTH_AND_HEIGHT; i++)
			for(int j = 0; j < BOARD_WIDTH_AND_HEIGHT; j++) {
				if((i + j) % 2 == 0)
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.GRAY);
				
				g.fillRect(i * ChessBoard.SQUARE_WIDTH_HEIGHT + 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, j * ChessBoard.SQUARE_WIDTH_HEIGHT
						+ ChessBoard.SQUARE_WIDTH_HEIGHT, 
						ChessBoard.SQUARE_WIDTH_HEIGHT, ChessBoard.SQUARE_WIDTH_HEIGHT); 
			}
	}
	
	/**
	 * This method draws all the buttons of the game.
	 * @param g Graphics object used to paint on screen.
	 */
	private void drawButtons(Graphics g) {
		g.drawImage(this.giveUp, this.GIVE_UP_X, this.GIVE_UP_Y, this.GIVE_UP_WIDTH, this.GIVE_UP_HEIGHT, null);
		g.drawImage(this.check, this.CHECK_X, this.CHECK_Y, this.CHECK_WIDTH, this.CHECK_HEIGHT, null);
		g.drawImage(this.checkMate, this.CHECKMATE_X, this.CHECKMATE_Y, this.CHECKMATE_WIDTH, this.CHECKMATE_HEIGHT, null);
		if(this.matchEnded) {
			g.drawImage(this.rematch, this.REMATCH_X, this.REMATCH_Y, this.REMATCH_WIDTH, this.REMATCH_HEIGHT, null);
			g.drawImage(this.printLog, this.PRINT_LOG_X, this.PRINT_LOG_Y, this.PRINT_LOG_WIDTH, this.PRINT_LOG_HEIGHT, null);
		}
	}
	
	/**
	 * This method generates the initial matrix full of NONE values.
	 */
	private void generateMatrix() {
		for(int i = 0; i < BOARD_WIDTH_AND_HEIGHT; i++)
			for(int j = 0; j < BOARD_WIDTH_AND_HEIGHT; j++)
				ChessBoard.matrix[i][j] = PieceType.NONE;
	}
	
	/**
	 * This method draws a String with the winner when the match is over.
	 * @param g Graphics object used to paint on screen.
	 */
	private void drawWinner(Graphics g) {
		if(this.matchEnded) {
			g.setFont(this.font);
			g.setColor(Color.RED);
			g.drawString("Player " + getOtherPlayer().getPlayerId() + " is the winner!", 
					f.getWidth() / 4, f.getHeight() / 2);
		}
	}
	
	/**
	 * This method paints the possible positions where a piece can move. If a position is empty, it's drawn in green, and it
	 * the position is taken by an enemy piece, it's drawn in magenta.
	 * @param g Graphics object used to paint on screen.
	 */
	private void drawPositions(Graphics g) {
		if(this.posibleMovementsList != null)
			for(Integer i [] : this.posibleMovementsList) {
				int x = i[0];
				int y = i[1];
				if(ChessBoard.matrix[x][y] != PieceType.NONE) {
					Color c = new Color(Color.MAGENTA.getRed() / 255, Color.MAGENTA.getGreen() / 255, Color.MAGENTA.getBlue() / 255, 0.5f);
					g.setColor(c);
					g.fillRect(x * ChessBoard.SQUARE_WIDTH_HEIGHT + XOFFSET, y * ChessBoard.SQUARE_WIDTH_HEIGHT + YOFFSET, Piece.WIDTH, Piece.HEIGHT);
				}else {
					Color c = new Color(Color.GREEN.getRed() / 255, Color.GREEN.getGreen() / 255, Color.GREEN.getBlue() / 255, 0.5f);
					g.setColor(c);
					g.fillRect(x * ChessBoard.SQUARE_WIDTH_HEIGHT + XOFFSET, y * ChessBoard.SQUARE_WIDTH_HEIGHT + YOFFSET, Piece.WIDTH, Piece.HEIGHT);
				}
			}
	}
	
	/**
	  * This method draws a String telling which player's turn is.
	  * @param g Graphics object used to paint on screen.
	  */
	private void drawPlayersTurn(Graphics g) {
		if(!this.matchEnded) {
			g.setFont(this.font);
			g.setColor(Color.BLACK);
			g.drawString("Is player " + getActivePlayer().getPlayerId() + " turn", 
					this.STRING_SEPARATION * 2 + this.CHECKMATE_WIDTH + this.CHECKMATE_X, this.GIVE_UP_HEIGHT + this.GIVE_UP_Y / 2);
		}
	}
	
	/**
	 * This method checks if the passed coordinates are in the possible movements list.
	 * @param x position in X axis of the mouse (dragging a piece) in screen coordinates.
	 * @param y position in Y axis of the mouse (dragging a piece) in screen coordinates.
	 * @return true if is in the list, false otherwise.
	 */
	private boolean isMovementLegal(int x, int y) {
		for(Integer i[] : this.posibleMovementsList)
			if(i[0] == x && i[1] == y)
				return true;
		return false;
	}
	
	/**
	 * This method changes the turn from one player to the other.
	 */
	private void passTurn() {
		for(Player p : players) {
			if(p.isTurnActive()) p.setTurnActive(false);
			else p.setTurnActive(true);
		}
		this.numberOfTurns++;
	}
	
	/**
	 * This method updates the matrix each turn a legal move is made.
	 * @param dx position in X axis of the moved piece.
	 * @param dy position in Y axis of the moved piece.
	 * @param x initial position in X axis of the moved piece.
	 * @param y initial position in Y axis of the moved piece.
	 * @param to the PieceType that is going to substitute the value saved in matrix in position x, y 
	 */
	private void updateMatrix(int dx, int dy, int x, int y, PieceType to) {
		ChessBoard.matrix[x][y] = PieceType.NONE;
		ChessBoard.matrix[dx][dy] = to;
	}
	
	private Image getImage(String imageUrl) {
		try {
		    return ImageIO.read(getClass().getResource(imageUrl)); 
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * This method checks if during a legal movement a piece is eaten or not.
	 * @param mouseX position of the mouse and dragged piece on X axis.
	 * @param mouseY position of the mouse and dragged piece on Y axis.
	 * @param matrixX position of mouse and dragged piece in matrix coordinates in X axis.
	 * @param matrixY position of mouse and dragged piece in matrix coordinates in Y axis.
	 * @return the PieceType that has been eaten, if any piece has been eaten, NONE otherwise.
	 */
	private PieceType eatPiece(int mouseX, int mouseY, int matrixX, int matrixY) {
		Player otherPlayer = getOtherPlayer();
		if(otherPlayer.isPieceOfTheSamePropietary(matrixX, matrixY)) {
			getActivePlayer().getEatenPieces().add(otherPlayer.getPiece(mouseX, mouseY));
			if(otherPlayer.getPiece(mouseX, mouseY).getPieceType() == PieceType.KING) {
				this.matchEnded = true;
				this.log += "Number of turns: " + this.numberOfTurns + "\n";
			}
			PieceType eatenPiece = otherPlayer.getPiece(mouseX, mouseY).getPieceType();
			otherPlayer.getPieces().remove(otherPlayer.getPiece(mouseX, mouseY));
			ChessBoard.matrix[matrixX][matrixY] = selectedPiece.getPieceType();
			return eatenPiece;
		}
		return PieceType.NONE;
	}
	
	/**
	 * This method gets the active player
	 * @return the active player.
	 */
	private Player getActivePlayer() {
		for(Player p : this.players)
			if(p.isTurnActive())
				return p;
		return null;
	}
	
	/**
	 * This method gets the player that is not active.
	 * @return the non active player.
	 */
	private Player getOtherPlayer() {
		for(Player p : this.players)
			if(!p.isTurnActive())
				return p;
		return null;
	}
	
	/**
	 * 
	 * @param initX initial position in X axis of the moved piece.
	 * @param initY initial position in Y axis of the moved piece.
	 * @param movedPieceX final position of the mouse and dragged image in X axis and screen coordinates.
	 * @param movedPieceY final position of the mouse and dragged image in Y axis and screen coordinates.
	 * @param piece the PieceType of the moved piece.
	 * @param player the player who is performing the movement.
	 * @param eatenPiece PieceType of the eaten piece if eaten, NONE otherwise.
	 */
	private void updateLog(int initX, int initY, int movedPieceX, int movedPieceY, PieceType piece, int player, PieceType eatenPiece) {
		this.log += "Player " + player + " moved " + piece + " from (" + columnToLetter(initX) + ", " + initY + ") to (" 
				+ columnToLetter(movedPieceX) + ", " + movedPieceY + ")." + ((eatenPiece == PieceType.NONE) ? "\n" : " and ate enemy " 
		+ eatenPiece + "\n");
	}
	
	/**
	 * This method transforms the column number to a letter.
	 * @param x the column to be transformed to letter
	 * @return the letter associated with the column.
	 */
	private String columnToLetter(int x) {
		switch(x) {
		case 0 : return "a";
		case 1 : return "b";
		case 2 : return "c";
		case 3 : return "d";
		case 4 : return "e";
		case 5 : return "f";
		case 6 : return "g";
		case 7 : return "h";
		default : return null;
		}
	}

	/**
	 * This method checks if the mouse is between a region.
	 * @param mouseX current position of mouse in X axis.
	 * @param mouseY current position of mouse in Y axis.
	 * @param initX lowest position of the region in X axis.
	 * @param initY lowest position of the region in Y axis.
	 * @param finalX highest position of the region in X axis.
	 * @param finalY highest position of the region in Y axis.
	 * @return true if mouse is in region, false otherwise.
	 */
	private boolean mouseOverRegion(int mouseX, int mouseY, int initX, int initY, int finalX, int finalY) {
		return (mouseX >= initX && mouseX <= finalX && mouseY >= initY && mouseY <= finalY); 
	}
	
}
