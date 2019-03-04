package br.com.sgnt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_TIPO_NUMERO")
public class TipoNumero {
	
	@Id
	@GeneratedValue
	@Column(name = "ID_TIPO")
	private Integer idTipoNumero;
	
	@Column(name = "NOME_TIPO")
	private String Nometipo;

	public Integer getIdTipoNumero() {
		return idTipoNumero;
	}

	public void setIdTipoNumero(Integer idTipoNumero) {
		this.idTipoNumero = idTipoNumero;
	}

	public String getNometipo() {
		return Nometipo;
	}

	public void setNometipo(String nometipo) {
		Nometipo = nometipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Nometipo == null) ? 0 : Nometipo.hashCode());
		result = prime * result + ((idTipoNumero == null) ? 0 : idTipoNumero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoNumero other = (TipoNumero) obj;
		if (Nometipo == null) {
			if (other.Nometipo != null)
				return false;
		} else if (!Nometipo.equals(other.Nometipo))
			return false;
		if (idTipoNumero == null) {
			if (other.idTipoNumero != null)
				return false;
		} else if (!idTipoNumero.equals(other.idTipoNumero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoNumero [idTipoNumero=" + idTipoNumero + ", Nometipo=" + Nometipo + "]";
	}
	
	
	
}
