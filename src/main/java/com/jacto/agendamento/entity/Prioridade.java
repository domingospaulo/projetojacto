package com.jacto.agendamento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

import jakarta.persistence.Column;

@Entity
@Table(name = "prioridade")
public class Prioridade {

    @Id
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "descricao", length = 250)
    private String descricao;

    @Column(name = "nivel")
    private Integer nivel;

    public Prioridade() {
    }

    public Prioridade(Integer codigo, String descricao, Integer nivel) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.nivel = nivel;
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Prioridade{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                ", nivel=" + nivel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prioridade)) return false;
        Prioridade that = (Prioridade) o;
        return Objects.equals(codigo, that.codigo) &&
               Objects.equals(descricao, that.descricao) &&
               Objects.equals(nivel, that.nivel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, descricao, nivel);
    }    
}