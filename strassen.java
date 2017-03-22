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
		if(flag == 1){
			//500 by 500
			Random rand = new Random(System.currentTimeMillis());
			try{
				FileWriter fw = new FileWriter("input1.txt");
				int val = -1;
				for(int i = 0; i < 500000; i++)
				{
					val = rand.nextInt(2);
					fw.write(val + "\n");
				}
				fw.close();
			}
			catch(IOException e){
				System.out.println("Error");
			}
			
			inFile = new File("input1.txt");
		}
		else if(flag == 2){
			//700x700
			Random rand = new Random(System.currentTimeMillis());
			try{
				FileWriter fw = new FileWriter("input2.txt");
				int val = -1;
				for(int i = 0; i < 980000; i++)
				{
					val = rand.nextInt(2);
					fw.write(val + "\n");
				}
				fw.close();
			}
			catch(IOException e){
				System.out.println("Error");
			}
			
			inFile = new File("input2.txt");
		}
		else if(flag == 3){
			Random rand = new Random(System.currentTimeMillis());
			try{
				FileWriter fw = new FileWriter("input3.txt");
				int val = -1;
				for(int i = 0; i < 2000000; i++)
				{
					val = rand.nextInt(2);
					fw.write(val + "\n");
				}
				fw.close();
			}
			catch(IOException e){
				System.out.println("Error");
			}
			
			inFile = new File("input3.txt");
		}
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
		/*System.out.println("Array A");
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
		}*/
		long startTime = -1;
		long endTime = -1;
		long duration = -1;
		//checking standard multiplication
		for(int x = 20; x<350; x+= 5){
			startTime = System.nanoTime();
			matrixMult s = new matrixMult(dimension, x);
			int[][] result = s.strassenMultiply(matrix_A, matrix_B, dimension);
			endTime = System.nanoTime();
			duration = (endTime - startTime);
			System.out.print(duration + "\n");
		}
		/*matrixMult s = new matrixMult(dimension, 2);
		int[][] result1 = s.standardMultiply(matrix_A, matrix_B, dimension);
		int[][] result2 = s.strassenMultiply(matrix_A, matrix_B, dimension);

		System.out.println("Multiplied");
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				System.out.println(result1[x][y]);
			}
		}

		//System.out.println(result2.length);
		System.out.println("Strassen");
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				System.out.println(result2[x][y]);
			}
			}*/
		
	}
}