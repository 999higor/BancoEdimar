
package model;

import java.sql.Date;


public class Pedido 
{
    private int numero;
    private Date previsao_entrega;
    private Date data_hora;
    private int codigo_fornecedor;

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
    
    
}
