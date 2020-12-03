public class Body{

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	
	static final double G = 6.67e-11; 

	/** First constructor for Body*/ 
	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/** Second constructor for Body takes an Body object*/
	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b) {
		double dx = b.xxPos - this.xxPos;
		double dy = b.yyPos - this.yyPos;
		// double sqr_dist = Math.pow(dx,2) + Math.pow(dy,2);
		// double distance = Math.sqrt(sqr_dist);
		double r = Math.hypot(dx,dy);
		return r;
	}

	public double calcForceExertedBy(Body b) {
		double force = Body.G * this.mass * b.mass / Math.pow(this.calcDistance(b),2);
		return force;
	}

	public double calcForceExertedByX(Body b) {
		double dx = b.xxPos - this.xxPos;
		double r = this.calcDistance(b);
		double f = this.calcForceExertedBy(b);
		double fx = f * dx / r;
		return fx;
	}

	public double calcForceExertedByY(Body b) {
		double dy = b.yyPos - this.yyPos;
		double r = this.calcDistance(b);
		double f = this.calcForceExertedBy(b);
		double fy = f * dy / r;
		return fy;
	}

	public double calcNetForceExertedByX(Body[] bodys) {
	double netFx = 0;
	for (Body b : bodys) {
		if (this.equals(b)) {
			continue;
		}
		netFx += this.calcForceExertedByX(b);
	}
	return netFx;

	}

	public double calcNetForceExertedByY(Body[] bodys) {
	double netFy = 0;
	for (Body b : bodys) {
		if (this.equals(b)) {
			continue;
		}
		netFy += this.calcForceExertedByY(b);
	}
	return netFy;
	}

	public void update(double dt, double fx, double fy) {
		double ax = fx / this.mass;
		double ay = fy / this.mass;
		this.xxVel += dt * ax;
		this.yyVel += dt * ay;
		this.xxPos += dt * xxVel;
		this.yyPos += dt * yyVel;
	} 

	/** Drawing One Planet */
	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}



}