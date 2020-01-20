package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Server.game_service;
import gameClient.KML_Logger;
import gameClient.MyGame;
import gameClient.MyGame_Automaticly;

class MyGame_AutomaticlyTest {
	game_service game;
	


	@Test
	void testMoveRobot() {
		MyGame m;
		for (int i=0;i<=23;i++)
		{
			 m= new MyGame_Automaticly();
			m.initGame(i);
			this.game=m.getGame();
			game.startGame();
			 m.MoveThread();
			while(game.isRunning())
			{
				m.moveRobot();
			}
			System.out.println("game number :"+i);
			System.out.println("Game Over :" +game.toString());
		}
		}
	}

	

