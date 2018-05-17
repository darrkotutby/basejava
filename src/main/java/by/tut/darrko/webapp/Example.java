package by.tut.darrko.webapp;

public class Example{
    private static void print(Car car, Get get){
        System.out.println(get.get(car));
    }

    public static void main(String[] args) {
        Car audiA3 = new Car("AudiA3", 5000, true, true);
        Car audiA6 = new Car("AudiA6", 8000, true, false);

        print(audiA6, c -> c.getName());
        print(audiA6, c -> c.getRPM());

    }
}
