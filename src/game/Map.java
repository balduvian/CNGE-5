package game;

import cnge.core.CCD;
import cnge.core.CNGE;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.CallbackI;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;

abstract public class Map {

    public static final int X0 = 0;
    public static final int Y0 = 1;
    public static final int X1 = 2;
    public static final int Y1 = 3;
    public static final int X2 = 4;
    public static final int Y2 = 5;

    public static class Triangle {
        public int index;
        public float[] points;
        public int type;

        public Triangle(float x0, float y0, float x1, float y1, float x2, float y2, int t) {
            points = new float[]{x0, y0, x1, y1, x2, y2};
            type = t;
        }

        public Triangle() {
            points = new float[6];
            type = 0;
        }
    }

    private int mapWidth;
    private int mapHeight;

    private int zonesWide;
    private int zonesTall;

    private float zoneWidth;
    private float zoneHeight;

    private Triangle[][][] triangleZones;
    private CCD.Line[][][] lineZones;

    private Triangle[] triangles;
    private CCD.Line[] lines;

    private int numTriangles;
    private int numLines;

    private static final char[] RECT_FLAG = {'r','e','c','t'};
    private static final char[] WIDTH_FLAG = {'w','i','d','t','h'};
    private static final char[] HEIGHT_FLAG = {'h','e','i','g','h','t'};
    private static final char[] CLASS_FLAG = {'c','l','a','s','s'};
    private static final char[] POINTS_FLAG = {'p','o','i','n','t','s'};
    private static final char[] POLYGON_FLAG = {'p','o','l','y','g','o','n'};
    private static final char[] LINE_FLAG = {'l','i','n','e'};
    private static final char[] X1_FLAG = {'x','1'};
    private static final char[] Y1_FLAG = {'y','1'};
    private static final char[] X2_FLAG = {'x','2'};
    private static final char[] Y2_FLAG = {'y','2'};

    private static class ReadStruct {
        public int mode;
        public int index;
        public int index2;
        public StringBuilder builder;
        public float[] tempList;

        public ReadStruct() {
            mode = 0;
            index = 0;
            index2 = 0;
            builder = new StringBuilder();
            tempList = new float[6];
        }
    }

    public Map(int w, int h, int zw, int zt, Triangle[] ts, CCD.Line[] ls) {
        init(w, h, zw, zt, ts, ls);
    }

