/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package timer;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



/**
 *
 * @author noeyyeon
 */

public class Test {
    
    // Context Part
    public static void main(String[] args) {
        new Test();
    }
    
    // Srate Part
    public Test() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing"); 
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane()); // call Concrete states
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
    
    //Concrete Stages Part
    public class TestPane extends JPanel {

        private LocalDateTime startTime;
        private JLabel label;
        private Timer timer;
        JTextField textfield1 = new JTextField("60",6);
        JTextField textfield2 = new JTextField("0",6);
        JTextField textfield3 = new JTextField("0",6);
        
        

        private Duration duration = Duration.ofSeconds(60);
        

        public TestPane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(2, 2, 2, 2);
            gbc.gridwidth = GridBagConstraints.REMAINDER;

            label = new JLabel("Countdown Timer");
            add(label, gbc);
            
            add(textfield3);
            add(textfield2);
            add(textfield1);

            JButton btn = new JButton("Start");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = textfield1.getText();
                    int i = Integer.parseInt(text);
                    String text2 = textfield2.getText();
                    int i2 = Integer.parseInt(text2);
                    String text3 = textfield3.getText();
                    int i3 = Integer.parseInt(text3);
                    
                    if(i2>0 || i3>0){
                        i += i2*60;
                        i += i3*60*60;}
                    
                    
                    
                    duration = Duration.ofSeconds(i);
                    if (timer.isRunning()) {
                        timer.stop();
                        startTime = null;
                        btn.setText("Start");
                    } else {
                        startTime = LocalDateTime.now();
                        timer.start();
                        btn.setText("Stop");
                    }
                }
            });
            add(btn, gbc);

            timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LocalDateTime now = LocalDateTime.now();
                    Duration runningTime = Duration.between(startTime, now);
                    Duration timeLeft = duration.minus(runningTime);
                    if (timeLeft.isZero() || timeLeft.isNegative()) {
                        timeLeft = Duration.ZERO;
                        btn.doClick(); // Cheat
                    }

                    label.setText(format(timeLeft));
                }
            });
        }

        protected String format(Duration duration) {
            long hours = duration.toHours();
            long mins = duration.minusHours(hours).toMinutes();
            long seconds = duration.minusMinutes(mins).toMillis() / 1000;

            
            return String.format("%02dh %02dm %02ds", hours, mins, seconds % 100);
        }

    }

}

