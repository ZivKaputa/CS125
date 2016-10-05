import java.awt.Graphics2D;

/**
 * @author zkaputa2,jwrogge2
 * @reviewed tjgrant2, exue3, denisbp2
 */
public class RainGame {

	public static void main(String[] args) {
		//
		// To get points type your netids above (CORRECTLY-Please double check your partner correctly spells your netid correctly!!)
		// Your netid is the unique part of your @illinois email address
		// Do not put your name or your UIN. 
		// REMEMBER TO COMMIT this file...
	
		double x=0, y=0, dx=0, dy=0, score = 0;
		//int collisionX = 0, collisionY = 0;
		
		String originalText= ""; //original text
		String currentText = ""; //current text
		String typed = ""; //the portion of the text already typed
		boolean newLevel=true;
		String temp = "";
	
		
		
		double lastCorrectTime = 0;
		int level = 1;
		int entryPoint;
		double dyingX=-100, dyingY=-100, dyingDX=0, dyingDY=0, dyingLevel=0, dyingOffsetX = 0,dyingOffsetY=0;
		double deadTime = 0;
		double newLevelTime = 0;
		boolean dead = false;
		int oldLevel = 1;
		
		boolean started = false;
		int successesUntilNextLevel=2;
		int imageHeight = 50;
		int offsetX = 0;
		int offsetY = 0;
		int speed = 0;
		int asteroidIteration = 0;

	
		// Start Screen
		Zen.drawImage("spaceBackground.jpg",0,0);
		Zen.setFont("Courier-80");
		Zen.setColor(256,256,256);
		Zen.drawText("Numberoids",74,120);
		Zen.setFont("Courier-15");
		Zen.drawText("Destroy the asteroids by typing in the numbers",95,180);
		Zen.drawText("Press Spacebar to advance the level",95,210);
		Zen.drawText("PRESS ANY KEY TO BEGIN",130,250);
		
		while (Zen.getEditText().isEmpty()){}
		Zen.setEditText("");
		
		// Main loop
		while (Zen.isRunning() && !dead) {


			Zen.setColor(0,0,0);
			//Check if the user has finished typing the number
			if (currentText.length() == 0) {
				oldLevel = level;
				
				if (started){
					dyingX=x;
					dyingY=y;
					dyingDX=dx;
					dyingDY=dy;
					dyingLevel=level;
					dyingOffsetX = offsetX;
					dyingOffsetY = offsetY;
					deadTime=System.currentTimeMillis();
				}
				
				
				
				entryPoint = (int) (Math.random() * 8);
				

				//Set the entry point of asteroid at random
				switch (entryPoint){
				case 0:
					//Top Left
					x=0-imageHeight;
					y=0-imageHeight;
					offsetX = -imageHeight+10;
					offsetY = -imageHeight+10;
					speed = 3;
					break;
				case 1:
					//Bottom Left
					x=0-imageHeight;
					y=Zen.getZenHeight();
					offsetX = -imageHeight+12;
					offsetY = -2;
					speed = 3;
					break;
				case 2:
					//Top Right
					x=Zen.getZenWidth();
					y=0-imageHeight;
					offsetX = 2;
					offsetY = -imageHeight+23;
					speed = 3;
					break;
				case 3:
					//Bottom Right
					x=Zen.getZenWidth();
					y=Zen.getZenHeight();
					offsetX = -3;
					offsetY = -3;
					speed = 3;
					break;
				case 4:
					//Left
					x=0-imageHeight;
					y=Zen.getZenHeight()/2-imageHeight/2;
					offsetX = -imageHeight+5;
					offsetY = -imageHeight/2+3;
					speed = 2;
					break;
				case 5:
					//Top
					x=Zen.getZenWidth()/2-imageHeight/2;
					y=0-imageHeight;
					offsetX = -imageHeight/2;
					offsetY = -imageHeight+8;
					speed = 2;
					break;
				case 6:
					//Bottom
					x=Zen.getZenWidth()/2-imageHeight/2;
					y=Zen.getZenHeight();;
					offsetX = -imageHeight/2;
					offsetY = 4;
					speed = 2;
					break;
				case 7:
					//Right
					x=Zen.getZenWidth();
					y=Zen.getZenHeight()/2-imageHeight/2;
					offsetX = 0;
					offsetY = -imageHeight/2;
					speed = 2;
					break;
				
				}
				
				
				
				
				
				// generate random number on asteroid
				currentText = "" + (int) (Math.random() * 999);
				originalText = ""+currentText;
				typed = "";
				
				if (started){
					score += level;
					successesUntilNextLevel--;
				}
				
			}
			
			
			
			// advance the level by score
			if (successesUntilNextLevel==0 && started){
				successesUntilNextLevel=2;
				level+=1;
				newLevel = true;
				newLevelTime = System.currentTimeMillis();
			}
			
			
			started = true;
			
			//Find direction towards center
			dx = (speed*(Zen.getZenWidth()/2.0-x)/Math.sqrt(Math.pow((Zen.getZenWidth()/2.0-x),2)+Math.pow((Zen.getZenHeight()/2.0-y),2)));
			dy = (speed*(Zen.getZenHeight()/2.0-y)/Math.sqrt(Math.pow((Zen.getZenWidth()/2.0-x),2)+Math.pow((Zen.getZenHeight()/2.0-y),2)));
			dyingDX = (speed*(Zen.getZenWidth()/2.0-dyingX)/Math.sqrt(Math.pow((Zen.getZenWidth()/2.0-dyingX),2)+Math.pow((Zen.getZenHeight()/2.0-dyingY),2)));
			dyingDY = (speed*(Zen.getZenHeight()/2.0-dyingY)/Math.sqrt(Math.pow((Zen.getZenWidth()/2.0-dyingX),2)+Math.pow((Zen.getZenHeight()/2.0-dyingY),2)));
			
	
			
			
			//Draw Background
			Zen.drawImage("spaceBackground.jpg",0,0);
			
			//Draw laser
			Zen.setColor(255,0,0);
			if (System.currentTimeMillis() - lastCorrectTime < 150 && System.currentTimeMillis() - deadTime > 150){
					Zen.drawLine(Zen.getZenWidth()/2, Zen.getZenHeight()/2, (int)x, (int)y);
			}
			else if (System.currentTimeMillis() - lastCorrectTime < 150 && System.currentTimeMillis() - deadTime < 150){
				Zen.drawLine(Zen.getZenWidth()/2, Zen.getZenHeight()/2, (int)dyingX, (int)dyingY);
			}
			
			
			String asteroidFileName = "asteroid_01_no_moblur-"+asteroidIteration%8+"-"+(int)(asteroidIteration/8)%4+".png";
			Zen.drawImage(asteroidFileName,(int) (x+offsetX),(int) (y+offsetY),50,50);
			
			//Detect collision
			if (Math.pow((Zen.getZenWidth()/2-x),2)+Math.pow((Zen.getZenHeight()/2-y),2) <= 50*50){
				
				dead = true;
				
			}
			
			
			//Flash dead asteroid and score
			if ((((int)(System.currentTimeMillis()-deadTime)/200)%2==1) && System.currentTimeMillis()-deadTime < 1000){
					
					Zen.setFont("Courier-20");
					Zen.setColor(240,240,240);
					Zen.drawImage(asteroidFileName, (int) (dyingX+dyingOffsetX), (int) (dyingY+dyingOffsetY),50,50);
					Zen.drawText("+"+oldLevel,300,355);
			}
			
			if (System.currentTimeMillis()-newLevelTime < 1000 && newLevel){
				Zen.setColor(240,240,240);
				Zen.setFont("Courier-60");
				Zen.drawText("Level "+level,175,80);
				Zen.setFont("Courier-30");
				Zen.drawText("+1 Speed",235,110);
				
			}
			
			if (System.currentTimeMillis()-newLevelTime > 1000 && newLevel){
				newLevel = false;
			}
			
			//Draw Center Circle
			Zen.drawImage("Earth.png",Zen.getZenWidth()/2-50, Zen.getZenHeight()/2-50, 100, 100);
			Zen.setColor(255, 255, 255);
			
			
			//Draw Number near asteroid
			Zen.setFont("Courier-30");
			Zen.setColor(255, 255, 255);
			Zen.drawText(originalText, (int) x, (int) y);
			Zen.setColor(255, 0, 0);
			Zen.drawText(typed, (int) x, (int) y);
			
			//Draw Level and Score
			Zen.setColor(255, 255, 255);
			Zen.setFont("Courier-20");
			Zen.drawText("Score",290,305);
			Zen.setFont("Courier-30");
			Zen.drawText(""+(int)score,Zen.getZenWidth()/2-(-6+12*(""+(int)score).length()),335);
			Zen.flipBuffer();
			
			//Move asteroid
			x += ((double)(level+2)/5 * dx);
			y += ((double)(level+2)/5 * dy);
			
			dyingX += ((double)(dyingLevel+1)/5 * dyingDX);
			dyingY += ((double)(dyingLevel+1)/5 * dyingDY); 
			
			
			
		
			
			
			
			
			
			
			// Find out what keys the user has been pressing.
			String user = Zen.getEditText();
			// Reset the keyboard input to an empty string
			// So next iteration we will only get the most recently pressed keys.
			Zen.setEditText("");
			for(int i=0;i < user.length();i++) {
				char c = user.charAt(i);
				if(!currentText.isEmpty() && c == currentText.charAt(0)){
					currentText = currentText.substring(1,currentText.length()); // all except first character
					typed = typed + c;
					lastCorrectTime = System.currentTimeMillis();
				}
				else if(!currentText.isEmpty() && c != currentText.charAt(0) && c != ' '){
					currentText=originalText;
					typed="";
				}
				if(c == ' '){
					level ++;
					newLevelTime=System.currentTimeMillis();
					newLevel = true;
				}
			}
			
			Zen.sleep(80);// sleep for 90 milliseconds
			asteroidIteration++;

		}
		
		
		Zen.drawImage("spaceBackground.jpg", 0, 0);
		Zen.setFont("Courier-80");
		Zen.setColor(256,256,256);
		Zen.drawText("GAME OVER",74,120);
		Zen.setFont("Courier-15");
		Zen.drawText("Score: "+(int)score,95,180);
		Zen.drawText("PRESS ANY KEY TO EXIT",130,250);
		Zen.flipBuffer();
		Zen.setEditText("");
		while(Zen.getEditText().isEmpty())
			temp = Zen.getEditText();
		System.exit(0);
	}
	


}
