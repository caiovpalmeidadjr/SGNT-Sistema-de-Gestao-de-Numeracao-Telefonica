package br.com.sgnt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_FUNCIONARIO")
public class Funcionario {

	// referenciando que matricula é um id na tabela funcionario e que o id da mesma
	// será gerado automaticamente
	@Id
	@GeneratedValue
	@Column(name = "ID_FUNC")
	private Integer idFuncionario;

	@Column(name = "MATRICULA")
	private Integer matriculaFunc;

	@Column(name = "NOME")
	private String nomeFunc;

	@Column(name = "TELEFONE")
	private String telefoneFunc;

	@Column(name = "EMAIL")
	private String emailFunc;

	@Column(name = "CARGO")
	private String Cargo;

	@Column(name = "DTADMISSAO")
	private Date dtAdmissao;

	@Column(name = "DTDEMISSAO")
	private Date dtDemissao;

	@Column(name = "DTNASCIMENTO")
	private Date dtNascimento;

	@Column(name = "RG")
	private Integer rg;

	@Column(name = "CPF")
	private Integer cpf;

	@Column(name = "ATIVO")
	private boolean ativo;
	
	@OneToOne
	private Usuario usuario;

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

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getCargo() {
		return Cargo;
	}

	public void setCargo(String cargo) {
		Cargo = cargo;
	}

	public Date getDtAdmissao() {
		return dtAdmissao;
	}

	public void setDtAdmissao(Date dtAdmissao) {
		this.dtAdmissao = dtAdmissao;
	}

	public Date getDtDemissao() {
		return dtDemissao;
	}

	public void setDtDemissao(Date dtDemissao) {
		this.dtDemissao = dtDemissao;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Integer getRg() {
		return rg;
	}

	public void setRg(Integer rg) {
		this.rg = rg;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Cargo == null) ? 0 : Cargo.hashCode());
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dtAdmissao == null) ? 0 : dtAdmissao.hashCode());
		result = prime * result + ((dtDemissao == null) ? 0 : dtDemissao.hashCode());
		result = prime * result + ((dtNascimento == null) ? 0 : dtNascimento.hashCode());
		result = prime * result + ((emailFunc == null) ? 0 : emailFunc.hashCode());
		result = prime * result + ((idFuncionario == null) ? 0 : idFuncionario.hashCode());
		result = prime * result + ((matriculaFunc == null) ? 0 : matriculaFunc.hashCode());
		result = prime * result + ((nomeFunc == null) ? 0 : nomeFunc.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
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
		if (Cargo == null) {
			if (other.Cargo != null)
				return false;
		} else if (!Cargo.equals(other.Cargo))
			return false;
		if (ativo != other.ativo)
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dtAdmissao == null) {
			if (other.dtAdmissao != null)
				return false;
		} else if (!dtAdmissao.equals(other.dtAdmissao))
			return false;
		if (dtDemissao == null) {
			if (other.dtDemissao != null)
				return false;
		} else if (!dtDemissao.equals(other.dtDemissao))
			return false;
		if (dtNascimento == null) {
			if (other.dtNascimento != null)
				return false;
		} else if (!dtNascimento.equals(other.dtNascimento))
			return false;
		if (emailFunc == null) {
			if (other.emailFunc != null)
				return false;
		} else if (!emailFunc.equals(other.emailFunc))
			return false;
		if (idFuncionario == null) {
			if (other.idFuncionario != null)
				return false;
		} else if (!idFuncionario.equals(other.idFuncionario))
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
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
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
		return "Funcionario [idFuncionario=" + idFuncionario + ", matriculaFunc=" + matriculaFunc + ", nomeFunc="
				+ nomeFunc + ", telefoneFunc=" + telefoneFunc + ", emailFunc=" + emailFunc + ", Cargo=" + Cargo
				+ ", dtAdmissao=" + dtAdmissao + ", dtDemissao=" + dtDemissao + ", dtNascimento=" + dtNascimento
				+ ", rg=" + rg + ", cpf=" + cpf + ", ativo=" + ativo + "]";
	}

}
