package pieces;

import java.util.List;

import main.ChessBoard;
import main.Player;

/**
 * This class represents the Bishop piece
 * @author bovacu
 *
 */

public class Bishop extends Piece{

	public Bishop(int x, int y, String imageUrl, Player player) {
		super(x, y, PieceType.BISHOP, imageUrl, player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Calculates the possible positions in all 4 diagonals (until a enemy or ally piece is found)
	 */
	@Override
	public List<Integer[]> getValidPositionsToMove() {
		int x = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
				ChessBoard.SQUARE_WIDTH_HEIGHT)[0];
		int y = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
				ChessBoard.SQUARE_WIDTH_HEIGHT)[1];
		
		boolean firstEncounter = true;
		for(int i = 1; i < ChessBoard.BOARD_WIDTH_AND_HEIGHT; i++) {
			if(!firstEncounter)
				break;
			
			if(super.inMatrixRange(x + i, y + i) && ChessBoard.matrix[x + i][y + i] == PieceType.NONE)
				super.posibleMovementsList.add(new Integer[] {x + i, y + i});
			else if(firstEncounter && super.inMatrixRange(x + i, y + i) && ChessBoard.matrix[x + i][y + i] != PieceType.NONE){
				if(!this.getPlayer().isPieceOfTheSamePropietary(x + i, y + i))
					super.posibleMovementsList.add(new Integer[] {x + i, y + i});
				firstEncounter = false;
			}
		}
		
		firstEncounter = true;
		for(int i = 1; i < ChessBoard.BOARD_WIDTH_AND_HEIGHT; i++) {
			if(!firstEncounter)
				break;
			
			if(super.inMatrixRange(x + i, y - i) && ChessBoard.matrix[x + i][y - i] == PieceType.NONE)
				super.posibleMovementsList.add(new Integer[] {x + i, y - i});
			else if(firstEncounter && super.inMatrixRange(x + i, y - i) && ChessBoard.matrix[x + i][y - i] != PieceType.NONE){
				if(!this.getPlayer().isPieceOfTheSamePropietary(x + i, y - i))
					super.posibleMovementsList.add(new Integer[] {x + i, y - i});
				firstEncounter = false;
			}
		}
		
		firstEncounter = true;
		for(int i = 1; i < ChessBoard.BOARD_WIDTH_AND_HEIGHT; i++) {
			if(!firstEncounter)
				break;
			
			if(super.inMatrixRange(x - i, y + i) && ChessBoard.matrix[x - i][y + i] == PieceType.NONE)
				super.posibleMovementsList.add(new Integer[] {x - i, y + i});
			else if(firstEncounter && super.inMatrixRange(x - i, y + i) && ChessBoard.matrix[x - i][y + i] != PieceType.NONE){
				if(!this.getPlayer().isPieceOfTheSamePropietary(x - i, y + i))
					super.posibleMovementsList.add(new Integer[] {x - i, y + i});
				firstEncounter = false;
			}
		}
		
		firstEncounter = true;
		for(int i = 1; i < ChessBoard.BOARD_WIDTH_AND_HEIGHT; i++) {
			if(!firstEncounter)
				break;
			
			if(super.inMatrixRange(x - i, y - i) && ChessBoard.matrix[x - i][y - i] == PieceType.NONE)
				super.posibleMovementsList.add(new Integer[] {x - i, y - i});
			else if(firstEncounter && super.inMatrixRange(x - i, y - i) && ChessBoard.matrix[x - i][y - i] != PieceType.NONE){
				if(!this.getPlayer().isPieceOfTheSamePropietary(x - i, y - i))
					super.posibleMovementsList.add(new Integer[] {x - i, y - i});
				firstEncounter = false;
			}
		}
		
		return super.posibleMovementsList;
	}

}
