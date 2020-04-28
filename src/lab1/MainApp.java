package lab1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class MainApp {
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	public static double[] getSet() throws IOException {
		String dataSet = "C:\\Users\\win7\\Desktop\\senzorskiSistemi\\AirQualityUCI.csv";
		BufferedReader br = new BufferedReader(new FileReader(dataSet));
		double dataSET[] = new double[10000];
		int pomosenIndex = 0;
		String line = br.readLine();
		while((line = br.readLine()) != null) {
			String[] clenovi=line.split(";");
			if(clenovi.length!=0) {
				clenovi[2] = clenovi[2].replaceAll(",", ".");
				if(Double.parseDouble(clenovi[2])!=-200) {
					dataSET[pomosenIndex++] = Double.parseDouble(clenovi[2]);
				}
			}
		}
		br.close();
		return dataSET;
	}
	
	public static String linear(double threshold,int frequency, double inputData[]) {
		int counter = 0;
		double minSquareError = 0.0;
		double sync[] = new double[inputData.length];
		int pomosenIndex = 0;
		sync[pomosenIndex++] = inputData[0];
		for(int i=frequency;i<inputData.length;i+=frequency) {
				double pretpostavka = sync[pomosenIndex-1];
				double razlika = inputData[i]-pretpostavka;
				minSquareError += razlika*razlika;
				if(Math.abs(razlika)>=threshold) {
					counter++;
					sync[pomosenIndex++] = inputData[i];
				}else {
					sync[pomosenIndex++] = pretpostavka;
				}
		}
		minSquareError /= pomosenIndex;
		double percentage = counter * 100.0/(pomosenIndex-1);
		//String procent = Double.toString(percentage).replaceAll(".", ",");
		return "TH: "+threshold+" CN: "+counter+" %: "+df.format(percentage)+" MSE: "+df.format(minSquareError);
	}
	
	
	public static String moving_average(int red, int frequency, double threshold, double inputData[]) {
		int counter = 0;
		double minSquareError = 0.0;
		double sync[] = new double[inputData.length];
		
		int pomosenIndex=0;
		for(int i=0;i<red*frequency;i+=frequency) {
			sync[pomosenIndex++] = inputData[i];
		}
		
		for(int i=red*frequency;i<inputData.length;i+=frequency) {
				double zbir=0;
				for(int j=pomosenIndex-red;j<pomosenIndex;j++) {
					zbir+=sync[j];
				}
				double pretpostavka = zbir/red;
				double razlika = inputData[i]-pretpostavka;
				minSquareError += razlika*razlika;
				if(Math.abs(razlika)>=threshold) {
					counter++;
					sync[pomosenIndex++] = inputData[i];
				}else {
					sync[pomosenIndex++] = pretpostavka;
				}
		}
		minSquareError /= pomosenIndex;
		double percentage = counter * 100.0/(pomosenIndex-1);
		//String procent = Double.toString(percentage).replaceAll(".", ",");
		return "TH: "+threshold+" CN: "+counter+" %: "+df.format(percentage)+" MSE: "+df.format(minSquareError);
		
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		double[] set = getSet();
		double thresholds[] = { 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0 };
		System.out.println("<====== FREFENCIJA=1 =====>");
		System.out.println("LPF1");
		for(double th : thresholds) {
			System.out.println(linear(th, 1, set));
		}
		
		System.out.println("\n\nMAF1L2");
		for(double th : thresholds) {
			System.out.println(moving_average(2, 1, th, set));
		}
		
		System.out.println("\n\nMAF1L3");
		for(double th : thresholds) {
			System.out.println(moving_average(3, 1, th, set));
		}
		
		
		System.out.println("\n\n<====== FREFENCIJA=2 =====>");
		System.out.println("LPF2");
		for(double th : thresholds) {
			System.out.println(linear(th, 2, set));
		}
		
		System.out.println("\n\nMAF2L2");
		for(double th : thresholds) {
			System.out.println(moving_average(2, 2, th, set));
		}
		
		System.out.println("\n\nMAF2L3");
		for(double th : thresholds) {
			System.out.println(moving_average(3, 2, th, set));
		}
		
		

		System.out.println("\n\n<====== FREFENCIJA=3 =====>");
		System.out.println("LPF3");
		for(double th : thresholds) {
			System.out.println(linear(th, 3, set));
		}
		
		System.out.println("\n\nMAF3L2");
		for(double th : thresholds) {
			System.out.println(moving_average(2, 3, th, set));
		}
		
		System.out.println("\n\nMAF3L3");
		for(double th : thresholds) {
			System.out.println(moving_average(3, 3, th, set));
		}
	}

}
