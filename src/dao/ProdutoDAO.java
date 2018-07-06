/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Produto;

/**
 *
 * @author Administrador
 */
public class ProdutoDAO 
{
     public boolean adicionar(Produto objeto) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO produto (nome, estoque ,preco ,quantidade_minima ,codigo_marca) VALUES (?,?,?,?,?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, objeto.getNome()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setInt(2, objeto.getEstoque());
            pstmt.setDouble(3, objeto.getPreco());
            pstmt.setInt(4, objeto.getQuantidade_minima());
            pstmt.setInt(5, objeto.getCodigo_marca());
            

            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Produto objeto) {
        try {
            String sql = " UPDATE produto "
                    + "    SET nome = ?, estoque = ?, preco = ?, quantidade_minima = ?"
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setString(1, objeto.getNome());
            pstmt.setInt(2, objeto.getEstoque());
            pstmt.setDouble(3,objeto.getPreco());
            pstmt.setInt(4,objeto.getQuantidade_minima());

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Produto objeto) {
        try {
            String sql = " DELETE FROM produto WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

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

    public List<Produto> selecionar() {
        String sql = "SELECT codigo ,nome ,preco ,quantidade_minima ,estoque FROM produto ORDER BY nome"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Produto> lista = new ArrayList<>(); //alterar classe

            while (rs.next()) {
                Produto objeto = new Produto(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                objeto.setCodigo(rs.getInt("codigo")); //alterar
                objeto.setNome(rs.getString("nome"));  //alterar
                objeto.setPreco(rs.getDouble("preco"));
                objeto.setQuantidade_minima(rs.getInt("quantidade_minima"));
                objeto.setEstoque(rs.getInt("estoque"));
                
                
                
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
        Produto objeto = new Produto(); //alterar
        objeto.setNome(JOptionPane.showInputDialog("Digite o Nome:"));
        objeto.setPreco(Double.parseDouble(JOptionPane.showInputDialog("Digite o preço:")));
        objeto.setEstoque(Integer.parseInt(JOptionPane.showInputDialog("Digite o estoque")));
        objeto.setQuantidade_minima(Integer.parseInt(JOptionPane.showInputDialog("digite a quantidade minima:")));
        ProdutoDAO dao = new ProdutoDAO(); //alterar
        dao.adicionar(objeto); //alterar
        
    }
}
