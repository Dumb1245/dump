import java.io.File;
import java.io.IOException;

public class FileRenamer {
    public static void main(String[] args) {
        // Specify the directory where the files are located
        File directory = new File("/path/to/directory");
        
        // List all files in the directory
        File[] files = directory.listFiles();

        if (files == null) {
            System.out.println("Directory does not exist or is not a directory.");
            return;
        }

        for (File file : files) {
            String fileName = file.getName();
            
            // Check if the file name matches the pattern
            if (fileName.startsWith("BDL_") && fileName.contains("ACKYINFO_UDP") && fileName.endsWith(".xml")) {
                String newFileName = "BFA_DM11_BDL_" + fileName.substring(4);
                File newFile = new File(directory, newFileName);

                // Rename the file
                if (file.renameTo(newFile)) {
                    System.out.println("Renamed " + fileName + " to " + newFileName);

                    // Run the shell command
                    try {
                        String command = "your_shell_command_here";  // Replace with your shell command
                        Process process = Runtime.getRuntime().exec(command);
                        process.waitFor();
                        System.out.println("Shell command executed successfully.");
                    } catch (IOException | InterruptedException e) {
                        System.err.println("Error executing shell command: " + e.getMessage());
                    }
                } else {
                    System.err.println("Failed to rename " + fileName);
                }
            }
        }
    }
}
