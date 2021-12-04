package model;

public class ItensLista {
    private int idItem;
    private Lista lista;
    private String itemLista;
    private int flagFinalizado;

    public ItensLista(int idItem, Lista lista, String itemLista, int flagFinalizado) {
        this.idItem = idItem;
        this.lista = lista;
        this.itemLista = itemLista;
        this.flagFinalizado = flagFinalizado;
    }

    public ItensLista() {

    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public String getItemLista() {
        return itemLista;
    }

    public void setItemLista(String itemLista) {
        this.itemLista = itemLista;
    }

    public int getFlagFinalizado() {
        return flagFinalizado;
    }

    public void setFlagFinalizado(int flagFinalizado) {
        this.flagFinalizado = flagFinalizado;
    }
}
