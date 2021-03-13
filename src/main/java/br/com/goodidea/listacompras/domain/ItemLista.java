package br.com.goodidea.listacompras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ItemLista.
 */
@Entity
@Table(name = "item_lista")
public class ItemLista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "preco", nullable = false)
    private Double preco;

    @ManyToOne
    @JsonIgnoreProperties(value = "itemListas", allowSetters = true)
    private Lista lista;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public ItemLista nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public ItemLista preco(Double preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Lista getLista() {
        return lista;
    }

    public ItemLista lista(Lista lista) {
        this.lista = lista;
        return this;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemLista)) {
            return false;
        }
        return id != null && id.equals(((ItemLista) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemLista{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", preco=" + getPreco() +
            "}";
    }
}
