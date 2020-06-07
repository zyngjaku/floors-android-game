package io.github.zyngjaku.floors.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import io.github.zyngjaku.floors.R;
import io.github.zyngjaku.floors.game.GameSurface;
import io.github.zyngjaku.floors.utils.Dimensions;
import io.github.zyngjaku.floors.utils.Utils;

public class ObstacleManager {
    private GameSurface gameSurface;

    private int level;
    LinkedList<Obstacle> listOfObstacle;
    LinkedList<LinkedList> listOfTop;
    LinkedList<LinkedList> listOfBottom;

    public ObstacleManager(GameSurface gameSurface) {
        this.gameSurface = gameSurface;

        listOfObstacle = new LinkedList<>();
        addNewObstacle(R.drawable.obstackle_up, (int) (Dimensions.screenWidth/2), (int) (Dimensions.blockPositions[0]+Dimensions.blockHeight));
    }

    public void update()  {
        for(Obstacle o : listOfObstacle) {
            o.update();
        }
    }

    public void draw(Canvas canvas)  {
        for(Obstacle o : listOfObstacle) {
            o.draw(canvas);
        }
    }

    public void newLevel(int level) {
        this.level = level;
        listOfObstacle.clear();

        Random random = new Random();
        int randomObstacles = random.nextInt(5);

        for(int position : getObstacleTop(randomObstacles)) {
            addNewObstacle(R.drawable.obstackle_down, position, (int) (Dimensions.blockPositions[level]));
        }

        for(int position : getObstacleBottom(randomObstacles)) {
            addNewObstacle(R.drawable.obstackle_up, position, (int) (Dimensions.blockPositions[level]+Dimensions.blockHeight));
        }
    }

    public void addNewObstacle(int drawableId, int x, int y) {
        Bitmap obstacleBitmap = BitmapFactory.decodeResource(gameSurface.getResources(), drawableId);
        Bitmap scaledObstacleBitmap = Utils.resizeBitmap(obstacleBitmap, (obstacleBitmap.getWidth() * Dimensions.obstackleSize)/obstacleBitmap.getHeight(), (Dimensions.obstackleSize));

        Obstacle obstacle = new Obstacle(gameSurface, scaledObstacleBitmap);
        obstacle.x = x - obstacle.width/2;
        obstacle.y = (drawableId == R.drawable.obstackle_down)? y : y - obstacle.height;

        listOfObstacle.add(obstacle);
    }

    public LinkedList<Integer> getObstacleTop(int value) {
        switch(value){
            case 0:
            case 2:
                return new LinkedList<>();
            case 1:
            case 3:
                return new LinkedList<>(Arrays.asList((int) Dimensions.screenWidth / 2));
            case 4:
                return new LinkedList<>(Arrays.asList((int) (Dimensions.screenWidth/3), (int) (2*Dimensions.screenWidth/3)));
        }

        return new LinkedList<>();
    }

    public LinkedList<Integer> getObstacleBottom(int value) {
        switch(value){
            case 0:
            case 1:
                return new LinkedList<>(Arrays.asList((int) (Dimensions.screenWidth / 2)));
            case 2:
            case 3:
            case 4:
                return new LinkedList<>(Arrays.asList((int) (Dimensions.screenWidth/3), (int) (2*Dimensions.screenWidth/3)));
        }

        return new LinkedList<>(Arrays.asList((int) (Dimensions.screenWidth / 2)));
    }

    public boolean detectCollision(Ninja ninja) {
        for (Obstacle obstacle : listOfObstacle) {
            if (ninja.getX() + ninja.getWidth() * 0.5 >= obstacle.getX() && ninja.getY() + ninja.getHeight() >= obstacle.getY()+obstacle.height*0.9 &&
                ninja.getX() + ninja.getWidth() * 0.5 <= obstacle.getX()+obstacle.width && ninja.getY() + ninja.getHeight() <= obstacle.getY()+obstacle.height*1.1) {

                return true;
            }

            if (ninja.getX() + ninja.getWidth() * 0.5 <= obstacle.getX() + obstacle.getWidth() * 0.55 && ninja.getX() + ninja.getWidth() * 0.5 >= obstacle.getX() + obstacle.getWidth() * 0.45 &&
                ninja.getY() + ninja.getHeight() >= obstacle.getY() && ninja.getY() + ninja.getHeight() <= obstacle.getY() + obstacle.getHeight() * 0.1) {

                return true;
            }
        }

        return false;
    }
}