package unicesumar.testRestLivro;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import unicesumar.testRestLivro.Livro;
import unicesumar.testRestLivro.LivroService;
import unicesumar.testRestLivro.LivroController;
import unicesumar.testRestLivroException.RegistryNotFoundException;

@WebMvcTest
@AutoConfigureMockMvc
class TestComRestLivro {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private LivroService service;
	@Autowired
	private ObjectMapper objMapper;
	@Autowired
	private LivroController controller;


	@Test
	void testingGetByIdWithExistingData() throws Exception {
		Livro livro = new Livro("1", "Carrie", "Stephen King");

		when(service.findById("1")).thenReturn(livro);

		mockMvc.perform(get("/api/Livro/1")).andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.titulo").value("Carrie"))
				.andExpect(jsonPath("$.autor").value("Stephen King"))
	}

	@Test
	void testingGetByIdWithlivroxistingData() throws Exception {
		when(service.findById("2")).thenThrow(RegistryNotFoundException.class);

		mockMvc.perform(get("/api/Livro/2")).andExpect(status().isNotFound());
	}

	@Test
	void testingGetAll() throws Exception {
		Livro carrie = new Livro("1", "Carrie", "Stephen King");
		Livro cthulhu = new Livro("2", "O Chamado de Cthulhu", "H. P. Lovecraft");

		when(service.findAll()).thenReturn(Arrays.asList(carrie, dell, acer));

		mockMvc.perform(get("/api/Livro")).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$.[0].id").value("1"))
				.andExpect(jsonPath("$.[1].id").value("2"))

				.andExpect(jsonPath("$.[0].titulo").value("Carrie"))
				.andExpect(jsonPath("$.[1].titulo").value("O Chamado de Cthulhu"))

				.andExpect(jsonPath("$.[0].modelo").value("Stephen King"))
				.andExpect(jsonPath("$.[1].modelo").value("H. P. Lovecraft"))
	}

	@Test
	void testingPost() throws Exception {
		when(service.save(ArgumentMatchers.any(Livro.class))).thenReturn("1");

		Map<String, String> carrie = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "1");
				put("titulo", "Carrie");
				put("autor", "Stephen King");
			}
		};

		String LivroJson = objMapper.writeValueAsString(carrie);

		mockMvc.perform(post("/api/Livro").contentType(MediaType.APPLICATION_JSON).content(LivroJson))
				.andExpect(status().isCreated()).andExpect(content().string("1"));

	}

	@Test
	void testingPutWithExistingData() throws Exception {
		Livro livro = new Livro("1", "Carrie", "Stephen King");

		when(service.save(ArgumentMatchers.any(Livro.class))).thenReturn("1");

		Map<String, String> carrie = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "1");
				put("titulo", "Carrie");
				put("autor", "Stephen King");
			}
		};

		String LivroJson = objMapper.writeValueAsString(carrie);

		mockMvc.perform(put("/api/Livro/1").contentType(MediaType.APPLICATION_JSON).content(LivroJson))
				.andExpect(status().isOk());
	}

	@Test
	void testingPutWithlivroxistingData() throws Exception {
		when(service.save(ArgumentMatchers.any(Livro.class))).thenReturn("2");

		mockMvc.perform(put("/api/Livro/2")).andExpect(status().isBadRequest());
	}

	@Test
	void testingDeleteByIdWithExistingData() throws Exception {
		Livro livro = new Livro("1", "Carrie", "Stephen King");

		when(service.findById("1")).thenReturn(livro);

		mockMvc.perform(delete("/api/Livro/1")).andExpect(status().isNoContent());
	}

	@Test
	void testingDeleteByIdNotWithExistingData() throws Exception {
		when(service.findById("2")).thenThrow(RegistryNotFoundException.class);

		mockMvc.perform(delete("/api/Livro/2")).andExpect(status().isBadRequest());
	}
}
