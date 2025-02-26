import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Sudoku {
     
    int boardWidth = 600;
    int boardHeight = 650;
    class Tile extends JButton {
        int r;
        int c;
        Tile(int r,int c){
            this.r=r;
            this.c=c;
        }
    }
    String[] puzzle = {
        "--74916-5",
        "2---6-3-9",
        "-----7-1-",
        "-586----4",
        "--3----9-",
        "--62--187",
        "9-4-7---2",
        "67-83----",
        "81--45---"
    };

    String[] solution = {
        "387491625",
        "241568379",
        "569327418",
        "758619234",
        "123784596",
        "496253187",
        "934176852",
        "675832941",
        "812945763"
    };


    JFrame frame = new JFrame("SUDOKU");
    Image icon = new ImageIcon(Sudoku.class.getResource("OIP.jpg")).getImage();
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton numSelected = null;
    int errors = 0;
    int blackTiles = 46;


    Sudoku(){
        
        frame.setVisible(true);
        frame.setIconImage(icon);
        frame.setSize(boardWidth,boardHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial",Font.BOLD, 30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Sudoku: 0");

        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(9,9));
        setupTiles();

        frame.add(boardPanel, BorderLayout.CENTER);

        buttonPanel.setLayout(new GridLayout(1,9));
        setupButtons();
        frame.add(buttonPanel,BorderLayout.SOUTH);

        frame.setVisible(true);


    }

    void setupTiles(){
        for(int r=0;r<9;r++){
            for(int c=0;c<9;c++){
                Tile tile = new Tile(r, c);
                char tileChar = puzzle[r].charAt(c);
                if(tileChar != '-'){

                    tile.setFont(new Font("Areal",Font.BOLD,20));
                    tile.setText(String.valueOf(tileChar));
                    tile.setBackground(Color.yellow);

                }else{
                    tile.setFont(new Font("Areal",Font.PLAIN,20));
                    tile.setBackground(Color.white);
                }
                if((r==2 && c==2)||(r==2 && c==5)||(r==5 && c==2)||(r==5 && c==5)){
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, Color.black));
                }
                else if(r==2 || r==5){
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, Color.black));
                }
                else if(c==2 || c==5){
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, Color.black));
                }
                else{
                    tile.setBorder(BorderFactory.createLineBorder(Color.black));
                }
                tile.setFocusable(false);
                boardPanel.add(tile);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if (blackTiles > 0) {
                        Tile tile = (Tile) e.getSource();
                        int r = tile.r;
                        int c = tile.c;
        
                        if(numSelected != null){
                            if(tile.getText() != ""){
                                return;
                            }
                            String numSelectedText = numSelected.getText();
                            String tileSolution = String.valueOf(solution[r].charAt(c));
                            if(numSelectedText.equals(tileSolution)){
                                tile.setText(numSelectedText);
                                tile.setBackground(Color.yellow);
                                blackTiles -=1;
                                
                            }else{
                                errors += 1;
                                textLabel.setText("Sudoku: " + String.valueOf(errors));
                            }
                        }
                    }else{
                        textLabel.setText("errors " + String.valueOf(errors));
                        showVictoryPanel();
                        
                    }


                    }
                });
            }
            
        }
    }

    void setupButtons() {
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 
    
        for (int i = 1; i < 10; i++) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.BOLD, 20)); 
            button.setText(String.valueOf(i));
            button.setFocusable(false);
            button.setBackground(Color.white);
            buttonPanel.add(button);

            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JButton button = (JButton) e.getSource();
                    if(numSelected != null){
                        numSelected.setBackground(Color.white);
                    }
                    numSelected = button;
                    numSelected.setBackground(Color.gray);
                }
                
            });
        }
    }
    private void showVictoryPanel() {
        
        frame.remove(boardPanel);
        
        JPanel victoryPanel = new JPanel();
        victoryPanel.setLayout(new BorderLayout());

        JLabel victoryLabel = new JLabel("VICTORY!");
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 50));
        victoryLabel.setHorizontalAlignment(JLabel.CENTER);
        victoryPanel.add(victoryLabel, BorderLayout.CENTER);
        victoryPanel.setBackground(Color.yellow);
        frame.add(victoryPanel, BorderLayout.CENTER);
        
        frame.revalidate();
        frame.repaint();
    }
    

}
