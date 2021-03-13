package br.com.goodidea.listacompras.repository;

import br.com.goodidea.listacompras.domain.ItemLista;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ItemLista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemListaRepository extends JpaRepository<ItemLista, Long> {
}
