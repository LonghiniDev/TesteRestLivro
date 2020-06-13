package unicesumar.testeRestLivro;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicesumar.segundoBimestreException.RegistryNotFoundException;

@Service
@Transactional
public class LivroService {
	@Autowired
	private LivroRepository repo;

	public String save(Livro livro) {
		return repo.save(livro).getId();
	}

	public Livro findById(String id) {
		return repo.findById(id).orElseThrow(RegistryNotFoundException::new);
	}

	public List<Livro> findAll() {
		return repo.findAll();
	}

	public void deleteById(String id) {
		repo.deleteById(id);
	}
}
