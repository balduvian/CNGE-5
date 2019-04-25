package cnge.core;

public class CCD {

    public static class Line {
        public float x0, y0, x1, y1;
        public int index;
        public boolean collidable;

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

    public static class Collision {
        public int info;
        public double t_;

        public Collision(int i, double t) {
            info = i;
            t_ = t;
        }

        public boolean collision() {
            return (info & 0x001) == 0x001;
        }

        public boolean collisionNormal() {
            return (info & 0x010) == 0x010;
        }

        public boolean collisionAntiNormal() {
            return (info & 0x100) == 0x100;
        }
    }

    private static double hitPoint(Line move, Line wall) {
        return (
            (wall.y1 - wall.y0) * (move.x0 - wall.x0) - (wall.x1 - wall.x0) * (move.y0 - wall.y0)
        ) / (
            (wall.x1 - wall.x0) * (move.y1 - move.y0) - (wall.y1 - wall.y0) * (move.x1 - move.x0)
        );
    }

    private static boolean inline(double t) {
        return !(t > 1 || t < 0);
    }

    private static boolean wallStartNormal(Line move, Line wall) {
        return (move.y1 - move.y0) * (move.x0 - wall.x0) + (move.x1 - move.x0) * (wall.y0 - move.y0) > 0;
    }

    private static boolean wallEndNormal(Line move, Line wall) {
        return (move.y1 - move.y0) * (move.x0 - wall.x1) + (move.x1 - move.x0) * (wall.y1 - move.y0) > 0;
    }

    public static double closestPoint(float x0, float y0, Line wall) {
        return (
            Math.pow(wall.x0 - x0, 2) + Math.pow(wall.y0 - y0, 2) + Math.pow(wall.x1 - wall.x0, 2) + Math.pow(wall.y1 - wall.y0, 2) - Math.pow(wall.x1 - x0, 2) - Math.pow(wall.y1 - y0, 2)
        ) / (
            2 * (Math.pow(wall.x1 - wall.x0, 2) + Math.pow(wall.y1 - wall.y0, 2))
        );
    }

    //
    //calls VVVVVVVV
    //

    public static Collision result(Line move, Line wall) {
        double t;
        if(inline(t = hitPoint(move, wall))) {
            boolean sn = wallStartNormal(move, wall);
            boolean en = wallEndNormal(move, wall);

            if(sn && !en) {
                return new Collision(0x011, t);
            } else if(!sn && en) {
                return new Collision(0x101, t);
            }
        }
        return new Collision(0x000, t);
    }

    public static double xAlong(double t, Line line) {
        return (line.x1 - line.x0) * t + line.x0;
    }

    public static double yAlong(double t, Line line) {
        return (line.y1 - line.y0) * t + line.y0;
    }

}
