package negocio;

import java.util.Date;

public class Anotacao {
    private Integer id;
    private String titulo;
    private Date dataHoraCriacao;
    private String descricao;
    private String cor;
    private byte foto[];

    public Anotacao(String titulo, String descricao, String cor, byte[] foto) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
        this.foto = foto;
        this.dataHoraCriacao = new Date();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataHoraCriacao() {
        return this.dataHoraCriacao;
    }

    public void setDataHoraCriacao() {
        this.dataHoraCriacao = new Date();
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}