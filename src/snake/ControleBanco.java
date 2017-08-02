package snake;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author fernandacunha
 */
public class ControleBanco {

    String ranking = "RECORDS\n\n";

    String url = "jdbc:postgresql://localhost/snakebd";

    public void salvarNoBanco(Jogador jogador) {

        Connection conexao;
        try {
            conexao = DriverManager.getConnection(url, "postgres", "postgres");

            String sql = "insert into jogador (nome, score) values (?, ?)";
            PreparedStatement pesquisa = conexao.prepareStatement(sql);
            pesquisa.setString(1, jogador.getNome());
            pesquisa.setInt(2, jogador.getScore());
            pesquisa.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ControleBanco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void mostrarRanking(Jogador jogador) {

        Connection conexao;

        try {
            conexao = DriverManager.getConnection(url, "postgres", "postgres");
            String sql = "select * from jogador order by score desc limit 5";
            PreparedStatement pesquisa = conexao.prepareStatement(sql);
            ResultSet resultado = pesquisa.executeQuery();

            while (resultado.next()) {
                String nome = resultado.getString("nome");
                String score = resultado.getString("score");
                ranking += "Jogador: " + nome + "\nScore:" + score + "\n\n";

            }
            JOptionPane.showMessageDialog(null, ranking);

        } catch (SQLException ex) {
            Logger.getLogger(ControleBanco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
