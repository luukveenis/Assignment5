
public class Tester {
	
	public static void printMatrix(int[][] matrix){
		int size = matrix.length;
		for (int i=0; i<size; i++){
			for (int j=0; j<size; j++){
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args){
		//int[][] matrix = {{0,1,0,5},{1,0,2,0},{0,2,0,1},{5,0,1,0}};
		int[][] matrix = {{0,5,0,3,0,7},{5,0,6,10,0,0},{0,6,0,1,0,0},{3,10,1,0,4,0},{0,0,0,4,0,20},{7,0,0,0,20,0}};
		int minimumWeight = MWST.mwst(matrix);
		System.out.println(minimumWeight);
	}
}
