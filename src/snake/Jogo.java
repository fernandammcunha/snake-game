package snake;

import static java.awt.AWTEventMulticaster.add;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Pos;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author fernandacunha
 */
public class Jogo extends JFrame {

    int score = 0;
    String direcao = "direita";
    Random random = new Random();

    ImageIcon iconhead = new ImageIcon(getClass().getResource("head.png"));
    ImageIcon icontail = new ImageIcon(getClass().getResource("tail.png"));
    ImageIcon iconfood = new ImageIcon(getClass().getResource("food.png"));

    JLabel lhead = new JLabel(iconhead);
    List<JLabel> ltail = new ArrayList<>();
    int initialtail = 1;

    JLabel lfood = new JLabel(iconfood);

    int posHeadX = 100;
    int posHeadY = 80;

    int posFoodX = 0;
    int posFoodY = 0;

    JFrame janela = new JFrame();

    public Jogo() {
        for (int i = 0; i < initialtail; i++) {
            this.ltail.add(new JLabel(icontail));
        }

        EditarJanela();

        new MovimentoAutomatico().start();

    }

    public void ColocarComida() {

        posFoodX = random.nextInt(500);
        posFoodY = random.nextInt(500);

        if (posFoodX <= 20) {
            posFoodX = 40;
        }
        if (posFoodX >= 480) {
            posFoodX = 400;
        }
        if (posFoodY <= 20) {
            posFoodY = 40;
        }
        if (posFoodY >= 480) {
            posFoodY = 400;
        }

        lfood.setBounds(posFoodX, posFoodY, 20, 20);

    }

    public void EditarJanela() {

        lhead.setBounds(posHeadX, posHeadY, 20, 20);

        for (int i = 0; i < ltail.size(); i++) {
            ltail.get(i).setBounds(posHeadX - (i * 20), posHeadY, 20, 20);
        }
        ColocarComida();

        janela.setTitle("Jogo da Cobrinha");
        janela.setSize(500, 500);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
        janela.add(lhead);

        for (int i = 0; i < ltail.size(); i++) {

            janela.add(ltail.get(i));

        }
        janela.add(lfood);

        JPanel background = new JPanel();

        background.setLayout(null);
        background.setBounds(0, 0, janela.getWidth(), janela.getHeight());
        background.setBackground(Color.BLACK);
        background.setVisible(true);
        //janela.add(background);

        janela.setFocusable(true);
        janela.requestFocusInWindow();
        janela.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (!direcao.equals("direita") && !direcao.equals("esquerda")) {
                        direcao = "direita";

                    }

                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (!direcao.equals("direita") && !direcao.equals("esquerda")) {
                        direcao = "esquerda";

                    }

                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (!direcao.equals("cima") && !direcao.equals("baixo")) {
                        direcao = "cima";

                    }

                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (!direcao.equals("cima") && !direcao.equals("baixo")) {
                        direcao = "baixo";

                    }

                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });

    }

    public boolean morreu() {

        if (direcao.equals("direita") && lhead.getX() >= 480) {
            return true;

        }
        if (direcao.equals("esquerda") && lhead.getX() <= 0) {
            return true;

        }
        if (direcao.equals("baixo") && lhead.getY() >= 460) {
            return true;

        }
        if (direcao.equals("cima") && lhead.getY() <= 0) {
            return true;

        }
        for (int i = ltail.size()-1; i >2; i--) {
            if (ltail.get (i).getX() == lhead.getX() && ltail.get(i).getY() == lhead.getY()) {
                return true;
            }
        }

        return false;

    }

    //Verifica se houve colisão de qualquer lado!
    public boolean comeu(Component a, Component b) {
        int aX = a.getX();
        int aY = a.getY();
        int ladoDireitoA = aX + a.getWidth();
        int ladoEsquerdoA = aX;
        int ladoBaixoA = aY + a.getHeight();
        int ladoCimaA = aY;

        int bX = b.getX();
        int bY = b.getY();
        int ladoDireitoB = bX + b.getWidth();
        int ladoEsquerdoB = bX;
        int ladoBaixoB = bY + b.getHeight();
        int ladoCimaB = bY;

        boolean comeu = false;

        boolean cDireita = false;
        boolean cCima = false;
        boolean cBaixo = false;
        boolean cEsquerda = false;

        if (ladoDireitoA >= ladoEsquerdoB) {
            cDireita = true;
        }
        if (ladoCimaA <= ladoBaixoB) {
            cCima = true;
        }
        if (ladoBaixoA >= ladoCimaB) {
            cBaixo = true;
        }
        if (ladoEsquerdoA <= ladoDireitoB) {
            cEsquerda = true;
        }

        if (cDireita && cEsquerda && cBaixo && cCima) {
            comeu = true;
        }

        return comeu;
    }

    public class MovimentoAutomatico extends Thread {

        public void run() {
            while (true) {
                if (!morreu()) {

                    try {
                        sleep(350);
                    } catch (Exception erro) {
                    }

                    if (direcao.equals("direita")) {

                        for (int i = ltail.size() - 1; i > 0; i--) {
                            ltail.get(i).setBounds(ltail.get(i - 1).getX(), ltail.get(i - 1).getY(), 20, 20);

                        }
                        ltail.get(0).setBounds(lhead.getX(), lhead.getY(), 20, 20);
                        lhead.setBounds(lhead.getX() + 20, lhead.getY(), 20, 20);

                    } else if (direcao.equals("baixo")) {
                        for (int i = ltail.size() - 1; i > 0; i--) {
                            ltail.get(i).setBounds(ltail.get(i - 1).getX(), ltail.get(i - 1).getY(), 20, 20);

                        }
                        ltail.get(0).setBounds(lhead.getX(), lhead.getY(), 20, 20);
                        lhead.setBounds(lhead.getX(), lhead.getY() + 20, 20, 20);

                    } else if (direcao.equals("cima")) {
                        for (int i = ltail.size() - 1; i > 0; i--) {
                            ltail.get(i).setBounds(ltail.get(i - 1).getX(), ltail.get(i - 1).getY(), 20, 20);

                        }
                        ltail.get(0).setBounds(lhead.getX(), lhead.getY(), 20, 20);
                        lhead.setBounds(lhead.getX(), lhead.getY() - 20, 20, 20);

                    } else if (direcao.equals("esquerda")) {
                        for (int i = ltail.size() - 1; i > 0; i--) {
                            ltail.get(i).setBounds(ltail.get(i - 1).getX(), ltail.get(i - 1).getY(), 20, 20);

                        }
                        ltail.get(0).setBounds(lhead.getX(), lhead.getY(), 20, 20);
                        lhead.setBounds(lhead.getX() - 20, lhead.getY(), 20, 20);

                    }

                    if (comeu(lhead, lfood)) {

                        score += 100;

                        JLabel newtail = new JLabel(icontail);

                        ltail.add(newtail);
                        janela.add(newtail);

                        ColocarComida();

                    }
                } else {
                    JFinicio obj = new JFinicio();
                    Jogador jog = new Jogador();
                    jog.setScore(score);
                    jog.setNome(obj.nome);

                    JOptionPane.showMessageDialog(null, "Game Over!!\n"
                            + "Jogador: " + jog.getNome()
                            + "\nPontuação: " + jog.getScore());

                    ControleBanco banco = new ControleBanco();
                    banco.salvarNoBanco(jog);
                    banco.mostrarRanking(jog);

                    System.exit(0);

                }

            }

        }

    }

}
