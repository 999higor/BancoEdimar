
package dao;

import config.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Pedido;
import java.sql.Date;


public class PedidoDAO 
{
    public boolean adicionar(Pedido objeto) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO pedido (previsao_entrega ,codigo_fornecedor ,codigo_produto) VALUES (?,?,?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setDate(1, new Date (objeto.getPrevisao_entrega().getTime())); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            
            pstmt.setInt(2, objeto.getCodigo_fornecedor());
            pstmt.setInt(3, objeto.getCodigo_produto());
            
            pstmt.executeUpdate(); //executando
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Pedido objeto) {
        try {
            String sql = " UPDATE pedido "
                    + "    SET previsao_entrega = ?, codigo_fornecedor = ? ,codigo_produto = ?"
                    + "  WHERE numero = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setDate(1, new Date (objeto.getPrevisao_entrega().getTime()));
           // pstmt.setDate(2, objeto.getData_hora());
            pstmt.setInt(2,objeto.getCodigo_fornecedor());
            pstmt.setInt(3,objeto.getCodigo_produto());
            pstmt.setInt(4, objeto.getNumero());

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean excluir(Pedido objeto) {
        try {
            String sql = " DELETE FROM pedido WHERE numero = ? "; //alterar a tabela e a chave primária no WHERE

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            pstmt.setInt(1, objeto.getNumero()); //alterar conforme a chave primária

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Pedido> selecionar() {
        String sql = "SELECT p.numero, p.previsao_entrega ,p.data_hora ,p.codigo_fornecedor ,p.codigo_produto, pr.nome AS nomeProduto ,f.nome_fornecedor AS nomeFornecedor FROM pedido p JOIN produto pr ON pr.codigo = p.codigo_produto JOIN fornecedor f ON f.codigo = p.codigo_fornecedor ORDER BY data_hora"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Pedido> lista = new ArrayList<>(); //alterar classe

            while (rs.next()) {
                Pedido objeto = new Pedido(); //alterar o nome da classe e o construtor
                //setar os atributos do objeto. Cuidar o tipo dos atributos
                objeto.setNumero(rs.getInt("numero")); //alterar
                objeto.setPrevisao_entrega(rs.getDate("previsao_entrega"));  //alterar
                objeto.setData_hora(rs.getTimestamp("data_hora"));
                objeto.setCodigo_fornecedor(rs.getInt("codigo_fornecedor"));
                objeto.setCodigo_produto(rs.getInt("codigo_produto"));
                objeto.setDescricaoProduto(rs.getString("nomeProduto"));
                objeto.setNomeFornecedor(rs.getString("nomeFornecedor"));
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
        Pedido objeto = new Pedido(); //alterar
        //objeto.setDescricao(JOptionPane.showInputDialog("Digite o Nome:"));
        PedidoDAO dao = new PedidoDAO(); //alterar
        dao.adicionar(objeto); //alterar
    }
}
