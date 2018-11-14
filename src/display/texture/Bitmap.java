package display.texture;

import java.util.Arrays;

public class Bitmap {
	private final int m_width, m_height; // width and height of the image
	private final byte m_components[]; // ARGB for every pixel

	public Bitmap(int width, int height) {
		m_width = width;
		m_height = height;
		m_components = new byte[width * height * 4];
	}

	public void clear(byte shade) {
		Arrays.fill(m_components, shade);
	}

	public void drawPixel(int x, int y, byte a, byte b, byte g, byte r) {
		final int index = (x + y * m_width) * 4;
		m_components[index] = a;
		m_components[index + 1] = b;
		m_components[index + 2] = g;
		m_components[index + 3] = r;
	}

	public void copyToByteArray(byte[] dest) {
		for (int i = 0; i < m_width * m_height; i++) {
			dest[i * 3] = m_components[i * 4 + 1];
			dest[i * 3 + 1] = m_components[i * 4 + 2];
			dest[i * 3 + 2] = m_components[i * 4 + 3];
		}
	}

	public void copyToIntArray(int[] dest) {
		for (int i = 0; i < m_width * m_height; i++) {
			int a = m_components[i * 4] << 24; // shift to byte 4
			int r = m_components[i * 4 + 1] << 16; // shift to byte 3
			int g = m_components[i * 4 + 2] << 8; // shift to byte 2
			int b = m_components[i * 4 + 3]; // is at byte 1

			dest[i] = a | r | g | b; // Bitwise or operation to combine them into an Integer
		}
	}
}
