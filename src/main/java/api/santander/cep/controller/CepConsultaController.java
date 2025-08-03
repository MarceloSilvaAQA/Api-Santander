package api.santander.cep.controller;

import api.santander.cep.dto.CepResponse;
import api.santander.cep.service.CepConsultaService;
import api.santander.cep.util.ApiRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiRoutes.Cep.BASE)
public class CepConsultaController {

    @Autowired
    private CepConsultaService cepConsultaService;

    @GetMapping(ApiRoutes.Cep.POR_CEP)
    public ResponseEntity<CepResponse> consultarCep(@PathVariable  String cep) {
        CepResponse  response = cepConsultaService.consultarCep(cep);
        return ResponseEntity.ok(response);
    }

}
