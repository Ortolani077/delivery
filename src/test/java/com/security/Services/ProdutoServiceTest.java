package com.security.Services;

import com.security.Model.Produto;
import com.security.Repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @InjectMocks
    private ProdutoServicesImplementation produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private Produto produtoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProdutoById() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoMock));

        Optional<Produto> found = produtoService.getProdutoById(1L);
        assertTrue(found.isPresent());
        assertEquals(produtoMock, found.get());
    }

    @Test
    void testCreateProduto() {
        when(produtoMock.getId()).thenReturn(null);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoMock);

        Produto created = produtoService.createProduto(produtoMock);
        assertNotNull(created);
        assertEquals(produtoMock, created);
    }

    @Test
    void testCreateProdutoWithId() {
        when(produtoMock.getId()).thenReturn(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.createProduto(produtoMock);
        });

        assertEquals("O produto já está cadastrado", exception.getMessage());
    }

    @Test
    void testUpdateProdutoWithId() {
        when(produtoMock.getId()).thenReturn(1L);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoMock);

        Produto updated = produtoService.updateProduto(produtoMock);
        assertNotNull(updated);
        assertEquals(produtoMock, updated);
    }

    @Test
    void testUpdateProdutoWithoutId() {
        when(produtoMock.getId()).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.updateProduto(produtoMock);
        });

        assertEquals("ID do produto está nulo, informe um ID válido", exception.getMessage());
    }

    @Test
    void testDeleteProduto() {
        doNothing().when(produtoRepository).deleteById(1L);

        produtoService.deleteProduto(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testListAll() {
        when(produtoRepository.findAll()).thenReturn(Collections.singletonList(produtoMock));

        assertEquals(1, produtoService.listAll().size());
        assertEquals(produtoMock, produtoService.listAll().get(0));
    }

    @Test
    void testUpdateProdutoById() {
        // Since this method is not implemented, we will just check the return value
        Produto result = produtoService.updateProduto(1L, produtoMock);
        assertNull(result);
    }
}
