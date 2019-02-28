package br.com.sgnt.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PERFIL")
public class Perfil {

	@Id
	@GeneratedValue
	@Column(name = "ID_PERF")
	private Integer idPerfil;

	@Column(name = "NOME")
	private String nome;

	@OneToMany(targetEntity=Permissao.class)
	private List<Permissao> listPermissao;

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Permissao> getListPermissao() {
		return listPermissao;
	}

	public void setListPermissao(List<Permissao> listPermissao) {
		this.listPermissao = listPermissao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPerfil == null) ? 0 : idPerfil.hashCode());
		result = prime * result + ((listPermissao == null) ? 0 : listPermissao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Perfil other = (Perfil) obj;
		if (idPerfil == null) {
			if (other.idPerfil != null)
				return false;
		} else if (!idPerfil.equals(other.idPerfil))
			return false;
		if (listPermissao == null) {
			if (other.listPermissao != null)
				return false;
		} else if (!listPermissao.equals(other.listPermissao))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Perfil [idPerfil=" + idPerfil + ", nome=" + nome + ", listPermissao="
				+ listPermissao + "]";
	}

}
