package pt.up.fe.ldts.states;

import pt.up.fe.ldts.Application;
import pt.up.fe.ldts.model.menus.Button;
import pt.up.fe.ldts.states.AppState;
import pt.up.fe.ldts.states.Game;
import pt.up.fe.ldts.states.InitialMenu;
import pt.up.fe.ldts.view.Renderer;
import pt.up.fe.ldts.view.gui.GUI;
import pt.up.fe.ldts.view.gui.LanternaGUI;

import java.io.IOException;

public class MapMenu extends AppState {

    private static final int TICK_TIME = 75;

    private final GUI gui;

    private static final int WIDTH = 40, HEIGHT = 40;

    public MapMenu(Application app) throws Exception {
        super(app);
        this.gui = new LanternaGUI(WIDTH, HEIGHT +1);

        Button defaultButton = new Button(10, 7, "ENTER to Play");
        Button goBackButton = new Button(10, 25, "Q to go back");

        Renderer.clearRenderer();
        Renderer.addDrawable(defaultButton);
        Renderer.addDrawable(goBackButton);
    }

    @Override
    public void start() throws Exception {
        boolean running = true, game = false;
        int FPS = 60;
        int frameTime = 1000 / FPS;

        long startTime = System.currentTimeMillis();
        while (running){

            long lastTime = System.currentTimeMillis();

            GUI.ACTION currentAction = gui.getNextAction();

            switch (currentAction) {
                case QUIT:
                    running = false;
                    break;
                case SELECT:
                    running=false;
                    game = true;
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
        if (game)
            this.app.changeState(new Game(this.app));
        else
            this.app.changeState(new InitialMenu(this.app));
    }

    @Override
    public void render() throws IOException {
        Renderer.render(gui);
    }
}
