
package dao;

import config.Conexao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Produto;


public class EntradaSaidaDAO 
{
    public boolean alterar(Produto objeto) 
    {
        try {
            String sql = " UPDATE produto "
                    + "    SET estoque = ?"
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            
           
            pstmt.setInt(1, objeto.getEstoque());           
            pstmt.setInt(2, objeto.getCodigo());

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
