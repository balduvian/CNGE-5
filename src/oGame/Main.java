package oGame;

import oCNGE.*;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    Window window;
    Base base;

    public Main() {
        window = new Window();

        base = new Base(window);

        base.gameLoop();
    }

}
