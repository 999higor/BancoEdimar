
package controller;

import dao.EntradaSaidaDAO;
import dao.ProdutoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Produto;
import view.EntradaSaidaView;


public class EntradaSaidaController 
{
    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ProdutoDAO dao = new ProdutoDAO(); //alterar
            List<Produto> objetos = dao.selecionar(); // alterar
            Object colunas[] = new Object[4]; //alterar o índice de acordo com o número de campos exibidos 
            
            
            if (!objetos.isEmpty()) {
                for (Produto objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getCodigo();  //alterar
                    colunas[1] = objeto.getNome(); //alterar
                    colunas[2] = objeto.getDescricaoMarca();
                    colunas[3] = objeto.getEstoque();
                    
                    
                    
                    model.addRow(colunas);
                }
            }
        } catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static void removeLinhasTabela(JTable tabela) {
        try {
            DefaultTableModel dtm = (DefaultTableModel) tabela.getModel();
            dtm.setRowCount(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void baixaEstoque(EntradaSaidaView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());
        int estoque = Integer.parseInt(tela.jtfQuantidade.getText().trim());
        //alterar:: criando objeto
        Produto objeto = new Produto();
        objeto.setCodigo(codigo); //na alteração tem que setar o código        
        objeto.setBaixaEstoque(estoque);

        //alterar:: alterando o objeto no banco de dados
        EntradaSaidaDAO dao = new EntradaSaidaDAO(); //alterar
        boolean resultado = dao.baixaEstoque(objeto); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Estoque Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
    public static void entradaEstoque(EntradaSaidaView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());
        int estoque = Integer.parseInt(tela.jtfQuantidade.getText().trim());
        //alterar:: criando objeto
        Produto objeto = new Produto();
        objeto.setCodigo(codigo); //na alteração tem que setar o código        
        objeto.setEntradaEstoque(estoque);

        //alterar:: alterando o objeto no banco de dados
        EntradaSaidaDAO dao = new EntradaSaidaDAO(); //alterar
        boolean resultado = dao.entradaEstoque(objeto); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Estoque Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
   
    
    public static void atualizaCampos(EntradaSaidaView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String codigo = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0       
        //String estoque = tela.tabela.getValueAt(linhaSelecionada, 3).toString(); //está na coluna 0
               

        //alterar setando os valores dos campos
        tela.jtfCodigo.setText(codigo);
        //tela.jtfQuantidade.setText(estoque);

        // habilita/desabilita botões
        tela.jbtEntrada.setEnabled(true);
        tela.jbtBaixa.setEnabled(true);
    }
    
    public static boolean verificarCampos(EntradaSaidaView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfQuantidade.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(tela, "Preencha o campo quantidade!");
            return false;
        }
        return true;
    }
    
    public static void limparCampos(EntradaSaidaView tela) {
        //alterar:: limpando os campos
        
        tela.jtfCodigo.setText("");
        tela.jtfQuantidade.setText("");

        //habilitando/desabilitando os botões
        
        tela.jbtBaixa.setEnabled(false);
        tela.jbtEntrada.setEnabled(false);
    }
}
