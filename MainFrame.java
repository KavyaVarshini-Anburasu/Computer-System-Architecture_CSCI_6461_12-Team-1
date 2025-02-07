/**
 * MainFrame - Graphical User Interface for the Assembler application.
 * This class represents the main window of the Assembler application,
 * enabling users to input and output file paths, browse files, and initiate
 * the assembly process.
 * 
 * @author [Kavya Varshini Anburasu, Khashayar, Devi - Team 1]
 */

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.io.File;
 import java.util.ArrayList;
 
 public class MainFrame extends JFrame {
     private JTextField inputPathField;
     private JTextField outputPathField;
     private JTextField listingPathField;
     private JTextArea resultTextArea;
 
     /**
      * Constructor for the MainFrame class.
      * Initializes the GUI components and sets up the main window.
      */
 
     public MainFrame() {
         setTitle("Project 0 - Assembler");
         setSize(500, 390);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
         JPanel container = new JPanel();
         container.setBackground(new Color(131, 105, 83));
         add(container);
         arrangeComponents(container);
 
         setVisible(true);
     }
 
     /**
      * Helper method to arrange GUI components on the panel.
      *
      * @param container The JPanel to which components are added.
      */
 
     private void arrangeComponents(JPanel container) { // We are arranging the components in the panel using absolute positioning.
         container.setLayout(null);
 
         JLabel inputLabel = new JLabel("Enter Input File:");
         inputLabel.setBounds(30, 20, 150, 25);
         inputLabel.setForeground(Color.BLACK);
         container.add(inputLabel);
 
         inputPathField = new JTextField(20);
         inputPathField.setBounds(140, 20, 200, 25);
         inputPathField.setBackground(new Color(255, 250, 240));
         inputPathField.setForeground(Color.BLACK);
         container.add(inputPathField);
 
         JButton inputBrowseButton = new JButton("Browse");
         inputBrowseButton.setBounds(350, 20, 80, 25);
         inputBrowseButton.setBackground(new Color(123, 104, 238)); // Button background color (Medium Slate Blue)
         inputBrowseButton.setForeground(new Color(131, 105, 83)); // Text color (Brown)
         container.add(inputBrowseButton);
 
         JLabel outputLabel = new JLabel("Enter Output File:");
         outputLabel.setBounds(30, 50, 150, 25);
         outputLabel.setForeground(Color.BLACK);
         container.add(outputLabel);

         outputPathField = new JTextField(20);
         outputPathField.setBounds(140, 50, 200, 25);
         outputPathField.setBackground(new Color(255, 250, 240)); // Output field background (Floral White)
         outputPathField.setForeground(Color.BLACK); 
         container.add(outputPathField);
 
         JButton outputBrowseButton = new JButton("Browse");
         outputBrowseButton.setBounds(350, 50, 80, 25);
         outputBrowseButton.setBackground(new Color(123, 104, 238)); // Button background color (Medium Slate Blue)
         outputBrowseButton.setForeground(new Color(131, 105, 83)); // Text color (Brown)
         container.add(outputBrowseButton);
 
         JLabel listingLabel = new JLabel("Enter Listing File:");
         listingLabel.setBounds(30, 80, 150, 25);
         listingLabel.setForeground(Color.BLACK);
         container.add(listingLabel);

         listingPathField = new JTextField(20);
         listingPathField.setBounds(140, 80, 200, 25);
         listingPathField.setBackground(new Color(255, 250, 240)); // Output field background (Floral White)
         listingPathField.setForeground(Color.BLACK); 
         container.add(listingPathField);
 
         JButton listingBrowseButton = new JButton("Browse");
         listingBrowseButton.setBounds(350, 80, 80, 25);
         listingBrowseButton.setBackground(new Color(123, 104, 238)); // Button background color (Medium Slate Blue)
         listingBrowseButton.setForeground(new Color(131, 105, 83)); // Text color (Brown)
         container.add(listingBrowseButton);

         JButton executeButton = new JButton("Assemble");
         executeButton.setBounds(185, 120, 100, 25);
         executeButton.setBackground(new Color(72, 61, 139)); // Button background color (Dark Slate Blue)
         executeButton.setForeground(new Color(131, 105, 83)); // Text color (Brown)
         container.add(executeButton);
 
         resultTextArea = new JTextArea();
         resultTextArea.setBackground(new Color(255, 255, 255)); // Text area background (White)
         resultTextArea.setForeground(Color.BLACK);
         JScrollPane scrollPane = new JScrollPane(resultTextArea);
         scrollPane.setBounds(30, 160, 420, 180);
         container.add(scrollPane);
 
         // Button action listeners
         
         inputBrowseButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 JFileChooser fileChooser = new JFileChooser();
                 int returnValue = fileChooser.showOpenDialog(null);
                 if (returnValue == JFileChooser.APPROVE_OPTION) {
                     File selectedFile = fileChooser.getSelectedFile();
                     inputPathField.setText(selectedFile.getAbsolutePath());
                 }
             }
         });
 
         outputBrowseButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 JFileChooser fileChooser = new JFileChooser();
                 int returnValue = fileChooser.showSaveDialog(null);
                 if (returnValue == JFileChooser.APPROVE_OPTION) {
                     File selectedFile = fileChooser.getSelectedFile();
                     outputPathField.setText(selectedFile.getAbsolutePath());
                 }
             }
         });

         listingBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    listingPathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
 
         executeButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String inputPath = inputPathField.getText();
                 String outputPath = outputPathField.getText();
                 String listingPath = listingPathField.getText();

                 ArrayList<String> fileLines = FileController.readLinesFromFile(inputPath, true);
                 AssemblerProgram assembler = new AssemblerProgram();
                 ArrayList<String> assemblyOutputs = assembler.parseInstructions(fileLines);
 
                 for (String line : assemblyOutputs) {
                     resultTextArea.append(line + "\n");
                 }
 
                 if (!outputPath.isEmpty()) {
                     FileController.writeLinesToFile(outputPath, assemblyOutputs);
                     createListingFile(inputPath, listingPath, assemblyOutputs);
                     JOptionPane.showMessageDialog(null, "Assembling completed. Output written to: " + outputPath);
                 }
             }
         });
     }
     private void createListingFile(String sourceFile, String targetFile, ArrayList<String> results) {
        ArrayList<String> sourceLines = FileController.readLinesFromFile(sourceFile, true);
        ArrayList<String> finalContent = new ArrayList<>();
        
        int maxLines = Math.max(sourceLines.size(), results.size());
        
        for (int i = 0; i < maxLines; i++) {
            String sourceLine = (i < sourceLines.size()) ? sourceLines.get(i) : "";
            String resultLine = (i < results.size()) ? results.get(i) : "";
            
            finalContent.add(String.format("%-40s %s", resultLine, sourceLine)); // Adjust the format to suit your needs
        }
        
        FileController.writeLinesToFile(targetFile, finalContent);
    }
    
     /**
      * Main method to launch the Assembler application.
      * @param args Command-line arguments (not used).
      */
 
     public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             public void run() {
                 new MainFrame();
             }
         });
     }
 }
 