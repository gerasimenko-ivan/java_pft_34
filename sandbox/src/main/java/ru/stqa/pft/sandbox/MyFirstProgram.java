package ru.stqa.pft.sandbox;

public class MyFirstProgram {
	public static void main(String[] args) {
		hello("World");
		hello("John");
		hello("user");

		Square s = new Square(5);
		System.out.println("Площадь квадрата со стороной " + s.l + " равна " + s.area());

		Rectangle r = new Rectangle(4, 7);
		System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " равна " + r.area());

		Point point0 = new Point(0, 0);
		Point point1 = new Point(3, 4);
		Point point2 = new Point(-1, -1);
		Point point3 = new Point(-1, 10);
		Point point4 = new Point(1, -10);
		System.out.println("Расстояние между точками " + point0 + " и " + point1 + " равно " + Point.distance(point0, point1));
		System.out.println("Расстояние между точками " + point0 + " и " + point2 + " равно " + point0.distance(point2));
		System.out.println("Расстояние между точками " + point2 + " и " + point3 + " равно " + Point.distance(point2, point3));
		System.out.println("Расстояние между точками " + point3 + " и " + point4 + " равно " + point3.distance(point4));
	}

	public static void hello(String somebody) {
		System.out.println("Hello, " + somebody + "!");
	}

}
