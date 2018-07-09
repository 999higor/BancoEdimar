
package controller;


import dao.FornecedorDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Fornecedor;
import model.Pedido;
import model.Produto;
import view.PedidoView;


public class PedidoController 
{
     public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            PedidoDAO dao = new PedidoDAO(); //alterar
            List<Pedido> objetos = dao.selecionar(); // alterar
            Object colunas[] = new Object[7]; //alterar o índice de acordo com o número de campos exibidos 
            
            //MarcaDAO dao1 = new MarcaDAO();
            //List<Marca> obj = dao1.selecionar();
            
            if (!objetos.isEmpty()) {
                for (Pedido objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getNumero();  //alterar
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    colunas[1] = format.format(objeto.getPrevisao_entrega()); //alterar
                    format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    //System.out.println("aki:"+objeto.getData_hora());
                    colunas[2] = format.format(objeto.getData_hora());
                    colunas[3] = objeto.getNomeFornecedor();
                    colunas[4] = objeto.getCodigo_fornecedor();
                    colunas[5] = objeto.getDescricaoProduto();
                    colunas[6] = objeto.getCodigo_produto();
                    
                    
                    model.addRow(colunas);
                }
            }
        } catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
    }

    public static void AtualizaComboBox(JComboBox cbfornecedor)
    {
        cbfornecedor.removeAllItems();
        FornecedorDAO dao = new FornecedorDAO();
        for(Fornecedor fornecedor: dao.selecionar())
        {
            cbfornecedor.addItem(fornecedor);
            
        }      
    }
    
    public static void AtualizaComboBoxProduto(JComboBox cbProduto)
    {
        cbProduto.removeAllItems();
        ProdutoDAO dao1 = new ProdutoDAO();
        for(Produto produto: dao1.selecionar())
        {
            cbProduto.addItem(produto);
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

    public static void atualizaCampos(PedidoView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String numero = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String previsao_entrega = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1
        String data_hora = tela.tabela.getValueAt(linhaSelecionada, 2).toString(); //está na coluna 0        
        int codigo_fornecedor = Integer.parseInt(tela.tabela.getValueAt(linhaSelecionada, 4).toString());
        int codigo_produto = Integer.parseInt(tela.tabela.getValueAt(linhaSelecionada, 6).toString());
        
        

        //alterar setando os valores dos campos
        tela.jtfNumero.setText(numero);
        tela.jtfPrevisao.setText(previsao_entrega);
        tela.jtfDataHora.setText(data_hora);
        
        //tela.jcbFornecedor.setSelectedItem(codigo_fornecedor);
        
          for(int i=0; i<tela.jcbFornecedor.getItemCount();i++)
        {
            if (tela.jcbFornecedor.getItemAt(i).getCodigo() == codigo_fornecedor){
                tela.jcbFornecedor.setSelectedIndex(i);
                break;
            }
        }
          for(int i=0; i<tela.jcbProduto.getItemCount();i++)
        {
            if (tela.jcbProduto.getItemAt(i).getCodigo() == codigo_produto){
                tela.jcbProduto.setSelectedIndex(i);
                break;
            }
        }
        

        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(PedidoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        
        String previsao_entrega = (tela.jtfPrevisao.getText().trim());       
        int codigo_fornecedor = ((Fornecedor)tela.jcbFornecedor.getSelectedItem()).getCodigo();
        int codigo_produto = ((Produto)tela.jcbProduto.getSelectedItem()).getCodigo();
        
        
        //alterar:: criando objeto
        Pedido objeto = new Pedido();
        
        objeto.setPrevisao_entrega(previsao_entrega);              
        objeto.setCodigo_fornecedor(codigo_fornecedor);
        objeto.setCodigo_produto(codigo_produto);
        //alterar:: adicionando o objeto no banco de dados
        PedidoDAO dao = new PedidoDAO();
        boolean resultado = dao.adicionar(objeto);
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Inserido com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a inserção!");
        }

    }

    public static void alterar(PedidoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer numero = Integer.parseInt(tela.jtfNumero.getText().trim());
        Date previsao_entrega = Date.valueOf(tela.jtfPrevisao.getText().trim());
        Date data_hora = Date.valueOf(tela.jtfDataHora.getText().trim());
        int codigo_fornecedor = ((Fornecedor)tela.jcbFornecedor.getSelectedItem()).getCodigo();
        int codigo_produto = ((Produto)tela.jcbProduto.getSelectedItem()).getCodigo();
        
        //int codigo_marca = Integer.parseInt(tela.jcbMarca.getSelectedItem());
        
        

        //alterar:: criando objeto
        Pedido objeto = new Pedido();
        objeto.setCodigo_fornecedor(numero); //na alteração tem que setar o código
        objeto.setPrevisao_entrega(previsao_entrega);
        objeto.setData_hora(data_hora);
        objeto.setCodigo_fornecedor(codigo_fornecedor);
        objeto.setCodigo_produto(codigo_produto);
       
      

        //alterar:: alterando o objeto no banco de dados
        PedidoDAO dao = new PedidoDAO(); //alterar
        boolean resultado = dao.alterar(objeto); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Alterado com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a alteração!");
        }
    }
    
    public static void excluir(PedidoView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer numero = Integer.parseInt(tela.jtfNumero.getText().trim());

        //alterar:: criando objeto
        Pedido objeto = new Pedido();
        objeto.setNumero(numero); //na exclusão só precisa setar a chave primária

        //alterar:: excluindo o objeto no banco de dados
        PedidoDAO dao = new PedidoDAO(); //alterar
        boolean resultado = dao.excluir(objeto); //alterar
        
        if (resultado) {
            atualizaTabela(tela.tabela);
            //limpa os campos e habilita/desabilita os botões
            limparCampos(tela);
            JOptionPane.showMessageDialog(tela, "Excluído com sucesso!"); //não alterar
        } else {
            JOptionPane.showMessageDialog(tela, "Problemas com a exclusão!");
        }
    }

    /**
     * Verifica se os campos estão preenchidos corretamente
     *
     * @param tela
     * @return true se todos os campos estão preenchidos corretamente, false se
     * algum campo não está preenchido corretamente
     */
    public static boolean verificarCampos(PedidoView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfPrevisao.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(tela, "Preencha o campo Previsão!");
            return false;
        }
        return true;
    }

    /**
     * Deixa os campos em branco e habilita/desabilita os botões
     *
     * @param tela
     */
    public static void limparCampos(PedidoView tela) {
        //alterar:: limpando os campos
        tela.jtfNumero.setText("");
        tela.jtfPrevisao.setText("");
        tela.jtfDataHora.setText("");
        tela.jcbFornecedor.setSelectedIndex(0);
        tela.jcbProduto.setSelectedIndex(0);
                

        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(false);
        tela.jbtExcluir.setEnabled(false);
    }
}
