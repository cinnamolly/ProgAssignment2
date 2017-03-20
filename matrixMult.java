import java.util.*;
import java.lang.*;

public class matrixMult
{
	private int dimension;
	int[][] output;
	int currDim; 
	public matrixMult(int dimension){
		this.dimension = dimension;
		output = new int[dimension][dimension];
	}

	public int[][] standardMultiply(int[][] matrix_A, int[][] matrix_B)
	{
		for (int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				output[x][y] = 0;
				for(int z = 0; z < dimension; z++){
					output[x][y] += matrix_A[x][z]*matrix_B[z][y];
				}
			}
		}
		return output;
	}

	public int[][] strassenMultiply(int[][] m_A, int[][] m_B, int dim)
	{
		if(currDim == 0)
			currDim = 1;
		int[][] a, b, c, d, e, f, g, h, p1, p2, p3, p4, p5, p6, p7, outputQuad1, outputQuad2, outputQuad3, outputQuad4 = new int[currDim][currDim];
		//odd case
		if(dim%2 != 0){
			//pad with 0's
		}

		//base case
		if(dim == 1){
			int[][] value = new int[1][1];
			value[0][0] = m_A[0][0]*m_B[0][0];
			System.out.println("value: " + value[0][0]);
			return value;
		}
		currDim = dim/2;
		System.out.println(currDim);
		//split into sub-arrays
		a = splitter(m_A, "a");
		b = splitter(m_A, "b");
		c = splitter(m_A, "c");
		d = splitter(m_A, "d");
		e = splitter(m_B, "e");
		f = splitter(m_B, "f");
		g = splitter(m_B, "g");
		h = splitter(m_B, "h");

		System.out.println("A:");
		for(int x = 0; x < currDim; x++){
			for(int y = 0; y < currDim; y++){
				System.out.println(a[x][y]);
			}
		}

		int[][] subtract = subtract(f,h);
		System.out.println("Subtract:");
		for(int x = 0; x < currDim; x++){
			for(int y = 0; y < currDim; y++){
				System.out.println(subtract[x][y]);
			}
		}


		//break into sub-problems - recursively use strassen's
		p1 = strassenMultiply(a, subtract(f, h), currDim);
		System.out.println("P1");
		for(int x = 0; x < p1.length; x++){
			for(int y = 0; y < p1.length; y++){
				System.out.println(p1[x][y]);
			}
		}
		p2 = strassenMultiply(add(a, b), h, currDim);
		System.out.println("A:");
		for(int x = 0; x < currDim; x++){
			for(int y = 0; y < currDim; y++){
				System.out.println(a[x][y]);
			}
		}
		System.out.println("P2");
		for(int x = 0; x < p2.length; x++){
			for(int y = 0; y < p2.length; y++){
				System.out.println(p2[x][y]);
			}
		}
		p3 = strassenMultiply(add(c, d), e, currDim);
		System.out.println("P3");
		for(int x = 0; x < p3.length; x++){
			for(int y = 0; y < p3.length; y++){
				System.out.println(p3[x][y]);
			}
		}
		p4 = strassenMultiply(d, subtract(g,e), currDim);
		p5 = strassenMultiply(add(a, d), add(e, h), currDim);
		p6 = strassenMultiply(subtract(b, d), add(g, h), currDim);
		p7 = strassenMultiply(subtract(a, c), add(e, f), currDim);
		System.out.println("here");

		System.out.println("P5");
		for(int x = 0; x < p5.length; x++){
			for(int y = 0; y < p5.length; y++){
				System.out.println(p5[x][y]);
			}
		}

		System.out.println("P4");
		for(int x = 0; x < p4.length; x++){
			for(int y = 0; y < p4.length; y++){
				System.out.println(p4[x][y]);
			}
		}

		System.out.println("P2");
		for(int x = 0; x < p4.length; x++){
			for(int y = 0; y < p4.length; y++){
				System.out.println(p2[x][y]);
			}
		}

		System.out.println("P6");
		for(int x = 0; x < p4.length; x++){
			for(int y = 0; y < p4.length; y++){
				System.out.println(p6[x][y]);
			}
		}
		//each quad should be of size 4
		outputQuad1 = add(subtract(add(p5, p4), p2), p6);
		outputQuad2 = add(p1, p2);
		outputQuad3 = subtract(subtract(add(p1, p5), p3), p7);
		outputQuad4 = add(p3, p4);

		System.out.println("Quad 1");
		for(int x = 0; x < outputQuad1.length; x++){
			for(int y = 0; y < outputQuad1.length; y++){
				System.out.println(outputQuad1[x][y]);
			}
		}

		System.out.println("Quad 2");
		for(int x = 0; x < outputQuad1.length; x++){
			for(int y = 0; y < outputQuad1.length; y++){
				System.out.println(outputQuad2[x][y]);
			}
		}

		System.out.println("Quad 3");
		for(int x = 0; x < outputQuad1.length; x++){
			for(int y = 0; y < outputQuad1.length; y++){
				System.out.println(outputQuad3[x][y]);
			}
		}

		System.out.println("Quad 4");
		for(int x = 0; x < outputQuad1.length; x++){
			for(int y = 0; y < outputQuad1.length; y++){
				System.out.println(outputQuad4[x][y]);

			}
		}

		int rowMarker = 0;
		int colMarker = 0;
		int[][] currMatrix = outputQuad1;
		int pieceDim = outputQuad1.length;
		int totalLength = pieceDim*2;
		int[][] out = new int[totalLength][totalLength];
		System.out.println("Piece Dim: " + pieceDim);
		//problem with piecing matricies together
		for(int x = 0; x< totalLength; x++){
			for(int y = 0; y <totalLength; y++){
				if(x>=pieceDim && y >= pieceDim){
					currMatrix = outputQuad3;
					rowMarker = pieceDim;
					colMarker = pieceDim;
				}
				else if(x >= pieceDim){
					currMatrix = outputQuad4;
					rowMarker = pieceDim;
					colMarker = 0;
				}
				else if (y >= pieceDim){
					currMatrix = outputQuad2;
					rowMarker = 0;
					colMarker = pieceDim;
				}
				out[x][y] = currMatrix[x-rowMarker][y-colMarker];
			}
		}

		System.out.println("out");
		for(int x = 0; x < out.length; x++){
			for(int y = 0; y < out.length; y++){
				System.out.println(out[x][y]);

			}
		}

		return out;
	}

	private int[][] splitter(int[][] matrix, String letter){
		int[][] result = new int[currDim][currDim];
		int rowMarker = 0;
		int colMarker = 0;
		if(letter.equals("b") || letter.equals("f")){
			rowMarker = currDim;
		}
		else if(letter.equals("c") || letter.equals("g")){
			colMarker = currDim;
		}
		else if(letter.equals("d") || letter.equals("h")){
			rowMarker = currDim;
			colMarker = currDim;
		}
		System.out.println(letter);
		for(int x = 0; x < currDim; x++)
			{
				for(int y = 0; y < currDim; y++){
					result[x][y] = matrix[x+colMarker][y+rowMarker];
					System.out.println("Splitter " + result[x][y]);
				}
			}
		return result;
	}

	private int[][] add(int[][] m1, int[][] m2)
	{
		int[][] algebraOutput = new int[currDim][currDim];
		for (int x = 0; x < currDim; x++){
			for(int y = 0; y < currDim; y++){
				algebraOutput[x][y] = m1[x][y] + m2[x][y];
			}
		}
		return algebraOutput;
	}

	private int[][] subtract(int[][] m1, int[][] m2)
	{
		int[][] algebraOutput = new int[currDim][currDim];
		for (int x = 0; x < currDim; x++){
			for(int y = 0; y < currDim; y++){
				algebraOutput[x][y] = m1[x][y] - m2[x][y];
			}
		}
		return algebraOutput;
	}
}