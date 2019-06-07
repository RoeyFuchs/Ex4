package com.example.ex4;

public interface ObservableInterface {

    void addToObserver(ObserverInterface obs);
    void notifyObservers(FlightDetails flightDetails);
}
