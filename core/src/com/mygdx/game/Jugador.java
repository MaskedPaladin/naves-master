package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Utils.random;

public class Jugador {
    Sound blast1 = Gdx.audio.newSound(Gdx.files.internal("sounds/blast1.mp3"));
    Sound blast2 = Gdx.audio.newSound(Gdx.files.internal("sounds/blast2.mp3"));
    Animacion animacion = new Animacion(16,
            new Texture("sprite.png")
    );

    float x, y, w, h, v;
    List<Disparo> disparos = new ArrayList<>();
    int vidas = 3;
    int puntos = 0;
    boolean muerto = false;
    Temporizador temporizadorFireRate = new Temporizador(60);
    Temporizador temporizadorRespawn = new Temporizador(120, false);

    Jugador() {
        x = 100;
        y = 100;
        w = 43 * 3;
        h = 21 * 3;
        v = 5;
    }

    void update() {
        for (Disparo disparo : disparos) disparo.update();

        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += v;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= v;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += v;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= v;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && temporizadorFireRate.suena() && !muerto) {
            boolean sound = random.nextBoolean();
            if(sound){ blast1.play(); }
            else{ blast2.play(); }


            disparos.add(new Disparo(x+x/2 , y+28 ));
        }

        if (x < 0) x = 0;

        if (temporizadorRespawn.suena()) {
            muerto = false;
        }
    }

    void render(SpriteBatch batch) {
        batch.draw(animacion.obtenerFrame(), x, y, w, h);
        for (Disparo disparo : disparos) disparo.render(batch);
    }

    public void morir() {
        vidas--;
        muerto = true;
        temporizadorRespawn.activar();
    }
}
