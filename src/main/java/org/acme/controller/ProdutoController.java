package org.acme.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.dto.CadastrarProdutoDTO;
import org.acme.entity.Produto;


@Path("produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoController {
    
    @GET
    public List<Produto> buscarTodosProdutos(){
        return Produto.listAll();
    }


    @POST
    @Transactional
    public void cadastrarProdutos(CadastrarProdutoDTO prodDTO){
        Produto produtoEntity = new Produto();
        produtoEntity.nome = prodDTO.nome;
        produtoEntity.valor = prodDTO.valor;
        produtoEntity.persist();
    }


    @PUT
    @Path("{id}")
    @Transactional
    public void atualizarProduto(@PathParam("id") Long id,CadastrarProdutoDTO prodDTO){
        Optional<Produto> produtoOp = Produto.findByIdOptional(id);
        if(produtoOp.isPresent()){
            Produto produto = produtoOp.get();
            produto.nome = prodDTO.nome;
            produto.valor = prodDTO.valor;
            produto.persist();
        }else {
            throw new NotFoundException("");
        }
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void deletarProduto(@PathParam("id") Long id){
        Optional<Produto> produtoOp = Produto.findByIdOptional(id);
        produtoOp.ifPresentOrElse(Produto::delete, () -> {
            throw new NotFoundException();
        });
    }
}
