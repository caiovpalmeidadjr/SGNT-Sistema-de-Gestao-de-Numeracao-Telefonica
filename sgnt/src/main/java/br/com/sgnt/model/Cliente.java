package br.com.sgnt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente {

	@Id
	@GeneratedValue
	@Column(name = "ID_CLIE")
	private Integer idCliente;

	@Column(name = "NOME")
	private String nomeClie;

	@Column(name = "LOGRADOURO")
	private String logradouroClie;

	@Column(name = "MUNICIPIO")
	private String municipioClie;

	@Column(name = "CEP")
	private String cepClie;

	@Column(name = "EMAIL")
	private String email;

	public Cliente() {
		// TODO Auto-generated constructor stub
	}
	
	public Cliente(String nome, String email) {
		this.nomeClie = nome;
		this.email = email;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomeClie() {
		return nomeClie;
	}

	public void setNomeClie(String nomeClie) {
		this.nomeClie = nomeClie;
	}

	public String getLogradouroClie() {
		return logradouroClie;
	}

	public void setLogradouroClie(String logradouroClie) {
		this.logradouroClie = logradouroClie;
	}

	public String getMunicipioClie() {
		return municipioClie;
	}

	public void setMunicipioClie(String municipioClie) {
		this.municipioClie = municipioClie;
	}

	public String getCepClie() {
		return cepClie;
	}

	public void setCepClie(String cepClie) {
		this.cepClie = cepClie;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cepClie == null) ? 0 : cepClie.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((logradouroClie == null) ? 0 : logradouroClie.hashCode());
		result = prime * result + ((municipioClie == null) ? 0 : municipioClie.hashCode());
		result = prime * result + ((nomeClie == null) ? 0 : nomeClie.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cepClie == null) {
			if (other.cepClie != null)
				return false;
		} else if (!cepClie.equals(other.cepClie))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		if (logradouroClie == null) {
			if (other.logradouroClie != null)
				return false;
		} else if (!logradouroClie.equals(other.logradouroClie))
			return false;
		if (municipioClie == null) {
			if (other.municipioClie != null)
				return false;
		} else if (!municipioClie.equals(other.municipioClie))
			return false;
		if (nomeClie == null) {
			if (other.nomeClie != null)
				return false;
		} else if (!nomeClie.equals(other.nomeClie))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nomeClie=" + nomeClie + ", logradouroClie=" + logradouroClie
				+ ", municipioClie=" + municipioClie + ", cepClie=" + cepClie + ", email=" + email + "]";
	}

}
