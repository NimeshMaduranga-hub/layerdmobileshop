package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class LoadingFormController {

    @FXML
    private Canvas loadingCanvas;

    private class Particle {
        double x, y, vx, vy, width, height, hue;

        Particle(double loaded, double loaderX, double loaderY, double loaderWidth, double loaderHeight) {
            Random rand = new Random();
            this.x = loaderX + (loaded / 100.0) * loaderWidth - rand.nextInt(2);
            this.y = loaderY + rand.nextInt((int) loaderHeight) - loaderHeight / 2;
            this.vx = (rand.nextDouble() * 4 - 2) / 100;
            this.vy = (rand.nextDouble() * 180 - 360) / 100;
            this.width = rand.nextInt(4) / 2.0 + 0.5;
            this.height = rand.nextInt(4) / 2.0 + 0.5;
            this.hue = 0;
        }

        void update(double gravity) {
            Random rand = new Random();
            vx += (rand.nextDouble() * 6 - 3) / 100.0;
            vy += gravity;
            x += vx;
            y += vy;
        }

        boolean isDead(double canvasHeight) {
            return y > canvasHeight;
        }

        void render(GraphicsContext gc) {
            Random rand = new Random();
            double alpha = (rand.nextInt(81) + 20) / 100.0; // 0.2 - 1
            double light = rand.nextInt(21) + 50; // 50-70%
            gc.setFill(Color.hsb(hue, 1, light / 100.0, alpha));
            gc.fillRect(x, y, width, height);
        }
    }

    private final List<Particle> particles = new ArrayList<>();
    private double loaded = 0;
    private final double loaderSpeed = 0.6;
    private final double loaderWidth = 310;
    private final double loaderHeight = 16;
    private final double loaderX = 45;
    private final double loaderY = 42;
    private final double gravity = 0.12;
    private final int particleRate = 4;
    private double hue = 0;

    @FXML
    public void initialize() {
        GraphicsContext gc = loadingCanvas.getGraphicsContext2D();

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (loaded < 100) loaded += loaderSpeed;
                else loaded = 0;

                for (int i = 0; i < particleRate; i++) {
                    Particle p = new Particle(loaded, loaderX, loaderY, loaderWidth, loaderHeight);
                    p.hue = hueStartEnd(loaded, 0, 120);
                    particles.add(p);
                }

                Iterator<Particle> it = particles.iterator();
                while (it.hasNext()) {
                    Particle p = it.next();
                    p.update(gravity);
                    if (p.isDead(loadingCanvas.getHeight())) it.remove();
                }

                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, loadingCanvas.getWidth(), loadingCanvas.getHeight());

                gc.setFill(Color.WHITE);
                gc.fillRect(loaderX, loaderY, loaderWidth, loaderHeight);

                double newWidth = (loaded / 100) * loaderWidth;
                gc.setFill(Color.hsb(hue, 1, 0.4));
                gc.fillRect(loaderX, loaderY, newWidth, loaderHeight);

                for (Particle p : particles) {
                    p.render(gc);
                }

                hue = (hue + 1) % 360;
            }
        }.start();
    }

    private double hueStartEnd(double loaded, double start, double end) {
        return start + (loaded / 100.0) * (end - start);
    }
}