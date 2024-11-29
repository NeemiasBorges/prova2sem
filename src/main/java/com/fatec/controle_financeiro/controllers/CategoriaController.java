package com.fatec.controle_financeiro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.controle_financeiro.domain.categoria.CategoriaRepository;
import com.fatec.controle_financeiro.entities.Categoria;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repositorioCategoria;
    // codigo do neemias
    @PostMapping
    public ResponseEntity<?> adicionarCategoria(@RequestBody Categoria novaCategoria) {
        try {
            if (novaCategoria == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro: A categoria não pode ser null.");
            }

            if (novaCategoria.getDescricao() == null || novaCategoria.getDescricao().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: A descrição e obrigate e nao pode ser nula");
            }

            if (repositorioCategoria.findByDescricao(novaCategoria.getDescricao()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: A descricao ja possue no bd");
            }

            Categoria categoriaSalva = repositorioCategoria.save(novaCategoria);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(categoriaSalva);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro: Erro ao adicionr");
        }
    }
}
