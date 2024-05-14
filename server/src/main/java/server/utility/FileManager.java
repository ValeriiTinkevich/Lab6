package server.utility;


import common.data.Chapter;
import common.data.Coordinates;
import common.data.MeleeWeapon;
import common.data.SpaceMarine;
import common.exceptions.NotUniqueIdException;
import common.utility.Outputter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class FileManager {
    public String fileName;
    public String splitChar;

    /**
     * File manager constructor.
     *
     * @param fileName file name
     * @param splitChar Character that file is going to be splitted with
     */
    public FileManager(String fileName, String splitChar) {
        this.fileName = fileName;
        this.splitChar = splitChar;
    }

    /**
     * Sets filename for file manager.
     * @param fileName file name
     */

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads collection from a csv file
     * @return returns a collection of elements that were successfully parsed.
     */
    public LinkedList<SpaceMarine> readCollection() {
        if(!fileName.isEmpty()) {
            LinkedList<SpaceMarine> result = new LinkedList<>();
            int trashCounter = 0;
            File file = new File(fileName);
            try(FileInputStream fis = new FileInputStream(file)) {
                int linenum = 0;
                Set<Long> assignedID = new HashSet<>();
                InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
                int c;
                StringBuilder s = new StringBuilder();
                do {
                    c =  inputStreamReader.read();
                    if (c == -1) break;
                    s.append((char) c);
                } while (true);
                Scanner scanner = new Scanner(s.toString());
                while (scanner.hasNextLine()){
                    linenum += 1;
                    String line = scanner.nextLine();
                    String[] lineArr = line.split(splitChar);
                    if(lineArr.length == 11) {
                        try {
                            SpaceMarine spaceMarine = new SpaceMarine();
                            if(assignedID.add(Long.parseLong(lineArr[0]))) {
                                spaceMarine.setId(Long.parseLong(lineArr[0]));
                            } else throw new NotUniqueIdException();
                            spaceMarine.setName(lineArr[1]);
                            spaceMarine.setCoordinates(new Coordinates(Float.parseFloat(lineArr[2]), Double.parseDouble(lineArr[3])));
                            spaceMarine.setCreationDate(ZonedDateTime.parse(lineArr[4]));
                            spaceMarine.setHealth(Integer.parseInt(lineArr[5]));
                            spaceMarine.setHeartCount(Long.parseLong(lineArr[6]));
                            spaceMarine.setHeight(Long.parseLong(lineArr[7]));
                            spaceMarine.setMeleeWeapon(MeleeWeapon.valueOf(lineArr[8].toUpperCase()));
                            spaceMarine.setChapter(new Chapter(lineArr[9], lineArr[10]));
                            result.add(spaceMarine);
                        } catch (DateTimeParseException | NumberFormatException e) {
                            Outputter.printError("Can't parse data in line " + linenum + "!");
                            trashCounter++;
                        } catch (NotUniqueIdException e) {
                            Outputter.printError("Id not unique in line " + linenum + "!");
                        }
                    } else {
                        trashCounter++;
                        Outputter.printError("Can't parse data in line " + linenum + "!");
                    }
                }
                Outputter.printLn("Successfully loaded " + result.size() + " Space Marines.");
                return result;
            } catch (FileNotFoundException e) {
                Outputter.printLn(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        return new LinkedList<>();
    }

    /**
     * Saves collection of elements to a csv file
     * @param spaceMarines
     */
    public void saveCollection(LinkedList<SpaceMarine> spaceMarines) {
        if(!fileName.isEmpty()) {
            File file = new File(fileName);
            String resultCSV = "";
            for(SpaceMarine spaceMarine: spaceMarines) {
                resultCSV += spaceMarine.getId() + ";"
                        + spaceMarine.getName() + ";"
                        + spaceMarine.getCoordinates().getX() + ";"
                        + spaceMarine.getCoordinates().getY() + ";"
                        + spaceMarine.getCreationDate() + ";"
                        + spaceMarine.getHealth() + ";"
                        + spaceMarine.getHeartCount() + ";"
                        + spaceMarine.getHeight() + ";"
                        + spaceMarine.getMeleeWeapon() + ";"
                        + spaceMarine.getChapter().getName() + ";"
                        + spaceMarine.getChapter().getWorld() + "\n";

            }
            try(FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buffer = resultCSV.getBytes();
                fos.write(buffer, 0, buffer.length);

            } catch (FileNotFoundException e) {
                Outputter.printLn(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException();
            }

        } else Outputter.printError("Filename is wrong or corrupted!");

    }

}
