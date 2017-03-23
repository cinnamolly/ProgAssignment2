import java.util.*;
import java.lang.*;

public class matrixMult
{
	private int crossover;

	public matrixMult(int dimension, int crossover){
		this.crossover = crossover;
	}

	public int[][] standardMultiply(int[][] matrix_A, int[][] matrix_B, int dim)
	{
		int [][] output = new int[dim][dim];
		for (int x = 0; x < dim; x++){
			for(int y = 0; y < dim; y++){
				for(int z = 0; z < dim; z++){
					output[x][y] += matrix_A[x][z]*matrix_B[z][y];
				}
			}
		}
		return output;
	}

	public int[][] strassenMultiply(int[][] m_A, int[][] m_B, int dim)
	{
		int currDim = -1;
		if (dim <= crossover)
		{
			int[][] value = standardMultiply(m_A, m_B, dim);
			return value;
		}
		else if(dim%2 != 0 && dim != 1 && dim != 2){
			//if the dimension is odd, pad to the next even number
			int[][] new_A = new int[dim+1][dim+1];
			int[][] new_B = new int[dim+1][dim+1];
			for(int x = 0; x < dim+1; x++){
				for(int y = 0; y < dim+1; y++){
					if(x < dim && y < dim){
						new_A[x][y] = m_A[x][y];
						new_B[x][y] = m_B[x][y];
					}
					else{
						new_A[x][y] = 0;
						new_B[x][y] = 0;
					}
				}
			}
			m_A = new_A;
			m_B = new_B;
			currDim = (dim+1)/2;
		}
		else if (dim !=1 && dim != 2){
			currDim = dim/2;
		}

		int[][] a, b, c, d, e, f, g, h, p1, p2, p3, p4, p5, p6, p7, outputQuad1, outputQuad2, outputQuad3, outputQuad4 = new int[currDim][currDim];

		//split into sub-arrays
		a = splitter(m_A, "a", currDim);
		b = splitter(m_A, "b", currDim);
		c = splitter(m_A, "c", currDim);
		d = splitter(m_A, "d", currDim);
		e = splitter(m_B, "e", currDim);
		f = splitter(m_B, "f", currDim);
		g = splitter(m_B, "g", currDim);
		h = splitter(m_B, "h", currDim);

		//break into sub-problems - recursively use strassen's
		p1 = strassenMultiply(a, subtract(f, h), currDim);
		p2 = strassenMultiply(add(a, b), h, currDim);
		p3 = strassenMultiply(add(c, d), e, currDim);
		p4 = strassenMultiply(d, subtract(g,e), currDim);
		p5 = strassenMultiply(add(a, d), add(e, h), currDim);
		p6 = strassenMultiply(subtract(b, d), add(g, h), currDim);
		p7 = strassenMultiply(subtract(a, c), add(e, f), currDim);

		outputQuad1 = add(subtract(add(p5, p4), p2), p6);
		outputQuad2 = add(p1, p2);
		outputQuad3 = subtract(subtract(add(p1, p5), p3), p7);
		outputQuad4 = add(p3, p4);
		
		//piece matrices together
		int rowMarker = 0;
		int colMarker = 0;
		int[][] currMatrix = outputQuad1;
		int pieceDim = outputQuad1.length;
		int totalLength = pieceDim*2;
		int[][] out = new int[totalLength][totalLength];
		for(int x = 0; x< totalLength; x++){
			for(int y = 0; y <totalLength; y++){
				if(x>=pieceDim && y >= pieceDim){
					currMatrix = outputQuad3;
					rowMarker = pieceDim;
					colMarker = pieceDim;
				}
				else if(x< pieceDim && y < pieceDim){
					currMatrix = outputQuad1;
					rowMarker = 0;
					colMarker = 0;
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

		return out;
	}

	private int[][] splitter(int[][] matrix, String letter, int currDim){
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
		for(int x = 0; x < currDim; x++)
			{
				for(int y = 0; y < currDim; y++){
					result[x][y] = matrix[x+colMarker][y+rowMarker];
				}
			}
		return result;
	}

	private int[][] add(int[][] m1, int[][] m2)
	{
		int currDim = m1.length;
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
		int currDim = m1.length;
		int[][] algebraOutput = new int[currDim][currDim];
		for (int x = 0; x < currDim; x++){
			for(int y = 0; y < currDim; y++){
				algebraOutput[x][y] = m1[x][y] - m2[x][y];
			}
		}
		return algebraOutput;
	}
}