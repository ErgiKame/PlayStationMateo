package dao;

public class ControlDAO {

	private static ControlDAO dao = new ControlDAO();
	private LoginDao login_dao = new LoginDao();
	private PerdoruesitDao perdoruesit_dao = new PerdoruesitDao();
	private ArtikujtDao artikujt_dao = new ArtikujtDao();
	private FurnizimDao furnizim_dao = new FurnizimDao();
	private InventariDao inventari_dao = new InventariDao();
	private ShitjetDao shitje_dao = new ShitjetDao();
	private PspcDao pspc_dao = new PspcDao();
	private LlogaritjeDao llogaritje_dao = new LlogaritjeDao();

	public PerdoruesitDao getPerdoruesitDao() {
		return perdoruesit_dao;
	}

	public ArtikujtDao getArtikujtDao() {
		return artikujt_dao;
	}

	public LoginDao getLoginDao() {
		return login_dao;
	}

	public ShitjetDao getShitjeDao(){ return shitje_dao; }

	public static ControlDAO getControlDao() {
		return dao;
	}

    public FurnizimDao getFurnizimDao() {
		return furnizim_dao;
    }

    public InventariDao getInventariDao() {
		return inventari_dao;
    }

	public PspcDao getPspcDao() {
		return pspc_dao;
	}

	public LlogaritjeDao getLlogaritjeDao() {
		return llogaritje_dao;
	}
}