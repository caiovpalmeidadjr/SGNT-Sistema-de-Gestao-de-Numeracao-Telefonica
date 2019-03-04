package br.com.sgnt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_NUMERO_STFC")
public class NumeroSTFC {

	@Id
	@GeneratedValue
	@Column(name = "ID_NUST")
	private Integer idNumeroSTFC;

	@Column(name = "PREFIXO")
	private Integer prefixoNumeroSTFC;

	@Column(name = "MCDU")
	private Integer mcduNumeroSTFC;

	@ManyToOne
	@JoinColumn(name = "ID_ARLC")
	private AreaLocal areaLocal;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO")
	private TipoNumero tipoNumero;

	@ManyToOne
	@JoinColumn(name = "ID_RESE")
	private Reserva reserva;

	public Integer getIdNumeroSTFC() {
		return idNumeroSTFC;
	}

	public void setIdNumeroSTFC(Integer idNumeroSTFC) {
		this.idNumeroSTFC = idNumeroSTFC;
	}

	public Integer getPrefixoNumeroSTFC() {
		return prefixoNumeroSTFC;
	}

	public void setPrefixoNumeroSTFC(Integer prefixoNumeroSTFC) {
		this.prefixoNumeroSTFC = prefixoNumeroSTFC;
	}

	public Integer getMcduNumeroSTFC() {
		return mcduNumeroSTFC;
	}

	public void setMcduNumeroSTFC(Integer mcduNumeroSTFC) {
		this.mcduNumeroSTFC = mcduNumeroSTFC;
	}

	public AreaLocal getAreaLocal() {
		return areaLocal;
	}

	public void setAreaLocal(AreaLocal areaLocal) {
		this.areaLocal = areaLocal;
	}

	public TipoNumero getTipoNumero() {
		return tipoNumero;
	}

	public void setTipoNumero(TipoNumero tipoNumero) {
		this.tipoNumero = tipoNumero;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaLocal == null) ? 0 : areaLocal.hashCode());
		result = prime * result + ((idNumeroSTFC == null) ? 0 : idNumeroSTFC.hashCode());
		result = prime * result + ((mcduNumeroSTFC == null) ? 0 : mcduNumeroSTFC.hashCode());
		result = prime * result + ((prefixoNumeroSTFC == null) ? 0 : prefixoNumeroSTFC.hashCode());
		result = prime * result + ((reserva == null) ? 0 : reserva.hashCode());
		result = prime * result + ((tipoNumero == null) ? 0 : tipoNumero.hashCode());
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
		NumeroSTFC other = (NumeroSTFC) obj;
		if (areaLocal == null) {
			if (other.areaLocal != null)
				return false;
		} else if (!areaLocal.equals(other.areaLocal))
			return false;
		if (idNumeroSTFC == null) {
			if (other.idNumeroSTFC != null)
				return false;
		} else if (!idNumeroSTFC.equals(other.idNumeroSTFC))
			return false;
		if (mcduNumeroSTFC == null) {
			if (other.mcduNumeroSTFC != null)
				return false;
		} else if (!mcduNumeroSTFC.equals(other.mcduNumeroSTFC))
			return false;
		if (prefixoNumeroSTFC == null) {
			if (other.prefixoNumeroSTFC != null)
				return false;
		} else if (!prefixoNumeroSTFC.equals(other.prefixoNumeroSTFC))
			return false;
		if (reserva == null) {
			if (other.reserva != null)
				return false;
		} else if (!reserva.equals(other.reserva))
			return false;
		if (tipoNumero == null) {
			if (other.tipoNumero != null)
				return false;
		} else if (!tipoNumero.equals(other.tipoNumero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NumeroSTFC [idNumeroSTFC=" + idNumeroSTFC + ", prefixoNumeroSTFC=" + prefixoNumeroSTFC
				+ ", mcduNumeroSTFC=" + mcduNumeroSTFC + ", areaLocal=" + areaLocal + ", tipoNumero=" + tipoNumero
				+ ", reserva=" + reserva + "]";
	}

}
