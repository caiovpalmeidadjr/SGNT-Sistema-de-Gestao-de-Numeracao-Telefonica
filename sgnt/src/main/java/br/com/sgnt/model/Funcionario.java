package br.com.sgnt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_FUNCIONARIO")
public class Funcionario {

	// referenciando que matricula é um id na tabela funcionario e que o id da mesma
	// será gerado automaticamente
	@Id
	@GeneratedValue
	@Column(name = "MATR_FUNC")
	private Integer matriculaFunc;

	@Column(name = "NOME_FUNC")
	private String nomeFunc;

	@Column(name = "TELE_FUNC")
	private String telefoneFunc;

	@Column(name = "EMAIL_FUNC")
	private String emailFunc;

	public Integer getMatriculaFunc() {
		return matriculaFunc;
	}

	public void setMatriculaFunc(Integer matriculaFunc) {
		this.matriculaFunc = matriculaFunc;
	}

	public String getNomeFunc() {
		return nomeFunc;
	}

	public void setNomeFunc(String nomeFunc) {
		this.nomeFunc = nomeFunc;
	}

	public String getTelefoneFunc() {
		return telefoneFunc;
	}

	public void setTelefoneFunc(String telefoneFunc) {
		this.telefoneFunc = telefoneFunc;
	}

	public String getEmailFunc() {
		return emailFunc;
	}

	public void setEmailFunc(String emailFunc) {
		this.emailFunc = emailFunc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailFunc == null) ? 0 : emailFunc.hashCode());
		result = prime * result + ((matriculaFunc == null) ? 0 : matriculaFunc.hashCode());
		result = prime * result + ((nomeFunc == null) ? 0 : nomeFunc.hashCode());
		result = prime * result + ((telefoneFunc == null) ? 0 : telefoneFunc.hashCode());
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
		Funcionario other = (Funcionario) obj;
		if (emailFunc == null) {
			if (other.emailFunc != null)
				return false;
		} else if (!emailFunc.equals(other.emailFunc))
			return false;
		if (matriculaFunc == null) {
			if (other.matriculaFunc != null)
				return false;
		} else if (!matriculaFunc.equals(other.matriculaFunc))
			return false;
		if (nomeFunc == null) {
			if (other.nomeFunc != null)
				return false;
		} else if (!nomeFunc.equals(other.nomeFunc))
			return false;
		if (telefoneFunc == null) {
			if (other.telefoneFunc != null)
				return false;
		} else if (!telefoneFunc.equals(other.telefoneFunc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funcionario [matriculaFunc=" + matriculaFunc + ", nomeFunc=" + nomeFunc + ", telefoneFunc="
				+ telefoneFunc + ", emailFunc=" + emailFunc + "]";
	}

}
