package api.santander.cep.service;

import api.santander.cep.dto.CepResponse;
import api.santander.cep.exception.CepInvalidoException;
import api.santander.cep.exception.CepNaoEncontradoException;
import api.santander.cep.model.ConsultaCep;
import api.santander.cep.repositoy.ConsultaCepRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class CepConsultaService {

    @Autowired
    private ObjectMapper objectMapper;


    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ConsultaCepRepository cepRepository;

    @Value("${cep.api.url}")
    private String cepApiUrl;

    public CepResponse consultarCep(String cep) {
        ConsultaCep consultaCep = new ConsultaCep();
        consultaCep.setCepConsultado(cep);
        consultaCep.setDataHora(LocalDateTime.now());

        try {
            if (cep == null || !cep.matches("\\d{8}")) {
                consultaCep.setRespostaJson("CEP inválido");
                throw new CepInvalidoException("CEP inválido - CEP deve conter 8 dígitos");
            }

            String url = cepApiUrl + "/" + cep + "/json";
            ResponseEntity<CepResponse> response = restTemplate.getForEntity(url, CepResponse.class);

            String json = objectMapper.writeValueAsString(response.getBody());
            consultaCep.setRespostaJson(json);

            return response.getBody();

        } catch (CepInvalidoException | CepNaoEncontradoException e) {
            consultaCep.setRespostaJson(e.getMessage());
            throw e;
        } catch (HttpClientErrorException e) {
            String msg = "CEP não encontrado: " + cep;
            consultaCep.setRespostaJson(msg);
            throw new CepNaoEncontradoException(msg);

        } catch (Exception e) {
            String msg = "Erro ao consultar o CEP: " + e.getMessage();
            consultaCep.setRespostaJson(msg);
            throw new RuntimeException(msg);

        } finally {
            cepRepository.save(consultaCep);
        }
    }
}
