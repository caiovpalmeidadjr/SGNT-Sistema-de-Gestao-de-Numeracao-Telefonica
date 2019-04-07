package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.Perfil;
import br.com.sgnt.repository.PerfilRepository;

@Service
public class PerfilServiceImpl implements IPerfilService {
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Override
	public void salvar(Perfil vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizar(Perfil vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Perfil vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Perfil> listPerfis(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Perfil> listPerfis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Perfil findOne(Integer id) {
		return perfilRepository.findOne(id);
	}
	
}