    public Map(int zw, int zt, String map) {
        try {
            FileInputStream input = new FileInputStream(new File(map));
            //stuff we get out
            int currentTriangle = 0;
            int currentLine = 0;
            int nt = 0;
            int nl = 0;
            Triangle[] ts = null;
            CCD.Line[] ls = null;
            int w = 0;
            int h = 0;

            ReadStruct rs = new ReadStruct();
            int current;

            //go thru and find number of lines and triangles
            while((current = input.read()) != -1) {
                switch (rs.mode) {
                    case 0:
                        dualFlag(current, POLYGON_FLAG, LINE_FLAG, rs, 1, 2);
                        break;
                    case 1:
                        ++nt;
                        rs.mode = 0;
                        break;
                    case 2:
                        ++nl;
                        rs.mode = 0;
                        break;
                }
            }

            ts = new Triangle[nt];
            ls = new CCD.Line[nl];

            input.close();
            input = new FileInputStream(new File(map));
            rs = new ReadStruct();

            while((current = input.read()) != -1) {
                switch(rs.mode) {
                    case 0:
                        readFlag(current, RECT_FLAG, rs, 1);
                        break;
                    case 1:
                        readFlag(current, WIDTH_FLAG, rs, 2);
                        break;
                    case 2:
                        goUntil(current, '"', rs, 3);
                        break;
                    case 3:
                        w = getInt(current, '"', rs, 4);
                        break;
                    case 4:
                        readFlag(current, HEIGHT_FLAG, rs, 5);
                        break;
                    case 5:
                        goUntil(current, '"', rs, 6);
                        break;
                    case 6:
                        h = getInt(current, '"', rs, 7);
                        break;
                    case 7: //find the next line or polygon
                        dualFlag(current, POLYGON_FLAG, LINE_FLAG, rs, 8, 13);
                        break;
                    case 8: //if we hit a polygon
                        dualFlag(current, CLASS_FLAG, POINTS_FLAG, rs, 9, 11);
                        ts[currentTriangle] = new Triangle();
                        break;
                    case 9: //if the polygon has a class
                        goUntil(current, '"', rs, 10);
                        break;
                    case 10: //read class
                        ts[currentTriangle].type = getInt(current, '"', rs, 11);
                        break;
                    case 11: //points time
                        goUntil(current, '"', rs, 12);
                        break;
                    case 12: //read points
                        ts[currentTriangle].points = getFloatList(current, '"', ' ', rs, 7);
                        if(rs.mode != 12) {
                            ++currentTriangle;
                        }
                        break;
                    case 13: //if we hit a line
                        readFlag(current, X1_FLAG, rs, 14);
                        ls[currentLine] = new CCD.Line();
                        break;
                    case 14:
                        goUntil(current, '"', rs, 15);
                        break;
                    case 15:
                        ls[currentLine].x0 = getFloat(current, '"', rs, 16);
                        break;
                    case 16:
                        goUntil(current, '"', rs, 17);
                        break;
                    case 17:
                        ls[currentLine].y0 = getFloat(current, '"', rs, 18);
                        break;
                    case 18:
                        goUntil(current, '"', rs, 19);
                        break;
                    case 19:
                        ls[currentLine].x1 = getFloat(current, '"', rs, 20);
                        break;
                    case 20:
                        goUntil(current, '"', rs, 21);
                        break;
                    case 21:
                        ls[currentLine].y1 = getFloat(current, '"', rs, 7);
                        if(rs.mode != 21) {
                            ++currentLine;
                        }
                        break;
                }
            }

            input.close();

            init(w, h, zw, zt, ts, ls);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void readFlag(int current, char[] flag, ReadStruct rs, int out) {
        if(current == flag[rs.index]) {
            if(++rs.index == flag.length) {
                rs.index = 0;
                rs.mode = out;
            }
        } else {
            rs.index = 0;
        }
    }

    private void dualFlag(int current, char[] flag, char[] flag2, ReadStruct rs, int out, int out2) {
        if(current == flag[rs.index]) {
            if(++rs.index == flag.length) {
                rs.index = 0;
                rs.mode = out;
            }
        } else {
            rs.index = 0;
        }

        if(current == flag2[rs.index2]) {
            if(++rs.index2 == flag2.length) {
                rs.index2 = 0;
                rs.mode = out2;
            }
        } else {
            rs.index2 = 0;
        }
    }

    private void goUntil(int current, char u, ReadStruct rs, int out) {
        if(current == u) {
            rs.mode = out;
        }
    }

    private int getInt(int current, char end, ReadStruct rs, int out) {
        if(current == end) {
            rs.mode = out;
            int ret = Integer.parseInt(rs.builder.toString());
            rs.builder = new StringBuilder();
            return ret;
        } else {
            if(current > 47 && current < 58) {
                rs.builder.append((char) current);
            }
            return 0;
        }
    }

    private float getFloat(int current, char end, ReadStruct rs, int out) {
        if(current == end) {
            rs.mode = out;
            float ret = Float.parseFloat(rs.builder.toString());
            rs.builder = new StringBuilder();
            return ret;
        } else {
            rs.builder.append((char)current);
            return 0;
        }
    }

    private float[] getFloatList(int current, char end, char space, ReadStruct rs, int out) {
        if(current == end || rs.index == 6) {
            float[] ret = rs.tempList;
            rs.tempList = new float[6];
            rs.index = 0;
            rs.builder = new StringBuilder();
            rs.mode = out;
            return ret;
        } else {
            if(current == space) {
                String kek = rs.builder.toString();
                rs.tempList[rs.index] = Float.parseFloat(rs.builder.toString());
                rs.builder = new StringBuilder();
                ++rs.index;
            } else {
                rs.builder.append((char)current);
            }
            return null;
        }
    }

    private void init(int w, int h, int zw, int zt, Triangle[] ts, CCD.Line[] ls) {
        triangles = ts;
        lines = ls;
        ArrayList<Triangle>[][] tempTriangleZones = new ArrayList[zw][zt];
        ArrayList<CCD.Line>[][] tempLineZones = new ArrayList[zw][zt];
        //init arraylists
        for(int i = 0; i < zw; ++i) {
            for(int j = 0; j < zt; ++j) {
                tempTriangleZones[i][j] = new ArrayList<>();
                tempLineZones[i][j] = new ArrayList<>();
            }
        }
        numTriangles = triangles.length;
        numLines = lines.length;

        mapWidth = w;
        mapHeight = h;
        zonesWide = zw;
        zonesTall = zt;

        zoneWidth = (float)(mapWidth / zonesWide);
        zoneHeight = (float)(mapHeight / zonesTall);

        //put triangles into zones
        for(int i = 0; i < numTriangles; ++i) {
            Triangle t = triangles[i];
            t.index = i;

            boolean[][] adds = new boolean[zonesWide][zonesTall];

            putIn(t, tempTriangleZones, adds, t.points[X0], t.points[Y0], t.points[X1], t.points[Y1]);
            putIn(t, tempTriangleZones, adds, t.points[X1], t.points[Y1], t.points[X2], t.points[Y2]);
            putIn(t, tempTriangleZones, adds, t.points[X2], t.points[Y2], t.points[X0], t.points[Y0]);
        }

        //put lines into zones
        for(int i = 0; i < numLines; ++i) {
            CCD.Line l = lines[i];
            l.index = i;

            boolean[][] adds = new boolean[zonesWide][zonesTall];

            putIn(l, tempLineZones, adds, l.x0, l.y0, l.x1, l.y1);
        }

        triangleZones = new Triangle[zonesWide][zonesTall][];
        for(int i = 0; i < zonesWide; ++i) {
            for(int j = 0; j < zonesTall; ++j) {
                triangleZones[i][j] = tempTriangleZones[i][j].toArray(new Triangle[0]);
            }
        }

        lineZones = new CCD.Line[zonesWide][zonesTall][];
        for(int i = 0; i < zonesWide; ++i) {
            for(int j = 0; j < zonesTall; ++j) {
                lineZones[i][j] = tempLineZones[i][j].toArray(new CCD.Line[0]);
            }
        }
    }

    private void putIn(Object t, ArrayList[][] put, boolean[][] check, float x0, float y0, float x1, float y1) {
        int[] bounds = getBoundsUnSorted(x0, y0, x1, y1);

        for(int j = bounds[0]; j <= bounds[1]; ++j) {
            for(int k = bounds[2]; k <= bounds[3]; ++k) {
                if(zoneInRange(j, k)) {
                    if (!check[j][k]) {
                        check[j][k] = true;
                        put[j][k].add(t);
                    }
                }
            }
        }
    }

    public int[] getBoundsUnSorted(float x0, float y0, float x1, float y1) {
        int zx0 = (int)(x0 / zoneWidth);
        int zy0 = (int)(y0 / zoneHeight);
        int zx1 = (int)(x1 / zoneWidth);
        int zy1 = (int)(y1 / zoneHeight);

        int right = Math.max(zx1, zx0);
        int left = Math.min(zx1, zx0);

        int down = Math.max(zy1, zy0);
        int up = Math.min(zy1, zy0);

        return new int[]{left, right, up, down};
    }

    public int[] getBoundsSorted(float l, float u, float r, float d) {
        int left = (int)(l / zoneWidth);
        int up = (int)(u / zoneHeight);
        int right = (int)(r / zoneWidth);
        int down = (int)(d / zoneHeight);

        return new int[]{left, right, up, down};
    }

    public boolean zoneInRange(float x, float y) {
        return (x >= 0 && x < zonesWide) && (y >= 0 && y < zonesTall);
    }

    public void render(float l, float u, float r, float d) {
        int[] bounds = getBoundsSorted(l, u, r, d);

        boolean[] alreadyT = new boolean[numTriangles];
        boolean[] alreadyL = new boolean[numLines];

        for(int i = bounds[0]; i <= bounds[1]; ++i) {
            for(int j = bounds[2]; j <= bounds[3]; ++j) {
                if(zoneInRange(i, j)) {
                    Triangle[] tList = triangleZones[i][j];
                    int num = tList.length;
                    for (int k = 0; k < num; ++k) {
                        Triangle tr = tList[k];
                        if (!alreadyT[tr.index]) {
                            renderTri(tr, i, j);
                            alreadyT[tr.index] = true;
                        }
                    }
                    CCD.Line[] lList = lineZones[i][j];
                    num = lList.length;
                    for(int k = 0; k < num; ++k) {
                        CCD.Line li = lList[k];
                        if(!alreadyL[li.index]) {
                            renderLin(li, i, j);
                            alreadyL[li.index] = true;
                        }
                    }
                }
            }
        }
    }

    public abstract void renderTri(Triangle t, int zx, int zy);
    public abstract void renderLin(CCD.Line l, int zx, int zy);

    public CCD.Line[][][] getTriangleZones() {
        return lineZones;
    }

    public int getZonesWide() {
        return zonesWide;
    }

    public int getZonesTall() {
        return zonesTall;
    }

    public int getWidth() {
        return mapWidth;
    }

    public int getheight() {
        return mapHeight;
    }

    public float getZoneWidth() {
        return zoneWidth;
    }

    public float getZoneHeight() {
        return zoneHeight;
    }

}
