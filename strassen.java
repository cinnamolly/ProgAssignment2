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
		//flags indicate the dimensions (and randomly generated matricies) we used to experimentally find the cross-over point
		if(flag == 1){
			//2000x2000
			Random rand = new Random(System.currentTimeMillis());
			try{
				FileWriter fw = new FileWriter("input1.txt");
				int val = -1;
				for(int i = 0; i < 8000000; i++)
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
			//1500x1500
			Random rand = new Random(System.currentTimeMillis());
			try{
				FileWriter fw = new FileWriter("input2.txt");
				int val = -1;
				for(int i = 0; i < 4500000; i++)
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
			//1000x1000
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
		//code used to find the graphs included in the write-up
		/*
		long startTime = -1;
		long endTime = -1;
		long duration = -1;
		for(int x = 20; x<500; x+= 5){
			startTime = System.nanoTime();
			endTime = System.nanoTime();
			duration = (endTime - startTime);
			System.out.print(duration + "\n");
		}*/
		//set the cross-over point to be the middle of the plateau as mentioned in the write-up
		matrixMult s = new matrixMult(dimension, 116);
		int[][] result = s.strassenMultiply(matrix_A, matrix_B, dimension);
		for(int x = 0; x < dimension; x++){
			System.out.println(result[x][x]);
		}
		
	}
}