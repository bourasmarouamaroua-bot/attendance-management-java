package service;

import interfaces.Storable;

public class FileManager implements Storable {

    @Override
    public void saveToFile() {

        System.out.println("Saving data to file...");
    }

    @Override
    public void loadFromFile() {

        System.out.println("Loading data from file...");
    }
}