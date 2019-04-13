package br.com.sgnt.model;

import java.sql.Timestamp;

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

	@ManyToOne
	@JoinColumn(name = "ID_STAT")
	private Status status;

	@Column(name = "DATA_HORA_STATUS")
	private Timestamp dataHoraStatus;

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Timestamp getDataHoraStatus() {
		return dataHoraStatus;
	}

	public void setDataHoraStatus(Timestamp dataHoraStatus) {
		this.dataHoraStatus = dataHoraStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataHoraStatus == null) ? 0 : dataHoraStatus.hashCode());
		result = prime * result + ((idNumeroCNG == null) ? 0 : idNumeroCNG.hashCode());
		result = prime * result + ((mcduNumeroCNG == null) ? 0 : mcduNumeroCNG.hashCode());
		result = prime * result + ((prefixoNumeroCNG == null) ? 0 : prefixoNumeroCNG.hashCode());
		result = prime * result + ((reserva == null) ? 0 : reserva.hashCode());
		result = prime * result + ((serieNumeroCNG == null) ? 0 : serieNumeroCNG.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (dataHoraStatus == null) {
			if (other.dataHoraStatus != null)
				return false;
		} else if (!dataHoraStatus.equals(other.dataHoraStatus))
			return false;
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
		if (prefixoNumeroCNG == null) {
			if (other.prefixoNumeroCNG != null)
				return false;
		} else if (!prefixoNumeroCNG.equals(other.prefixoNumeroCNG))
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
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NumeroCNG [idNumeroCNG=" + idNumeroCNG + ", prefixoNumeroCNG=" + prefixoNumeroCNG + ", serieNumeroCNG="
				+ serieNumeroCNG + ", mcduNumeroCNG=" + mcduNumeroCNG + ", reserva=" + reserva + ", status=" + status
				+ ", dataHoraStatus=" + dataHoraStatus + "]";
	}

}
