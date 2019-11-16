import royaume.*;
import troupes.*;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chateau c = new Chateau("joueur1",100,new ArrayList<Piquier>(),new ArrayList<Chevalier>(),new ArrayList<Onagre>(),"bas");
		System.out.println(c.enProduction());
		c.lancerProduction("amelioration");
		System.out.println(c.enProduction());
		c.terminerProduction();
		System.out.println(c.enProduction());
		
	}

}
