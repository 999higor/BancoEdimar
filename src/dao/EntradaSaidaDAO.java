
package dao;

import config.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Produto;


public class EntradaSaidaDAO 
{
    public boolean baixaEstoque(Produto objeto) 
    {
        try {
            String sql = " UPDATE produto "
                    + "    SET estoque = estoque - ?"
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            
           
            pstmt.setInt(1, objeto.getBaixaEstoque());           
            pstmt.setInt(2, objeto.getCodigo());

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public boolean entradaEstoque(Produto objeto) 
    {
        try {
            String sql = " UPDATE produto "
                    + "    SET estoque = estoque + ?"
                    + "    WHERE codigo = ? "; //alterar tabela, atributos e chave primária
            
            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            
           
            pstmt.setInt(1, objeto.getEntradaEstoque());           
            pstmt.setInt(2, objeto.getCodigo());

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
    
    public List<Produto> selecionar() 
    {
        String sql = "SELECT p.codigo ,p.nome AS nome ,m.descricao AS marca ,p.estoque FROM produto p JOIN marca m ON m.codigo = p.codigo_marca ORDER BY nome"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Produto> lista = new ArrayList<>(); //alterar classe

            while (rs.next()) {
                Produto objeto = new Produto(); //alterar o nome da classe e o construtor

                //setar os atributos do objeto. Cuidar o tipo dos atributos
                objeto.setCodigo(rs.getInt("codigo")); //alterar
                objeto.setNome(rs.getString("nome"));  //alterar
                //objeto.setPreco(rs.getDouble("preco"));
                //objeto.setQuantidade_minima(rs.getInt("quantidade_minima"));
                objeto.setDescricaoMarca(rs.getString("marca"));
                objeto.setEstoque(rs.getInt("estoque"));
                
                //objeto.setCodigo_marca(rs.getInt("CodMarca"));
                
                
                lista.add(objeto);
            }
            stmt.close();
            return lista;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
