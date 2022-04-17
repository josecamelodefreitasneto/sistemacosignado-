package br.impl.outros;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import gm.utils.abstrato.IdObject;
import gm.utils.number.UInteger;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass @Getter @Setter
public abstract class EntityModelo implements IdObject {
	
	@Override
	public abstract Integer getId();
	
	@Override
	public abstract void setId(Integer id);

	public abstract Boolean getExcluido();
	public abstract void setExcluido(Boolean value);

	public abstract Boolean getRegistroBloqueado();
	public abstract void setRegistroBloqueado(Boolean value);

//	@NotNull private Boolean excluido;
//	@Column(name="registrobloqueado", nullable=false) private Boolean registroBloqueado;
	@Transient private transient EntityModelo old;
	@Transient private transient boolean ignorarUniquesAoPersistir;

	public boolean eq(Integer id) {
		return UInteger.equals(getId(), id);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + ":" + getId();
	}

}