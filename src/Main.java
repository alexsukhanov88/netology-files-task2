import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static void saveGame(String filePath, GameProgress save) {
        File saveFile = new File(filePath);
        try (FileOutputStream outputStream = new FileOutputStream(saveFile); ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(save);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String zipPath , List<String> saveList) {
        try (FileOutputStream outputStream = new FileOutputStream(zipPath); ZipOutputStream zos = new ZipOutputStream(outputStream)) {
            for (String savePath : saveList){
                FileInputStream fis = new FileInputStream(savePath);
                ZipEntry entry = new ZipEntry(savePath);
                zos.putNextEntry(entry);
                zos.write(fis.readAllBytes());
                //byte[] buffer = new byte[fis.available()];  fis.read(buffer);
                //zos.write(buffer);
                zos.closeEntry();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {

        File games = new File("savegames");
        games.mkdir();

        GameProgress save1 = new GameProgress(100, 10, 15, 1.0);
        GameProgress save2 = new GameProgress(50, 20, 13, 2.0);
        GameProgress save3 = new GameProgress(75, 3, 20, 3.0);

        File saveFile1 = new File(games, "save1.dat");
        File saveFile2 = new File(games, "save2.dat");
        File saveFile3 = new File(games, "save3.dat");
        saveGame(saveFile1.getPath(), save1);
        saveGame(saveFile2.getPath(), save2);
        saveGame(saveFile3.getPath(), save3);

        List<String> saves = new ArrayList<>();
        saves.add(saveFile1.getPath());
        saves.add(saveFile2.getPath());
        saves.add(saveFile3.getPath());

        zipFiles(games + "/saves.zip", saves);

        saveFile1.delete();
        saveFile2.delete();
        saveFile3.delete();
    }
}
