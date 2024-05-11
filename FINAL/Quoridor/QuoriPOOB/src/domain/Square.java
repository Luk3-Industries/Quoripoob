package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * Create a square.
 * 
 * @author Daniel Diaz && Miguel Motta
 * @version (a version number or a date)
 */
public abstract class Square implements Serializable{
    protected Board board;
    protected Token token;
    protected int row;
    protected int column;
    protected Wall wallLeft;
    protected Wall wallRight;
    protected Wall wallUp;
    protected Wall wallDown;

    

    /**
     * Constructor for objects of class Square
     * 
     * @param row    the row where the square is located
     * @param column the column where the square is located
     * @param board  the board where the square is located
     * @throws QuoriPOOBException
     */
    public Square(int row, int column, Board board) throws QuoriPOOBException {
        if (row > board.getSize() || column > board.getSize() || row < 0 || column < 0)
            throw new QuoriPOOBException(QuoriPOOBException.SQUARE_OUT_OF_RANGE);

        this.row = row;
        this.column = column;
        this.board = board;
        this.token = null;
        this.wallDown = null;
        this.wallLeft = null;
        this.wallRight = null;
        this.wallUp = null;
    }

    /**
     * Add a token if a token is on the square
     * 
     * @param token the token on the square
     * @throws QuoriPOOBException
     */
    public void setToken(Token token) throws QuoriPOOBException {
        if (this.token != null)
            throw new QuoriPOOBException(QuoriPOOBException.TOKEN_IN_SQUARE);

        this.token = token;
    }

    /**
     * Delete the token that is on top of the box
     */
    public void delToken() {
        this.token = null;
    }

    /**
     * Add a wall above the square
     * 
     * @param wall the wall to be added
     */
    public void addWallUp(Wall wall) {
        this.wallUp = wall;
    }

    /**
     * Add a wall to the left of the square
     * 
     * @param wall the wall to be added
     */
    public void addWallLeft(Wall wall) {
        this.wallLeft = wall;
    }

    /**
     * Add a wall below the square
     * 
     * @param wall the wall to be added
     */
    public void addWallDown(Wall wall) {
        this.wallDown = wall;
    }

    /**
     * Add a wall to the right of the square
     * 
     * @param wall the wall to be added
     */
    public void addWallRight(Wall wall) {
        this.wallRight = wall;
    }

    /**
     * Delete the wall at the top of the square
     */
    public void delWallUp() {
        setWallUp(null);
        if(this.row >0){
            board.getMatrixBoard()[row-1][column].setWallDown(null);
        }
    }

    /**
     * Delete the wall to the left of the square
     */
    public void delWallLeft() {
        setWallLeft(null);
        if(this.column >0){
            board.getMatrixBoard()[row][column-1].setWallRight(null);
        }
    }

    /**
     * Delete the wall at the bottom of the square
     */
    public void delWallDown() {
        setWallDown(null);
        if(this.row < board.getSize()-1){
            board.getMatrixBoard()[row+1][column].setWallUp(null);
        }
    }

    /**
     * Delete the wall to the right of the square
     */
    public void delWallRight() {
        setWallRight(null);
        if(this.column < board.getSize() -1 ){
            board.getMatrixBoard()[row][column+1].setWallLeft(null);
        }
    }

    /**
     * Check if the there is a wall above the square blocking the passage to the
     * token
     * 
     * @param token the token to pass
     * @return TRUE, if the there is a wall blocking the tocken. FALSE, otherwise
     */
    public boolean blockUp(Color token) {
        boolean block = false;
        if (this.wallUp != null) {
            block = this.wallUp.blockToken(token);
        }

        return block;
    }

    /**
     * Check if the there is a wall to the left of the square blocking the passage
     * to the token
     * 
     * @param token the token to pass
     * @return TRUE, if the there is a wall blocking the tocken. FALSE, otherwise
     */
    public boolean blockLeft(Color token) {
        boolean block = false;
        if (this.wallLeft != null) {
            block = this.wallLeft.blockToken(token);
        }

        return block;
    }

    /**
     * Check if the there is a wall below the square blocking the passage to the
     * token
     * 
     * @param token the token to pass
     * @return TRUE, if the there is a wall blocking the tocken. FALSE, otherwise
     */
    public boolean blockDown(Color token) {
        boolean block = false;
        if (this.wallDown != null) {
            block = this.wallDown.blockToken(token);
        }

        return block;
    }

    /**
     * Check if the there is a wall to the right of the square blocking the passage
     * to the token
     * 
     * @param token the token to pass
     * @return TRUE, if the there is a wall blocking the tocken. FALSE, otherwise
     */
    public boolean blockRight(Color token) {
        boolean block = false;
        if (this.wallRight != null) {
            block = this.wallRight.blockToken(token);
        }

        return block;
    }

    /**
     * Check if the square block the diagonal movements of the token
     * 
     * @return TRUE, if the square block the diagonal movements. FALSE, otherwise;
     */
    public boolean blockDiagonalMovements() {
        boolean res = true;
        // res puede variar si square es de tipo Teleporter?
        return res;
    }

    // Getters y Setters

    /**
     * return the position in the board of the
     * 
     * @return an int array with the row and column of the square
     */
    public int[] getCoordenates() {
        return new int[] { row, column };
    }

    /**
     * return the top wall of the square
     * 
     * @return an Wall object
     */
    public Wall getWallUp() {
        return this.wallUp;
    }

    /**
     * return the wall to the left of the square
     * 
     * @return an Wall object
     */
    public Wall getWallLeft() {
        return this.wallLeft;
    }

    /**
     * return the bottom wall of the square
     * 
     * @return an Wall object
     */
    public Wall getWallDown() {
        return this.wallDown;
    }

    /**
     * return the wall to the roght of the square
     * 
     * @return an Wall object
     */
    public Wall getWallRight() {
        return this.wallRight;
    }

    /**
     * Sets the left wall of the Square
     * @param wallLeft
     */
    public void setWallLeft(Wall wallLeft) {
        this.wallLeft = wallLeft;
    }

    /**
     * Sets the right wall of the Square
     * @param wallRight
     */
    public void setWallRight(Wall wallRight) {
        this.wallRight = wallRight;
    }

    /**
     * Sets the up wall of the Square
     * @param wallUp
     */
    public void setWallUp(Wall wallUp) {
        this.wallUp = wallUp;
    }

    /**
     * Sets the down wall of the Square
     * @param wallDown
     */
    public void setWallDown(Wall wallDown) {
        this.wallDown = wallDown;
    }

    /**
     * return the token that is on the square
     * 
     * @return an Token object
     */
    public Token getToken() {
        return this.token;
    }

    /**
     * Its the behavior of the square
     * 
     * @throws QuoriPOOBException
     */
    public abstract void act() throws QuoriPOOBException;
}
