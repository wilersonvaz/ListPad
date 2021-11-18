package model;

public class Lista {
    private int idLista;
    private int idUsuario;
    private Categoria categoria;
    private String descricaoLista;
    private int flagUrgencia;

    public Lista(){

    }
    public Lista(int idLista, int idUsuario, Categoria categoria, String descricaoLista, int flagUrgencia) {
        this.idLista = idLista;
        this.idUsuario = idUsuario;
        this.categoria = categoria;
        this.descricaoLista = descricaoLista;
        this.flagUrgencia = flagUrgencia;
    }

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricaoLista() {
        return descricaoLista;
    }

    public void setDescricaoLista(String descricaoLista) {
        this.descricaoLista = descricaoLista;
    }

    public int getFlagUrgencia() {
        return flagUrgencia;
    }

    public void setFlagUrgencia(int flagUrgencia) {
        this.flagUrgencia = flagUrgencia;
    }
}
