package pieces;

import java.util.List;

import main.ChessBoard;
import main.Player;

/**
 * This class represents the King piece.
 * @author bovacu
 *
 */

public class King extends Piece{

	public King(int x, int y, String imageUrl, Player player) {
		super(x, y, PieceType.KING, imageUrl, player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Checks the possible positions of all 8 neighbors.
	 */
	@Override
	public List<Integer[]> getValidPositionsToMove() {
		int x = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
				ChessBoard.SQUARE_WIDTH_HEIGHT)[0];
		int y = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
				ChessBoard.SQUARE_WIDTH_HEIGHT)[1];
		
		final int MAX_NEIGHBOURS = 3;
		for(int i = -1; i < MAX_NEIGHBOURS - 1; i++) { 
			for (int j = -1; j < MAX_NEIGHBOURS - 1; j++) { 
				if(super.inMatrixRange(x + i, y + j) && ChessBoard.matrix[x + i][y + j] == PieceType.NONE)
					super.posibleMovementsList.add(new Integer[] {x + i, y + j});
				else if(super.inMatrixRange(x + i, y + j) && ChessBoard.matrix[x + i][y + j] != PieceType.NONE)
					if(!this.getPlayer().isPieceOfTheSamePropietary(x + i, y + j))
						super.posibleMovementsList.add(new Integer[] {x + i, y + j});
			}
		}
		
		return super.posibleMovementsList; 
	}

}
