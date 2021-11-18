package model;

public class Categoria {
    private int idCategoria;
    private Usuarios usuarios;
    private String descricaoCategoria;

    public Categoria(){

    }

    public Categoria(int idCategoria, Usuarios usuarios, String descricaoCategoria) {
        this.idCategoria = idCategoria;
        this.usuarios = usuarios;
        this.descricaoCategoria = descricaoCategoria;
    }

    public Categoria(int idCategoria, String descricaoCategoria) {
        this.idCategoria = idCategoria;
        this.descricaoCategoria = descricaoCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public void setDescricaoCategoria(String descricaoCategoria) {
        this.descricaoCategoria = descricaoCategoria;
    }

    @Override
    public String toString() {
        return  descricaoCategoria;
    }
}
