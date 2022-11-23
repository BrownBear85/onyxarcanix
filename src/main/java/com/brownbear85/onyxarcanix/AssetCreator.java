package com.brownbear85.onyxarcanix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class AssetCreator {
    public static final String MOD_PATH = "C:\\Users\\Connor Wright\\Desktop\\onyx_arcanix\\onyxarcanix";

    public static class Templates {
        public static final String basicBlockstate = """
                {
                  "variants": {
                    "": {
                      "model": "onyxarcanix:block/%s"
                    }
                  }
                }""";
        public static final String basicBlockModel = """
                {
                  "parent": "minecraft:block/cube_all",
                  "textures": {
                    "all": "onyxarcanix:block/%s"
                  }
                }""";
        public static final String basicBlockItemModel = """
                {
                  "parent": "onyxarcanix:block/%s"
                }
                """;
        public static final String basicItemModel = """
                {
                  "parent": "minecraft:item/generated",
                  "textures": {
                    "layer0": "onyxarcanix:item/%s"
                  }
                }""";
        public static final String basicBlockLootTable = """
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
        // TODO: needs to be updated to work with any name, also change particle
        public static final String runedBlockModel = """
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
        public static final String stairBlockstate = """
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
        public static final String stairModel = """
                {
                  "parent": "minecraft:block/stairs",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String stairInnerModel = """
                {
                  "parent": "minecraft:block/inner_stairs",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String stairOuterModel = """
                {
                  "parent": "minecraft:block/outer_stairs",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String slabBlockstate = """
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
        public static final String slabModel = """
                {
                  "parent": "minecraft:block/slab",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String slabTopModel = """
                {
                  "parent": "minecraft:block/slab_top",
                  "textures": {
                    "bottom": "onyxarcanix:block/%s",
                    "side": "onyxarcanix:block/%s",
                    "top": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String wallBlockstate = """
                {
                  "multipart": [
                    {
                      "apply": {
                        "model": "onyxarcanix:block/%s_post"
                      },
                      "when": {
                        "up": "true"
                      }
                    },
                    {
                      "apply": {
                        "model": "onyxarcanix:block/%s_side",
                        "uvlock": true
                      },
                      "when": {
                        "north": "low"
                      }
                    },
                    {
                      "apply": {
                        "model": "onyxarcanix:block/%s_side",
                        "uvlock": true,
                        "y": 90
                      },
                      "when": {
                        "east": "low"
                      }
                    },
                    {
                      "apply": {
                        "model": "onyxarcanix:block/%s_side",
                        "uvlock": true,
                        "y": 180
                      },
                      "when": {
                        "south": "low"
                      }
                    },
                    {
                      "apply": {
                        "model": "onyxarcanix:block/%s_side",
                        "uvlock": true,
                        "y": 270
                      },
                      "when": {
                        "west": "low"
                      }
                    },
                    {
                      "apply": {
                        "model": "onyxarcanix:block/%s_side_tall",
                        "uvlock": true
                      },
                      "when": {
                        "north": "tall"
                      }
                    },
                    {
                      "apply": {
                        "model": "onyxarcanix:block/%s_side_tall",
                        "uvlock": true,
                        "y": 90
                      },
                      "when": {
                        "east": "tall"
                      }
                    },
                    {
                      "apply": {
                        "model": "onyxarcanix:block/%s_side_tall",
                        "uvlock": true,
                        "y": 180
                      },
                      "when": {
                        "south": "tall"
                      }
                    },
                    {
                      "apply": {
                        "model": "onyxarcanix:block/%s_side_tall",
                        "uvlock": true,
                        "y": 270
                      },
                      "when": {
                        "west": "tall"
                      }
                    }
                  ]
                }
                """;
        public static final String wallInventoryModel = """
                {
                  "parent": "minecraft:block/wall_inventory",
                  "textures": {
                    "wall": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String wallPostModel = """
                {
                  "parent": "minecraft:block/template_wall_post",
                  "textures": {
                    "wall": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String wallSideModel = """
                {
                  "parent": "minecraft:block/template_wall_side",
                  "textures": {
                    "wall": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String wallSideTallModel = """
                {
                  "parent": "minecraft:block/template_wall_side_tall",
                  "textures": {
                    "wall": "onyxarcanix:block/%s"
                  }
                }
                """;
        public static final String stonecutterRecipe = """
                {
                  "type": "minecraft:stonecutting",
                  "count": %d,
                  "ingredient": {
                    "item": "%s"
                  },
                  "result": "%s"
                }
                """;

    }

    private static final String[] alphabet = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    /* util methods */

    public static void createJSON(String path, String name, String contents) {
        try {
            File file = new File(MOD_PATH + "\\src\\main\\resources\\" + path + "\\" + name + ".json");
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file);
                writer.write(contents);
                writer.close();
                System.out.println("Created " + path + "\\" + name);
            } else {
                System.out.println("Skipped " + path + "\\" + name + " | file already exists");
            }
        } catch (IOException e) {
            System.out.println("An error occurred whilst creating " + path + "\\" + name);
        }
    }

    public static String replace(String str, String target, String replace) {
        StringBuilder wordBuilder = new StringBuilder();
        StringBuilder correctWordBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            for (int ch = 0; ch < target.length(); ch++) {
                if (str.charAt(i + ch) == target.charAt(ch)) {
                    correctWordBuilder.append(str.charAt(i + ch));
                    if (ch + 1 == target.length()) {
                        wordBuilder.append(replace);
                        correctWordBuilder.delete(0, correctWordBuilder.length());
                        i += target.length() - 1;
                    }
                } else {
                    correctWordBuilder.append(str.charAt(i + ch));
                    wordBuilder.append(correctWordBuilder);
                    i += correctWordBuilder.length() - 1;
                    correctWordBuilder.delete(0, correctWordBuilder.length());
                    break;
                }
            }
        }
        return wordBuilder.toString();
    }

    public static String idToName(String id) {
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
        createJSON("assets\\onyxarcanix\\blockstates", name, String.format(Templates.basicBlockstate, name));
    }

    private static void createBasicBlockModel(String name) {
        createJSON("assets\\onyxarcanix\\models\\block", name, String.format(Templates.basicBlockModel, name));
    }


    private static void createStairBlockState(String name) {
        createJSON("assets\\onyxarcanix\\blockstates", name, replace(Templates.stairBlockstate, "%s", name));
    }

    private static void createStairBasicModel(String name, String texture) {
        createJSON("assets\\onyxarcanix\\models\\block", name, replace(Templates.stairModel, "%s", texture));
    }

    private static void createStairInnerModel(String name, String texture) {
        name = name + "_inner";
        createJSON("assets\\onyxarcanix\\models\\block", name, replace(Templates.stairInnerModel, "%s", texture));
    }

    private static void createStairOuterModel(String name, String texture) {
        name = name + "_outer";
        createJSON("assets\\onyxarcanix\\models\\block", name, replace(Templates.stairOuterModel, "%s", texture));
    }


    private static void createWallBlockState(String name) {
        createJSON("assets\\onyxarcanix\\blockstates", name, replace(Templates.wallBlockstate, "%s", name));
    }

    private static void createWallInventoryModel(String name, String texture) {
        createJSON("assets\\onyxarcanix\\models\\block", name, String.format(Templates.wallInventoryModel, texture));
    }

    private static void createWallPostModel(String name, String texture) {
        name = name + "_post";
        createJSON("assets\\onyxarcanix\\models\\block", name, String.format(Templates.wallPostModel, texture));
    }

    private static void createWallSideModel(String name, String texture) {
        name = name + "_side";
        createJSON("assets\\onyxarcanix\\models\\block", name, String.format(Templates.wallSideModel, texture));
    }

    private static void createWallSideTallModel(String name, String texture) {
        name = name + "_side_tall";
        createJSON("assets\\onyxarcanix\\models\\block", name, String.format(Templates.wallSideTallModel, texture));
    }


    private static void createSlabBlockState(String name, String base) {
        createJSON("assets\\onyxarcanix\\models\\block", name, String.format(Templates.slabBlockstate, name, base, name));
    }

    private static void createSlabBasicModel(String name, String texture) {
        createJSON("assets\\onyxarcanix\\models\\block", name, replace(Templates.slabModel, "%s", texture));
    }

    private static void createSlabTopModel(String name, String texture) {
        name = name + "_top";
        createJSON("assets\\onyxarcanix\\models\\block", name, replace(Templates.slabTopModel, "%s", texture));
    }


    private static void createBasicBlockitemModel(String name) {
        createJSON("assets\\onyxarcanix\\models\\item", name, String.format(Templates.basicBlockItemModel, name));
    }

    private static void createBasicItemModel(String name) {
        createJSON("assets\\onyxarcanix\\models\\item", name, String.format(Templates.basicItemModel, name));
    }

    private static void createBasicBlockLootTable(String name) {
        createJSON("data\\onyxarcanix\\loot_tables\\blocks", name, String.format(Templates.basicBlockLootTable, name));
    }

    private static void addToLang(String key, String value) {
        try {
            File file = new File(MOD_PATH + "\\src\\main\\resources\\assets\\onyxarcanix\\lang\\en_us.json");
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

            System.out.println("Added " + key + " to lang");
        } catch (Exception e) {
            System.out.println("An error occurred whilst adding " + key + " to lang");
        }
    }

    private static void addToTag(String path, String tag, String item) {
        try {
            File file = new File(MOD_PATH + "\\src\\main\\resources\\data\\" + path + "\\" + tag + ".json");
            Scanner scanner = new Scanner(file);
            StringBuilder builder = new StringBuilder();
            String data;
            while (scanner.hasNextLine()) {
                data = scanner.nextLine();
                builder.append(data).append("\n");
            }

            String lang = builder.toString().split("]")[0];
            if (lang.contains(item)) {
                System.out.println("Item " + item + " is already in tag " + tag);
            } else {
                lang = lang.substring(0, lang.length() - 3) + String.format(",%n    \"%s\"%n  ]%n}", item);
                FileWriter writer = new FileWriter(file);
                writer.write(lang);
                writer.close();
                System.out.println("Added " + item + " to tag " + tag);
            }

        } catch (Exception e) {
            System.out.println("An error occurred whilst adding " + item + " to tag " + tag);
        }
    }

    public static void createStonecutterRecipe(String input, String result, int count) {
        createJSON("data\\onyxarcanix\\recipes", result.split(":")[1] + "_from_" + input.split(":")[1] + "_stonecutting", String.format(Templates.stonecutterRecipe, count, input, result));
    }

    /* block/item creation methods */

    public static void cloneRecipe(String recipe, String[][] strings, String name) {
        try {
            File file = new File("C:\\Users\\Connor Wright\\Desktop\\Files\\minecraft assets\\data\\minecraft\\recipes\\" + recipe + ".json");
            Scanner scanner = new Scanner(file);
            StringBuilder builder = new StringBuilder();
            String data;
            while (scanner.hasNextLine()) {
                data = scanner.nextLine();
                builder.append(data).append("\n");
            }

            String newRecipe = builder.toString();
            for (int i = 0; i < strings[0].length; i++) {
                newRecipe = replace(newRecipe, strings[0][i], strings[1][i]);
            }

            createJSON("data\\onyxarcanix\\recipes\\", name, newRecipe);
        } catch (Exception e) {
            System.out.println("An error occurred while cloning recipe " + name);
        }
    }

    public static void createBasicBlock(String name) {
        createBasicBlockState(name);
        createBasicBlockModel(name);
        createBasicBlockLootTable(name);
        createBasicBlockitemModel(name);
        addToLang("block.onyxarcanix." + name, idToName(name));
    }

    public static void createBasicItem(String name) {
        createBasicItemModel(name);
        addToLang("item.onyxarcanix." + name, idToName(name));
    }

    public static void createRunedBlock(String name) {
        for (String s : alphabet) {
            try {
                File blockModel = new File(MOD_PATH + "\\src\\main\\resources\\assets\\onyxarcanix\\models\\block\\runed_" + name + "\\rune_" + s + ".json");
                if (blockModel.createNewFile()) {
                    FileWriter writer = new FileWriter(blockModel);
                    writer.write(String.format(Templates.runedBlockModel, s));
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

    public static void createStairBlock(String name, String texture) {
        createStairBlockState(name);
        createStairBasicModel(name, texture);
        createStairInnerModel(name, texture);
        createStairOuterModel(name, texture);
        createBasicBlockLootTable(name);
        createBasicBlockitemModel(name);
        addToLang("block.onyxarcanix." + name, idToName(name));
    }

    public static void createSlabBlock(String name, String texture) {
        createSlabBlockState(name, texture);
        createSlabBasicModel(name, texture);
        createSlabTopModel(name, texture);
        createBasicBlockLootTable(name);
        createBasicBlockitemModel(name);
        addToLang("block.onyxarcanix." + name, idToName(name));
    }

    public static void createWallBlock(String name, String texture) {
        createWallBlockState(name);
        createWallInventoryModel(name, texture);
        createWallPostModel(name, texture);
        createWallSideModel(name, texture);
        createWallSideTallModel(name, texture);
        createBasicBlockLootTable(name);
        createBasicBlockitemModel(name);
        addToTag("minecraft\\tags\\blocks", "walls", "onyxarcanix:" + name);
        addToLang("block.onyxarcanix." + name, idToName(name));
    }

    public static void main(String[] args) {
        createBasicItem("empty_spellbook");
    }
}