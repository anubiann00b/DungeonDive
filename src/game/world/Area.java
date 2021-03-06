package game.world;

public class Area {
    
    public static final byte TYPE_DUNGEON = 0;
    public static final byte TYPE_CAVE = 1;
    public static final byte TYPE_TREASURE = 2;
    
    private Tile[][] map;
    private int mapWidth;
    private int mapHeight;
    
    public int getWidth() { return mapWidth; }
    public int getHeight() { return mapHeight; }
    
    public Tile getTile(int x, int y) {
        if (x<0 || x>mapWidth-1 || y<0 || y>mapHeight-1)
            return Tile.OUT_OF_BOUNDS;
        return map[x][y];
    }
    
    public Area(int width, int height, byte type) {
        this.mapWidth = width;
        this.mapHeight = height;
        map = new Tile[width][height];
        
        switch (type) {
            case TYPE_CAVE:
                generateCaves();
                break;
            case TYPE_DUNGEON:
                generateDungeon();
                break;
            case TYPE_TREASURE:
                generateThroneRoom();
                break;
            default:
                System.out.println("Unhandled case in Area constructor.");
                System.exit(-1);
                break;
        }
    }
    
    private void generateThroneRoom() {
        for (int i=0;i<mapWidth;i++) {
            for (int j=0;j<mapHeight;j++) {
                map[i][j] = Math.random()<0.5 ? Tile.STONE_FLOOR : Tile.STONE_WALL;
            }
        }
    }
    
    private void generateDungeon() {
        for (int i=0;i<mapWidth;i++) {
            for (int j=0;j<mapHeight;j++) {
                map[i][j] = Math.random()<0.5 ? Tile.STONE_FLOOR : Tile.STONE_WALL;
            }
        }
    }
    
    private void generateCaves() {
        for (int i=0;i<mapWidth;i++) {
            for (int j=0;j<mapHeight;j++) {
                map[i][j] = Math.random()<0.5 ? Tile.STONE_FLOOR : Tile.STONE_WALL;
            }
        }
        
        Tile[][] newMap = new Tile[mapWidth][mapHeight];
        
        for (int c=0;c<5;c++) {
            for (int i=0;i<mapWidth;i++) {
                for (int j=0;j<mapHeight;j++) {
                    int nFloor = 0;
                    int nWall = 0;
                    
                    for (int ox=-1;ox<=1;ox++) {
                        for (int oy=-1;oy<=1;oy++) {
                            if (i+ox<0 || i+ox>=mapWidth || j+oy<0 || j+oy>=mapHeight)
                                continue;
                            if (map[i+ox][j+oy] == Tile.STONE_FLOOR)
                                nFloor++;
                            else
                                nWall++;
                        }
                    }
                    newMap[i][j] = nFloor>=nWall ? Tile.STONE_FLOOR : Tile.STONE_WALL;
                }
            }
            map = newMap;
        }
    }
}