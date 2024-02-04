package net.mrbt0907.utils;
import java.util.Random;
/**A simple math library that can be used to simplify math related calculations*/
public class Maths
{
	private static Random random = new Random();
	/**Used to create 2D vectors for positioning calculations*/
	public static class Vec
	{
		/**Position X of this 2D vector*/
		public double vecX = 0.0D;
		/**Position Z of this 2D vector*/
		public double vecY = 0.0D;
		/**Used to create 2D vectors for positioning calculations*/
		public Vec() 
		{

		}


		/**Used to create 2D vectors for positioning calculations*/
		public Vec(int vecX, int vecY)
		{
			this.vecX = vecX;
			this.vecY = vecY;
		}

		/**Used to create 2D vectors for positioning calculations*/
		public Vec(float vecX, float vecY)
		{
			this.vecX = vecX;
			this.vecY = vecY;
		}

		/**Used to create 2D vectors for positioning calculations*/
		public Vec(double vecX, double vecY)
		{
			this.vecX = vecX;
			this.vecY = vecY;
		}

		/**Calculates the distance between this 2D vector and another set of positions*/
		public double distance(double vecX, double vecY)
		{
			return Math.sqrt((this.vecX - vecX) * (this.vecX - vecX) + (this.vecY - vecY) * (this.vecY - vecY));
		}

		/**Calculates the distance between this 2D vector and another 2D vector*/
		public double distance(Vec vector)
		{
			return distance(vector.vecX, vector.vecY);
		}

		/**Calculates the distance between this 2D vector and another 3D vector. vecY of the 3D vector is not used in the formula*/
		public double distance(Vec3 vector)
		{
			return distance(vector.vecX, vector.vecZ);
		}

		/**Returns the speed of the object based on this 3D motion vector.*/
		public double speed()
		{
			return speed(vecX, vecY);
		}

		/**Returns the speed of the object based on 2D motion vectors.*/
		public double speed(Vec vector)
		{
			return speed(vector.vecX, vector.vecY);
		}

		/**Returns the speed of the object based on 3D motion vectors.*/
		public double speed(Vec3 vector)
		{
			return speed(vector.vecX, vector.vecZ);
		}

		/**Returns the speed of the object based on 2D motion.*/
		public double speed(double motionX, double motionY)
		{
			return motionX * motionX + motionY * motionY;
		}
	}

	/**Used to create 3D vectors for positioning calculations*/
	public static class Vec3
	{
		/**Position X of this 3D vector*/
		public double vecX = 0.0D;
		/**Position Y of this 3D vector*/
		public double vecY = 0.0D;
		/**Position Z of this 3D vector*/
		public double vecZ = 0.0D;
		/**Used to create 3D vectors for positioning calculations*/
		public Vec3() 
		{

		}


		/**Used to create 3D vectors for positioning calculations*/
		public Vec3(int vecX, int vecY, int vecZ)
		{
			this.vecX = vecX;
			this.vecY = vecY;
			this.vecZ = vecZ;
		}

		/**Used to create 3D vectors for positioning calculations*/
		public Vec3(float vecX, float vecY, float vecZ)
		{
			this.vecX = vecX;
			this.vecY = vecY;
			this.vecZ = vecZ;
		}

		/**Used to create 3D vectors for positioning calculations*/
		public Vec3(double vecX, double vecY, double vecZ)
		{
			this.vecX = vecX;
			this.vecY = vecY;
			this.vecZ = vecZ;
		}

		/**Calculates the distance between this 3D vector and another set of positions*/
		public double distance(double vecX, double vecY, double vecZ)
		{
			return Math.sqrt((this.vecX - vecX) * (this.vecX - vecX) + (this.vecY - vecY) * (this.vecY - vecY) + (this.vecZ - vecZ) * (this.vecZ - vecZ));
		}

		/**Calculates the distance between this 3D vector and another 2D vector. The vecY value for the 2D vector is equal to vecY of this vector*/
		public double distance(Vec vector)
		{
			return distance(vector.vecX, vecY, vector.vecY);
		}

		/**Calculates the distance between this 3D vector and another 3D vector*/
		public double distance(Vec3 vector)
		{
			return distance(vector.vecX, vector.vecY, vector.vecZ);
		}

		/**Returns the speed of the object based on this 3D motion vector.*/
		public double speed()
		{
			return speed(vecX, vecY, vecZ);
		}

		/**Returns the speed of the object based on 2D motion vectors.*/
		public double speed(Vec vector)
		{
			return speed(vector.vecX, vector.vecY, 0.0D);
		}

