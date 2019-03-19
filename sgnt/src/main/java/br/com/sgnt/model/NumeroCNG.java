package br.com.sgnt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_NUMERO_CNG")
public class NumeroCNG {

	@Id
	@GeneratedValue
	@Column(name = "ID_NCNG")
	private Integer idNumeroCNG;

	@Column(name = "PREFIXO")
	private Integer prefixoNumeroCNG;

	@Column(name = "SERIE")
	private Integer serieNumeroCNG;

	@Column(name = "MCDU")
	private Integer mcduNumeroCNG;

	@ManyToOne
	@JoinColumn(name = "ID_RESE")
	private Reserva reserva;

	public Integer getIdNumeroCNG() {
		return idNumeroCNG;
	}

	public void setIdNumeroCNG(Integer idNumeroCNG) {
		this.idNumeroCNG = idNumeroCNG;
	}

	public Integer getSerieNumeroCNG() {
		return serieNumeroCNG;
	}

	public void setSerieNumeroCNG(Integer serieNumeroCNG) {
		this.serieNumeroCNG = serieNumeroCNG;
	}

	public Integer getMcduNumeroCNG() {
		return mcduNumeroCNG;
	}

	public void setMcduNumeroCNG(Integer mcduNumeroCNG) {
		this.mcduNumeroCNG = mcduNumeroCNG;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Integer getPrefixoNumeroCNG() {
		return prefixoNumeroCNG;
	}

	public void setPrefixoNumeroCNG(Integer prefixoNumeroCNG) {
		this.prefixoNumeroCNG = prefixoNumeroCNG;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idNumeroCNG == null) ? 0 : idNumeroCNG.hashCode());
		result = prime * result + ((mcduNumeroCNG == null) ? 0 : mcduNumeroCNG.hashCode());
		result = prime * result + ((reserva == null) ? 0 : reserva.hashCode());
		result = prime * result + ((serieNumeroCNG == null) ? 0 : serieNumeroCNG.hashCode());
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
		NumeroCNG other = (NumeroCNG) obj;
		if (idNumeroCNG == null) {
			if (other.idNumeroCNG != null)
				return false;
		} else if (!idNumeroCNG.equals(other.idNumeroCNG))
			return false;
		if (mcduNumeroCNG == null) {
			if (other.mcduNumeroCNG != null)
				return false;
		} else if (!mcduNumeroCNG.equals(other.mcduNumeroCNG))
			return false;
		if (reserva == null) {
			if (other.reserva != null)
				return false;
		} else if (!reserva.equals(other.reserva))
			return false;
		if (serieNumeroCNG == null) {
			if (other.serieNumeroCNG != null)
				return false;
		} else if (!serieNumeroCNG.equals(other.serieNumeroCNG))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NumeroCNG [idNumeroCNG=" + idNumeroCNG + ", serieNumeroCNG=" + serieNumeroCNG + ", mcduNumeroCNG="
				+ mcduNumeroCNG + ", reserva=" + reserva + "]";
	}

}
