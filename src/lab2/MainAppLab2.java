package lab2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainAppLab2 {
	static MainAppLab2 obj = new MainAppLab2();
	
	public static double[][] getSet() throws IOException {
		double[][] dataSET = new double[2][9358];
		
		String dataSet = "C:\\Users\\win7\\Desktop\\fakultet\\senzorskiSistemi\\laboratoriska2\\AirQualityUCI.csv";
		BufferedReader br = new BufferedReader(new FileReader(dataSet));
		
		String line = br.readLine();
		int row1 = 0, row2 = 1;
		int col = 0;
		int brojac=0;
		while((line = br.readLine()) != null) {
			String copy = line;
			if(copy.replaceAll(";", "").length() != 0) {
				String[] lineParts = line.split(";");
				double jaglerodMonoksid = Double.parseDouble(lineParts[2].replaceAll(",", "."));
				double benzen = Double.parseDouble(lineParts[5].replaceAll(",", "."));
				
				if(jaglerodMonoksid != -200 && benzen != -200) {
					dataSET[row1][col] = jaglerodMonoksid;
					dataSET[row2][col++] = benzen;
				}
			}
		}
		
		br.close();
		
		return dataSET;
	}
	
	
	public static Result linearPrediction(double threshold1,double threshold2,int frequency, double inputData[][]) {
		int counter = 0;
		double meanSquareErrorCO = 0.0, meanSquareErrorC6H6 = 0.0;
		double sync[][] = new double[2][9358];
		
		int pomosenIndexRow1 = 0, pomosenIndexRow2 = 1, col = 0;
		sync[pomosenIndexRow1][col] = inputData[0][0];
		sync[pomosenIndexRow2][col++] = inputData[1][0];
		
		for(int i = frequency; i < 9358 ; i+=frequency) {
			
			double pretpostavkaCO = sync[pomosenIndexRow1][col-1];
			double pretpostavkaC6H6 = sync[pomosenIndexRow2][col-1];
			
			double razlikaCO = inputData[pomosenIndexRow1][i] - pretpostavkaCO;
			double razlikaC6H6 = inputData[pomosenIndexRow2][i] - pretpostavkaC6H6;
			
			meanSquareErrorCO += Math.pow(razlikaCO, 2);
			meanSquareErrorC6H6 += Math.pow(razlikaC6H6, 2);
			
			if(Math.abs(razlikaCO)>=threshold1 || Math.abs(razlikaC6H6)>=threshold2) {
				counter++;
				sync[pomosenIndexRow1][col] = inputData[pomosenIndexRow1][i];
				sync[pomosenIndexRow2][col++] = inputData[pomosenIndexRow2][i];
			}else {
				sync[pomosenIndexRow1][col] = pretpostavkaCO;
				sync[pomosenIndexRow2][col++] = pretpostavkaC6H6;
			}
		}
		
		meanSquareErrorCO /= (col-1);
		meanSquareErrorC6H6 /= (col-1);
		
		double percentage = counter * 100.0 / (col-1);
		
		return obj.new Result(threshold1, threshold2, counter, percentage, meanSquareErrorCO, meanSquareErrorC6H6);
	}
	
	public static double average(int red, double set[][], int row,int lastElement) {
		double zbir=0.0;
		int pom=1;
		while(pom<=red) {
			zbir+=set[row][lastElement-pom];
			pom++;
		}
		return zbir/red;
		
	}
	
	public static Result moving_average(double threshold1, double threshold2, int frequency, double inputData[][], int red) {
		int counter = 0;
		double meanSquareErrorCO = 0.0, meanSquareErrorC6H6 = 0.0;
		double sync[][] = new double[2][9358];
		
		int pomosenIndexRow1 = 0, pomosenIndexRow2 = 1, col = 0;
		for(int i = 0; i < red*frequency ; i+=frequency) {
			sync[pomosenIndexRow1][col] = inputData[pomosenIndexRow1][i];
			sync[pomosenIndexRow2][col++] = inputData[pomosenIndexRow2][i];
		}
		
		for(int i = red*frequency; i < 9358; i+=frequency) {
			double pretpostavkaCO = average(red, sync, pomosenIndexRow1, col);
			double pretpostavkaC6H6 = average(red, sync, pomosenIndexRow2, col);
			
			double razlikaCO = inputData[pomosenIndexRow1][i] - pretpostavkaCO;
			double razlikaC6H6 = inputData[pomosenIndexRow2][i] - pretpostavkaC6H6;
			
			meanSquareErrorCO += Math.pow(razlikaCO, 2);
			meanSquareErrorC6H6 += Math.pow(razlikaC6H6, 2);
			
			if(Math.abs(razlikaCO)>=threshold1 || Math.abs(razlikaC6H6)>=threshold2) {
				counter++;
				sync[pomosenIndexRow1][col] = inputData[pomosenIndexRow1][i];
				sync[pomosenIndexRow2][col++] = inputData[pomosenIndexRow2][i];
			}else {
				sync[pomosenIndexRow1][col] = pretpostavkaCO;
				sync[pomosenIndexRow2][col++] = pretpostavkaC6H6;
			}
		}
		
		meanSquareErrorCO /= (col-1);
		meanSquareErrorC6H6 /= (col-1);
		
		double percentage = counter * 100.0 / (col-1);
		
		return obj.new Result(threshold1, threshold2, counter, percentage, meanSquareErrorCO, meanSquareErrorC6H6);
	}
	
	public static void main(String[] args) throws IOException {
		double dataSet[][] = new double[2][9358];
		dataSet = getSet();
		double threshold1[] = {0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
		double threshold2[] = {0.68, 1.68, 2.68, 3.68, 4.68, 5.68, 6.68, 7.68};
		
		System.out.println("<======== FREQUENCY=1 =======>");
		for(int i = 0;i < 8;i++) {
			System.out.println("\t>>>>>TH1:"+threshold1[i]+"<<<<<<< >>>>>>>TH2: "+threshold2[i]+"<<<<<<<<");
			System.out.println("\t\tLINEAR PREDICTION:");
			System.out.println(linearPrediction(threshold1[i],threshold2[i],1,dataSet));
			System.out.println();
			System.out.println("\t\tMOVING AVERAGE LEVEL 2:");
			System.out.println(moving_average(threshold1[i], threshold2[i],1, dataSet, 2));
			System.out.println();
			System.out.println("\t\tMOVING AVERAGE LEVEL 3:");
			System.out.println(moving_average(threshold1[i], threshold2[i],1, dataSet, 3));
			
			System.out.println();
			System.out.println();
			System.out.println();
		}
		
		/*System.out.println("<======== FREQUENCY=2 =======>");
		for(int i = 0;i < 8;i++) {
			System.out.println("\t>>>>>TH1:"+threshold1[i]+"<<<<<<< >>>>>>>TH2: "+threshold2[i]+"<<<<<<<<");
			System.out.println("\t\tLINEAR PREDICTION:");
			System.out.println(linearPrediction(threshold1[i],threshold2[i],2,dataSet));
			System.out.println();
			System.out.println("\t\tMOVING AVERAGE LEVEL 2:");
			System.out.println(moving_average(threshold1[i], threshold2[i],2, dataSet, 2));
			System.out.println();
			System.out.println("\t\tMOVING AVERAGE LEVEL 3:");
			System.out.println(moving_average(threshold1[i], threshold2[i],2, dataSet, 3));
			
			System.out.println();
			System.out.println();
			System.out.println();
		}*/
		
		/*System.out.println("<======== FREQUENCY=3 =======>");
		for(int i = 0;i < 8;i++) {
			System.out.println("\t>>>>>TH1:"+threshold1[i]+"<<<<<<< >>>>>>>TH2: "+threshold2[i]+"<<<<<<<<");
			System.out.println("\t\tLINEAR PREDICTION:");
			System.out.println(linearPrediction(threshold1[i],threshold2[i],3,dataSet));
			System.out.println();
			System.out.println("\t\tMOVING AVERAGE LEVEL 2:");
			System.out.println(moving_average(threshold1[i], threshold2[i],3, dataSet, 2));
			System.out.println();
			System.out.println("\t\tMOVING AVERAGE LEVEL 3:");
			System.out.println(moving_average(threshold1[i], threshold2[i],3, dataSet, 3));
			
			System.out.println();
			System.out.println();
			System.out.println();
		}*/
	}
	
	
	
	
	
	class Result{
		double threshold1;
		double threshold2;
		int numberOfSent;
		double percentageOfSent;
		double meanSquareError1;
		double meanSquareError2;
		double meanSquareError;
		private DecimalFormat df = new DecimalFormat("0.00");
		
		public Result(double threshold1, double threshold2, int numberOfSent, double percentageOfSent,
				double meanSquareError1, double meanSquareError2) {
			super();
			this.threshold1 = threshold1;
			this.threshold2 = threshold2;
			this.numberOfSent = numberOfSent;
			this.percentageOfSent = percentageOfSent;
			this.meanSquareError1 = meanSquareError1;
			this.meanSquareError2 = meanSquareError2;
			this.meanSquareError= this.meanSquareError1+this.meanSquareError2;
		}

		@Override
		public String toString() {
			return "Result [threshold1=" + threshold1 + ", threshold2=" + threshold2 + ", numberOfSent=" + numberOfSent
					+ ", percentageOfSent=" + df.format(percentageOfSent) 
					+ ", meanSquareError=" + df.format(meanSquareError) + "]";
		}
		
		
		
		
				
		
	}

}
