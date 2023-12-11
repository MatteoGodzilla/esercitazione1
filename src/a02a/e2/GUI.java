package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButton> cells = new ArrayList<>();
    private int counter = 0;
    
    public GUI(int size) {
        Controller controller = new ControllerImpl();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);

        controller.setDimentions(size, size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);

        controller.onStep((position,value) -> {
            if(value.isPresent()){
                int index = position.getY() * size + position.getX();
                this.cells.get(index).setText(String.valueOf(value.get()));
            } else {
                System.exit(0);
            }
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.add(jb);
                jb.addActionListener(e -> controller.step());
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
