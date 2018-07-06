
package dao;

import config.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Marca;


public class MarcaDAO 
{
     public boolean adicionar(Marca objeto) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO marca (descricao) VALUES (?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, objeto.getDescricao()); // alterar o primeiro parâmetro indica a interrogação, começando em 1

            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Marca objeto) {
        try {
            String sql = " UPDATE marca "
                    + "    SET descricao = ?"
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, objeto.getDescricao());
            pstmt.setInt(2, objeto.getCodigo());

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Marca objeto) {
        try {
            String sql = " DELETE FROM marca WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

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

    public List<Marca> selecionar() {
        String sql = "SELECT codigo, descricao FROM marca ORDER BY descricao"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Marca> lista = new ArrayList<>(); //alterar classe

            while (rs.next()) {
                Marca objeto = new Marca(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                objeto.setCodigo(rs.getInt("codigo")); //alterar
                objeto.setDescricao(rs.getString("descricao"));  //alterar
                
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
        Marca objeto = new Marca(); //alterar
        objeto.setDescricao(JOptionPane.showInputDialog("Digite o Nome:"));
        MarcaDAO dao = new MarcaDAO(); //alterar
        dao.adicionar(objeto); //alterar
    }
}