		/**Returns the speed of the object based on 3D motion vectors.*/
		public double speed(Vec3 vector)
		{
			return speed(vector.vecX, vector.vecY, vector.vecZ);
		}

		/**Returns the speed of the object based on .*/
		public double speed(double motionX, double motionY, double motionZ)
		{
			return motionX * motionX + motionY * motionY + motionZ * motionZ;
		}

		/**Returns the speed of the object based on .*/
		public double speed(double motionX, double motionY)
		{
			return speed(motionX, motionY, 0.0D);
		}
	}

	/**Changes the randomizer to a new randomizer.*/
	public static void updateRandom()
	{
		updateRandom(new Random());
	}

	/**Changes the randomizer to a defined randomizer.*/
	public static void updateRandom(Random rand)
	{
		random = rand;
	}

	public static double growth(double start, double rate, double time)
	{
		return start * Math.pow(1 + rate, time);
	}

	/**Returns the average of the given numbers.*/
	public static double mean(Object... numbers)
	{
		double mean = 0;
		for (int i = 0; i < numbers.length; i++)
		if (numbers[i] instanceof Integer)
		mean += (int)numbers[i];
		else if (numbers[i] instanceof Long)
		mean += (long)numbers[i];
		else if (numbers[i] instanceof Short)
		mean += (short)numbers[i];
		else if (numbers[i] instanceof Float)
		mean += (float)numbers[i];
		else if (numbers[i] instanceof Double)
		mean += (double)numbers[i];
		return mean == 0.0D ? 0.0D : mean / (double)numbers.length;
	}

	/**Returns the average of the given doubles.*/
	public static double mean(double... numbers)
	{
		double mean = 0;
		for (int i = 0; i < numbers.length; i++)
		mean += numbers[i];
		return mean == 0.0D ? 0.0D : mean / (double)numbers.length;
	}

	/**Returns the average of the given floats.*/
	public static float mean(float... numbers)
	{
		float mean = 0;
		for (int i = 0; i < numbers.length; i++)
		mean += numbers[i];
		return mean == 0.0F ? 0.0F : mean / (float)numbers.length;
	}

	/**Returns the average of the given integers.*/
	public static int mean(int... numbers)
	{
		int mean = 0;
		for (int i = 0; i < numbers.length; i++)
		mean += numbers[i];
		return mean == 0 ? 0 : mean / numbers.length;
	}

	/**Returns true 50% of the time this is ran.*/
	public static boolean chance()
	{
		return (random(1) == 1) ? true : false;
	}

	/**Returns true if a 0 through 100 random value is equal or greater than the given chance.
	1 = 1% Chance, 25 = 25% Chance, 100 = 100% Chance*/
	public static boolean chance(int chance)
	{
		return (random(0,100) <= chance) ? true : false;
	}

	/**Returns true if a 0.0F through 1.0F random value is equal or greater than the given chance.
	0.01F = 1% Chance, 0.25F = 25% Chance, 1.0F = 100% Chance*/
	public static boolean chance(float chance)
	{
		return (random(0.0F,1.0F) <= chance) ? true : false;
	}

	/**Returns true if a 0.0D through 1.0D random value is equal or greater than the given chance.
	0.01D = 1% Chance, 0.25D = 25% Chance, 1.0D = 100% Chance*/
	public static boolean chance(double chance)
	{
		return (random(0.0D,1.0D) <= chance) ? true : false;
	}

	/**Returns a random integer value from 0 through integerA*/
	public static int random(int integerA)
	{
		return random(0, integerA);
	}

	/**Returns a random integer value from integerA through integerB*/
	public static int random(int integerA, int integerB)
	{
		if (integerA >= integerB)
		return integerA;
		else
		return random.nextInt(integerB - integerA + 1) + integerA;
	}

	/**Returns a random float value from 0.0F through floatA*/
	public static float random(float floatA)
	{
		return random(0.0F, floatA);
	}

	/**Returns a random float value from floatA through floatB*/
	public static float random(float floatA, float floatB)
	{
		if (floatA >= floatB)
		return floatA;
		else
		return (random.nextFloat() * (floatB - floatA + 1.0F)) + floatA;
	}

	/**Returns a random double value from 0.0D through doubleA*/
	public static double random(double doubleA)
	{
		return random(0, doubleA);
	}

	/**Returns a random double value from doubleA through doubleB*/
	public static double random(double doubleA, double doubleB)
	{
		if (doubleA >= doubleB)
		return doubleA;
		else
		return (random.nextDouble() * (doubleB - doubleA + 1.0D)) + doubleA;
	}
}


