package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Put any input and see the output
        List<String> commands = Arrays.asList(
                "CREATE fruits",
                "CREATE vegetables",
                "CREATE grains",
                "CREATE fruits/apples",
                "CREATE fruits/apples/fuji",
                "LIST",
                "CREATE grains/squash",
                "MOVE grains/squash vegetables",
                "CREATE foods",
                "MOVE grains foods",
                "MOVE fruits foods",
                "MOVE vegetables foods",
                "LIST",
                "DELETE fruits/apples",
                "DELETE foods/fruits/apples",
                "LIST"
        );
        Directory dir = new Directory(commands);
    }
}