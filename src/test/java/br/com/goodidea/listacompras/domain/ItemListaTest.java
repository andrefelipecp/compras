package br.com.goodidea.listacompras.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.goodidea.listacompras.web.rest.TestUtil;

public class ItemListaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemLista.class);
        ItemLista itemLista1 = new ItemLista();
        itemLista1.setId(1L);
        ItemLista itemLista2 = new ItemLista();
        itemLista2.setId(itemLista1.getId());
        assertThat(itemLista1).isEqualTo(itemLista2);
        itemLista2.setId(2L);
        assertThat(itemLista1).isNotEqualTo(itemLista2);
        itemLista1.setId(null);
        assertThat(itemLista1).isNotEqualTo(itemLista2);
    }
}
