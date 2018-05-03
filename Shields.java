import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
public class Shields{
	private Image shield;
	private Color colour; //used to get the colour at a point
	public Shields(){ //constructor
		shield = new ImageIcon("shield.png").getImage();
	}
	public void create(Graphics g){ //draws the 4 shields
		g.drawImage(shield,50,550,null);
		g.drawImage(shield,200,550,null);
		g.drawImage(shield,350,550,null);
		g.drawImage(shield,500,550,null);		
	}
	public boolean hit(Graphics g,boolean fire,int firex,int firey,ArrayList<Integer> hitxcoor,ArrayList<Integer> hitycoor){ //checks if the shield has gotten hit
		if (fire){
			if (550<=firey&&firey<=623){ //checks if the bullets x and y values collide wiht any shield
				if (50<=firex+2&&firex+2<=150||200<=firex+2&&firex+2<=300||350<=firex+2&&firex+2<=450||500<=firex+2&&firex+2<=600){
					try{
						colour=(getColour(firex+2,firey+2));
					}
					catch (AWTException e){
		    			System.out.println("Oh no! "+e);
		    		}
		    		System.out.println(colour);
		    		if (colour.equals(Color.BLACK)==false){ //if the colour at the point of the bullet is not black then that means it is hitting a shield
		    			hitxcoor.add(firex);
		    			hitycoor.add(firey);
		    			return false;	
		    		}
		    		else{
		    			return true;
		    		}
				}
				else{
					return true;
				}
				
			}
			else{
				return true;
			}
		}
		else{
			return false;
		}
	}
	public void damage(Graphics g,ArrayList<Integer> hitxcoor,ArrayList<Integer> hitycoor){ //draws black circles wherever the bullet has hit the shield
		for (int e=0;e<hitxcoor.size();e++){
			g.setColor(Color.BLACK);
			g.fillOval(hitxcoor.get(e)-8,hitycoor.get(e)-9,20,20);
		}	
	}
	public static Color getColour(int x, int y)throws AWTException{ //gets the colour at a point
   		return new Robot().getPixelColor(x,y);
	}
}

	
				/*
			try{
				colour=(getColour(firex,firey));
				black=(getColour(325,50));
			}
			catch (AWTException e){
		    	System.out.println("Oh no! "+e);
		    }
		    System.out.println(colour);
		    if (colour.equals(Color.black)==false){
		    	System.out.println("GFGFDGHDFH");
		    	hitxcoor.add(firex);
		    	hitycoor.add(firey);
		    	return false;	
		    }
		    else{
		    //	System.out.println("GFGFDGHDFH");
		    	return true;
		    }
		    */