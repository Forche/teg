package objetos;

public enum SimboloEnum {

	BARCO("barco"), GLOBO("globo"), CANON("canon"), COMODIN("comodin");

	private String key;

	SimboloEnum(String key) {
		this.setKey(key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
