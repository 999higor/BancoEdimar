
package dao;

import config.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Fornecedor;



public class FornecedorDAO 
{
    public boolean adicionar(Fornecedor objeto) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO fornecedor (nome_fornecedor, telefone ,codigo_marca) VALUES (?,?,?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, objeto.getNome_fornecedor()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setString(2, objeto.getTelefone());
            pstmt.setInt(3, objeto.getCodigo_marca());
            
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Fornecedor objeto) {
        try {
            String sql = " UPDATE fornecedor "
                    + "    SET nome_fornecedor = ?, telefone = ?, codigo_marca = ?"
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, objeto.getNome_fornecedor());
            pstmt.setString(2, objeto.getTelefone());
            pstmt.setInt(3,objeto.getCodigo_marca());

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Fornecedor objeto) {
        try {
            String sql = " DELETE FROM fornecedor WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, objeto.getCodigo()); //alterar conforme a chave primária

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Fornecedor> selecionar() {
        String sql = "SELECT codigo, nome_fornecedor ,telefone ,codigo_marca FROM fornecedor ORDER BY nome_fornecedor"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Fornecedor> lista = new ArrayList<>(); //alterar classe

            while (rs.next()) {
                Fornecedor objeto = new Fornecedor(); //alterar o nome da classe e o construtor
                //setar os atributos do objeto. Cuidar o tipo dos atributos
                objeto.setCodigo(rs.getInt("codigo")); //alterar
                objeto.setNome_fornecedor(rs.getString("nome_fornecedor"));  //alterar
                objeto.setTelefone(rs.getString("telefone"));
                objeto.setCodigo_marca(rs.getInt("codigo_marca"));
                
                lista.add(objeto);
            }
            stmt.close();
            return lista;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //método só para testar
    public static void main(String[] args) {
        Fornecedor objeto = new Fornecedor(); //alterar
        //objeto.setDescricao(JOptionPane.showInputDialog("Digite o Nome:"));
        FornecedorDAO dao = new FornecedorDAO(); //alterar
        dao.adicionar(objeto); //alterar
    }
}
