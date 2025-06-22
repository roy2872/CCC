package org.example;

import java.io.*;
import java.nio.file.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.GameState;

public class SaveManager {
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Path savePath = Paths.get(System.getenv("APPDATA"), "CookieClickerClone", "save.json");

    public static void save(GameState state) {
        try {
            Files.createDirectories(savePath.getParent()); // Ensure directory exists
            try (Writer writer = new FileWriter(savePath.toFile())) {
                gson.toJson(state, writer);
                // System.out.println("Game saved to " + savePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState load() {
        if (Files.exists(savePath)) {
            try (Reader reader = new FileReader(savePath.toFile())) {
                return gson.fromJson(reader, GameState.class);
            } catch (IOException e) {
                e.printStackTrace();
                return GameState.newDefaultState();
            }
        }
        return GameState.newDefaultState();
    }
}
