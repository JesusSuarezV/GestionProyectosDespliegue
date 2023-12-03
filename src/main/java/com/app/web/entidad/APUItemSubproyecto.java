package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "apu_item_subproyecto")
public class APUItemSubproyecto {

	@Id
	@Column(name = "id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "item_subproyecto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_item_subproyecto"))
	private ItemSubproyecto itemSubproyecto;
	@ManyToOne
	@JoinColumn(name = "apu_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_apu"))
	private APU apu;
	@Column(name = "cantidad")
	private double cantidad;
	@Column(name = "visibilidad")
	boolean visibilidad;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ItemSubproyecto getItemSubproyecto() {
		return itemSubproyecto;
	}
	public void setItemSubproyecto(ItemSubproyecto itemSubproyecto) {
		this.itemSubproyecto = itemSubproyecto;
	}
	public APU getApu() {
		return apu;
	}
	public void setApu(APU apu) {
		this.apu = apu;
	}
	public double getCantidad() {
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
	public APUItemSubproyecto(int id, ItemSubproyecto itemSubproyecto, APU apu, double cantidad, boolean visibilidad) {
		super();
		this.id = id;
		this.itemSubproyecto = itemSubproyecto;
		this.apu = apu;
		this.cantidad = cantidad;
		this.visibilidad = visibilidad;
	}
	public APUItemSubproyecto() {
		super();
	}
	
	
	
}