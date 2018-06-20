package pieces;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.ChessBoard;
import main.Player;

/**
 * This is the abstract class that contains all the methods and info that all the specific pieces of chess will need.
 * <p>
 * This class can be reused in other board games, as it contains many methods that are shared in other games, such as
 * the posible movements that a piece can make, the player it belongs to, methods to be drawn, center the piece in a square
 * once it's moved, get the current and last position...
 * @author bovacu
 *
 */

public abstract class Piece {
	public static String PAWN_DIR =   "/graphics/pawn_";
	public static String ROOK_DIR =   "/graphics/rook_";
	public static String KING_DIR =   "/graphics/king_";
	public static String QUEEN_DIR =  "/graphics/queen_";
	public static String BISHOP_DIR = "/graphics/bishop_";
	public static String KNIGHT_DIR = "/graphics/knight_";
	
	public static final int WIDTH = 64;
	public static final int HEIGHT = 64;
	
	private int x, y, previousX, previousY;
	private boolean firstMove;
	private PieceType type;
	private Image sprite;
	
	protected List<Integer[]> posibleMovementsList;
	
	private Player player;
	
	protected Piece(int x, int y, PieceType type, String imageUrl, Player player) {
		this.x = x;
		this.y = y;
		this.previousX = x;
		this.previousY = y;
		this.type = type;
		this.firstMove = true;
		this.sprite = getImage(imageUrl);
		this.posibleMovementsList = new ArrayList<>();
		this.player = player;
	}
	
	/**
	 * This method will be implemented in every child of this class, and gives the list of possible movements of a piece.
	 * @return the list of possible movements.
	 */
	public abstract List<Integer[]> getValidPositionsToMove();
	
	public int[] getPosition() {
		return new int[] {this.x, this.y};
	}
	
	public int[] getInitPosition() {
		return new int[] {this.previousX, this.previousY};
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * This method draws the piece at its current position.
	 * @param g Graphics object used to paint on screen.
	 */
	public void drawPiece(Graphics g) {
		g.drawImage(this.sprite, this.x, this.y, Piece.WIDTH, Piece.HEIGHT, null);
	}
	
	/**
	 * This method draws the piece at a certain (x, y) position.
	 * @param g Graphics object used to paint on screen.
	 * @param x position in X axis to be drawn in screen coordinates.
	 * @param y position in Y axis to be drawn in screen coordinates.
	 */
	public void drawPieceAt(Graphics g, int x, int y) {
		g.drawImage(this.sprite, x, y, Piece.WIDTH, Piece.HEIGHT, null);
	}
	
	public PieceType getPieceType() {
		return this.type;
	}
	
	public boolean isFirstMOve() {
		return this.firstMove;
	}
	
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Checks if mouse is over this piece.
	 * @param x mouse position in X axis in screen coordinates.
	 * @param y mouse position in Y axis in screen coordinates.
	 * @return true if is over, false otherwise.
	 */
	public boolean mouseOver(int x, int y) {
		return (x >= this.x && x <= this.x + Piece.WIDTH && y >= this.y && y <= this.y + Piece.HEIGHT); 
	}
	
	/**
	 * This method is called when the mouse is released and centers the piece where it is left.
	 * @param x position in X axis of the dragged piece in screen coordinates.
	 * @param y position in Y axis of the dragged piece in screen coordinates.
	 * @param xOffset how much the grid is away from the 0 in X axis.
	 * @param yOffset how much the grid is away from the 0 in Y axis.
	 * @param boardSize the number of squares in X or Y axis of the board.
	 */
	public void centerPiece(int x, int y, int xOffset, int yOffset, int boardSize) {
		double dx = Math.floor((x - xOffset) / boardSize);
		double dy = Math.floor((y - yOffset) / boardSize);
		
		int xRest = (int)dx % ChessBoard.BOARD_WIDTH_AND_HEIGHT;
		int yRest = (int)dy % ChessBoard.BOARD_WIDTH_AND_HEIGHT;
		
		this.x = ((int)dx - xRest) * ChessBoard.BOARD_WIDTH_AND_HEIGHT + xOffset;
		this.y = ((int)dy - yRest) * ChessBoard.BOARD_WIDTH_AND_HEIGHT + yOffset; 
	}
	
	/**
	 * 
	 * @param x mouse or dragged piece position in X axis
	 * @param y mouse or dragged piece position in Y axis.
	 * @param xOffset how much the grid is away from the 0 in X axis.
	 * @param yOffset how much the grid is away from the 0 in Y axis.
	 * @return [dx, dy] in matrix coordinates
	 */
	public int[] screenCoordToMatrixCoord(int x, int y, int xOffset, int yOffset) {
		int dx = (int)Math.floor((x - xOffset) / ChessBoard.SQUARE_WIDTH_HEIGHT);
		int dy = (int)Math.floor((y - yOffset) / ChessBoard.SQUARE_WIDTH_HEIGHT);
		return new int[] {dx, dy};
	}
	
	/**
	 * Checks if mouse is inside matrix or not.
	 * @param x mouse position in X axis.
	 * @param y mouse position in Y axis.
	 * @return true if is in, false otherwise.
	 */
	protected boolean inMatrixRange(int x, int y) {
		return (x >= 0 && x < ChessBoard.BOARD_WIDTH_AND_HEIGHT && y >= 0 && y < ChessBoard.BOARD_WIDTH_AND_HEIGHT); 
	}
	
	public void resetMovementsList() {
		this.posibleMovementsList = new ArrayList<>();
	}
	
	public void goToLastPosition() {
		this.x = this.previousX;
		this.y = this.previousY;
	}
	 /**
	  * Sets the previousX and previousY to the new position.
	  */
	public void updatePosition() {
		this.previousX = this.x;
		this.previousY = this.y;
	}
	
	private Image getImage(String imageUrl) {
		try {
		    return ImageIO.read(getClass().getResource(imageUrl)); 
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
