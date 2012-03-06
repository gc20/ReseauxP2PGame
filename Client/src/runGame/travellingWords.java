package runGame;

public class travellingWords 
{
	String city;
	int x;
	int y;
	int velX;
	int velY;
	
	public travellingWords (String city, int x, int y, int velX, int velY)
	{
		this.city = city;
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
	}
	
	public int getX ()
	{
		return x;
	}
	
	public int getY ()
	{
		return y;
	}
	
	public void updateX ()
	{
		this.x += velX;
	}
	
	public void updateY ()
	{
		this.y += velY;
	}
	
	public String getCity ()
	{
		return city;
	}
	
	public void burnCity ()
	{
		this.city = "xx";
	}
	
	public int getVelX()
	{
		return velX;
	}
	
	public int getVelY()
	{
		return velY;
	}
}
