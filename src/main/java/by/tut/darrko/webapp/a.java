package by.tut.darrko.webapp;

class Car{
    private String name;
    private Integer rpm; //добавим в класс обороты двигателя
    private boolean isFullDrive;
    private boolean isGasEngine;

    public Car(String name, Integer rpm, boolean isFullDrive, boolean isGasEngine){
        this.name = name;
        this.rpm = rpm; //инициализируем обороты двигателя в конструкторе
        this.isFullDrive = isFullDrive;
        this.isGasEngine = isGasEngine;
    }

    public boolean isFullDrive(){
        return isFullDrive;
    }

    public boolean isGasEngine(){
        return isGasEngine;
    }

    //метод геттер для названия машины
    public String getName(){
        return name;
    }

    //метод геттер для получения оборотов двигателя
    public Integer getRPM(){
        return rpm;
    }

}

interface Get<T>{
    public T get(Car car);
}

