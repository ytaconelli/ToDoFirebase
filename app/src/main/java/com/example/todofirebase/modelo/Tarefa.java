package com.example.todofirebase.modelo;

public class Tarefa {

    //Atributos da classe
    private String uuid;
    private String nome;
    private String imageScr = "https://img.olhardigital.com.br/uploads/acervo_imagens/2013/08/r16x9/20130820184205_1200_675_-_android_logo.jpg";
    private boolean status;

    public Tarefa(){

    }


    //Instancia um objeto
    public Tarefa(String uuid, String nome) {
        this.uuid = uuid;
        this.nome = nome;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImageScr() {
        return imageScr;
    }

    public void setImageScr(String imageScr) {
        this.imageScr = imageScr;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "uuid='" + uuid + '\'' +
                ", nome='" + nome + '\'' +
                ", status=" + status +
                '}';
    }
}
