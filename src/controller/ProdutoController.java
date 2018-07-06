
package controller;


import dao.MarcaDAO;
import dao.ProdutoDAO;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Marca;
import model.Produto;
import view.ProdutoView;




public class ProdutoController 
{
    
    public static void atualizaTabela(JTable tabela) {
        removeLinhasTabela(tabela);
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ProdutoDAO dao = new ProdutoDAO(); //alterar
            List<Produto> objetos = dao.selecionar(); // alterar
            Object colunas[] = new Object[7]; //alterar o índice de acordo com o número de campos exibidos 
            
            //MarcaDAO dao1 = new MarcaDAO();
            //List<Marca> obj = dao1.selecionar();
            
            if (!objetos.isEmpty()) {
                for (Produto objeto : objetos) {//alterar a classe
                    //alterar definir o que vai em cada linha - 1 linha para cada atributo exibido na tabela
                    colunas[0] = objeto.getCodigo();  //alterar
                    colunas[1] = objeto.getNome(); //alterar
                    colunas[2] = objeto.getEstoque();
                    colunas[3] = objeto.getPreco();
                    colunas[4] = objeto.getQuantidade_minima();
                    colunas[5] = objeto.getDescricaoMarca();
                    colunas[6] = objeto.getCodigo_marca();
                    
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

    public static void atualizaCampos(ProdutoView tela) {
        int linhaSelecionada = tela.tabela.getSelectedRow();

        //alterar obtendo os valores da tabela
        String codigo = tela.tabela.getValueAt(linhaSelecionada, 0).toString(); //está na coluna 0
        String nome = tela.tabela.getValueAt(linhaSelecionada, 1).toString(); //está na coluna 1
        String estoque = tela.tabela.getValueAt(linhaSelecionada, 2).toString(); //está na coluna 0
        String preco = tela.tabela.getValueAt(linhaSelecionada, 3).toString(); //está na coluna 0
        String quantidade_minima = tela.tabela.getValueAt(linhaSelecionada, 4).toString(); //está na coluna 0
        int codigo_marca = Integer.parseInt(tela.tabela.getValueAt(linhaSelecionada, 6).toString());
        
        

        //alterar setando os valores dos campos
        tela.jtfCodigo.setText(codigo);
        tela.jtfNome.setText(nome);
        tela.jtfEstoque.setText(estoque);
        tela.jtfPreco.setText(preco);
        tela.jtfQuantidade_minima.setText(quantidade_minima);
        
        for(int i=0; i<tela.jcbMarca.getItemCount();i++)
        {
            if (tela.jcbMarca.getItemAt(i).getCodigo() == codigo_marca){
                tela.jcbMarca.setSelectedIndex(i);
                break;
            }
        }
        //tela.jcbMarca.setSelectedItem(codigo_marca);
        

        // habilita/desabilita botões
        tela.jbtAdicionar.setEnabled(false);
        tela.jbtAlterar.setEnabled(true);
        tela.jbtExcluir.setEnabled(true);
    }

    public static void adicionar(ProdutoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }

        //alterar:: obtendo os valores preenchidos
        String nome = tela.jtfNome.getText().trim();
        int estoque = Integer.parseInt(tela.jtfEstoque.getText().trim());
        double preco = Double.parseDouble(tela.jtfPreco.getText().trim());
        int quantidade_minima = Integer.parseInt(tela.jtfQuantidade_minima.getText().trim());
        int cod_marca = ((Marca)tela.jcbMarca.getSelectedItem()).getCodigo();
        
        
        //alterar:: criando objeto
        Produto objeto = new Produto();
        objeto.setNome(nome); 
        objeto.setEstoque(estoque);
        objeto.setPreco(preco);
        objeto.setQuantidade_minima(quantidade_minima);
        objeto.setCodigo_marca(cod_marca);
        //alterar:: adicionando o objeto no banco de dados
        ProdutoDAO dao = new ProdutoDAO();
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

    public static void alterar(ProdutoView tela) {
        //verificando se os campos estão preenchidos
        if (!verificarCampos(tela)) {
            return; //algum campo não está preenchido corretamente
        }
        //alterar:: obtendo os valores preenchidos
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());
        String nome = tela.jtfNome.getText().trim();
        int estoque = Integer.parseInt(tela.jtfEstoque.getText().trim());
        double preco = Double.parseDouble(tela.jtfPreco.getText().trim());
        int quantidade_minima = Integer.parseInt(tela.jtfQuantidade_minima.getText().trim());
        int codigo_marca = ((Marca)tela.jcbMarca.getSelectedItem()).getCodigo();
        
        

        //alterar:: criando objeto
        Produto objeto = new Produto();
        objeto.setCodigo(codigo); //na alteração tem que setar o código
        objeto.setNome(nome);
        objeto.setEstoque(estoque);
        objeto.setPreco(preco);
        objeto.setQuantidade_minima(quantidade_minima);
        objeto.setCodigo_marca(codigo_marca);

        //alterar:: alterando o objeto no banco de dados
        ProdutoDAO dao = new ProdutoDAO(); //alterar
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
    
    public static void excluir(ProdutoView tela) {
        //verificando se usuário tem certeza
        int result = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja excluir?", "Exclusão", JOptionPane.YES_NO_OPTION);
        if (result!=JOptionPane.YES_OPTION) {
            return; //não quer excluir
        }
        
        //alterar:: obtendo a chave primária
        Integer codigo = Integer.parseInt(tela.jtfCodigo.getText().trim());

        //alterar:: criando objeto
        Produto objeto = new Produto();
        objeto.setCodigo(codigo); //na exclusão só precisa setar a chave primária

        //alterar:: excluindo o objeto no banco de dados
        ProdutoDAO dao = new ProdutoDAO(); //alterar
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
    public static boolean verificarCampos(ProdutoView tela) {
        //alterar:: conforme os campos obrigatórios
        if (tela.jtfNome.getText().isEmpty())
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
    public static void limparCampos(ProdutoView tela) {
        //alterar:: limpando os campos
        
        tela.jtfCodigo.setText("");
        tela.jtfNome.setText("");
        tela.jtfEstoque.setText("");
        tela.jtfPreco.setText("");
        tela.jtfQuantidade_minima.setText("");
        tela.jcbMarca.setSelectedIndex(0);

        //habilitando/desabilitando os botões
        tela.jbtAdicionar.setEnabled(true);
        tela.jbtAlterar.setEnabled(false);
        tela.jbtExcluir.setEnabled(false);
    }
}
