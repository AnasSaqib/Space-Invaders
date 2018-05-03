import javax.swing.*; //imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
public class SpaceInvaders extends JFrame implements KeyListener{
	private Image ship; //ship
	private BufferedImage dbImage;
	private Graphics dbg;
	private boolean[] keys=new boolean[256];
	private int shipx,firey,firex,alienx,alieny; //x and y coor for ship, bullet and alien
	private int counter=0; //used for blitting the aliens
	private int score=0; //score
	private int lives=3; //lives
	private int gobackx,goforwardx,largestw,smallestw; //xcoor for when the aliens whoudl change direction
	private int imagenum=1; //two images of aliens 1 and 2
	private boolean fire,firstfire; //firing
	private String direction; //direction that the aliens are moving
	private Enemy enemy; //Enemy object
	private Shields shields; //Shields object
	private ArrayList<Integer> hitxcoor = new ArrayList<Integer>(); //x coordinates that are hit on the shield
	private ArrayList<Integer> hitycoor = new ArrayList<Integer>(); //y coordinates that are hit on the shield
	private int aliens[][] = new int[5][11]; //2d arraylist to keep track of whether the aliens are dead or alive. 1 means alive and 2 means dead
	private String scorecheck[][] = new String[5][11]; //used to check if the score has been added for each alien
	private String gamestatus="running";
	public SpaceInvaders(){ //constructor
		super("Space Invaders");
		setSize(650,750);
		direction="forward";
		shipx=303;
		firey=630;
		alienx=20;
		alieny=155;
		ship = new ImageIcon("ship.png").getImage();
		enemy=new Enemy();
		shields=new Shields();
		for(int q=0;q<5;q++){ //all the aliens are alive
			for (int w=0;w<11;w++){
				aliens[q][w]=1;
			}
		}
		for(int q=0;q<5;q++){ //none of the scores have been checked
			for (int w=0;w<11;w++){
				scorecheck[q][w]="no";
			}
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		setVisible(true);
	}
	public void update(){
		Graphics g = getGraphics();
		if (dbImage==null){
			dbImage=new BufferedImage(getWidth(),getHeight(),1);
			dbg=dbImage.getGraphics();
		}
		if (alieny+130==675){ //checks if the aliens have reached the bottom
			lives-=1;
		}
		paint(dbg); //using the graphics context dbg it allows paint to draw the dbimage
		g.drawImage(dbImage,0,0,this);
	}
	public void keyPressed(KeyEvent e){
		Color col= new Color(dbImage.getRGB(50,50));
		int k=e.getKeyCode();
		keys[k]=true;
	}
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()]=false;
	}
	public void keyTyped(KeyEvent e){}
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,650,750);
		g.setColor(Color.WHITE);
		g.setFont(new Font(getToolkit().getFontList()[2],Font.BOLD,30)); //sets up the font size and type
		g.drawString("Score",45,75); //writes score
		g.drawString("Lives",490,75); //writes lives
		g.setColor(Color.GREEN);
		score=checkScore(score,aliens); //adds the score
		g.drawString(Integer.toString(score),130,76); //displays the score
		g.drawString(Integer.toString(lives),575,76); //displays the lives
		g.drawImage(ship,shipx,646,this); //draws the ship
		g.drawLine(0,675,650,675);
		g.drawLine(0,100,650,100);
		if (firstfire==true){ //initialises the bullets x coor(will stay the same)
			firex=shipx+21;
			firstfire=false;
		}
		shields.create(g); //creates the shields
		shields.damage(g,hitxcoor,hitycoor); //checks if the shields got damaged
		fire=shields.hit(g,fire,firex,firey,hitxcoor,hitycoor); //checks if the bullets are hitting the shield
		if (fire==true){ //draws the bullet
			g.setColor(Color.RED);
			g.fillRect(firex,firey,4,10);
			firey-=12;
		}
		enemy.moveEnemy(g,alienx,alieny,direction,imagenum,aliens); //moves the enemy
		counter+=1;
		if (counter==16){
			counter=0;
			if (imagenum==1){
				imagenum=2;
			}
			else if(imagenum==2){
				imagenum=1;
			}
			if (direction.equals("forward")==true){
				alienx+=15;
			}
			else if(direction.equals("back")==true){
				alienx-=15;
				counter=0;	
			}
			largestw=0;
			for(int q=4;0<=q;q--){
				for (int w=10;0<=w;w--){
					if (aliens[q][w]==1){
						if(w>largestw){
							largestw=w;
							gobackx=635-((w*45)+30);	
						}
					}
				}
			}
			smallestw=10;
			for(int q=0;q<5;q++){
				for (int w=0;w<11;w++){
					if (aliens[q][w]==1){
						if(w<smallestw){
							smallestw=w;
							goforwardx=20-(w*45);	
						}
					}
				}
			}
			if(alienx>gobackx){ //changes direction
				direction="back";
				alienx=gobackx;
				alieny+=25;
			}
			if (alienx<goforwardx){ //changes direction
				direction="forward";
				alienx=goforwardx;
				alieny+=25;
			}
		}
		fire=enemy.hit(firex,firey,alienx,alieny,fire,aliens); //checks if any of the aliens have gotten hit
		if (fire==false){
			firstfire=false;
			firey=630;	
		}
	}
	public boolean playing(){
		if (gamestatus.equals("over")){
			return false;
		}
		else{
			return true;
		}
	}
	public void moveStuff(){ //moves the ship and bullet
		if(keys[37]){ //move left
			shipx-=10;
		}	
		if(keys[39]){ //move right
			shipx+=10;
		}
		if (keys[32]&&fire==false){
			fire=true;
			firstfire=true;
		}
		if (firey<100){
			firstfire=false;
			fire=false;
			firey=630;
		}
		if (shipx>600){
			shipx=600;
		}
		if (shipx<5){
			shipx=5;
		}
	}
	public int checkScore(int score,int[][]aliens){ //checks and increases the score accordingly
		for(int q=0;q<5;q++){
				for (int w=0;w<11;w++){
					if (aliens[q][w]==2&&scorecheck[q][w].equals("no")==true){
						if(q==0){
							score+=40;	
						}
						if(q==1){
							score+=20;	
						}
						if(q==2){
							score+=20;
						}
						if(q==3){
							score+=10;
						}
						if(q==4){
							score+=10;
						}
						scorecheck[q][w]="yes";
					}
				}
			}	
		return score;
	}
	
}

