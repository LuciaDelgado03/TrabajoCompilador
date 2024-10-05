public class DatosTablaSimbolos {
    private int token;
    private String tipo;

    public DatosTablaSimbolos (int token){
        this.token = token;
        this.tipo = null;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
