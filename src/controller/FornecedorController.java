
package controller;

import dao.FornecedorDAO;
import dao.MarcaDAO;
import dao.ProdutoDAO;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Fornecedor;
import model.Marca;
import view.FornecedorView;



public class FornecedorController 
{
    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            FornecedorDAO dao = new FornecedorDAO(); //alterar
            List<Fornecedor> objetos = dao.selecionar(); // alterar
            Object colunas[] = new Object[5]; //alterar o índice de acordo com o número de campos exibidos 
            
            //MarcaDAO dao1 = new MarcaDAO();
            //List<Marca> obj = dao1.selecionar();
            
            if (!objetos.isEmpty()) {
                for (Fornecedor objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getCodigo();  //alterar
                    colunas[1] = objeto.getNome_fornecedor(); //alterar
                    colunas[2] = objeto.getTelefone();
                    colunas[3] = objeto.getDescricaoMarca();
                    colunas[4] = objeto.getCodigo_marca();                                       
                    
                    model.addRow(colunas);
                }
            }
        } catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
    }

    public static void AtualizaComboBox(JComboBox cbmarca)
    {
        cbmarca.removeAllItems();
        MarcaDAO dao = new MarcaDAO();
        for(Marca marca: dao.selecionar())
        {
            cbmarca.addItem(marca);
            
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

    public static void atualizaCampos(FornecedorView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String codigo = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String nome_fonecedor = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1
        String telefone = tela.tabela.getValueAt(linhaSelecionada, 2).toString(); //está na coluna 0
        String codigo_marca = tela.tabela.getValueAt(linhaSelecionada, 3).toString();
        
        

        //alterar setando os valores dos campos
        tela.jtfCodigo.setText(codigo);
        tela.jtfNome_Fornecedor.setText(nome_fonecedor);
        tela.jtfTelefone.setText(telefone);
       
        tela.jcbMarca.setSelectedItem(codigo_marca);
        

        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(FornecedorView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        String nome_fornecedor = tela.jtfNome_Fornecedor.getText().trim();
        String telefone = tela.jtfTelefone.getText().trim();
        
        int cod_marca = ((Marca)tela.jcbMarca.getSelectedItem()).getCodigo();
        
        
        //alterar:: criando objeto
        Fornecedor objeto = new Fornecedor();
        objeto.setNome_fornecedor(nome_fornecedor); 
        objeto.setTelefone(telefone);
        
        objeto.setCodigo_marca(cod_marca);
        //alterar:: adicionando o objeto no banco de dados
        FornecedorDAO dao = new FornecedorDAO();
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

    public static void alterar(FornecedorView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());
        String nome_fornecedor = tela.jtfNome_Fornecedor.getText().trim();
        String telefone = tela.jtfTelefone.getText().trim();
        
        //int codigo_marca = Integer.parseInt(tela.jcbMarca.getSelectedItem());
        
        

        //alterar:: criando objeto
        Fornecedor objeto = new Fornecedor();
        objeto.setCodigo(codigo); //na alteração tem que setar o código
        objeto.setNome_fornecedor(nome_fornecedor);
        objeto.setTelefone(telefone);
        
      

        //alterar:: alterando o objeto no banco de dados
        FornecedorDAO dao = new FornecedorDAO(); //alterar
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
    
    public static void excluir(FornecedorView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());

        //alterar:: criando objeto
        Fornecedor objeto = new Fornecedor();
        objeto.setCodigo(codigo); //na exclusão só precisa setar a chave primária

        //alterar:: excluindo o objeto no banco de dados
        FornecedorDAO dao = new FornecedorDAO(); //alterar
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
    public static boolean verificarCampos(FornecedorView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfNome_Fornecedor.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(tela, "Preencha o campo nome!");
            return false;
        }
        return true;
    }

    /**
     * Deixa os campos em branco e habilita/desabilita os botões
     *
     * @param tela
     */
    public static void limparCampos(FornecedorView tela) {
        //alterar:: limpando os campos
        tela.jtfCodigo.setText("");
        tela.jtfNome_Fornecedor.setText("");
        tela.jtfTelefone.setText("");
        

        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }
}
