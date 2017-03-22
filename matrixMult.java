import java.util.*;
import java.lang.*;

public class matrixMult
{
	private int dimension, crossover;
	//int currDim; 
	public matrixMult(int dimension, int crossover){
		this.dimension = dimension;
		this.crossover = crossover;
	}

	public int[][] standardMultiply(int[][] matrix_A, int[][] matrix_B, int dim)
	{
		int [][] output = new int[dim][dim];
		//System.out.println("DIM: " + dim);
		/*System.out.println("STANDARD A ");
			for(int x = 0; x < matrix_A.length; x++){
				for(int y = 0; y < matrix_A.length; y++){
					System.out.println(matrix_A[x][y]);
				}
			}*/
		for (int x = 0; x < dim; x++){
			for(int y = 0; y < dim; y++){
				for(int z = 0; z < dim; z++){
					output[x][y] += matrix_A[x][z]*matrix_B[z][y];
				}
			}
		}
		/*System.out.println("output length " + output.length);
		for(int x = 0; x < output.length; x++){
				for(int y = 0; y < output.length; y++){
					System.out.println(output[x][y]);
				}
			}*/
		return output;
	}

	public int[][] strassenMultiply(int[][] m_A, int[][] m_B, int dim)
	{
		//int currDim = dim/2;
		//odd case
		int currDim = -1;
		if (dim <= crossover)
		{
			int[][] value = standardMultiply(m_A, m_B, dim);
			//System.out.println("value: " + value[0][0]);
			return value;
		}
		else if(dim%2 != 0 && dim != 1 && dim != 2){
			//determine value to pad
			//int padValue = (int) Math.pow(((int)(Math.log(dim)/Math.log(2))+1), 2);
			//System.out.println("PAD " + padValue);
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
			//ßsSystem.out.println("ODD CURR DIM: " + currDim);
			/*System.out.println("New Matrix A");
			for(int x = 0; x < m_A.length; x++){
				for(int y = 0; y < m_A.length; y++){
					System.out.println(m_A[x][y]);
				}
			}
			System.out.println("New Matrix B");
			for(int x = 0; x < m_B.length; x++){
				for(int y = 0; y < m_B.length; y++){
					System.out.println(m_B[x][y]);
				}
			}*/
		}
		else if (dim !=1 && dim != 2){
			currDim = dim/2;
		}

		int[][] a, b, c, d, e, f, g, h, p1, p1_reg, p2, p2_reg, p3_reg, p4_reg, p5_reg, p6_reg, p7_reg, p3, p4, p5, p6, p7, outputQuad1, outputQuad2, outputQuad3, outputQuad4 = new int[currDim][currDim];
		//System.out.println("CURRENT DIMENSION: " + currDim);

		/*System.out.println("Matrix A");
		for(int x = 0; x < m_A.length; x++){
			for(int y = 0; y < m_A.length; y++){
				System.out.println(m_A[x][y]);
			}
		}*/

		//base case


		//split into sub-arrays
		a = splitter(m_A, "a", currDim);
		b = splitter(m_A, "b", currDim);
		c = splitter(m_A, "c", currDim);
		d = splitter(m_A, "d", currDim);
		e = splitter(m_B, "e", currDim);
		f = splitter(m_B, "f", currDim);
		g = splitter(m_B, "g", currDim);
		h = splitter(m_B, "h", currDim);

		/*System.out.println("a");
		for(int x = 0; x < a.length; x++){
			for(int y = 0; y < a.length; y++){
				System.out.println(a[x][y]);
			}
		}
		System.out.println("b");
		for(int x = 0; x < b.length; x++){
			for(int y = 0; y < b.length; y++){
				System.out.println(b[x][y]);
			}
		}
		System.out.println("c");
		for(int x = 0; x < c.length; x++){
			for(int y = 0; y < c.length; y++){
				System.out.println(c[x][y]);
			}
		}
		System.out.println("d");
		for(int x = 0; x < d.length; x++){
			for(int y = 0; y < d.length; y++){
				System.out.println(d[x][y]);
			}
		}
		System.out.println("e");
		for(int x = 0; x < e.length; x++){
			for(int y = 0; y < e.length; y++){
				System.out.println(e[x][y]);
			}
		}
		System.out.println("f");
		for(int x = 0; x < f.length; x++){
			for(int y = 0; y < f.length; y++){
				System.out.println(f[x][y]);
			}
		}
		System.out.println("g");
		for(int x = 0; x < g.length; x++){
			for(int y = 0; y < g.length; y++){
				System.out.println(g[x][y]);
			}
		}
		System.out.println("h");
		for(int x = 0; x < h.length; x++){
			for(int y = 0; y < h.length; y++){
				System.out.println(h[x][y]);
			}
		}*/

		//break into sub-problems - recursively use strassen's
		p1 = strassenMultiply(a, subtract(f, h), currDim);
		p2 = strassenMultiply(add(a, b), h, currDim);
		p3 = strassenMultiply(add(c, d), e, currDim);
		p4 = strassenMultiply(d, subtract(g,e), currDim);
		p5 = strassenMultiply(add(a, d), add(e, h), currDim);
		p6 = strassenMultiply(subtract(b, d), add(g, h), currDim);
		p7 = strassenMultiply(subtract(a, c), add(e, f), currDim);

		/*p1 = standardMultiply(a, subtract(f, h), currDim);
		p2 = standardMultiply(add(a, b), h, currDim);
		p3 = standardMultiply(add(c, d), e, currDim);
		p4 = standardMultiply(d, subtract(g,e), currDim);
		p5 = standardMultiply(add(a, d), add(e, h), currDim);
		p6 = standardMultiply(subtract(b, d), add(g, h), currDim);
		p7 = standardMultiply(subtract(a, c), add(e, f), currDim);*/

		//debugging
		/*System.out.println("P5");
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
		}*/
		//each quad should be of size 4
		outputQuad1 = add(subtract(add(p5, p4), p2), p6);
		outputQuad2 = add(p1, p2);
		outputQuad3 = subtract(subtract(add(p1, p5), p3), p7);
		outputQuad4 = add(p3, p4);
		
		//debugging
/*		System.out.println("Quad 1");
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
*/
		int rowMarker = 0;
		int colMarker = 0;
		int[][] currMatrix = outputQuad1;
		int pieceDim = outputQuad1.length;
		int totalLength = pieceDim*2;
		int[][] out = new int[totalLength][totalLength];
		//problem with piecing matricies together
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
		//System.out.println("SPLITTER CURR DIM: " + currDim);
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
		//System.out.println(letter);
		for(int x = 0; x < currDim; x++)
			{
				for(int y = 0; y < currDim; y++){
					result[x][y] = matrix[x+colMarker][y+rowMarker];
					//System.out.println("Splitter " + result[x][y]);
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