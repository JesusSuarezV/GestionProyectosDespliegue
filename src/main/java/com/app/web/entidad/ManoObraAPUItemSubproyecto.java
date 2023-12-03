package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mano_obra_apu_item_subproyecto")
public class ManoObraAPUItemSubproyecto {

	@Id
	@Column(name = "id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "apu_item_subproyecto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_apu_item_subproyecto"))
	private APUItemSubproyecto apuItemSubproyecto;
	@ManyToOne
	@JoinColumn(name = "mano_obra_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_mano_obra"))
	private ManoObra manoObra;
	@Column(name = "jornal")
	private double jornal;
	@Column(name = "prestacion")
	private double prestacion;
	@Column(name = "rdto")
	private double rdto;
	@Column(name = "cantidad")
	private int cantidad;
	@Column(name = "visibilidad")
	boolean visibilidad;
	
	public double getJornalTotal() {
		double jornalTotal = jornal + prestacion;
		jornalTotal = Math.round(jornalTotal * 100d)/100d;
		return jornalTotal;
	}

	public double getValorParcial() {
		
		double valorParcial = (cantidad *  getJornalTotal())/rdto;
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

	public ManoObra getManoObra() {
		return manoObra;
	}

	public void setManoObra(ManoObra manoObra) {
		this.manoObra = manoObra;
	}

	public double getJornal() {
		return jornal;
	}

	public void setJornal(double jornal) {
		this.jornal = jornal;
	}

	public double getPrestacion() {
		return prestacion;
	}

	public void setPrestacion(double prestacion) {
		this.prestacion = prestacion;
	}

	public double getRdto() {
		return rdto;
	}

	public void setRdto(double rdto) {
		this.rdto = rdto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}

	public ManoObraAPUItemSubproyecto(int id, APUItemSubproyecto apuItemSubproyecto, ManoObra manoObra, double jornal,
			double prestacion, double rdto, int cantidad, boolean visibilidad) {
		super();
		this.id = id;
		this.apuItemSubproyecto = apuItemSubproyecto;
		this.manoObra = manoObra;
		this.jornal = jornal;
		this.prestacion = prestacion;
		this.rdto = rdto;
		this.cantidad = cantidad;
		this.visibilidad = visibilidad;
	}

	public ManoObraAPUItemSubproyecto() {
		super();
	}
	
}