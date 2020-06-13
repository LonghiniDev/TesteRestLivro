package unicesumar.TesteRestLivroException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManagerException {
	@ExceptionHandler({ RegistryNotFoundException.class })
	public ResponseEntity<Void> dealWith(RegistryNotFoundException rnfe) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Void> dealWith(EmptyResultDataAccessException erdae) {
		return ResponseEntity.notFound().build();
	}
}
