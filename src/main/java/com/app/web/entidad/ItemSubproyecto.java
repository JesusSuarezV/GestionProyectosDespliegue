package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_subproyecto")
public class ItemSubproyecto {

	@Id
	@Column(name = "Id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_item"))
	private Item item;
	@ManyToOne
	@JoinColumn(name = "subproyecto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_subproyecto"))
	private Subproyecto subproyecto;
	@Column(name = "visibilidad")
	boolean visibilidad;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Subproyecto getSubproyecto() {
		return subproyecto;
	}

	public void setSubproyecto(Subproyecto subproyecto) {
		this.subproyecto = subproyecto;
	}

	public boolean isVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}

	public ItemSubproyecto(int id, Item item, Subproyecto subproyecto, boolean visibilidad) {
		super();
		this.id = id;
		this.item = item;
		this.subproyecto = subproyecto;
		this.visibilidad = visibilidad;
	}

	public ItemSubproyecto() {
		super();
	}
	
	
	
	
}
