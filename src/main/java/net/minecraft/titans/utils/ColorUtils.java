package net.minecraft.titans.utils;

public class ColorUtils
{
	public static int toHex(int R, int G, int B, int A)
	{
		R = R << 24;
		G = G << 24;
		B = B << 24;
		A = A << 24;
		return R + G + B + A;
	}
}
