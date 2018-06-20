package pieces;

import java.util.List;

import main.ChessBoard;
import main.Player;

/**
 * This class represents the Knight piece
 * @author vazqu
 *
 */

public class Knight extends Piece{

	public Knight(int x, int y, String imageUrl, Player player) {
		super(x, y, PieceType.KNIGHT, imageUrl, player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Cheks the possible positions in L forms
	 */
	@Override
	public List<Integer[]> getValidPositionsToMove() {
		int x = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
				ChessBoard.SQUARE_WIDTH_HEIGHT)[0];
		int y = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
				ChessBoard.SQUARE_WIDTH_HEIGHT)[1];
		
		int direction = 1;
		for(int i = 0; i < 2; i++) {
			if(super.inMatrixRange(x - 1 * direction, y - 2 * direction) && 
					ChessBoard.matrix[x - 1 * direction][y - 2 * direction] == PieceType.NONE) 
				super.posibleMovementsList.add(new Integer[] {x - 1 * direction, y - 2 * direction});
			else if(super.inMatrixRange(x - 1 * direction, y - 2 * direction) && ChessBoard.matrix[x - 1 * direction][y - 2 * direction] != PieceType.NONE) {
				if(!this.getPlayer().isPieceOfTheSamePropietary(x - 1 * direction, y - 2 * direction))
					super.posibleMovementsList.add(new Integer[] {x - 1 * direction, y - 2 * direction}); 
			}
			direction = -1; 
		}
		
		direction = 1;
		for(int i = 0; i < 2; i++) {
			if(super.inMatrixRange(x - 2 * direction, y - 1 * direction) && 
					ChessBoard.matrix[x - 2 * direction][y - 1 * direction] == PieceType.NONE) 
				super.posibleMovementsList.add(new Integer[] {x - 2 * direction, y - 1 * direction});
			else if(super.inMatrixRange(x - 2 * direction, y - 1 * direction) && ChessBoard.matrix[x - 2 * direction][y - 1 * direction] != PieceType.NONE) {
				if(!this.getPlayer().isPieceOfTheSamePropietary(x - 2 * direction, y - 1 * direction))
					super.posibleMovementsList.add(new Integer[] {x - 2 * direction, y - 1 * direction}); 
			}
			direction = -1;
		}
		
		direction = 1;
		for(int i = 0; i < 2; i++) {
			if(super.inMatrixRange(x - 2 * direction, y + 1 * direction) &&
					ChessBoard.matrix[x - 2 * direction][y + 1 * direction] == PieceType.NONE) 
				super.posibleMovementsList.add(new Integer[] {x - 2 * direction, y + 1 * direction});
			else if(super.inMatrixRange(x - 2 * direction, y + 1 * direction) && ChessBoard.matrix[x - 2 * direction][y + 1 * direction] != PieceType.NONE) {
				if(!this.getPlayer().isPieceOfTheSamePropietary(x - 2 * direction, y + 1 * direction))
					super.posibleMovementsList.add(new Integer[] {x - 2 * direction, y + 1 * direction}); 
			}
			direction = -1;
		}
		
		direction = 1;
		for(int i = 0; i < 2; i++) {
			if(super.inMatrixRange(x + 1 * direction, y - 2 * direction) && 
					ChessBoard.matrix[x + 1 * direction][y - 2 * direction] == PieceType.NONE) 
				super.posibleMovementsList.add(new Integer[] {x + 1 * direction, y - 2 * direction});
			else if(super.inMatrixRange(x + 1 * direction, y - 2 * direction) && ChessBoard.matrix[x + 1 * direction][y - 2 * direction] != PieceType.NONE) {
				if(!this.getPlayer().isPieceOfTheSamePropietary(x + 1 * direction, y - 2 * direction))
					super.posibleMovementsList.add(new Integer[] {x + 1 * direction, y - 2 * direction}); 
			}
			direction = -1; 
		}
		
		return super.posibleMovementsList; 
	}

}
