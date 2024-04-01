package components;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import special.event.Event;
import special.event.Main;

import java.awt.Desktop;
import java.util.logging.Logger;

public class ImageUploader extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private transient Path copiedFilePath;
    private static final Logger logger = Logger.getLogger(ImageUploader.class.getName());

    private JLabel imageLabel;
    private File selectedFile;
    private transient Event event = null;
    public ImageUploader() {
        setTitle("Image Uploader");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        JPanel uploadPanel = new JPanel();
        JButton uploadButton = new JButton("Upload Image");
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".png")
                            || f.getName().toLowerCase().endsWith(".jpg")
                            || f.getName().toLowerCase().endsWith(".gif")
                            || f.isDirectory();
                }

                public String getDescription() {
                    return "Image files (*.png, *.jpg, *.gif)";
                }
            });

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                try {
                    saveImage(selectedFile.toPath());
                    showImage(selectedFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while saving the image.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        uploadPanel.add(uploadButton);

        cardPanel.add(uploadPanel, "upload");

        JPanel imagePanel = new JPanel();
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300, 200));
        imagePanel.add(imageLabel);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            try {
                saveImage(selectedFile.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while saving the image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });
        imagePanel.add(okButton);

        cardPanel.add(imagePanel, "image");

        cardLayout.show(cardPanel, "upload");
    }

    public void saveImage(Path sourcePath) throws IOException {
        String destinationPath = "C:\\Users\\DELL\\software_project\\EventPlaner991\\src\\main\\resources\\images";
        Path destination = Path.of(destinationPath);
        if (!Files.exists(destination)) {
            Files.createDirectories(destination);
        }

        Files.copy(sourcePath, destination.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
         copiedFilePath = Files.copy(sourcePath, destination.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        if (event != null) {
            event.setpath(copiedFilePath);
        }

    }

    private void showImage(File selectedFile) {
        this.selectedFile = selectedFile;
        ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
        Image image = imageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));

        cardLayout.show(cardPanel, "image");
    }
    public static void openImage(Path imagePath) {
        File imageFile = imagePath.toFile();
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (imageFile.exists()) {
                try {
                    desktop.open(imageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                logger.info("File does not exist: " + imagePath);
            }
        } else {
            logger.info("Desktop is not supported");
        }
    }

    public Path getimagepath()
    {
        return copiedFilePath;
    }
    public void setEvent(Event event) {
        this.event = event;
    }


}

