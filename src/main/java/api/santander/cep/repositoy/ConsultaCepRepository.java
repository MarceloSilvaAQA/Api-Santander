package api.santander.cep.repositoy;

import api.santander.cep.model.ConsultaCep;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaCepRepository extends JpaRepository<ConsultaCep, Id> {
}
