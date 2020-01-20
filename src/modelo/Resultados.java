package modelo;

public class Resultados {
	private int manga1;
	private int manga2;
	private int manga3;
	private int manga4;
	private int manga5;
	private int manga6;
	private int total;
	public Resultados(int manga1, int manga2, int manga3, int manga4, int manga5, int manga6, int total) {
		super();
		this.manga1 = manga1;
		this.manga2 = manga2;
		this.manga3 = manga3;
		this.manga4 = manga4;
		this.manga5 = manga5;
		this.manga6 = manga6;
		this.total = total;
	}
	public int getManga1() {
		return manga1;
	}
	public void setManga1(int manga1) {
		this.manga1 = manga1;
	}
	public int getManga2() {
		return manga2;
	}
	public void setManga2(int manga2) {
		this.manga2 = manga2;
	}
	public int getManga3() {
		return manga3;
	}
	public void setManga3(int manga3) {
		this.manga3 = manga3;
	}
	public int getManga4() {
		return manga4;
	}
	public void setManga4(int manga4) {
		this.manga4 = manga4;
	}
	public int getManga5() {
		return manga5;
	}
	public void setManga5(int manga5) {
		this.manga5 = manga5;
	}
	public int getManga6() {
		return manga6;
	}
	public void setManga6(int manga6) {
		this.manga6 = manga6;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Resultados [manga1=" + manga1 + ", manga2=" + manga2 + ", manga3=" + manga3 + ", manga4=" + manga4
				+ ", manga5=" + manga5 + ", manga6=" + manga6 + ", total=" + total + "]";
	}
	
	
}
