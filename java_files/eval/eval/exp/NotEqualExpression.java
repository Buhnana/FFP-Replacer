package mcheli.eval.eval.exp;

public class NotEqualExpression extends Col2Expression {
  public NotEqualExpression() {
    setOperator("!=");
  }
  
  protected NotEqualExpression(NotEqualExpression from, ShareExpValue s) {
    super(from, s);
  }
  
  public AbstractExpression dup(ShareExpValue s) {
    return new NotEqualExpression(this, s);
  }
  
  protected long operateLong(long vl, long vr) {
    return (vl != vr) ? 1L : 0L;
  }
  
  protected double operateDouble(double vl, double vr) {
    return (vl != vr) ? 1.0D : 0.0D;
  }
  
  protected Object operateObject(Object vl, Object vr) {
    return this.share.oper.notEqual(vl, vr);
  }
}
