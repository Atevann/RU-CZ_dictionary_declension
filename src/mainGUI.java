import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class mainGUI {
    private JTextField searchBox;
    private JButton searchButton;
    private JTextArea translationArea;
    private JTable tablePady;
    private JPanel panelMain;
    mainClass main = new mainClass();


    public mainGUI(){
        //initialize GUI
        JFrame frame = new JFrame("Перевести и просклонять");
        frame.setContentPane(panelMain);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On button click function "translate" will be started, search box will be emptied and request focus
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                translate(searchBox.getText());
                searchBox.setText("");
                searchBox.requestFocus();
            }
        });

        // Tap on enter is equal to tap on searchButton
        searchBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                translate(searchBox.getText());
                searchBox.setText("");
                searchBox.requestFocus();
            }
        });
    }

    public void translate (String word){
        // Getting word translation and formatting text to fill translationArea
        String translation = String.join("\n", main.translateWord(word));

        // Display translation result in translationArea
        translationArea.setText(translation);

        // Start filling tha table with parsed info from main.sklonenie function
        fillTable(main.sklonenie(word));
    }

   public void fillTable(ArrayList<String> word){
        //initialize and mark the table
        DefaultTableModel model = new DefaultTableModel();
        Object[] columns = {"Pád", "Čislo 1", "Čislo 2"};
        model.setColumnIdentifiers(columns);

        // parsing ArrayList
        String[] data = new String[3];
        for (int i=0; i<word.size()-2; i+=3){ //every 4-th word in ArrayList is a new row
            for (int q=0; q<3; q++){{ //after getting register to a new row parsing 3 words of the row
                data[q]=word.get(i+q);
            }}

            // adding parsed words to the table
            model.addRow(data);
        }
        tablePady.setEnabled(false);
        tablePady.setModel(model);
    }
}
