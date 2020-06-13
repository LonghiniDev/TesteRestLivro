package unicesumar.testeRestLivro;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Livro")
public class LivroController {
	@Autowired
	private LivroService service;

	@GetMapping
	public List<Livro> getAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Livro getById(@PathVariable("id") String id) {
		return service.findById(id);
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping
	public String post(@RequestBody Livro livro) {
		return service.save(livro);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping("/{id}")
	public ResponseEntity<Void> put(@PathVariable("id") String id, @RequestBody Livro livro) {
		if (!id.equals(livro.getId())) {
			return ResponseEntity.badRequest().build();
		}

		service.save(livro);
		return ResponseEntity.ok().build();
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") String id) {
		service.deleteById(id);
	}
}
