/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.treinaweb.agenda.repositorios.impl;

import br.com.treinaweb.agenda.entidades.Contato;
import br.com.treinaweb.agenda.repositorios.interfaces.AgendaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author netos
 */
public class ContatoRepositorio implements AgendaRepositorio<Contato> {

    private static List<Contato> contatos = new ArrayList<>();

    @Override
    public List<Contato> selecionar() {
        return contatos;
    }

    @Override
    public void inserir(Contato entidade) {
        contatos.add(entidade);
    }

    @Override
    public void atualizar(Contato entidade) {
        Optional<Contato> original = contatos.stream().filter(contato -> contato.getNome().equals(entidade.getNome())).findFirst();
        if (original.isPresent()) {
            original.get().setIdade(entidade.getIdade());
            original.get().setTell(entidade.getTell());
        }
    }

    @Override
    public void excluir(Contato entidade) {
        contatos.remove(entidade);
    }

}
