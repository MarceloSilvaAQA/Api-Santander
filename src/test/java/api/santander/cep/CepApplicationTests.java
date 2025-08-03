package api.santander.cep;

import api.santander.cep.dto.CepResponse;
import api.santander.cep.exception.CepInvalidoException;
import api.santander.cep.exception.CepNaoEncontradoException;
import api.santander.cep.repositoy.ConsultaCepRepository;
import api.santander.cep.service.CepConsultaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import static org.mockito.Mockito.*;


@SpringBootTest
public class CepApplicationTests {

	@InjectMocks
	private CepConsultaService consultaCepService;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private ConsultaCepRepository cepRepository;

	@Mock
	private ObjectMapper objectMapper;

	private final String cepApiUrl = "http://localhost:8050/ws";

	@BeforeEach
	void setUp() throws Exception {
		ReflectionTestUtils.setField(consultaCepService, "cepApiUrl", cepApiUrl);
	}

	@Test
	void deveRetornarCepResponseQuandoCepValido() throws Exception {
		String cep = "14810089";

		CepResponse cepResponse = new CepResponse();
		cepResponse.setCep("14810089");
		cepResponse.setLogradouro("Avenida São Sebastião");

		String jsonResponse = "{\"cep\":\"14810089\",\"logradouro\":\"Avenida São Sebastião\"}";

		ResponseEntity<CepResponse> responseEntity = ResponseEntity.ok(cepResponse);

		when(restTemplate.getForEntity(anyString(), eq(CepResponse.class))).thenReturn(responseEntity);

		when(objectMapper.writeValueAsString(cepResponse)).thenReturn(jsonResponse);


		CepResponse resultado = consultaCepService.consultarCep(cep);


		assertNotNull(resultado);
		assertEquals("14810089", resultado.getCep());

		verify(cepRepository, times(1)).save(any());
	}

	@Test
	void deveLancarCepInvalidoExceptionQuandoCepInvalido() {
		String cepInvalido = "123";

		CepInvalidoException exception = assertThrows(CepInvalidoException.class, () -> {
			consultaCepService.consultarCep(cepInvalido);
		});

		assertEquals("CEP inválido - CEP deve conter 8 dígitos", exception.getMessage());
		verify(cepRepository, times(1)).save(any());
	}

	@Test
	void deveLancarCepNaoEncontradoExceptionQuandoHttpClientErrorException() {
		String cep = "99999999";

		when(restTemplate.getForEntity(anyString(), eq(CepResponse.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		CepNaoEncontradoException exception = assertThrows(CepNaoEncontradoException.class, () -> {
			consultaCepService.consultarCep(cep);
		});

		assertTrue(exception.getMessage().contains("CEP não encontrado"));
		verify(cepRepository, times(1)).save(any());
	}
}
