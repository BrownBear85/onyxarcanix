package com.brownbear85.onyxarcanix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class AssetCreator {
    public static class Templates {
        public static final String blockstateTemplate = """
                {
                  "variants": {
                    "": {
                      "model": "onyxarcanix:block/%s"
                    }
                  }
                }""";
        public static final String blockModelTemplate = """
                {
                  "parent": "minecraft:block/cube_all",
                  "textures": {
                    "all": "onyxarcanix:block/%s"
                  }
                }""";
        public static final String blockitemModelTemplate = """
                {
                  "parent": "onyxarcanix:block/%s"
                }
                """;
        public static final String itemModelTemplate = """
                {
                  "parent": "minecraft:item/generated",
                  "textures": {
                    "layer0": "onyxarcanix:item/%s"
                  }
                }""";
        public static final String blockLootTableTemplate = """
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
        public static final String runedBlockModelTemplate = """
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
        public static final String stairBlockStateTemplate = """
                {
                  "variants": {
                    "facing=east,half=bottom,shape=inner_left": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "y": 270
                    },
                    "facing=east,half=bottom,shape=inner_right": {
                      "model": "onyxarcanix:block/%s_inner"
                                
                    },
                    "facing=east,half=bottom,shape=outer_left": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "y": 270
                    },
                    "facing=east,half=bottom,shape=outer_right": {
                      "model": "onyxarcanix:block/%s_outer"
                                
                    },
                    "facing=east,half=bottom,shape=straight": {
                      "model": "onyxarcanix:block/%s"
                                
                    },
                    "facing=east,half=top,shape=inner_left": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "x": 180
                    },
                    "facing=east,half=top,shape=inner_right": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 90
                    },
                    "facing=east,half=top,shape=outer_left": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "x": 180
                    },
                    "facing=east,half=top,shape=outer_right": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 90
                    },
                    "facing=east,half=top,shape=straight": {
                      "model": "onyxarcanix:block/%s",
                                
                      "uvlock": true,
                      "x": 180
                    },
                    "facing=north,half=bottom,shape=inner_left": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "y": 180
                    },
                    "facing=north,half=bottom,shape=inner_right": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "y": 270
                    },
                    "facing=north,half=bottom,shape=outer_left": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "y": 180
                    },
                    "facing=north,half=bottom,shape=outer_right": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "y": 270
                    },
                    "facing=north,half=bottom,shape=straight": {
                      "model": "onyxarcanix:block/%s",
                                
                      "uvlock": true,
                      "y": 270
                    },
                    "facing=north,half=top,shape=inner_left": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 270
                    },
                    "facing=north,half=top,shape=inner_right": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "x": 180
                    },
                    "facing=north,half=top,shape=outer_left": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 270
                    },
                    "facing=north,half=top,shape=outer_right": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "x": 180
                    },
                    "facing=north,half=top,shape=straight": {
                      "model": "onyxarcanix:block/%s",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 270
                    },
                    "facing=south,half=bottom,shape=inner_left": {
                      "model": "onyxarcanix:block/%s_inner"
                                
                    },
                    "facing=south,half=bottom,shape=inner_right": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "y": 90
                    },
                    "facing=south,half=bottom,shape=outer_left": {
                      "model": "onyxarcanix:block/%s_outer"
                                
                    },
                    "facing=south,half=bottom,shape=outer_right": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "y": 90
                    },
                    "facing=south,half=bottom,shape=straight": {
                      "model": "onyxarcanix:block/%s",
                                
                      "uvlock": true,
                      "y": 90
                    },
                    "facing=south,half=top,shape=inner_left": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 90
                    },
                    "facing=south,half=top,shape=inner_right": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 180
                    },
                    "facing=south,half=top,shape=outer_left": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 90
                    },
                    "facing=south,half=top,shape=outer_right": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 180
                    },
                    "facing=south,half=top,shape=straight": {
                      "model": "onyxarcanix:block/%s",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 90
                    },
                    "facing=west,half=bottom,shape=inner_left": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "y": 90
                    },
                    "facing=west,half=bottom,shape=inner_right": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "y": 180
                    },
                    "facing=west,half=bottom,shape=outer_left": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "y": 90
                    },
                    "facing=west,half=bottom,shape=outer_right": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "y": 180
                    },
                    "facing=west,half=bottom,shape=straight": {
                      "model": "onyxarcanix:block/%s",
                                
                      "uvlock": true,
                      "y": 180
                    },
                    "facing=west,half=top,shape=inner_left": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 180
                    },
                    "facing=west,half=top,shape=inner_right": {
                      "model": "onyxarcanix:block/%s_inner",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 270
                    },
                    "facing=west,half=top,shape=outer_left": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 180
                    },
                    "facing=west,half=top,shape=outer_right": {
                      "model": "onyxarcanix:block/%s_outer",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 270
                    },
                    "facing=west,half=top,shape=straight": {
                      "model": "onyxarcanix:block/%s",
                                
                      "uvlock": true,
                      "x": 180,
                      "y": 180
                    }
                  }
                }
                """;
        public static final String stairModelTemplate = """
                {
                  "parent": "minecraft:block/stairs",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String stairInnerModelTemplate = """
                {
                  "parent": "minecraft:block/inner_stairs",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String stairOuterModelTemplate = """
                {
                  "parent": "minecraft:block/outer_stairs",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String slabBlockStateTemplate = """
                {
                  "variants": {
                    "type=bottom": {
                      "model": "onyxarcanix:block/%s"
                    },
                    "type=double": {
                      "model": "onyxarcanix:block/%s"
                    },
                    "type=top": {
                      "model": "onyxarcanix:block/%s_top"
                    }
                  }
                }
                """;
        public static final String slabModelTemplate = """
                {
                  "parent": "minecraft:block/slab",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String slabTopModelTemplate = """
                {
                  "parent": "minecraft:block/slab_top",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
    }

    private static final String[] alphabet = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    /* util methods */

    public static String replace(String str, String replace, String newStr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            for (int ch = 0; ch < replace.length(); ch++) {
                if (str.charAt(i + ch) != replace.charAt(ch)) {
                    builder.append(str.charAt(i + ch));
                    break;
                } else if (ch + 1 == replace.length()) {
                    builder.append(newStr);
                    i += replace.length() - 1;
                }
            }
        }
        return builder.toString();
    }

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

    /* file creation methods */

    private static void createBasicBlockState(String name) {
        try {
            File blockstate = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\blockstates\\" + name + ".json");
            if (blockstate.createNewFile()) {
                FileWriter writer = new FileWriter(blockstate);
                writer.write(String.format(Templates.blockstateTemplate, name));
                writer.close();
                System.out.println("Created blockstates\\" + name);
            } else {
                System.out.println("File blockstates\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating blockstates\\" + name);
        }
    }

    private static void createBasicBlockModel(String name) {
        try {
            File blockModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\" + name + ".json");
            if (blockModel.createNewFile()) {
                FileWriter writer = new FileWriter(blockModel);
                writer.write(String.format(Templates.blockModelTemplate, name));
                writer.close();
                System.out.println("Created models\\block\\" + name);
            } else {
                System.out.println("File models\\block\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\block\\" + name);
        }
    }


    private static void createStairBlockState(String name) {
        try {
            File blockstate = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\blockstates\\" + name + ".json");
            if (blockstate.createNewFile()) {
                FileWriter writer = new FileWriter(blockstate);
                writer.write(replace(Templates.stairBlockStateTemplate, "%s", name));
                writer.close();
                System.out.println("Created blockstates\\" + name);
            } else {
                System.out.println("File blockstates\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating blockstates\\" + name);
        }
    }

    private static void createStairBasicModel(String name, String texture) {
        try {
            File blockModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\" + name + ".json");
            if (blockModel.createNewFile()) {
                FileWriter writer = new FileWriter(blockModel);
                writer.write(replace(Templates.stairModelTemplate, "%s", texture));
                writer.close();
                System.out.println("Created models\\block\\" + name);
            } else {
                System.out.println("File models\\block\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\block\\" + name);
        }
    }

    private static void createStairInnerModel(String name, String texture) {
        name = name + "_inner";
        try {
            File blockModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\" + name + ".json");
            if (blockModel.createNewFile()) {
                FileWriter writer = new FileWriter(blockModel);
                writer.write(replace(Templates.stairInnerModelTemplate, "%s", texture));
                writer.close();
                System.out.println("Created models\\block\\" + name);
            } else {
                System.out.println("File models\\block\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\block\\" + name);
        }
    }

    private static void createStairOuterModel(String name, String texture) {
        name = name + "_outer";
        try {
            File blockModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\" + name + ".json");
            if (blockModel.createNewFile()) {
                FileWriter writer = new FileWriter(blockModel);
                writer.write(replace(Templates.stairOuterModelTemplate, "%s", texture));
                writer.close();
                System.out.println("Created models\\block\\" + name);
            } else {
                System.out.println("File models\\block\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\block\\" + name);
        }
    }


    private static void createSlabBlockState(String name, String base) {
        try {
            File blockstate = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\blockstates\\" + name + ".json");
            if (blockstate.createNewFile()) {
                FileWriter writer = new FileWriter(blockstate);
                writer.write(String.format(Templates.slabBlockStateTemplate, name, base, name));
                writer.close();
                System.out.println("Created blockstates\\" + name);
            } else {
                System.out.println("File blockstates\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating blockstates\\" + name);
        }
    }

    private static void createSlabBasicModel(String name, String texture) {
        try {
            File blockModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\" + name + ".json");
            if (blockModel.createNewFile()) {
                FileWriter writer = new FileWriter(blockModel);
                writer.write(replace(Templates.slabModelTemplate, "%s", texture));
                writer.close();
                System.out.println("Created models\\block\\" + name);
            } else {
                System.out.println("File models\\block\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\block\\" + name);
        }
    }

    private static void createSlabTopModel(String name, String texture) {
        name = name + "_top";
        try {
            File blockModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\" + name + ".json");
            if (blockModel.createNewFile()) {
                FileWriter writer = new FileWriter(blockModel);
                writer.write(replace(Templates.slabTopModelTemplate, "%s", texture));
                writer.close();
                System.out.println("Created models\\block\\" + name);
            } else {
                System.out.println("File models\\block\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\block\\" + name);
        }
    }


    private static void createBasicBlockitemModel(String name) {
        try {
            File blockitemModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\item\\" + name + ".json");
            if (blockitemModel.createNewFile()) {
                FileWriter writer = new FileWriter(blockitemModel);
                writer.write(String.format(Templates.blockitemModelTemplate, name));
                writer.close();
                System.out.println("Created models\\item\\" + name);
            } else {
                System.out.println("File models\\item\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\item\\" + name);
        }
    }

    private static void createBasicItemModel(String name) {
        try {
            File itemModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\item\\" + name + ".json");
            if (itemModel.createNewFile()) {
                FileWriter writer = new FileWriter(itemModel);
                writer.write(String.format(Templates.itemModelTemplate, name));
                writer.close();
                System.out.println("Created models\\item\\" + name);
            } else {
                System.out.println("File models\\item\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating models\\item\\" + name);
        }
    }

    private static void createBasicBlockLootTable(String name) {
        try {
            File blockstate = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\data\\onyxarcanix\\loot_tables\\blocks\\" + name + ".json");
            if (blockstate.createNewFile()) {
                FileWriter writer = new FileWriter(blockstate);
                writer.write(String.format(Templates.blockLootTableTemplate, name));
                writer.close();
                System.out.println("Created loot_tables\\blocks\\" + name);
            } else {
                System.out.println("File loot_tables\\blocks\\" + name + " already exists, skipping it");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating loot_tables\\blocks\\" + name);
        }
    }


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

    /* block/item creation methods */

    public static void createBasicBlock(String name, boolean item) {
        createBasicBlockState(name);
        createBasicBlockModel(name);
        createBasicBlockLootTable(name);
        addToLang("block.onyxarcanix." + name, idToName(name));
        if (item) createBasicBlockitemModel(name);
    }

    public static void createBasicItem(String name) {
        createBasicItemModel(name);
        addToLang("item.onyxarcanix." + name, idToName(name));
    }

    public static void createStairBlock(String name, String texture) {
        createStairBlockState(name);
        createStairBasicModel(name, texture);
        createStairInnerModel(name, texture);
        createStairOuterModel(name, texture);
        createBasicBlockLootTable(name);
        addToLang("block.onyxarcanix." + name, idToName(name));
        createBasicBlockitemModel(name);
    }

    public static void createSlabBlock(String name, String texture) {
        createSlabBlockState(name, texture);
        createSlabBasicModel(name, texture);
        createSlabTopModel(name, texture);
        createBasicBlockLootTable(name);
        addToLang("block.onyxarcanix." + name, idToName(name));
        createBasicBlockitemModel(name);
    }

    public static void createRunedBlock(String name) {
        for (String s : alphabet) {
            try {
                File blockModel = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\runed_" + name + "\\rune_" + s + ".json");
                if (blockModel.createNewFile()) {
                    FileWriter writer = new FileWriter(blockModel);
                    writer.write(String.format(Templates.runedBlockModelTemplate, s));
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

    public static void main(String[] args) {
        createSlabBlock("onyx_slab", "onyx");
        createSlabBlock("onyx_brick_slab", "onyx_bricks");
    }
}
