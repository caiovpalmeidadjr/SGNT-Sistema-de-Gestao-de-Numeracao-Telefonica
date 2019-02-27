package br.com.sgnt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AREA_LOCAL")
public class AreaLocal {

	@Id
	@GeneratedValue
	@Column(name = "ID_ARLO")
	private Integer idAreaLocal;

	@Column(name = "SIGLA")
	private String siglaAreaLocal;

	@Column(name = "NOME")
	private String nomeAreaLocal;

	@Column(name = "UF")
	private String ufAreaLocal;

	@Column(name = "CN")
	private Integer cnAreaLocal;

	@Column(name = "NUMATENDIMENTO")
	private Integer numAtendimentoAreaLocal;

	public Integer getIdAreaLocal() {
		return idAreaLocal;
	}

	public void setIdAreaLocal(Integer idAreaLocal) {
		this.idAreaLocal = idAreaLocal;
	}

	public String getSiglaAreaLocal() {
		return siglaAreaLocal;
	}

	public void setSiglaAreaLocal(String siglaAreaLocal) {
		this.siglaAreaLocal = siglaAreaLocal;
	}

	public String getNomeAreaLocal() {
		return nomeAreaLocal;
	}

	public void setNomeAreaLocal(String nomeAreaLocal) {
		this.nomeAreaLocal = nomeAreaLocal;
	}

	public String getUfAreaLocal() {
		return ufAreaLocal;
	}

	public void setUfAreaLocal(String ufAreaLocal) {
		this.ufAreaLocal = ufAreaLocal;
	}

	public Integer getCnAreaLocal() {
		return cnAreaLocal;
	}

	public void setCnAreaLocal(Integer cnAreaLocal) {
		this.cnAreaLocal = cnAreaLocal;
	}

	public Integer getNumAtendimentoAreaLocal() {
		return numAtendimentoAreaLocal;
	}

	public void setNumAtendimentoAreaLocal(Integer numAtendimentoAreaLocal) {
		this.numAtendimentoAreaLocal = numAtendimentoAreaLocal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnAreaLocal == null) ? 0 : cnAreaLocal.hashCode());
		result = prime * result + ((idAreaLocal == null) ? 0 : idAreaLocal.hashCode());
		result = prime * result + ((nomeAreaLocal == null) ? 0 : nomeAreaLocal.hashCode());
		result = prime * result + ((numAtendimentoAreaLocal == null) ? 0 : numAtendimentoAreaLocal.hashCode());
		result = prime * result + ((siglaAreaLocal == null) ? 0 : siglaAreaLocal.hashCode());
		result = prime * result + ((ufAreaLocal == null) ? 0 : ufAreaLocal.hashCode());
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
		AreaLocal other = (AreaLocal) obj;
		if (cnAreaLocal == null) {
			if (other.cnAreaLocal != null)
				return false;
		} else if (!cnAreaLocal.equals(other.cnAreaLocal))
			return false;
		if (idAreaLocal == null) {
			if (other.idAreaLocal != null)
				return false;
		} else if (!idAreaLocal.equals(other.idAreaLocal))
			return false;
		if (nomeAreaLocal == null) {
			if (other.nomeAreaLocal != null)
				return false;
		} else if (!nomeAreaLocal.equals(other.nomeAreaLocal))
			return false;
		if (numAtendimentoAreaLocal == null) {
			if (other.numAtendimentoAreaLocal != null)
				return false;
		} else if (!numAtendimentoAreaLocal.equals(other.numAtendimentoAreaLocal))
			return false;
		if (siglaAreaLocal == null) {
			if (other.siglaAreaLocal != null)
				return false;
		} else if (!siglaAreaLocal.equals(other.siglaAreaLocal))
			return false;
		if (ufAreaLocal == null) {
			if (other.ufAreaLocal != null)
				return false;
		} else if (!ufAreaLocal.equals(other.ufAreaLocal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AreaLocal [idAreaLocal=" + idAreaLocal + ", siglaAreaLocal=" + siglaAreaLocal + ", nomeAreaLocal="
				+ nomeAreaLocal + ", ufAreaLocal=" + ufAreaLocal + ", cnAreaLocal=" + cnAreaLocal
				+ ", numAtendimentoAreaLocal=" + numAtendimentoAreaLocal + "]";
	}

}
