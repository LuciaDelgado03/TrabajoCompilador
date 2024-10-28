public class DatosTablaSimbolos {
    private int token;
    private String tipo;
    private String uso;
    private String ambito;
    //private DatosEspecificos datos;

    public DatosTablaSimbolos (int token){
        this.token = token;
        this.tipo = null;
        this.uso = null;
        this.ambito = null;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public void setAmbito(String amb){this.ambito = amb;}

    public void setUso(String uso){this.uso = uso;}

    public int getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAmbito() {
        return ambito;
    }

    public String getUso() {
        return uso;
    }
    public void seVuelve(DatosTablaSimbolos dato){
        tipo = dato.tipo;
        uso = dato.uso;
        ambito = dato.ambito;
        //datos = dato.datos
    }

}
