package testrismenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


public class ButtonControls extends JButton {
    
    
    private InputHandler input;
    
    public ButtonControls() {
    }
    
    public ButtonControls(JButton btnC, InputHandler input) throws Exception {
        
        this.input = input;
//        btnC.setBackground(new Color(161, 161, 161));
//        btnC.setHorizontalAlignment(CENTER);
        
        MenuControls mc = new MenuControls(this.input);
        mc.setVisible(false);
        
//        btnC.setContentAreaFilled(false);
        btnC.setOpaque(true);
        
        btnC.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mc.setVisible(true);
            }
        });
    }
}
