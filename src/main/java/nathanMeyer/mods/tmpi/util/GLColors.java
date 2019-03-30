package nathanMeyer.mods.tmpi.util;

public enum GLColors{
	BLACK("BLACK",0),
	DARK_BLUE("DARK_BLUE",170),
	DARK_GREEN("DARK_GREEN",43520),
	DARK_AQUA("DARK_AQUA",43690),
	DARK_RED("DARK_RED",11141120),
	DARK_PURPLE("DARK_PURPLE",11141290),
	GOLD("GOLD",16755200),
	GRAY("GRAY",11184810),
	DARK_GRAY("DARK_GRAY",5592405),
	BLUE("BLUE",5592575),
	GREEN("GREEN",5635925),
	AQUA("AQUA",5636095),
	RED("RED",16733525),
	LIGHT_PURPLE("LIGHT_PURPLE",16733695),
	YELLOW("YELLOW",16777045),
	WHITE("WHITE",16777215);
	public final String name;
	public final int decimal;
	
	GLColors(String name,int decimal){
		this.name = name;
		this.decimal = decimal;
	}
}