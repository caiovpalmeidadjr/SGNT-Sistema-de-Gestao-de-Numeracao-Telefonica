package br.com.sgnt.model;

import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_RESERVA")
public class Reserva {

	@Id
	@GeneratedValue
	@Column(name = "ID_RESE")
	private Integer idReserva;

	@Column(name = "DATAHORA_RESERVA")
	private Timestamp dataHoraReserva;

	@Column(name = "DATA_PREVISTA")
	private Date dataPrevista;

	@ManyToOne
	@JoinColumn(name = "ID_FUNC")
	private Funcionario funcionario;

	@ManyToOne
	@JoinColumn(name = "ID_CLIE")
	private Cliente cliente;

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public Timestamp getDataHoraReserva() {
		return dataHoraReserva;
	}

	public void setDataHoraReserva(Timestamp dataHoraReserva) {
		this.dataHoraReserva = dataHoraReserva;
	}

	public Date getDataPrevista() {
		return dataPrevista;
	}

	public void setDataPrevista(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dataHoraReserva == null) ? 0 : dataHoraReserva.hashCode());
		result = prime * result + ((dataPrevista == null) ? 0 : dataPrevista.hashCode());
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((idReserva == null) ? 0 : idReserva.hashCode());
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
		Reserva other = (Reserva) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dataHoraReserva == null) {
			if (other.dataHoraReserva != null)
				return false;
		} else if (!dataHoraReserva.equals(other.dataHoraReserva))
			return false;
		if (dataPrevista == null) {
			if (other.dataPrevista != null)
				return false;
		} else if (!dataPrevista.equals(other.dataPrevista))
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (idReserva == null) {
			if (other.idReserva != null)
				return false;
		} else if (!idReserva.equals(other.idReserva))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", dataHoraReserva=" + dataHoraReserva + ", dataPrevista="
				+ dataPrevista + ", funcionario=" + funcionario + ", cliente=" + cliente + "]";
	}
	
	
}
