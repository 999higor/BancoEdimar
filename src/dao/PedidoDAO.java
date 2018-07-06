
package dao;

import config.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Pedido;


public class PedidoDAO 
{
    public boolean adicionar(Pedido objeto) { //alterar a classe do parâmetro
        try {
            String sql = "INSERT INTO pedido (previsao_entrega, data_hora ,codigo_fornecedor) VALUES (?,?,?)"; //alterar a tabela, os atributos e o número de interrogações, conforme o número de atributos

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);
            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setDate(1, objeto.getPrevisao_entrega()); // alterar o primeiro parâmetro indica a interrogação, começando em 1
            pstmt.setDate(2, objeto.getData_hora());
            pstmt.setInt(3, objeto.getCodigo_fornecedor());
            
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
                    + "    SET previsao_entrega = ?, data_hora = ?, codigo_fornecedor = ?"
                    + "  WHERE codigo = ? "; //alterar tabela, atributos e chave primária

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            //definindo as interrogações (uma linha para cada ? do SQL)
            pstmt.setDate(1, objeto.getPrevisao_entrega());
            pstmt.setDate(2, objeto.getData_hora());
            pstmt.setInt(3,objeto.getCodigo_fornecedor());

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
            String sql = " DELETE FROM pedido WHERE codigo = ? "; //alterar a tabela e a chave primária no WHERE

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
        String sql = "SELECT numero, previsao_entrega ,data_hora ,codigo_fornecedor FROM pedido ORDER BY data_hora"; //alterar tabela e atributos

        try {
            Statement stmt = Conexao.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Pedido> lista = new ArrayList<>(); //alterar classe

            while (rs.next()) {
                Pedido objeto = new Pedido(); //alterar o nome da classe e o construtor
                //setar os atributos do objeto. Cuidar o tipo dos atributos
                objeto.setNumero(rs.getInt("numero")); //alterar
                objeto.setPrevisao_entrega(rs.getDate("previsao_entrega"));  //alterar
                objeto.setData_hora(rs.getDate("data_hora"));
                objeto.setCodigo_fornecedor(rs.getInt("codigo_fornecedor"));
                
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
