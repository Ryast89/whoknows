public class map {
public byte[][] pixels = new byte[600][600];

public map(int level) {
	switch(level) {
		case 1: 
			platform(30, 0, 600);
			platform(29, 0, 600);
			platform(28, 0, 120);
			platform(27, 0, 120);
			platform(23, 60, 440);
			platform(22, 60, 440);
			platform(21, 0, 240);
			platform(20, 0, 240);
			platform(18, 420, 180);
			platform(17, 420, 180);
			platform(16, 420, 180);
			platform(15, 120, 420);
			platform(14, 120, 440);
			spike(14, 120, 340);
			spike(13, 120, 340);
			spike(11, 120, 340);
			spike(10, 120, 340);
			platform(10, 0, 480);
			platform(9, 260, 100);
			exit(9, 300, 20);
			exit(8, 300, 20);
			spike(21, 300, 100);
			//spike(20, 320, 80);
			
			platform(30, 595, 5);
			platform(29, 595, 5);
			platform(28, 595, 5);
			platform(27, 595, 5);
			platform(21, 595, 5);
			platform(20, 595, 5);
			platform(18, 0, 5);
			platform(17, 0, 5);
			platform(16, 0, 5);
			platform(15, 120, 5);
			platform(14, 120, 5);
			platform(10, 595, 5);
			platform(9, 260, 5);
		
			break;
			
		
	}

	
}

	private void platform(int height, int indent, int length) {
		for(int j = 0; j < 20; j++) {
			for(int i = 0; i < length; i++) {
				pixels[indent + i][((height*20) - 1) - j] = 1;
			}
		}
	}
	private void exit(int height, int indent, int size) {
		for(int j = 0; j < 20; j++) {
			for(int i = 0; i < size; i++) {
				pixels[indent + i][((height*20) - 1) - j] = 3;
			}
		}
	}
	private void spike(int height, int indent, int length) {
		for(int j = 0; j < 20; j++) {
			for(int i = 0; i < length; i++) {
				pixels[indent + i][((height*20)) - j] = 2;
			}
		}
	}
	
}