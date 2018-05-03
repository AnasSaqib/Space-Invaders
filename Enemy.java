import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
public class Enemy{
	private Image alien11,alien12,alien21,alien22,alien31,alien32;
	public Enemy(){ //constructor
		alien11 = new ImageIcon("alien11.png").getImage();
		alien12 = new ImageIcon("alien12.png").getImage();
		alien21 = new ImageIcon("alien21.png").getImage();
		alien22 = new ImageIcon("alien22.png").getImage();
		alien31 = new ImageIcon("alien31.png").getImage();
		alien32 = new ImageIcon("alien32.png").getImage();
	}
	public void moveEnemy(Graphics g,int alienx,int alieny,String direction,int imagenum,int[][] aliens){ //moves the enemy
		for (int i=0;i<=450;i+=45){ //checks if the specific alien is alive and then which image it is, 1 or 2..draws all 11 aliens 45 pixels apart
			if (imagenum==1){
				if (aliens[0][i/45]==1){ 
					g.drawImage(alien11,alienx+i,alieny,null);	
				}
				if (aliens[1][i/45]==1){
					g.drawImage(alien21,alienx+i,alieny+50,null);
				}
				if (aliens[2][i/45]==1){
					g.drawImage(alien21,alienx+i,alieny+100,null);
				}
				if (aliens[3][i/45]==1){
					g.drawImage(alien31,alienx+i,alieny+150,null);
				}
				if (aliens[4][i/45]==1){
					g.drawImage(alien31,alienx+i,alieny+200,null);	
				}		
			}
			if (imagenum==2){
				if (aliens[0][i/45]==1){
					g.drawImage(alien12,alienx+i,alieny,null);	
				}
				if (aliens[1][i/45]==1){
					g.drawImage(alien22,alienx+i,alieny+50,null);
				}
				if (aliens[2][i/45]==1){
					g.drawImage(alien22,alienx+i,alieny+100,null);
				}
				if (aliens[3][i/45]==1){
					g.drawImage(alien32,alienx+i,alieny+150,null);	
				}	
				if (aliens[4][i/45]==1){
					g.drawImage(alien32,alienx+i,alieny+200,null);	
				}		
			}			
		}
	}
	public boolean hit(int firex,int firey,int alienx,int alieny,boolean fire,int[][] aliens){ //checks if any of the aliens have gotten hit
		if (fire){
			if (alienx<=firex&&firex<=alienx+480){ //checks if the bullet's x coor is same as an alien's x coor and same fo the y coor
				if (firex<=alienx+(45*((firex-alienx)/45))+30){
					if (alieny<=firey&&firey<=alieny+230){
						if(firey<=alieny+(50*((firey-alieny)/50))+30){
							if (aliens[(firey-alieny)/50][(firex-alienx)/45]==1){
								aliens[(firey-alieny)/50][(firex-alienx)/45]=2;
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
}

		