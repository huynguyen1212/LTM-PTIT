package webservices;
import model.Sudoku;

public class Game {
	public boolean checkValid(Sudoku sudo) {
		int lastX = sudo.getLastX();
		int lastY = sudo.getLastY();
		int[][] value = sudo.getArrayValue();
		int lastValue = sudo.getLastValue();

		for (int i = 0; i <= 8; i++) {
			if (lastValue == value[lastX][i]) {
				return false;
			}
		}

		for (int i = 0; i <= 8; i++) {
			if (lastValue == value[lastX][i]) {
				return false;
			}
		}

		int dem = 0;
		int rMin = lastX / 3 * 3;
		int rMax = lastX / 3 * 3 + 2;
		int cMin = lastY / 3 * 3;
		int cMax = lastY / 3 * 3 + 2;
		for (int i = rMin; i <= rMax; i++) {
			for (int j = cMin; j <= cMax; j++) {
				if (lastValue == value[i][j]) {
					dem++;
				}
			}

		}
		if (dem > 0) {
			return false;
		}

		return true;
	}

	public boolean checkWin(Sudoku sudo) {
		int value[][] = sudo.getArrayValue();
		int dem = 0;
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				if (value[i][j] > 0) {
					continue;
				} else {
					dem++;
				}
			}
		}
		if (dem < 2)
			return true;
		return false;
	}

}
