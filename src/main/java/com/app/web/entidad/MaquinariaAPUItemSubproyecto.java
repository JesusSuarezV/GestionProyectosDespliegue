package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "maquinaria_apu_item_subproyecto")
public class MaquinariaAPUItemSubproyecto {

	@Id
	@Column(name = "id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "apu_item_subproyecto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_apu_item_subproyecto"))
	private APUItemSubproyecto apuItemSubproyecto;
	@ManyToOne
	@JoinColumn(name = "maquinaria_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_maquinaria"))
	private Maquinaria maquinaria;
	@Column(name = "tarifa")
	private double tarifa;
	@Column(name = "rdto")
	private double rdto;
	@Column(name = "visibilidad")
	boolean visibilidad;

	public double getValorParcial() {

		double valorParcial = tarifa / rdto;
		valorParcial = Math.round(valorParcial * 100d) / 100d;
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

	public Maquinaria getMaquinaria() {
		return maquinaria;
	}

	public void setMaquinaria(Maquinaria maquinaria) {
		this.maquinaria = maquinaria;
	}

	public double getTarifa() {

		tarifa = Math.round(tarifa * 100d) / 100d;
		return tarifa;
	}

	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}

	public double getRdto() {

		rdto = Math.round(rdto * 100d) / 100d;

		return rdto;
	}

	public void setRdto(double rdto) {
		this.rdto = rdto;
	}

	public boolean isVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}

	public MaquinariaAPUItemSubproyecto(int id, APUItemSubproyecto apuItemSubproyecto, Maquinaria maquinaria,
			double tarifa, double rdto, boolean visibilidad) {
		super();
		this.id = id;
		this.apuItemSubproyecto = apuItemSubproyecto;
		this.maquinaria = maquinaria;
		this.tarifa = tarifa;
		this.rdto = rdto;
		this.visibilidad = visibilidad;
	}

	public MaquinariaAPUItemSubproyecto() {
		super();
	}

}