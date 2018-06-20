package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceType;
import pieces.Queen;
import pieces.Rook;

/**
 * This class contains all information about the player
 * @author bovacu
 *
 */

public class Player {

	/**
	 * This static variable assigns automatically id to players.
	 */
	private static int numberOfPlayers;
	private List<Piece> pieces;
	private boolean turnActive;
	private int playerId;
	private List<Piece> eatenPieces;
	
	public Player() {
		Player.numberOfPlayers++;
		this.playerId = Player.numberOfPlayers;
		this.turnActive = (this.playerId == 1);
		this.pieces = generatePieces();
		this.eatenPieces = new ArrayList<>();
	}
	
	/**
	 * This method creates a list of Piece set to a default position.
	 * @return the list of pieced
	 */
	private List<Piece> generatePieces(){
		List<Piece> pieces = new ArrayList<>();
		int row = (this.playerId == 1) ? 1 : 6;
		String suffix = (this.playerId == 1) ? "white.png" : "black.png"; 
		for(int i = 0; i < ChessBoard.matrix.length; i++) {
			ChessBoard.matrix[i][row] = PieceType.PAWN;
			pieces.add(new Pawn(i * Piece.WIDTH + 2 * Piece.WIDTH, row * Piece.HEIGHT + Piece.HEIGHT, Piece.PAWN_DIR + suffix, this));
		}
		row = (this.playerId == 1) ? 0 : 7;
		ChessBoard.matrix[0][row] = PieceType.ROOK;
		pieces.add(new Rook(0 * Piece.WIDTH + 2 * Piece.WIDTH, row * Piece.HEIGHT + Piece.HEIGHT, Piece.ROOK_DIR + suffix,  this));
		ChessBoard.matrix[7][row] = PieceType.ROOK;
		pieces.add(new Rook(7 * Piece.WIDTH + 2 * Piece.WIDTH, row * Piece.HEIGHT+ Piece.HEIGHT, Piece.ROOK_DIR + suffix,  this));
		
		ChessBoard.matrix[1][row] = PieceType.KNIGHT;
		pieces.add(new Knight(1 * Piece.WIDTH + 2 * Piece.WIDTH, row * Piece.HEIGHT+ Piece.HEIGHT, Piece.KNIGHT_DIR + suffix, this));
		ChessBoard.matrix[6][row] = PieceType.KNIGHT;
		pieces.add(new Knight(6 * Piece.WIDTH + 2 * Piece.WIDTH, row * Piece.HEIGHT+ Piece.HEIGHT, Piece.KNIGHT_DIR + suffix, this));
		
		ChessBoard.matrix[2][row] = PieceType.BISHOP;
		pieces.add(new Bishop(2 * Piece.WIDTH + 2 * Piece.WIDTH, row * Piece.HEIGHT+ Piece.HEIGHT, Piece.BISHOP_DIR + suffix, this));
		ChessBoard.matrix[5][row] = PieceType.BISHOP;
		pieces.add(new Bishop(5 * Piece.WIDTH + 2 * Piece.WIDTH, row * Piece.HEIGHT+ Piece.HEIGHT, Piece.BISHOP_DIR + suffix, this));
		
		ChessBoard.matrix[4][row] = PieceType.KING;
		pieces.add(new King(4 * Piece.WIDTH + 2 * Piece.WIDTH, row * Piece.HEIGHT+ Piece.HEIGHT, Piece.KING_DIR + suffix, this));
		
		ChessBoard.matrix[3][row] = PieceType.QUEEN;
		pieces.add(new Queen(3 * Piece.WIDTH + 2 * Piece.WIDTH, row * Piece.HEIGHT+ Piece.HEIGHT, Piece.QUEEN_DIR + suffix, this)); 
		
		return pieces; 
	}
	
	/**
	 * This method draws all player pieces that are alive at it's current position.
	 * @param g Graphics object used to paint on screen.
	 */
	public void drawPlayerPieces(Graphics g) {
		for(Piece p : this.pieces)
			p.drawPiece(g);
	}
	
	/**
	 * Puts the static numberOfPlayers to 0.
	 */
	public void resetId() {
		Player.numberOfPlayers = 0;
	}
	
	/**
	 * This method draws the pieces that this player has eaten to the other player.
	 * @param g Graphics object used to paint on screen.
	 */
	public void drawEatenPieces(Graphics g) {
		int yPos = Piece.HEIGHT;
		int xPos = (playerId == 1) ? Piece.WIDTH : Piece.WIDTH * 8 + 2 * Piece.WIDTH;
		int counter = 0;
		for(Piece p : this.eatenPieces) {
			p.drawPieceAt(g, xPos, yPos);
			yPos += 64;
			if(counter == 8 && (xPos == 64 || xPos == 640)) {
				xPos -= (playerId == 1) ? Piece.WIDTH : -Piece.WIDTH;
				yPos = Piece.HEIGHT;
			}
			counter++;
		}
	}
	
	public boolean isTurnActive() {
		return this.turnActive;
	}
	
	public void setTurnActive(boolean turnActive) {
		this.turnActive = turnActive;
	}
	
	/**
	 * This method gets a piece of the player given its coordineates.
	 * @param x position in screen coordinates of the piece in X axis.
	 * @param y position in screen coordinates of the piece in Y axis.
	 * @return the piece at position if it exists.
	 */
	public Piece getPiece(int x, int y) {
		for(Piece p : this.pieces)
			if(p.mouseOver(x, y))
				return p;
		return null;
	}
	
	/**
	 * This method checks if a piece is of the same team or not.
	 * @param x position in matrix coordinates of the piece in X axis.
	 * @param y position in matrix coordinates of the piece in Y axis.
	 * @return true if is of the same player, false otherwise.
	 */
	public boolean isPieceOfTheSamePropietary(int x, int y) {
		for(Piece p : this.pieces) {
			int dx = p.screenCoordToMatrixCoord(p.getPosition()[0], p.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
					ChessBoard.SQUARE_WIDTH_HEIGHT)[0];
			int dy = p.screenCoordToMatrixCoord(p.getPosition()[0], p.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
					ChessBoard.SQUARE_WIDTH_HEIGHT)[1];
			if(dx == x && dy == y)
				return true;
		}
		return false; 
	}
	
	public List<Piece> getEatenPieces(){
		return this.eatenPieces;
	}
	
	public List<Piece> getPieces(){
		return this.pieces;
	}
	
	public int getPlayerId() {
		return this.playerId;
	}
}
