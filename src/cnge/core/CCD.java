package cnge.core;

public class CCD {

    public static class Line {
        public float x0, y0, x1, y1;

        public Line(float x0, float y0, float x1, float y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
        }

        public Line() {
            x0 = 0;
            y0 = 0;
            x1 = 0;
            y1 = 0;
        }
    }

    private static double hitPoint(Line move, Line wall) {
        return ((move.y1 - move.y0) * (wall.x0 - move.x0) - (move.x1 - move.x0) * (wall.y0 - move.y0)) / ((move.x1 - move.x0) * (wall.y1 - wall.y0) - (move.y1 - move.y0) * (wall.x1 - wall.x0));
    }

    private static boolean inline(double t) {
        return !(t > 1 || t < 0);
    }

    private static boolean startNormal(Line move, Line wall) {
        return (wall.y1 - wall.y0) * (wall.x0 - move.x0) + (wall.x1 - wall.x0) * (move.y0 - wall.y0) >= 0;
    }

    private static boolean endNormal(Line move, Line wall) {
        return (wall.y1 - wall.y0) * (wall.x0 - move.x1) + (wall.x1 - wall.x0) * (move.y1 - wall.y0) >= 0;
    }

    //
    //calls VVVVVVVV
    //


    public static int result(Line move, Line wall) {
        if(inline(hitPoint(move, wall))) {
            boolean sn = startNormal(move, wall);
            boolean en = endNormal(move, wall);

            if(sn && !en) {
                return 0x011;
            } else if(!sn && en) {
                return 0x101;
            }
        }
        return 0x000;
    }

    public static double xAlong(double t, Line line) {
        return (line.x1 - line.x0) * t + line.x0;
    }

    public static double yAlong(double t, Line line) {
        return (line.y1 - line.y0) * t + line.y0;
    }

    public boolean collision(int result) {
        return (result & 0x001) == 0x001;
    }

    public boolean collisionNormal(int result) {
        return (result & 0x010) == 0x010;
    }

    public boolean collisionAntiNormal(int result) {
        return (result & 0x100) == 0x100;
    }

}
