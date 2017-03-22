import java.util.*;
import java.io.*;

public class strassen
{
	public static void main(String[] args){
		int flag = Integer.parseInt(args[0]);
		int dimension = Integer.parseInt(args[1]);
		String input = args[2];
		int[][] matrix_A = new int[dimension][dimension];
		int[][] matrix_B = new int[dimension][dimension];
		boolean isA = true;
		//load arrays with input
		File inFile = new File(input);
		try(Scanner stdin = new Scanner(inFile);){
			for(int z = 0; z < 2; z++)
			{
				for(int x = 0; x < dimension; x++){
					for(int y = 0; y < dimension; y++){
						if(isA)
							matrix_A[x][y] = Integer.parseInt(stdin.next());
						else
							matrix_B[x][y] = Integer.parseInt(stdin.next());
					}
				}
				isA = false;
			}
		} catch(FileNotFoundException e){
			System.out.println ("File not Found");
		}
		//checking contents of arrays
		System.out.println("Array A");
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				System.out.println(matrix_A[x][y]);
			}
		}		
		System.out.println("Array B");
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				System.out.println(matrix_B[x][y]);
			}
		}
		
		//checking standard multiplication
		matrixMult s = new matrixMult(dimension);
		int[][] result = s.standardMultiply(matrix_A, matrix_B, dimension);

		System.out.println("Multiplied");
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				System.out.println(result[x][y]);
			}
		}
		int[][] result2 = s.strassenMultiply(matrix_A, matrix_B, dimension);

		//System.out.println(result2.length);
		System.out.println("Strassen");
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				System.out.println(result2[x][y]);
			}
			}
		
	}
}