package model;

import java.util.List;

public class VentasDTO {
	
	private int codigo_venta;
	private long cedula_cliente;
	private String nombre_cliente;
	private long cedula_usuario;
	private String nombre_usuario;
	private int ivaventa;
	private double total_venta;
	private double valor_venta;
	private List<DetalleVentaDTO> detalle_venta;
	
	public VentasDTO(int codigo_venta, String nombre_cliente, String nombre_usuario, int ivaventa, double total_venta,
			double valor_venta) {
		this.codigo_venta = codigo_venta;
		this.nombre_cliente = nombre_cliente;
		this.nombre_usuario = nombre_usuario;
		this.ivaventa = ivaventa;
		this.total_venta = total_venta;
		this.valor_venta = valor_venta;
	}

	public VentasDTO(int codigo_venta, long cedula_cliente, long cedula_usuario, int ivaventa, double total_venta,
			double valor_venta, List<DetalleVentaDTO> detalle_venta) {
		this.codigo_venta = codigo_venta;
		this.cedula_cliente = cedula_cliente;
		this.cedula_usuario = cedula_usuario;
		this.ivaventa = ivaventa;
		this.total_venta = total_venta;
		this.valor_venta = valor_venta;
		this.detalle_venta = detalle_venta;
	}

	public int getCodigo_venta() {
		return codigo_venta;
	}

	public void setCodigo_venta(int codigo_venta) {
		this.codigo_venta = codigo_venta;
	}

	public long getCedula_cliente() {
		return cedula_cliente;
	}

	public void setCedula_cliente(long cedula_cliente) {
		this.cedula_cliente = cedula_cliente;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public long getCedula_usuario() {
		return cedula_usuario;
	}

	public void setCedula_usuario(long cedula_usuario) {
		this.cedula_usuario = cedula_usuario;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public int getIvaventa() {
		return ivaventa;
	}

	public void setIvaventa(int ivaventa) {
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

	public List<DetalleVentaDTO> getDetalle_venta() {
		return detalle_venta;
	}

	public void setDetalle_venta(List<DetalleVentaDTO> detalle_venta) {
		this.detalle_venta = detalle_venta;
	}

}
