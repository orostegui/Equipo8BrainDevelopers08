package model;

public class VentasDTO {
	
	private long codigo_venta;
	private int cedula_cliente;
	private int cedula_usuario;
	private double ivaventa;
	private double total_venta;
	private double valor_venta;
	private String nombre_usuario;
	private String nombre_cliente;
	
	public VentasDTO() {
	}

	public VentasDTO(long codigo_venta, double total_venta,  String nombre_usuario, String nombre_cliente) {
		this.codigo_venta = codigo_venta;
		this.total_venta = total_venta;
		this.nombre_usuario = nombre_usuario;
		this.nombre_cliente = nombre_cliente;
	}

	public long getCodigo_venta() {
		return codigo_venta;
	}

	public void setCodigo_venta(long codigo_venta) {
		this.codigo_venta = codigo_venta;
	}

	public int getCedula_cliente() {
		return cedula_cliente;
	}

	public void setCedula_cliente(int cedula_cliente) {
		this.cedula_cliente = cedula_cliente;
	}

	public int getCedula_usuario() {
		return cedula_usuario;
	}

	public void setCedula_usuario(int cedula_usuario) {
		this.cedula_usuario = cedula_usuario;
	}

	public double getIvaventa() {
		return ivaventa;
	}

	public void setIvaventa(double ivaventa) {
		this.ivaventa = ivaventa;
	}

	public double getTotal_venta() {
		return total_venta;
	}

	public void setTotal_venta(double total_venta) {
		this.total_venta = total_venta;
	}

	public double getValor_venta() {
		return valor_venta;
	}

	public void setValor_venta(double valor_venta) {
		this.valor_venta = valor_venta;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}
	
}
