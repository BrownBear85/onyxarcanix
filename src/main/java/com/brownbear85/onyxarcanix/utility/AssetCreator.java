package com.brownbear85.onyxarcanix.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * This class has methods to automatically create all necessary json files for the creation of a basic block or item
 */
public final class AssetCreator {
    private static final String blockstateTemplate = """
            {
              "variants": {
                "": {
                  "model": "onyxarcanix:block/%s"
                }
              }
            }""";
    private static final String blockModelTemplate = """
            {
              "parent": "minecraft:block/cube_all",
              "textures": {
                "all": "onyxarcanix:block/%s"
              }
            }""";
    private static final String blockitemModelTemplate = """
            {
              "parent": "onyxarcanix:block/%s"
            }
            """;
    private static final String itemModelTemplate = """
            {
              "parent": "minecraft:item/generated",
              "textures": {
                "layer0": "onyxarcanix:item/%s"
              }
            }""";

    private static final String blockLootTableTemplate = """
            {
              "type": "minecraft:block",
              "pools": [
                {
                  "rolls": 1,
                  "entries": [
                    {
                      "type": "minecraft:item",
                      "name": "onyxarcanix:%s"
                    }
                  ]
                }
              ]
            }
                        
            """;

    private static final String runedBlockModelTemplate = """
            {
                "credit": "Made with Blockbench",
                "texture_size": [64, 64],
                "textures": {
                    "1": "onyxarcanix:block/runed_stone_bricks/rune_%s",
                    "2": "onyxarcanix:block/runed_stone_bricks/blank",
                    "particle": "onyxarcanix:block/runed_stone_bricks/rune_a"
                },
                "elements": [
                    {
                        "name": "block",
                        "from": [0, 0, 0],
                        "to": [16, 16, 16],
                        "faces": {
                            "north": {"uv": [0, 0, 16, 16], "texture": "#1"},
                            "east": {"uv": [0, 0, 16, 16], "texture": "#1"},
                            "south": {"uv": [0, 0, 16, 16], "texture": "#1"},
                            "west": {"uv": [0, 0, 16, 16], "texture": "#1"},
                            "up": {"uv": [16, 16, 0, 0], "rotation": 180, "texture": "#2"},
                            "down": {"uv": [0, 16, 16, 0], "rotation": 90, "texture": "#2"}
                        }
                    }
                ]
             }
            """;

