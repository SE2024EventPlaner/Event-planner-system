package components;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import special.event.Event;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
public class ImageUploader extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    Path copiedFilePath;
    private JLabel imageLabel;
    private File selectedFile;
   public Event event=null;

    public ImageUploader() {
        setTitle("Image Uploader");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Panel for uploading image
        JPanel uploadPanel = new JPanel();
        JButton uploadButton = new JButton("Upload Image");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        saveImage(selectedFile.toPath());
                        showImage(selectedFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error occurred while saving the image.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        uploadPanel.add(uploadButton);

        cardPanel.add(uploadPanel, "upload");

        // Panel for displaying image
        JPanel imagePanel = new JPanel();
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300, 200));
        imagePanel.add(imageLabel);

        // Add OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform save action
                try {
                    // Assuming selectedFile is declared as a field in the class
                    saveImage(selectedFile.toPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while saving the image.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                // Close the window
                dispose(); // Close the JFrame
            }
        });
        imagePanel.add(okButton);

        cardPanel.add(imagePanel, "image");

        // Show upload panel initially
        cardLayout.show(cardPanel, "upload");
    }

    private void saveImage(Path sourcePath) throws IOException {
        String destinationPath = "C:\\Users\\DELL\\software_project\\EventPlaner991\\src\\main\\resources\\images";
        Path destination = Path.of(destinationPath);
        // Create the directory if it doesn't exist
        if (!Files.exists(destination)) {
            Files.createDirectories(destination);
        }

        // Copy the file to the destination
        Files.copy(sourcePath, destination.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
         copiedFilePath = Files.copy(sourcePath, destination.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        if (event != null) {
            // Set the path in the event object
            event.setpath(copiedFilePath);
        }
        // Assuming event is already initialized

    }

    private void showImage(File selectedFile) {
        this.selectedFile = selectedFile; // Assign selectedFile when showing the image
        ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
        Image image = imageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));

        // Switch to image panel
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
                System.out.println("File does not exist: " + imagePath);
            }
        } else {
            System.out.println("Desktop is not supported");
        }
    }

    public Path getimagepath()
    {
        return copiedFilePath;
    }



}

