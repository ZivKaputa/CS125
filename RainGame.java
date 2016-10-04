/**
 * @author zkaputa2,jwrogge2
 */
public class RainGame {

	public static void main(String[] args) {
		//
		// To get points type your netids above (CORRECTLY-Please double check your partner correctly spells your netid correctly!!)
		// Your netid is the unique part of your @illinois email address
		// Do not put your name or your UIN. 
		// REMEMBER TO COMMIT this file...
	
		int x=0, y=0, dx=0, dy=0, score = 0;
		
		String originalText= ""; //original text
		String currentText = ""; //current text
		String typed = ""; //the portion of the text already typed
		
		Bobber colorBobber = new Bobber();
		colorBobber.currentValue = 130;
		colorBobber.increment = 2;
		colorBobber.lowerValue = 120;
		colorBobber.upperValue = 180;
		
		
		//CHANGED
		int level = 1;
		long startTime =System.currentTimeMillis();
		
		//CHANGED
		boolean started = false;
		int successesUntilNextLevel=2;
		boolean success=false;
		long elapsed;
		
		Zen.setFont("Arial-40");
		while (Zen.isRunning()) {

		
			colorBobber.fire();
			System.out.println(colorBobber.currentValue);
			
			
			
			//Check if the user has finished typing the number
			if (currentText.length() == 0) {
				success=true;
				x = 0;
				y = Zen.getZenHeight() / 2;
				dx = 2;
				dy = 0;
				currentText = "" + (int) (Math.random() * 999);
				originalText = ""+currentText;
				typed = "";
				elapsed = System.currentTimeMillis() - startTime;
				startTime = System.currentTimeMillis();
				if (started) score += 3000 / elapsed;
				successesUntilNextLevel--;
				
			}
			
			
			
			
			if (successesUntilNextLevel==0){
				successesUntilNextLevel=2;
				level+=1;
			}
			
			
			
			
			started = true;
			Zen.setColor(255, colorBobber.currentValue, 255);
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
			
			Zen.setColor(255,255,colorBobber.currentValue);
			Zen.fillRect(0,0,Zen.getZenWidth(),80);
			//Do ALL foregorund drawing after this point
			
			Zen.fillOval(x-20,y-40,colorBobber.currentValue,colorBobber.currentValue);
			
			Zen.setColor(0, 255-x, 0);
			if (success){
				Zen.drawText("+2"+level,x,200);
			}
			Zen.drawText(originalText, x, y);
			Zen.setColor(255, 0, 0);
			Zen.drawText(typed, x, y);
			
			Zen.drawText("Level: "+level,200,60);
			Zen.drawText("Score: "+score,10,60);
			Zen.flipBuffer();
			
			x += dx;
			y += dy;
			
			// Find out what keys the user has been pressing.
			String user = Zen.getEditText();
			// Reset the keyboard input to an empty string
			// So next iteration we will only get the most recently pressed keys.
			Zen.setEditText("");
			
			for(int i=0;i < user.length();i++) {
				char c = user.charAt(i);
				if(c == currentText.charAt(0)){
					currentText = currentText.substring(1,currentText.length()); // all except first character
					typed = typed + c;
				}
				if(c == ' '){
					level ++;
				}
			}
			
			Zen.sleep(90/level);// sleep for 90 milliseconds

		}
	}

}

