package Library.ui;

public class Borrow {
	
	public int ID;
	public Borrow( int ID){
		this.ID=ID;
	}
	
	public String getValue(String propertyName) {
		switch (propertyName) {
			
			case "ID":
				return this.ID+"";
			
		}
		return null;
	}
	public double getID() {
		return this.ID;
	}

}
