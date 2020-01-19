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
			if(game!=null)
			{
			game.startGame();
			while(game.isRunning())
			{
				m.moveRobot();
				if(game.timeToEnd()<=15)
					break;
			}
			System.out.println("game number :"+i);
			System.out.println("Game Over :" +game.toString());
		}
		}
	}

	
}
