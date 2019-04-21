package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.AreaLocal;
import br.com.sgnt.repository.AreaLocalRepository;

@Service
public class AreaLocalServiceImpl implements IAreaLocalService{
	
	@Autowired
	private AreaLocalRepository areaLocalRepository;
	
	@Override
	public void salvar(AreaLocal vo) {
		areaLocalRepository.save(vo);
	}

	@Override
	public void atualizar(AreaLocal vo) {
		areaLocalRepository.save(vo);
	}

	@Override
	public void excluir(AreaLocal vo) {
		areaLocalRepository.delete(vo);
	}

	@Override
	public List<AreaLocal> listAreas(String query) {
		return areaLocalRepository.listAreaLocal();
	}

	@Override
	public List<AreaLocal> listAreas() {
		return areaLocalRepository.listAreaLocal();
	}

	@Override
	public List<AreaLocal> listAreaLocalCN(Integer cn) {
		return areaLocalRepository.listAreaLocalCN(cn);
	}

	@Override
	public List<AreaLocal> listDistinctCN() {
		return areaLocalRepository.listDistinctCN();
	}

	@Override
	public AreaLocal areaLocalNome(String nome) {
		return areaLocalRepository.areaLocalNome(nome);
	}
	
	
}