    /**
     * Creates a basic blockstates JSON for the given block name
     *
     * @param name same name as block is registered with
     * @throws IOException if an error occurs while creating the file
     */
    private static void createBasicBlockState(String name) throws IOException {
        try {
            File blockstate = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\blockstates\\" + name + ".json");
            if (blockstate.createNewFile()) {
                FileWriter writer = new FileWriter(blockstate);
                writer.write(String.format(blockstateTemplate, name));
                writer.close();
                System.out.println("Created blockstates\\" + name);
            } else {
                System.out.println("File blockstates\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating blockstates\\" + name);
        }
    }

    /**
     * Creates a basic block model JSON for the given block name
     *
     * @param name same name as block is registered with
     * @throws IOException if an error occurs while creating the file
     */
    private static void createBasicBlockModel(String name) throws IOException {
        try {
            File blockModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\" + name + ".json");
            if (blockModel.createNewFile()) {
                FileWriter writer = new FileWriter(blockModel);
                writer.write(String.format(blockModelTemplate, name));
                writer.close();
                System.out.println("Created models\\block\\" + name);
            } else {
                System.out.println("File models\\block\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\block\\" + name);
        }
    }

    /**
     * Creates a basic blockitem model JSON for the given block name
     *
     * @param name same name as block is registered with
     * @throws IOException if an error occurs while creating the file
     */
    private static void createBasicBlockitemModel(String name) throws IOException {
        try {
            File blockitemModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\item\\" + name + ".json");
            if (blockitemModel.createNewFile()) {
                FileWriter writer = new FileWriter(blockitemModel);
                writer.write(String.format(blockitemModelTemplate, name));
                writer.close();
                System.out.println("Created models\\item\\" + name);
            } else {
                System.out.println("File models\\item\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\item\\" + name);
        }
    }

    /**
     * Creates a basic item model JSON for the given item name
     *
     * @param name same name as item is registered with
     * @throws IOException if an error occurs while creating the file
     */
    private static void createBasicItemModel(String name) throws IOException {
        try {
            File itemModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\item\\" + name + ".json");
            if (itemModel.createNewFile()) {
                FileWriter writer = new FileWriter(itemModel);
                writer.write(String.format(itemModelTemplate, name));
                writer.close();
                System.out.println("Created models\\item\\" + name);
            } else {
                System.out.println("File models\\item\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\item\\" + name);
        }
    }

    /**
     * Takes a block/item id and formats it as a name.
     * Ex. "diamond_sword" -> "Diamond Sword"
     *
     * @param id a minecraft block or item id - iron_ingot, apple
     * @return what the name of that item is - Iron Ingot, Apple
     */
    private static String idToName(String id) {
        String[] arr = id.replace("_", " ").split("\\s");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1);
            if (i > 0) arr[i] = " " + arr[i];
            result.append(arr[i]);
        }
        return result.toString();
    }

    /**
     * Adds an entry to the en_us.lang file
     *
     * @param key key in lang file
     * @param value the value to be assigned to that key
     */
    private static void addToLang(String key, String value) {
        try {
            File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\lang\\en_us.json");
            Scanner scanner = new Scanner(file);
            StringBuilder builder = new StringBuilder();
            String data;
            while (scanner.hasNextLine()) {
                data = scanner.nextLine();
                builder.append(data).append("\n");
            }
            String lang = builder.toString().split("}")[0];
            if (lang.contains(key)) {
                System.out.println("Key " + key + " already exists in lang, skipping it");
                return;
            }
            lang = lang.substring(0, lang.length() - 1) + String.format(",%n  \"%s\": \"%s\"%n}", key, value);
            FileWriter writer = new FileWriter(file);
            writer.write(lang);
            writer.close();
            System.out.println("Adding " + key + " to lang");
        } catch (Exception e) {
            System.out.println("An error occurred whilst adding " + key + " to lang");
        }
    }

    /**
     * Creates a simple block loot table (drops itself every time)
     * @param name id of the block
     * @throws IOException if a file error occurs
     */
    private static void createBasicBlockLootTable(String name) throws IOException {
        try {
            File blockstate = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\data\\onyxarcanix\\loot_tables\\blocks\\" + name + ".json");
            if (blockstate.createNewFile()) {
                FileWriter writer = new FileWriter(blockstate);
                writer.write(String.format(blockLootTableTemplate, name));
                writer.close();
                System.out.println("Created loot_tables\\blocks\\" + name);
            } else {
                System.out.println("File loot_tables\\blocks\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating loot_tables\\blocks\\" + name);
        }
    }

    /**
     * Create all the necessary json files for a basic block: blockstates, block model, blockitem model, and it also adds an entry to lang.
     *
     * @param name same name as block is registered with
     * @param item if it should create a blockitem
     * @throws IOException if an io error occurs during any step of the process
     */
    public static void createBasicBlock(String name, boolean item) throws IOException {
        createBasicBlockState(name);
        createBasicBlockModel(name);
        createBasicBlockLootTable(name);
        addToLang("block.onyxarcanix." + name, idToName(name));
        if (item) createBasicBlockitemModel(name);
    }

    /**
     * Create item model JSON and add item to lang
     *
     * @param name same name as item is registered with
     * @throws IOException if an io error occurs during any step of the process
     */
    public static void createBasicItem(String name) throws IOException {
        createBasicItemModel(name);
        addToLang("item.onyxarcanix." + name, idToName(name));
    }

    private static final String[] alphabet = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    /**
     *
     * @param name the name of the runed block. "runed_" will be appended to the beginning. Ex. "stone_bricks" -> "runed_stone_bricks"
     * @throws IOException if an error occurs during file creation
     */
    public static void createRunedBlock(String name) throws IOException {
        for (String s : alphabet) {
            try {
                File blockModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\runed_" + name + "\\rune_" + s + ".json");
                if (blockModel.createNewFile()) {
                    FileWriter writer = new FileWriter(blockModel);
                    writer.write(String.format(runedBlockModelTemplate, s));
                    writer.close();
                    System.out.println("Created models\\block\\runed_\" + name + \"\\rune_" + s);
                } else {
                    System.out.println("File models\\block\\runed_\" + name + \"\\rune_" + s + " already exists, skipping it");
                }
            } catch (IOException e) {
                System.out.println("An error occurred whilst creating models\\block\\runed_\" + name + \"\\rune_" + s);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        createBasicBlock("stone_pedestal", true);
    }
}
