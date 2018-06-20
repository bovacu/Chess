package pieces;

import java.util.List;

import main.ChessBoard;
import main.Player;

/**
 * This class represents the Queen piece.
 * @author bovacu
 *
 */
public class Queen extends Piece{

	public Queen(int x, int y, String imageUrl, Player player) {
		super(x, y, PieceType.QUEEN, imageUrl, player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Checks the possible movements of this piece by mixing the rook and bishop movements.
	 */
	@Override
	public List<Integer[]> getValidPositionsToMove() {
		int x = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
				ChessBoard.SQUARE_WIDTH_HEIGHT)[0];
		int y = super.screenCoordToMatrixCoord(super.getPosition()[0], super.getPosition()[1], 2 * ChessBoard.SQUARE_WIDTH_HEIGHT, 
				ChessBoard.SQUARE_WIDTH_HEIGHT)[1];
		
		rookLikeSearch(x, y);
		bishopLikeSearch(x, y);
		
		return super.posibleMovementsList; 
	}
	
	/**
	 * This is the same movement as the rook.
	 * @param x position in X axis in matrix coords of the piece.
	 * @param y position in Y axis in matrix coords of the piece.
	 */
	private void rookLikeSearch(int x, int y) {
		boolean firstEncounter = true;
		for(int i = 1; i < ChessBoard.BOARD_WIDTH_AND_HEIGHT; i++) {
			if(!firstEncounter)
				break;
			
			if(super.inMatrixRange(x, y + i) && ChessBoard.matrix[x][y + i] == PieceType.NONE)
				super.posibleMovementsList.add(new Integer[] {x, y + i});
			else if(firstEncounter && super.inMatrixRange(x, y + i) && ChessBoard.matrix[x][y + i] != PieceType.NONE){
				if(!this.getPlayer().isPieceOfTheSamePropietary(x, y + i))
					super.posibleMovementsList.add(new Integer[] {x, y + i});
				firstEncounter = false;
			}
		}
		
		firstEncounter = true;
		for(int i = 1; i < ChessBoard.BOARD_WIDTH_AND_HEIGHT; i++) {
			if(!firstEncounter)
				break;
			
			if(super.inMatrixRange(x, y - i) && ChessBoard.matrix[x][y - i] == PieceType.NONE)
				super.posibleMovementsList.add(new Integer[] {x, y - i});
			else if(firstEncounter && super.inMatrixRange(x, y - i) && ChessBoard.matrix[x][y - i] != PieceType.NONE){
				if(!this.getPlayer().isPieceOfTheSamePropietary(x, y - i))
					super.posibleMovementsList.add(new Integer[] {x, y - i});
				firstEncounter = false;
			}
		}
		
		firstEncounter = true;
		for(int i = 1; i < ChessBoard.BOARD_WIDTH_AND_HEIGHT; i++) {
			if(!firstEncounter)
				break;
			
			if(super.inMatrixRange(x + i, y) && ChessBoard.matrix[x + i][y] == PieceType.NONE)
				super.posibleMovementsList.add(new Integer[] {x + i, y});
			
			else if(firstEncounter && super.inMatrixRange(x + i, y) && ChessBoard.matrix[x + i][y] != PieceType.NONE){
				if(!this.getPlayer().isPieceOfTheSamePropietary(x + i, y))
					super.posibleMovementsList.add(new Integer[] {x + i, y});
				firstEncounter = false;
			}
		}
		
		firstEncounter = true;
		for(int i = 1; i < ChessBoard.BOARD_WIDTH_AND_HEIGHT; i++) {
			if(!firstEncounter)
				break;
			
			if(super.inMatrixRange(x - i, y) && ChessBoard.matrix[x - i][y] == PieceType.NONE)
				super.posibleMovementsList.add(new Integer[] {x - i, y});
			
			else if(firstEncounter && super.inMatrixRange(x - i, y) && ChessBoard.matrix[x - i][y] != PieceType.NONE){
				if(!this.getPlayer().isPieceOfTheSamePropietary(x - i, y))
					super.posibleMovementsList.add(new Integer[] {x - i, y});
				firstEncounter = false;
			}
		}
	}
	
	/**
	 * This is the same movement as the bishop.
	 * @param x position in X axis in matrix coords of the piece.
	 * @param y position in Y axis in matrix coords of the piece.
	 */
	private void bishopLikeSearch(int x, int y) {
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
	}
}
