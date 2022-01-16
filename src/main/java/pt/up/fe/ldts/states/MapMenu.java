package pt.up.fe.ldts.states;

import pt.up.fe.ldts.Application;
import pt.up.fe.ldts.model.map.DefaultMap;
import pt.up.fe.ldts.model.map.FileLoadedMap;
import pt.up.fe.ldts.model.menus.Button;
import pt.up.fe.ldts.model.menus.MenuDisplay;
import pt.up.fe.ldts.states.AppState;
import pt.up.fe.ldts.states.Game;
import pt.up.fe.ldts.states.InitialMenu;
import pt.up.fe.ldts.view.Renderer;
import pt.up.fe.ldts.view.gui.GUI;
import pt.up.fe.ldts.view.gui.LanternaGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapMenu extends AppState {

    private static final int TICK_TIME = 75;

    private final GUI gui;

    private static final int WIDTH = 40, HEIGHT = 40;

    private MenuDisplay display;

    public MapMenu(Application app) throws Exception {
        super(app);
        this.gui = new LanternaGUI(WIDTH, HEIGHT +1);

        List<Button> buttons = new ArrayList<>();

        buttons.add(new Button(10, 2, "EASY MODE (1)"));
        buttons.add(new Button(10, 10, "DEFAULT MODE (2)"));
        buttons.add(new Button(10, 18, "HARD MODE (3)"));
        buttons.add(new Button(10, 30, "Q TO GO BACK"));

        display = new MenuDisplay(buttons);

        Renderer.clearRenderer();
        Renderer.addDrawable(display);
    }

    @Override
    public void start() throws Exception {
        boolean running = true;
        String mapName;
        int FPS = 60;
        int frameTime = 1000 / FPS;

        long startTime = System.currentTimeMillis();

        this.display.selectTop();

        while (running){

            long lastTime = System.currentTimeMillis();

            GUI.ACTION currentAction = gui.getNextAction();

            switch (currentAction) {
                case QUIT:           // not necessary here, staying as an extra
                    running = false;
                    break;
                case SELECT:
                    running=false;
                    break;
                case UP:
                    display.selectUP();
                    break;
                case DOWN:
                    display.selectDown();
                    break;
                default:
                    break;
            }

            if (lastTime - startTime > TICK_TIME) {
                startTime = lastTime;
            }

            this.render();

            long elapsedTime = System.currentTimeMillis() - lastTime;
            long sleepTime = frameTime - elapsedTime;

            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {
            }
        }
        gui.close();
        switch (display.getSelected()) {
            case 0:
                mapName = "baby";
                this.app.changeState(new Game(this.app, mapName));
                break;
            case 1:
                mapName = "default";
                this.app.changeState(new Game(this.app, mapName));
                break;
            case 2:
                mapName = "hard";
                this.app.changeState(new Game(this.app, mapName));
                break;
            case 3:
                this.app.changeState(new InitialMenu(this.app));
                break;
        }


    }

    @Override
    public void render() throws IOException {
        Renderer.render(gui);
    }
}
