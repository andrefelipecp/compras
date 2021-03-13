package br.com.goodidea.listacompras.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Lista.
 */
@Entity
@Table(name = "lista")
public class Lista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "lista")
    private Set<ItemLista> itemListas = new HashSet<>();

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

    public Lista nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<ItemLista> getItemListas() {
        return itemListas;
    }

    public Lista itemListas(Set<ItemLista> itemListas) {
        this.itemListas = itemListas;
        return this;
    }

    public Lista addItemLista(ItemLista itemLista) {
        this.itemListas.add(itemLista);
        itemLista.setLista(this);
        return this;
    }

    public Lista removeItemLista(ItemLista itemLista) {
        this.itemListas.remove(itemLista);
        itemLista.setLista(null);
        return this;
    }

    public void setItemListas(Set<ItemLista> itemListas) {
        this.itemListas = itemListas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lista)) {
            return false;
        }
        return id != null && id.equals(((Lista) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lista{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
