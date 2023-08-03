import java.io.*;
import java.util.*;

public class CharCheckUtility {
    public static void main(String[] args) {
        String filePath1 = "file1.txt";
        String filePath2 = "file2.txt";

        try {
            HashMap<String, String> file1Data = readData(filePath1);
            HashMap<String, String> file2Data = readData(filePath2);

            for(String module : file1Data.keySet()){
                if(file2Data.containsKey(module)){
                    compareData(module, file1Data.get(module), file2Data.get(module));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> readData(String filePath) throws IOException {
        HashMap<String, String> data = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();

        while(line != null) {
            String[] parts = line.split("\\|");
            if(parts.length >= 2) {
                String moduleName = parts[0].trim();
                String uniqueId = parts[1].trim();
                data.put(moduleName, uniqueId);
            }
            line = reader.readLine();
        }
        reader.close();
        return data;
    }

    public static void compareData(String module, String uniqueId1, String uniqueId2) {
        int length = Math.min(uniqueId1.length(), uniqueId2.length());
        for(int i = 0; i < length; i++) {
            if(uniqueId1.charAt(i) != uniqueId2.charAt(i)) {
                System.out.println("Module_name: " + module + " | Character check: " + uniqueId1.charAt(i) + " != " + uniqueId2.charAt(i));
            }
        }
    }
}
