package com.jacto.agendamento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.util.Objects;

@Entity
@Table(name = "peca_reposicao")
public class PecaReposicao {

    @Id
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "descricao", length = 250)
    private String descricao;

    public PecaReposicao() {
    }

    public PecaReposicao(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "PecaReposicao{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PecaReposicao)) return false;
        PecaReposicao that = (PecaReposicao) o;
        return Objects.equals(codigo, that.codigo) &&
               Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, descricao);
    }
}