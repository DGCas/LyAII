package Semantica;

public class If extends Statement{
	private Expression exp;
	private Statement s;
	
	public If(Expression exp, Statement s){
		this.exp = exp;
		this.s = s;
	}
	
	public Expression getExp(){
		return exp;
	}
	
	public void setExp(Expression exp){
		this.exp = exp;
	}
	
	public Statement getS (){
		return s;
	}
	
	public void setS (Statement s){
		this.s = s;
	}
	
	/*public String toString(){
		return exp.toString()+ " " + s;
	}*/
	
}
