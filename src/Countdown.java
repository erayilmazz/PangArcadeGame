
public class Countdown implements Runnable{
	private GameManager gm;
	
	public Countdown(GameManager gm) {
		this.gm = gm;
	}
	@Override
	public void run() {
		while(gm.running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gm.countdown--;
			if(gm.countdown == 95) gm.isGamePaused = false;
			if(gm.freezeTime == gm.getCountdown()) {
				gm.isFreeze = false;
				gm.freezeTime = 98;
			}
			if(gm.arrowTime == gm.countdown) {
				gm.arrowType = "normal";
				gm.arrowTime = 98;
			}
			if(gm.countdown <= 0) {
				gm.gameOver('t');
			}
			if(gm.player.isInvisible() == true) {
				if(gm.countdown == gm.invisibleTime) gm.player.setInvisible(false);
			}
		}
	}
}
