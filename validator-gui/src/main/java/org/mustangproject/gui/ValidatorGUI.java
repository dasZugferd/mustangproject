package org.mustangproject.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.mustangproject.validator.ZUGFeRDValidator;

/**
 * Einfache grafische Oberfläche zum Validieren von ZUGFeRD/Factur-X Dateien.
 */
public class ValidatorGUI extends JFrame {
    private final JTextArea outputArea = new JTextArea();

    public ValidatorGUI() {
        super("ZUGFeRD Validator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton openButton = new JButton("Datei wählen...");
        openButton.addActionListener(this::chooseFile);
        add(openButton, BorderLayout.NORTH);

        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private void chooseFile(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            validateFile(file);
        }
    }

    private void validateFile(File file) {
        outputArea.setText("Validiere " + file.getName() + "...\n");
        ZUGFeRDValidator validator = new ZUGFeRDValidator();
        String result = validator.validate(file.getAbsolutePath());
        outputArea.append(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ValidatorGUI gui = new ValidatorGUI();
            gui.setVisible(true);
        });
    }
}
