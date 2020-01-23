package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gameClient.MyGame;
import gameClient.MyGame_Automaticly;
import gameClient.MyGame_Manual;

class MyGame_ManualTest {

	@Test
	void testInitGame() {
		MyGame m;
		for (int i=0;i<=23;i++)
		{
			m= new MyGame_Manual();
			m.initGame(i);
		}
	}


}
