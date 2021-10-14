package model;

public class DetalleVentaDTO {

	private int codigo_producto;
	private double precio_venta;
	private int iva;
	private int cantidad;
	private double total_venta;
	
	public DetalleVentaDTO(int codigo_producto, double precio_venta, int iva, int cantidad,
			double total_venta) {
		this.codigo_producto = codigo_producto;
		this.precio_venta = precio_venta;
		this.iva = iva;
		this.cantidad = cantidad;
		this.total_venta = total_venta;
	}

	public int getCodigo_producto() {
		return codigo_producto;
	}

	public void setCodigo_producto(int codigo_producto) {
		this.codigo_producto = codigo_producto;
	}

	public double getPrecio_venta() {
		return precio_venta;
	}

	public void setPrecio_venta(double precio_venta) {
		this.precio_venta = precio_venta;
	}

	public int getIva() {
		return iva;
	}

	public void setIva(int iva) {
		this.iva = iva;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getTotal_venta() {
		return total_venta;
	}

	public void setTotal_venta(double total_venta) {
		this.total_venta = total_venta;
	}
		
}
