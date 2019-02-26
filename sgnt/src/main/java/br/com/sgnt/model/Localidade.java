package br.com.sgnt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_LOCALIDADE")
public class Localidade {

	@Id
	@GeneratedValue
	@Column(name = "CNL_LOCA")
	private Integer cnlLocalidade;

	@Column(name = "SIGLA")
	private String siglaLocalidade;

	@Column(name = "NOME")
	private String nomeLocalidade;

	// Lazy Loading, o Eager Loading carrega os dados mesmo que você não vá
	// utilizá-los, mas é óbvio que você só
	// utilizará esta técnica se de fato você for precisar com muita frequência dos
	// dados carregados.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_MUNI", insertable = true, updatable = true, referencedColumnName = "ID_MUNI")
	private Municipio municipio;

	public Integer getCnlLocalidade() {
		return cnlLocalidade;
	}

	public void setCnlLocalidade(Integer cnLocalidade) {
		this.cnlLocalidade = cnLocalidade;
	}

	public String getSiglaLocalidade() {
		return siglaLocalidade;
	}

	public void setSiglaLocalidade(String siglaLocalidade) {
		this.siglaLocalidade = siglaLocalidade;
	}

	public String getNomeLocalidade() {
		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade) {
		this.nomeLocalidade = nomeLocalidade;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnlLocalidade == null) ? 0 : cnlLocalidade.hashCode());
		result = prime * result + ((municipio == null) ? 0 : municipio.hashCode());
		result = prime * result + ((nomeLocalidade == null) ? 0 : nomeLocalidade.hashCode());
		result = prime * result + ((siglaLocalidade == null) ? 0 : siglaLocalidade.hashCode());
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
		Localidade other = (Localidade) obj;
		if (cnlLocalidade == null) {
			if (other.cnlLocalidade != null)
				return false;
		} else if (!cnlLocalidade.equals(other.cnlLocalidade))
			return false;
		if (municipio == null) {
			if (other.municipio != null)
				return false;
		} else if (!municipio.equals(other.municipio))
			return false;
		if (nomeLocalidade == null) {
			if (other.nomeLocalidade != null)
				return false;
		} else if (!nomeLocalidade.equals(other.nomeLocalidade))
			return false;
		if (siglaLocalidade == null) {
			if (other.siglaLocalidade != null)
				return false;
		} else if (!siglaLocalidade.equals(other.siglaLocalidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Localidade [cnLocalidade=" + cnlLocalidade + ", siglaLocalidade=" + siglaLocalidade
				+ ", nomeLocalidade=" + nomeLocalidade + ", municipio=" + municipio + "]";
	}

}
