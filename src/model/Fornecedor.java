
package model;


public class Fornecedor 
{
    private int codigo;
    private String nome_fornecedor;
    private String telefone;
    private int codigo_marca;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome_fornecedor() {
        return nome_fornecedor;
    }

    public void setNome_fornecedor(String nome_fornecedor) {
        this.nome_fornecedor = nome_fornecedor;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getCodigo_marca() {
        return codigo_marca;
    }

    public void setCodigo_marca(int codigo_marca) {
        this.codigo_marca = codigo_marca;
    }
    
    public String toString() {
        return nome_fornecedor;
    }
    
}
