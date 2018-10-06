package Semantica;

public class Asignation extends Statement{
	private Identifier identifier;
	private Expression exp;
	
	public Asignation (Identifier identifier, Expression exp){
		this.identifier = identifier;
		this.exp = exp;
	}
	
	public Identifier getIdentifier (){
		return identifier;
	}
	
	public void setIdentifier (Identifier identifier){
		this.identifier = identifier;
	}
	
	public Expression getExpression(){
		return exp;
	}
	
	public void setExpression(Expression exp){
		this.exp = exp;
	}
	public String toString(){
		return identifier + " " + exp;
	}
}
