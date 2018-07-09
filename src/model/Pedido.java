
package model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;


public class Pedido 
{
    private int numero;
    private Date previsao_entrega;
    private Date data_hora;
    private int codigo_fornecedor;
    private int codigo_produto;
    
    private String descricaoProduto;
    private String nomeFornecedor;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getPrevisao_entrega() {
        return previsao_entrega;
    }

    public void setPrevisao_entrega(Date previsao_entrega) {
        this.previsao_entrega = previsao_entrega;
    }
    public void setPrevisao_entrega(String previsao_entrega)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
            setPrevisao_entrega(format.parse(previsao_entrega));
        }catch(ParseException ex)
        {
            Logger.getLogger("erro na data");
            
        }
    }

    public Date getData_hora() {
        return data_hora;
    }

    public void setData_hora(Date data_hora) {
        this.data_hora = data_hora;
    }

    public int getCodigo_fornecedor() {
        return codigo_fornecedor;
    }

    public void setCodigo_fornecedor(int codigo_fornecedor) {
        this.codigo_fornecedor = codigo_fornecedor;
    }

    public int getCodigo_produto() {
        return codigo_produto;
    }

    public void setCodigo_produto(int codigo_produto) {
        this.codigo_produto = codigo_produto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
    
    
}
