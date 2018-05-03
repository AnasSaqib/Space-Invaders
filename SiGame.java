class SiGame{ 
	public static void main(String[]args){ //main loop
		SpaceInvaders game=new SpaceInvaders(); //creates a game object
		while(game.playing()){ 
			game.moveStuff(); //moves the stuff
			game.update();  //forces call to paint and gives it Graphics g
			delay(20);
		}
	}
	public static void delay(int n){ //delay method
		try{
			Thread.sleep(n);
		}
		catch(InterruptedException e){
			System.out.println("oops........ "+e);
		}
	}
}