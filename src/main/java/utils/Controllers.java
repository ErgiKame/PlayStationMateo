package utils;

import controller.*;
import javafx.scene.CacheHint;
import javafx.scene.layout.VBox;

public class Controllers {

	private static RaporteController raporteController;
	private static ShitjetController shitjetController;
	private static ArtikujController artikujController;
	private static FurnizimController furnizimController;
	private static InventariController inventariController;
	private static PerdoruesitController perdoruesitController;
	private static PspcController pspcController;
	
	public static void getRaporte(VBox box) {
		raporteController = raporteController == null ? new RaporteController() : raporteController ;
		config(box, raporteController);
	}
	
	public static void getShitjet(VBox box) {
		shitjetController = shitjetController == null ? new ShitjetController() : shitjetController ;
		config(box, shitjetController);
	}
	
	public static void getArtikuj(VBox box) {
		artikujController = artikujController == null ? new ArtikujController() : artikujController ;
		config(box, artikujController);
	}
	
	public static void getFurnizim(VBox box) {
		furnizimController = furnizimController == null ? new FurnizimController() : furnizimController ;
		config(box, furnizimController);
	}
	
	public static void getInventari(VBox box) {
		inventariController =  new InventariController();
		config(box, inventariController);
	}
	
	public static void getPerdoruesit(VBox box) {
		perdoruesitController =  perdoruesitController == null ? new PerdoruesitController() : perdoruesitController ;
		config(box, perdoruesitController);
	}

	public static void getPspc(VBox box) {
		pspcController =  pspcController == null ? new PspcController() : pspcController ;
		config(box, pspcController);
	}
	
	public static void config(VBox box, VBox content) {
		box.getChildren().clear();
		box.getChildren().add(content);
		box.setCache(true);
		box.setCacheShape(true);
		box.setCacheHint(CacheHint.SPEED);
	}

	
}
