package gui;

/** @author Find 2黑 0空 1白 记得在进入房间的时候同步这里的放的黑白棋 */
public class ChessList {
	//
	private int[][] chesstable = new int[8][8];
	private int press;// 己方执的是什么子
	private boolean[][] step;// 每次放子要翻动的那些
	int sx, sy;
	int depth;

	public ChessList(boolean isblack) {
		press = isblack ? 2 : 1;
		chesstable[3][3] = 1;
		chesstable[4][3] = -1;
		chesstable[4][4] = 1;
		chesstable[3][4] = -1;
	}
	/**@author Find 放对方放的子*/
	public void setOp(int x,int y){
		step = new boolean[8][8];
		sx = x;
		sy = y;
		int[] dx = { 0, 0, 1, 1, 1, -1, -1, -1 };
		int[] dy = { 1, -1, 0, -1, 1, 0, -1, 1 };
		int temp = chesstable[x][y];
		if (temp == 0) {
			// 如果八个方向都非法，则非法
			chesstable[x][y] = press;
			step[x][y] = true;
			for (int i = 0; i <= 7; i++) {
				depth = 0;
				walk(x + dx[i], y + dy[i], dx[i], dy[i], press);
			}
		}
	}
	
	public boolean canset(int x, int y) {
		step = new boolean[8][8];
		sx = x;
		sy = y;
		int[] dx = { 0, 0, 1, 1, 1, -1, -1, -1 };
		int[] dy = { 1, -1, 0, -1, 1, 0, -1, 1 };
		int temp = chesstable[x][y];
		int num = 0;
		if (temp == 0) {
			// 如果八个方向都非法，则非法
			chesstable[x][y] = press;
			step[x][y] = true;
			for (int i = 0; i <= 7; i++) {
				depth = 0;
				num += walk(x + dx[i], y + dy[i], dx[i], dy[i], press);
			}
			// 直接记录路径，然后记录好每个要设置的点
			if (num > 0) {
				return true;
			} else {
				return false;
			}
		} else
			return false;
	}

	public int walk(int x, int y, int dx, int dy, int stand) {
		if ((x < 0) || (x > 7) || (y < 0) || (y > 7)) {
			// 判断是否出界
			depth = 0;
			return 0;
		}

		if (depth > 0) {
			if (chesstable[x][y] == stand) {
				for (int i = 1; i <= depth; i++) {
					// 更新数组
					step[sx+i*dx][sy+i*dy]=true;
					chesstable[sx + i * dx][sy + i * dy] = stand;
				}
				return depth;
			} else {
				if (chesstable[x][y] == 0) {
					depth = 0;
					return depth;
				} else {
					depth++;
					x += dx;
					y += dy;
					walk(x, y, dx, dy, stand);
				}
			}

		} else {
			if ((chesstable[x][y] == 0) || (chesstable[x][y] == stand)) {
				depth = 0;
				return depth;
			} else {
				depth++;
				x += dx;
				y += dy;
				walk(x, y, dx, dy, stand);
			}
		}
		return depth;
	}

	/** @author Find 检测能不能再放子 */
	public boolean checkset() {
		int x = 0;
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				x = canset(i, j) ? x++ : x;
			}
		}
		return x > 0;
	}

	public boolean[][] getstep() {
		return step;
	}

	// 返回棋盘
	public int[][] getchesstable() {
		return chesstable;
	}

	public void setchesstable(int[][] temp) {
		chesstable = temp;
	}
}
