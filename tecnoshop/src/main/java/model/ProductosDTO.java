package model;

public class ProductosDTO {
	
	private long codigo_producto;
	private String nombre_proveedor;
	private int ivacompra;
	private String nombre_producto;
	private double precio_compra;
	private int porcentaje_utilidad;
	private double precio_venta;
	
	public ProductosDTO() {
	}

	public ProductosDTO(long codigo_producto, int ivacompra, String nombre_proveedor, String nombre_producto,
			double precio_compra, int porcentaje_utilidad, double precio_venta) {
		this.codigo_producto = codigo_producto;
		this.ivacompra = ivacompra;
		this.nombre_proveedor = nombre_proveedor;
		this.nombre_producto = nombre_producto;
		this.precio_compra = precio_compra;
		this.porcentaje_utilidad = porcentaje_utilidad;
		this.precio_venta = precio_venta;
	}

	public long getCodigo_producto() {
		return codigo_producto;
	}

	public void setCodigo_producto(long codigo_producto) {
		this.codigo_producto = codigo_producto;
	}

	public String getNombre_proveedor() {
		return nombre_proveedor;
	}

	public void setNombre_proveedor(String nombre_proveedor) {
		this.nombre_proveedor = nombre_proveedor;
	}

	public int getIvacompra() {
		return ivacompra;
	}

	public void setIvacompra(int ivacompra) {
		this.ivacompra = ivacompra;
	}

	public String getNombre_producto() {
		return nombre_producto;
	}

	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

	public double getPrecio_compra() {
		return precio_compra;
	}

	public void setPrecio_compra(double precio_compra) {
		this.precio_compra = precio_compra;
	}

	public int getPorcentaje_utilidad() {
		return porcentaje_utilidad;
	}

	public void setPorcentaje_utilidad(int porcentaje_utilidad) {
		this.porcentaje_utilidad = porcentaje_utilidad;
	}

	public double getPrecio_venta() {
		return precio_venta;
	}

	public void setPrecio_venta(double precio_venta) {
		this.precio_venta = precio_venta;
	}	
	
}
