package pieces;

import java.util.List;

import main.ChessBoard;
import main.Player;

/**
 * This class represents the Pawn piece.
 * @author bovacu
 *
 */

public class Pawn extends Piece{

	public Pawn(int x, int y, String imageUrl, Player player) {
		super(x, y, PieceType.PAWN, imageUrl, player);
	}

	/**
	 * Checks the possible positions in straight line, 4 positions if is first move (2 straight and 2 diagonals if there are enemies)
	 * or 3 positions (1 straight and 2 diagonals if there are enemies).
	 */
	@Override
	public List<Integer[]> getValidPositionsToMove() {
		int x = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
			ChessBoard.SQUARE_WIDTH_HEIGHT)[0];
		int y = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
			ChessBoard.SQUARE_WIDTH_HEIGHT)[1];
		int direction = (super.getPlayer().getPlayerId() == 1) ? 1 : -1; 
		
		if(super.inMatrixRange(x, y + 1 * direction) && ChessBoard.matrix[x][y + 1 * direction] == PieceType.NONE) 
			super.posibleMovementsList.add(new Integer[] {x, y + 1 * direction});
		
		if(super.isFirstMOve()) {
			super.setFirstMove(false);
			if(super.inMatrixRange(x, y + 2 * direction) && ChessBoard.matrix[x][y + 2 * direction] == PieceType.NONE)
				super.posibleMovementsList.add(new Integer[] {x, y + 2 * direction});
		}
		
		if(super.inMatrixRange(x - 1, y + 1 * direction) && ChessBoard.matrix[x - 1][y + 1 * direction] != PieceType.NONE) {
			if(!this.getPlayer().isPieceOfTheSamePropietary(x - 1, y + 1 * direction))
				super.posibleMovementsList.add(new Integer[] {x - 1, y + 1 * direction});
		}
		if(super.inMatrixRange(x + 1, y + 1 * direction) && ChessBoard.matrix[x + 1][y + 1 * direction] != PieceType.NONE) {
			if(!this.getPlayer().isPieceOfTheSamePropietary(x + 1, y + 1 * direction))
				super.posibleMovementsList.add(new Integer[] {x + 1, y + 1 * direction});
		}
		return super.posibleMovementsList;
	}

}
