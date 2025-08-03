package api.santander.cep.exception;

public class CepNaoEncontradoException extends  RuntimeException {

    public CepNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
