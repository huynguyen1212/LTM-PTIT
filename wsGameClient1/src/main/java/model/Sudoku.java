package model;

import java.io.Serializable;
public class Sudoku implements Serializable {

    private static final long serialVersionUID = 20210811004L;
    
    private int[][] arrayValue;
    private int lastX;
    private int lastY;
    private int lastValue;

    public Sudoku() {
    }

    public Sudoku(int[][] arrayValue, int lastX, int lastY, int lastValue) {
        this.arrayValue = arrayValue;
        this.lastX = lastX;
        this.lastY = lastY;
        this.lastValue = lastValue;
    }

    public int[][] getArrayValue() {
        return arrayValue;
    }

    public void setArrayValue(int[][] arrayValue) {
        this.arrayValue = arrayValue;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    public int getLastValue() {
        return lastValue;
    }

    public void setLastValue(int lastValue) {
        this.lastValue = lastValue;
    }

}
