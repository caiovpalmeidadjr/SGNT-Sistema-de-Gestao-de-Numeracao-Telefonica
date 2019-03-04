package br.com.sgnt.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_STATUS")
public class Status {
	
	@Id
	@GeneratedValue
	@Column(name = "ID_STAT")
	private Integer idStatus;
	
	@Column(name = "NOME")
	private String nomeStatus;
	
	@Column(name = "DATA_HORA")
	private Timestamp dataHoraStatus;
	
	public Integer getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
	}

	public String getNomeStatus() {
		return nomeStatus;
	}

	public void setNomeStatus(String nomeStatus) {
		this.nomeStatus = nomeStatus;
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
		result = prime * result + ((idStatus == null) ? 0 : idStatus.hashCode());
		result = prime * result + ((nomeStatus == null) ? 0 : nomeStatus.hashCode());
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
		Status other = (Status) obj;
		if (dataHoraStatus == null) {
			if (other.dataHoraStatus != null)
				return false;
		} else if (!dataHoraStatus.equals(other.dataHoraStatus))
			return false;
		if (idStatus == null) {
			if (other.idStatus != null)
				return false;
		} else if (!idStatus.equals(other.idStatus))
			return false;
		if (nomeStatus == null) {
			if (other.nomeStatus != null)
				return false;
		} else if (!nomeStatus.equals(other.nomeStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Status [idStatus=" + idStatus + ", nomeStatus=" + nomeStatus + ", dataHoraStatus=" + dataHoraStatus
				+ "]";
	}

}
