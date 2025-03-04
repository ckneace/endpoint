package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Directory {

    private static List<String> directories = new ArrayList<>();

    public Directory(List<String> commandsLines) {

        for(String commands : commandsLines) {

            if(commands.startsWith("CREATE ")) {
                String path = commands.substring("CREATE ".length());
                if(!directories.contains(path)) {
                    System.out.println("CREATE " + path);
                    directories.add(path);
                }
            }
            else if(commands.startsWith("MOVE ")) {

                String[] parse = commands.substring("MOVE ".length()).split("\\s+");

                if(parse.length != 2) {
                    System.out.println("Invalid command");
                }
                else {
                    moveCommand(parse);
                }

            }
            else if(commands.startsWith("DELETE ")) {
                delete(commands.substring("DELETE ".length()));
            }
            else if(commands.startsWith("LIST")) {
                Collections.sort(directories);
                System.out.println("LIST");
                printList();
            }
            else{

                System.out.println("Invalid command");
            }
        }

    }
    private void delete(String path) {

        String[] parts = path.split("/");
        String upperLevel = parts[0];
        if (!upperLevel(upperLevel)) {
            System.out.println("Cannot delete " + path + " - " + upperLevel + " does not exist");
            return;
        }

        if (getMatch(path) == null) {
            System.out.println("Cannot delete " + path + " - " + (parts.length > 1 ? parts[0] : path) + " does not exist");
            return;
        }

        List<String> notDeleted = new ArrayList<>();
        boolean deleted = false;
        for (String dir : directories) {
            if (dir.equals(path) || dir.startsWith(path + "/")) {
                deleted = true;
            } else {
                notDeleted.add(dir);
            }
        }
        directories = notDeleted;

        if (deleted) {
            System.out.println("DELETED " + path);
        }
    }
    private void moveCommand(String[] parse) {
        String source = parse[0];
        String destination = parse[1];

        String sourceExist = getMatch(source);
        if (sourceExist == null) {
            System.out.println("source not found");
            return;
        }

        String destinationExist = getMatch(destination);
        if (destinationExist == null) {
            System.out.println("Destination  not found");
            return;
        }

        String[] sourceParts = source.split("/");
        String lastString = sourceParts[sourceParts.length - 1];
        String start = destination + "/" + lastString;

        List<String> movedDirectory = new ArrayList<>();

        boolean moved = false;
        for (String directory : directories) {
            if (directory.equals(source) || directory.startsWith(source + "/")) {

                String rest = directory.length() == source.length() ? "" : directory.substring(source.length());
                movedDirectory.add(start + rest);
                moved = true;
            } else {
                movedDirectory.add(directory);
            }
        }
        if (moved) {
            directories = movedDirectory;
            System.out.println("MOVE " + source + " " + destination);
        }

    }

    private boolean upperLevel(String top) {
        for (String dir : directories) {
            String topPart = dir.contains("/") ? dir.substring(0, dir.indexOf("/")) : dir;
            if (topPart.equals(top)) {
                return true;
            }
        }
        return false;
    }
    private String getMatch(String path) {
        for (String dir : directories) {
            if (dir.equals(path)) {
                return dir;
            }
        }
        return null;
    }
    void printList() {
        for(String path : directories) {
            String indent = " ".repeat(path.length() - path.replace("/", "").length());
            String last = path.contains("/") ? path.substring(path.lastIndexOf("/") + 1) : path;
            System.out.println(indent + last);
        }
    }
}
