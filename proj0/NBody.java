public class NBody {

	public static double readRadius(String filename) {
		In in = new In(filename);
		in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Body[] readBodies(String filename) {
		In in = new In(filename);
		int num = in.readInt(); //First line
		in.readDouble(); //Skip the second line
		Body[] bodys = new Body[num];
		for (int i=0; i<num; i++) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			bodys[i] = new Body(xP, yP, xV, yV, m, img);
		}
		return bodys;
	}

	public static void main(String[] args) {

		/** Collect all needed input */
		double T = Double.parseDouble(args[0]); // convert String to double
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double uniRadius = NBody.readRadius(filename);
		Body[] bodys = NBody.readBodies(filename);

		/** Drawing the background */
		StdDraw.setScale(-uniRadius,uniRadius);
		StdDraw.clear(); // Good to clear before start drawing
		StdDraw.picture(0,0,"images/starfield.jpg");

		/** Drawing all of the bodys in their initial position */
		for (Body b : bodys) {
			b.draw();
		}
		/** enableDoubleBuffering: prevent flickering in the animation */
		StdDraw.enableDoubleBuffering();

		/**
		Create a time variable and set it to 0. 
		Set up a loop to loop until this time variable is T.
		For each time through the loop, do the following:
		Create an xForces array and yForces array.
		Calculate the net x and y forces for each planet, storing these in the xForces and yForces arrays respectively.
		Call update on each of the bodys. This will update each planet’s position, velocity, and acceleration.
		Draw the background image.
		Draw all of the bodys.
		Show the offscreen buffer (see the show method of StdDraw).
		Pause the animation for 10 milliseconds (see the pause method of StdDraw). You may need to tweak this on your computer.
		Increase your time variable by dt.
		*/

		for (double time=0; time<=T; time+=dt) {
			double[] xForces = new double[bodys.length];
			double[] yForces = new double[bodys.length];

			/** Calculate the netForces for each planet */
			for (int i=0; i< bodys.length; i++) {
				xForces[i] = bodys[i].calcNetForceExertedByX(bodys);
				yForces[i] = bodys[i].calcNetForceExertedByY(bodys);
			}

			/** Update each planet’s position, velocity, and acceleration */
			for (int i=0; i< bodys.length; i++) {
				bodys[i].update(dt, xForces[i], yForces[i]);
			}

			/** Draw the background image */
			StdDraw.picture(0,0,"images/starfield.jpg");

			/** Draw all of the bodys */
			for (int i=0; i<bodys.length; i++) {
				bodys[i].draw();
			}

			/** Show the offscreen buffer */
			StdDraw.show();

			/** Pause the animation for 10 milliseconds */
			StdDraw.pause(10);
			}

			/** Print out the final state of the universe in the same format as the input */
			StdOut.printf("%d\n", bodys.length);
			StdOut.printf("%.2e\n", uniRadius);
			for (int i=0; i<bodys.length; i++) {
				StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            	bodys[i].xxPos, bodys[i].yyPos, bodys[i].xxVel,
                bodys[i].yyVel, bodys[i].mass, bodys[i].imgFileName);
			}
		}
}