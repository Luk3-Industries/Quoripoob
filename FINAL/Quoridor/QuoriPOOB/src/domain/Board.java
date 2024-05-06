package domain;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

	private int size;
	private Player playerPlaying;
	private Square[][] matrixBoard;
	private ArrayList<Token> tokens;
	private ArrayList<Square> squares;
	private ArrayList<Wall> walls;
	private HashMap<Color, Player> players;

	public Board(int size, HashMap<String, int[][]> specialSquares) throws QuoriPOOBException {
		if (size <= 1) throw new QuoriPOOBException(QuoriPOOBException.WRONG_SIZE);
		this.size = size;
		this.matrixBoard = new Square[size][size];
		this.squares = new ArrayList<>();
		if (specialSquares != null) createSpecialSquares(specialSquares);
		createNormalSquares();
	}

	public void setPlayers(HashMap<Color, Player> players) {
		this.players = players;
	}

	public void addWallToBoard(Wall wall) {
		this.walls.add(wall);
	}

	public void delWallFromBoard(Wall wall) {
		this.walls.remove(wall);
	}

	public void moveTokenUp(Color token) {
		// In Construction
	}

	public void moveTokenDown(Color token) {
		// In Construction
	}

	public void moveTokenLeft(Color token) {
		// In Construction
	}

	public void moveTokenRight(Color token) {
		// In Construction
	}

	public void moveTokenUpLeft(Color token) {
		// In Construction
	}

	public void moveTokenUpRight(Color token) {
		// In Construction
	}

	public void moveTokenDownLeft(Color token) {
		// In Construction
	}

	public void moveTokenDownRight(Color token) {
		// In Construction
	}

	// Getters and Setters

	public Object[][] getMatrixBoard(){
		return matrixBoard;
	}
	
	public int getSize() {
		return this.size;
	}

	public Square getSquare(int row, int column) {
		return this.matrixBoard[row][column];
	}

	public Player getPlayerPlaying() {
		return this.playerPlaying;
	}

	public void setPlayerPlaying(Player player) {
		// In construction
	}

	// Private Methods

	private void createNormalSquares() throws QuoriPOOBException {
		for (int row = 0; row < this.size; row++) {
			for (int column = 0; column < this.size; column++) {
				if (matrixBoard[row][column] == null) {
					Square emptySquare = new NormalSquare(row, column, this);
					matrixBoard[row][column] = emptySquare;
					squares.add(emptySquare);
				}
			}
		}
	}

	private void createSpecialSquares(HashMap<String, int[][]> specialSquares) throws QuoriPOOBException{
		for (Map.Entry<String, int[][]> entry : specialSquares.entrySet()) {
			String type = entry.getKey();
			int[][] squares = entry.getValue();
			if (squares.length > Math.pow(this.matrixBoard.length, 2)) throw new QuoriPOOBException(QuoriPOOBException.WRONG_NUMER_SQUARES);
			for (int i = 0; i < squares.length; i++) {
				int row = squares[i][0];
				int column = squares[i][1];
				Square square = createSquare(type, row, column);
				matrixBoard[row][column] = square;
				this.squares.add(square);
			}
		}
	}

	private Square createSquare(String type, int row, int column) {
		Square square = null;
		try {
			Class<?> cls = Class.forName(type);
			if (!Square.class.isAssignableFrom(cls)) throw new QuoriPOOBException(QuoriPOOBException.SQUARE_NOT_EXIST);
			Constructor<?> constructor = cls.getDeclaredConstructor(int.class, int.class, Board.class);
			constructor.setAccessible(true);
			square = (Square) constructor.newInstance(row, column, this);
			this.squares.add(square);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return square;
	}
}
