import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import static java.awt.AWTEvent.INPUT_METHOD_EVENT_MASK;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_S;

/**
 * Shinn Edit text editing GUI
 * @author Patrick Shinn
 * @version 5/9/16
 */
public class GUI extends javax.swing.JFrame {

    // global variables
    private String[] settings = new String[3];
    private File selectedFile;
    private Boolean fileOpened = false;
    private final JFileChooser chooser = new JFileChooser();
    private String oldLine = "";
    private javax.swing.JLabel lblFonts = new javax.swing.JLabel();
    private javax.swing.JFrame aboutFrame;
    private javax.swing.JComboBox<String> comboFonts;
    private javax.swing.JComboBox<String> comboPoint;
    private javax.swing.JComboBox<String> comboThemes;
    private javax.swing.JDialog saveWarning;
    private javax.swing.JTextArea textArea;

    // builds GUI
    public GUI() {
        initComponents();
        loadFile();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt", "text");
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("java files", "java");
        saveWarning.setLocationRelativeTo(this);
        chooser.setFileFilter(filter);
        chooser.setFileFilter(filter1);
    }

    // builds the GUI components.
    private void initComponents() {
        // Create and set application icon
        ImageIcon image = new ImageIcon("icon.png");
        setIconImage(image.getImage());

        // variable declaration
        saveWarning = new javax.swing.JDialog();
        javax.swing.JPanel savePanel = new javax.swing.JPanel();
        javax.swing.JLabel warningLabel = new javax.swing.JLabel();
        javax.swing.JButton btnSave = new javax.swing.JButton();
        javax.swing.JButton bntDontSave = new javax.swing.JButton();
        aboutFrame = new javax.swing.JFrame();
        javax.swing.JPanel aboutPanel = new javax.swing.JPanel();
        javax.swing.JLabel appName = new javax.swing.JLabel();
        javax.swing.JLabel appVersion = new javax.swing.JLabel();
        javax.swing.JPanel mainPanel = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        comboFonts = new javax.swing.JComboBox<>();
        comboPoint = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblPt = new javax.swing.JLabel();
        javax.swing.JSeparator separator = new javax.swing.JSeparator();
        comboThemes = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblTheme = new javax.swing.JLabel();
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu menuFile = new javax.swing.JMenu();
        javax.swing.JMenuItem menuClose = new javax.swing.JMenuItem();
        javax.swing.JMenuItem fileOpen = new javax.swing.JMenuItem();
        javax.swing.JMenuItem fileSave = new javax.swing.JMenuItem();
        javax.swing.JMenuItem fileSaveAs = new javax.swing.JMenuItem();
        javax.swing.JMenu menuHelp = new javax.swing.JMenu();
        javax.swing.JMenuItem helpAbout = new javax.swing.JMenuItem();

        //set up warning dialogue
        saveWarning.setLocationRelativeTo(saveWarning.getParent());
        saveWarning.setModal(true);
        saveWarning.setTitle("File Not Saved");
        saveWarning.setMinimumSize(new java.awt.Dimension(532, 137));
        saveWarning.setResizable(false);
        warningLabel.setText("You are about to close without saving, would you like to save?");
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        bntDontSave.setText("Don't Save");
        bntDontSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntDontSaveActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(savePanel);
        savePanel.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(185, 185, 185)
                                                .addComponent(bntDontSave, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(warningLabel))
                                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(warningLabel)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSave)
                                        .addComponent(bntDontSave))
                                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout saveWarningLayout = new javax.swing.GroupLayout(saveWarning.getContentPane());
        saveWarning.getContentPane().setLayout(saveWarningLayout);
        saveWarningLayout.setHorizontalGroup(
                saveWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(savePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        saveWarningLayout.setVerticalGroup(
                saveWarningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(saveWarningLayout.createSequentialGroup()
                                .addComponent(savePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        // setting up aboutFrame
        aboutFrame.setLocationRelativeTo(this);
        aboutFrame.setTitle("About");
        aboutFrame.setMinimumSize(new java.awt.Dimension(395, 100));
        aboutFrame.setSize(new java.awt.Dimension(395, 100));
        appName.setText("Shinn Edit");
        appVersion.setText("Version: 1.0");
        javax.swing.GroupLayout aboutPanelLayout = new javax.swing.GroupLayout(aboutPanel);
        aboutPanel.setLayout(aboutPanelLayout);
        aboutPanelLayout.setHorizontalGroup(
                aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(aboutPanelLayout.createSequentialGroup()
                                .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(aboutPanelLayout.createSequentialGroup()
                                                .addGap(158, 158, 158)
                                                .addComponent(appVersion))
                                        .addGroup(aboutPanelLayout.createSequentialGroup()
                                                .addGap(164, 164, 164)
                                                .addComponent(appName)))
                                .addContainerGap(141, Short.MAX_VALUE))
        );
        aboutPanelLayout.setVerticalGroup(
                aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(aboutPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(appName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(appVersion))
        );

        javax.swing.GroupLayout aboutFrameLayout = new javax.swing.GroupLayout(aboutFrame.getContentPane());
        aboutFrame.getContentPane().setLayout(aboutFrameLayout);
        aboutFrameLayout.setHorizontalGroup(
                aboutFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(aboutFrameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(aboutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        aboutFrameLayout.setVerticalGroup(
                aboutFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(aboutFrameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(aboutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );



        // text area setup
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        // fonts combo box setup
        lblFonts.setText("Fonts");
        comboFonts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Arial", "Sans", "Ubuntu", "Monospace", "Courier"}));
        comboFonts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFontsActionPerformed(evt);
            }
        });

        // point combo box setup
        lblPt.setText("Point");
        comboPoint.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"12", "14", "16", "18", "20", "24", "30", "34"}));
        comboPoint.setMaximumSize(new java.awt.Dimension(95, 25));
        comboPoint.setMinimumSize(new java.awt.Dimension(95, 25));
        comboPoint.setPreferredSize(new java.awt.Dimension(95, 25));
        comboPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPointActionPerformed(evt);
            }
        });

        // themes combo box setup
        lblTheme.setText("Themes");
        comboThemes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Light", "Dark"}));
        comboThemes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboThemesActionPerformed(evt);
            }
        });

        // Main panel setup
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(comboFonts, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblFonts, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2) // gap between comboPoint and comboFont
                                                .addComponent(comboPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblPt)
                                                .addGap(20) // gap between comboThemes and comboPoint
                                                .addComponent(comboThemes, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblTheme)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(separator, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(comboFonts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblFonts)
                                                        .addComponent(comboPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblPt)
                                                        .addComponent(comboThemes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblTheme))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                                .addContainerGap())
        );

        // menu setup
        menuBar.setBackground(new java.awt.Color(51, 51, 51));
        menuBar.setBorder(null);
        menuBar.setEnabled(false);
        menuBar.setMaximumSize(new java.awt.Dimension(37, 32769));
        menuBar.setMinimumSize(new java.awt.Dimension(37, 2));
        menuBar.setName(""); // NOI18N
        menuBar.setPreferredSize(new java.awt.Dimension(37, 21));
        menuFile.setText("File");
        menuClose.setText("Close");
        menuClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCloseActionPerformed(evt);
            }
        });
        menuFile.add(menuClose);
        fileOpen.setText("Open");
        fileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenActionPerformed(evt);
            }
        });
        menuFile.add(fileOpen);
        fileSave.setText("Save");
        fileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveActionPerformed(evt);
            }
        });
        menuFile.add(fileSave);
        fileSaveAs.setText("Save As");
        fileSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveAsActionPerformed(evt);
            }
        });
        menuFile.add(fileSaveAs);
        menuBar.add(menuFile);
        menuHelp.setText("Help");
        helpAbout.setText("About");
        helpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuHelp.add(helpAbout);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        // final setup of main jFrame
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Shinn Edit");
        setSize(new java.awt.Dimension(923, 589));
        setLocationRelativeTo(null);


        // Set closing operations
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // checks if the user saved the document before they close and lose it forever.
                if (!oldLine.equals(textArea.getText())) {
                    saveWarning.setVisible(true);
                }
                try {
                    FileOutputStream fos = new FileOutputStream("settings.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(settings);
                    oos.close();
                    fos.close();
                } catch (IOException error) {
                    // do nothing
                }
                System.exit(0);
            }
        };
        addWindowListener(exitListener);

        // Set Keyboard Commands
        menuBar.setEnabled(true);
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(( VK_O), InputEvent.CTRL_DOWN_MASK));
        fileSave.setAccelerator(KeyStroke.getKeyStroke((VK_S), InputEvent.CTRL_DOWN_MASK));
        menuClose.setAccelerator(KeyStroke.getKeyStroke(("alt C")));
        fileSaveAs.setAccelerator(KeyStroke.getKeyStroke("ctrl alt S"));
    }

    // Opens a file of the user's choice for editing.
    private void fileOpenActionPerformed(java.awt.event.ActionEvent evt) {
        FileReader myReader;
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            try {
                myReader = new FileReader(selectedFile);
                Scanner scan = new Scanner(myReader);
                String text = "";
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    text += line + "\n";
                }
                textArea.setText(text);
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                fileOpened = true;
            } catch (IOException error) {
                // do nothing
            }

        }
    }

    /*
    Saves the text file. If the file has not been saved before, a new save file is generated, otherwise it is saved
    to the last file that it was saved to.
     */
    private void fileSaveActionPerformed(java.awt.event.ActionEvent evt) {
        String line = textArea.getText();
        if (!fileOpened) {
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File fileToSave = chooser.getSelectedFile();
                try {
                    PrintWriter saveFile = new PrintWriter(fileToSave);
                    saveFile.print(line);
                    saveFile.close();
                    selectedFile = fileToSave;
                    fileOpened = true;
                    oldLine = line;
                } catch (IOException error) {
                    // do nothing
                }
            }
        } else {
            try {
                PrintWriter writer = new PrintWriter(selectedFile);
                writer.print(line);
                writer.close();
                oldLine = line;
            } catch (IOException error) {
                // do nothing
            }
        }

    }

    // Save file under a new name, save as
    private void fileSaveAsActionPerformed(java.awt.event.ActionEvent evt) {
        String line = textArea.getText();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            try {
                PrintWriter saveFile = new PrintWriter(fileToSave);
                saveFile.print(line);
                saveFile.close();
                selectedFile = fileToSave;
                fileOpened = true;
                oldLine = line;
            } catch (IOException error) {
                // do nothing
            }
        }
    }

    // Closes the current file, if not saved, it will ask the user if they want to save their work
    private void menuCloseActionPerformed(java.awt.event.ActionEvent evt) {
        if (!oldLine.equals(textArea.getText())) {
            saveWarning.setVisible(true);
        } else {
            fileOpened = false;
            textArea.setText("");
        }
    }

    // Saves the users work in a file not saved warning.
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        String line = textArea.getText();
        if (!fileOpened) {
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File fileToSave = chooser.getSelectedFile();
                try {
                    PrintWriter saveFile = new PrintWriter(fileToSave);
                    saveFile.print(line);
                    saveFile.close();
                    selectedFile = fileToSave;
                    fileOpened = true;
                    oldLine = line;
                } catch (IOException error) {
                    // do nothing
                }
            }
        } else {
            try {
                PrintWriter writer = new PrintWriter(selectedFile);
                writer.print(line);
                writer.close();
                oldLine = line;
            } catch (IOException error) {
                // do nothing
            }
        }
        saveWarning.setVisible(false);
    }

    // Does not save the users work in a file not saved warning.
    private void bntDontSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntDontSaveActionPerformed
        fileOpened = false;
        textArea.setText("");
        saveWarning.setVisible(false);
    }//GEN-LAST:event_bntDontSaveActionPerformed

    // Shows the about frame.
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        aboutFrame.setVisible(true);
    }

    // Allows the user to select a font from a list and apply it.
    private void comboFontsActionPerformed(java.awt.event.ActionEvent evt) {
        int selection = comboFonts.getSelectedIndex();
        String selectedFont = comboFonts.getItemAt(selection);
        int currentFontSize = textArea.getFont().getSize();
        Font newFont = new Font(selectedFont, Font.PLAIN, currentFontSize);
        textArea.setFont(newFont);
        lblFonts.setText(selectedFont);
        settings[1] = selectedFont;
    }

    // Allows the user to select a font size from a list, them applies it.
    private void comboPointActionPerformed(java.awt.event.ActionEvent evt) {
        String fontName = textArea.getFont().getFontName();
        int selectedSizeIndex = comboPoint.getSelectedIndex();
        int newSize = Integer.parseInt(comboPoint.getItemAt(selectedSizeIndex));
        Font font = new Font(fontName, Font.PLAIN, newSize);
        textArea.setFont(font);
        settings[2] = String.valueOf(newSize);
    }

    // Allows the user to select a theme from a list, then applies it.
    private void comboThemesActionPerformed(java.awt.event.ActionEvent evt) {
        if (comboThemes.getSelectedIndex() == 0) {
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.black);
        } else {
            textArea.setBackground(Color.darkGray);
            textArea.setForeground(Color.white);
        }
        int currentSelection = comboThemes.getSelectedIndex();
        settings[0] = comboThemes.getItemAt(currentSelection);
    }

    // loads the setting file if it exists, then applies the settings.
    private void loadFile() { // loads saved settings
        try {
            FileInputStream fis = new FileInputStream("settings.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            settings = (String[]) ois.readObject();
            ois.close();
            fis.close();

        } catch (IOException | ClassNotFoundException error) {
            settings[0] = "Light";
            settings[1] = "Arial";
            settings[2] = "12";
        }
        String fontName = settings[1];
        int fontSize = Integer.parseInt(settings[2]);
        Font font = new Font(fontName, Font.PLAIN, fontSize);
        textArea.setFont(font);
        comboThemes.setSelectedItem(settings[0]);
        comboFonts.setSelectedItem(settings[1]);
        comboPoint.setSelectedItem(settings[2]);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // tries to set nimbus look and feel.
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // creates and displays GUI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}
