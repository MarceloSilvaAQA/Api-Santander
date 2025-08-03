package api.santander.cep.util;

public class ApiRoutes {

    private ApiRoutes() {}

    public static final String API_BASE = "api/v1";

    public static final  class Cep {

        public static final String BASE = API_BASE + "/consultaCep";

        public static final String POR_CEP = "/{cep}";
    }
}
