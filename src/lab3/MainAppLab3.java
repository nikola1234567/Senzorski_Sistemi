package lab3;

public class MainAppLab3 {
	public static void main(String[] args) {
		TrilaterationStarter ts = new TrilaterationStarter();
		
		//ts.generateGraphic__X_R__Y_Loc__Change_fa__Fixed_err();
		//ts.generateGraphic__X_fa__Y_Loc__Change_R__Fixed_err();
		//ts.generateGraphic__X_R__Y_ALE__Change_err__Fixed_fa("non-iterative");
		//ts.generateGraphic__X_err__Y_ALE__Change_R__Fixed_fa("non-iterative");
		//ts.generateGraphic__X_fa__Y_ALE__Change_R__Fixed_err("non-iterative");
		//ts.test_generateGraphic__X_R__Y_Loc__Change_fa__Fixed_err_Najbliski();
		//ts.test_generateGraphic__X_R__Y_Loc__Change_fa__Fixed_err_Najrelevantni();
		//ts.generateGraphic__X_R__Y_ALE__Change_err__Fixed_fa("iterative");
		//ts.generateGraphic__X_err__Y_ALE__Change_R__Fixed_fa("iterative");
		ts.generateGraphic__X_fa__Y_ALE__Change_R__Fixed_err("iterative");
		
	}

}
