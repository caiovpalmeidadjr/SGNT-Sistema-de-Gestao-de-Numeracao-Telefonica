package br.com.sgnt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_MUNICIPIO")
public class Municipio {

	@Id
	@GeneratedValue
	@Column(name = "ID_MUNI")
	private Integer idMunicipio;

	@Column(name = "SIGL_MUNI")
	private String siglaMunicipio;

	@Column(name = "NOME_MUNI")
	private String nomeMunicipio;

	// Lazy Loading, o Eager Loading carrega os dados mesmo que você não vá
	// utilizá-los, mas é óbvio que você só
	// utilizará esta técnica se de fato você for precisar com muita frequência dos
	// dados carregados.
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "ID_ARLC", insertable = true, updatable = true, referencedColumnName = "ID_ARLC")
	@ManyToOne
	@JoinColumn(name="ID_ARLC")
	private AreaLocal areaLocal;
	
	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getSiglaMunicipio() {
		return siglaMunicipio;
	}

	public void setSiglaMunicipio(String siglaMunicipio) {
		this.siglaMunicipio = siglaMunicipio;
	}

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}

	public AreaLocal getAreaLocal() {
		return areaLocal;
	}

	public void setAreaLocal(AreaLocal areaLocal) {
		this.areaLocal = areaLocal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaLocal == null) ? 0 : areaLocal.hashCode());
		result = prime * result + ((idMunicipio == null) ? 0 : idMunicipio.hashCode());
		result = prime * result + ((nomeMunicipio == null) ? 0 : nomeMunicipio.hashCode());
		result = prime * result + ((siglaMunicipio == null) ? 0 : siglaMunicipio.hashCode());
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
		Municipio other = (Municipio) obj;
		if (areaLocal == null) {
			if (other.areaLocal != null)
				return false;
		} else if (!areaLocal.equals(other.areaLocal))
			return false;
		if (idMunicipio == null) {
			if (other.idMunicipio != null)
				return false;
		} else if (!idMunicipio.equals(other.idMunicipio))
			return false;
		if (nomeMunicipio == null) {
			if (other.nomeMunicipio != null)
				return false;
		} else if (!nomeMunicipio.equals(other.nomeMunicipio))
			return false;
		if (siglaMunicipio == null) {
			if (other.siglaMunicipio != null)
				return false;
		} else if (!siglaMunicipio.equals(other.siglaMunicipio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Municipio [idMunicipio=" + idMunicipio + ", siglaMunicipio=" + siglaMunicipio + ", nomeMunicipio="
				+ nomeMunicipio + ", areaLocal=" + areaLocal + "]";
	}

}
