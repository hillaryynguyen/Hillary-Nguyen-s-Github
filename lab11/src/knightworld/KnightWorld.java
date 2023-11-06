package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {

    private TETile[][] tiles;
    // TODO: Add additional instance variables here

    public KnightWorld(int width, int height, int holeSize) {
        // TODO: Fill in this constructor and class, adding helper methods and/or classes as necessary to draw the
        //  specified pattern of the given hole size for a window of size width x height. If you're stuck on how to
        //  begin, look at the provided demo code!
        this.tiles = new TETile[width][height];

        for (int back = 0; back <width; back++) {
            for (int front = 0; front < height; front++) {
                tiles[back][front] = Tileset.WATER;
            }
        }
        for (int f1 = 0; f1 < height; f1 = f1 + 5 * holeSize) {
            for (int b1 = 0; b1 < width; b1 = b1 + 5 * holeSize) {
                makeYoHole(holeSize + b1, f1, holeSize, tiles, width, height);
                makeYoHole(holeSize * 4 + b1, holeSize + f1, holeSize, tiles, width, height);
                makeYoHole(holeSize * 2 + b1, holeSize * 2 + f1, holeSize, tiles, width, height);
                makeYoHole(b1, holeSize * 3 + f1, holeSize, tiles, width, height);
                makeYoHole(holeSize * 3 + b1, holeSize * 4 + f1, holeSize, tiles, width, height);
            }
        }
    }

    public void makeYoHole(int b, int f, int holeSize, TETile[][] tiles, int width, int height) {
        for (int i = b; i < b + holeSize; i++) {
            for (int j = f ; j < f + holeSize; j++) {
                if (i <width && j < height) {
                    tiles[i][j] = Tileset.TREE;
                }
            }
        }
    }

    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 60;
        int height = 40;
        int holeSize = 4;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(knightWorld.getTiles());

    }
}