package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transporte")
public class Transporte {

	@Id
	@Column(name = "id")
	private int id;
	@OneToOne
	@JoinColumn(name = "material_apu_item_subproyecto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_material_apu_item_subproyecto"))
	private MaterialAPUItemSubproyecto materialAPUItemSubproyecto;
	@Column(name = "distancia")
	private double distancia;
	@Column(name = "precio")
	private double precio;
	@Column(name = "visibilidad")
	boolean visibilidad;

	public double getCantidadKM() {

		double cantidadKM = distancia * materialAPUItemSubproyecto.getCantidad();
		cantidadKM = Math.round(cantidadKM * 100d) / 100d;
		return cantidadKM;

	}
	
	public double getValorParcial() {
		
		double valorParcial = getCantidadKM() * precio;
		valorParcial = Math.round(valorParcial * 100d) / 100d;
		return valorParcial;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MaterialAPUItemSubproyecto getMaterialAPUItemSubproyecto() {
		return materialAPUItemSubproyecto;
	}

	public void setMaterialAPUItemSubproyecto(MaterialAPUItemSubproyecto materialAPUItemSubproyecto) {
		this.materialAPUItemSubproyecto = materialAPUItemSubproyecto;
	}

	public double getDistancia() {
		distancia = Math.round(distancia * 100d) / 100d;
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public double getPrecio() {
		precio = Math.round(precio * 100d) / 100d;
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}

	public Transporte(int id, MaterialAPUItemSubproyecto materialAPUItemSubproyecto, double distancia, double precio,
			boolean visibilidad) {
		super();
		this.id = id;
		this.materialAPUItemSubproyecto = materialAPUItemSubproyecto;
		this.distancia = distancia;
		this.precio = precio;
		this.visibilidad = visibilidad;
	}

	public Transporte() {
		super();
	}

}