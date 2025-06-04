
public class GameLoop implements Runnable{
	private GameManager gm;
	private CollisionManager cm;
	private ObjectManager om;
	private SubPanel sp;
	private GamePanel gp;
	public GameLoop(GameManager gm) {
		this.gm = gm;
		cm = gm.getCollisionManager();
		om = gm.getObjectManager();
		sp = gm.getSubPanel();
		gp = gm.getGamePanel();
	}
	@Override
	public void run() {
		while(gm.running) {
			if(!gm.isGamePaused) {
				gm.player.move();
				synchronized (gm.getBalls()) {
					om.handleExplotion();
					om.updateBalls();
				}
				om.updateArrows();
				om.updateFallingObjects();
				om.updateBlocks();
				cm.checkPlayerBallCollision(gm.player, gm.getBalls());
				cm.checkArrowBallCollision(gm.getArrows(), gm.getBalls());
				cm.checkPlayerItemCollision(gm.player, gm.getFallingObjects());
				cm.checkArrowBlockCollision(gm.getArrows(), gm.getBlocks());
				gm.updateAnimation();
				if(gm.getBalls().isEmpty()) {
					if(gm.getCurrentLevel() == 4) {
						gm.gameOver('f');
						break;
					}
					gm.loadNextLevel();
					break;
				}
			}
			sp.modifySubPanel();
			gp.repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(gm.isGameOver()) gm.running = false;
		}
	}
}
