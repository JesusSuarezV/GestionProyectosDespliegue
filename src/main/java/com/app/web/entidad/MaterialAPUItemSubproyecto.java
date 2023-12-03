package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "material_apu_item_subproyecto")
public class MaterialAPUItemSubproyecto {

	@Id
	@Column(name = "id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "apu_item_subproyecto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_apu_item_subproyecto"))
	private APUItemSubproyecto apuItemSubproyecto;
	@ManyToOne
	@JoinColumn(name = "material_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_material"))
	private Material material;
	@Column(name = "proveedor")
	private int proveedor;
	@Column(name = "valor_unitario")
	private double valorUnitario;
	@Column(name = "cantidad")
	private double cantidad;
	@Column(name = "visibilidad")
	boolean visibilidad;

	public double getValorParcial() {
		
		double valorParcial = valorUnitario * cantidad;
		valorParcial = Math.round(valorParcial * 100d)/100d;
		return valorParcial;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public APUItemSubproyecto getApuItemSubproyecto() {
		return apuItemSubproyecto;
	}

	public void setApuItemSubproyecto(APUItemSubproyecto apuItemSubproyecto) {
		this.apuItemSubproyecto = apuItemSubproyecto;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getProveedor() {
		return proveedor;
	}

	public void setProveedor(int proveedor) {
		this.proveedor = proveedor;
	}

	public double getValorUnitario() {
		valorUnitario = Math.round(valorUnitario * 100d)/100d;
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public double getCantidad() {
		cantidad = Math.round(cantidad * 100d)/100d;
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}

	public MaterialAPUItemSubproyecto(int id, APUItemSubproyecto apuItemSubproyecto, Material material, int proveedor,
			double valorUnitario, double cantidad, boolean visibilidad) {
		super();
		this.id = id;
		this.apuItemSubproyecto = apuItemSubproyecto;
		this.material = material;
		this.proveedor = proveedor;
		this.valorUnitario = valorUnitario;
		this.cantidad = cantidad;
		this.visibilidad = visibilidad;
	}

	public MaterialAPUItemSubproyecto() {
		super();
	}

}
