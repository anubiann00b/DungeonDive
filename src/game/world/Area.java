package game.world;

public class Area {
    
    public static final byte TYPE_DUNGEON = 0;
    public static final byte TYPE_CAVE = 1;
    public static final byte TYPE_THRONE = 2;
    
    private Tile[][] map;
    private int width;
    private int height;
    
    public Area(int width, int height, byte type) {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
        
        switch (type) {
            case TYPE_CAVE:
                generateCaves();
                break;
            case TYPE_DUNGEON:
                generateDungeon();
                break;
            case TYPE_THRONE:
                generateThroneRoom();
                break;
            default:
                System.out.println("Unhandled case in Area constructor.");
                System.exit(-1);
                break;
        }
    }
    
    private void generateThroneRoom() {
        
    }
    
    private void generateDungeon() {
        
    }
    
    private void generateCaves() {
        
    }
}