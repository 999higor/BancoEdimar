
package model;


public class Produto 
{
    private String nome;
    private int codigo;
    private int estoque;
    private double preco;
    private int quantidade_minima;
    private int codigo_marca;
    private String descricaoMarca;
    
    private int entradaEstoque;
    private int baixaEstoque;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade_minima() {
        return quantidade_minima;
    }

    public void setQuantidade_minima(int quantidade_minima) {
        this.quantidade_minima = quantidade_minima;
    }

    public int getCodigo_marca() {
        return codigo_marca;
    }

    public void setCodigo_marca(int codigo_marca) {
        this.codigo_marca = codigo_marca;
    }

    public String getDescricaoMarca() {
        return descricaoMarca;
    }

    public void setDescricaoMarca(String descricaoMarca) {
        this.descricaoMarca = descricaoMarca;
    }

    public int getEntradaEstoque() {
        return entradaEstoque;
    }

    public void setEntradaEstoque(int entradaEstoque) {
        this.entradaEstoque = entradaEstoque;
    }

    public int getBaixaEstoque() {
        return baixaEstoque;
    }

    public void setBaixaEstoque(int baixaEstoque) {
        this.baixaEstoque = baixaEstoque;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
}
