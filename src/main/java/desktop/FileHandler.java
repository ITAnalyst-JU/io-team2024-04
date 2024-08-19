// package desktop;

// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;

// public class FileHandler {
//     public static BufferedWriter getFileWriter(String path) {
//         File file = new File(path);
//         file.getParentFile().mkdirs();

//         if (!file.isFile()) {
//             try {
//                 file.createNewFile();
//             } catch (IOException e) {
//                 System.err.println("[FileHandler] Failed to create file: " + path);
//                 System.exit(2);
//             }
//         }

//         try {
//             return new BufferedWriter(new FileWriter(file));
//         } catch (IOException e) {
//             System.err.println("[FileHandler] Failed to create BufferedWriter for file: " + path);
//             System.exit(2);
//         }
//         return null; // not reachable
//     }
// }
