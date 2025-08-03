package api.santander.cep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "cep_consulta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaCep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cepConsultado;

    private LocalDateTime dataHora;

    @Column(columnDefinition = "TEXT")
    private String respostaJson;


}
