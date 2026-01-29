package ru.smart.home;

public class Radio {
    private int currentStation;
    private int currentVolume;

    public Radio() {
        this.currentStation = 0;
        this.currentVolume = 0;
    }

    // Геттеры
    public int getCurrentStation() { return currentStation; }
    public int getCurrentVolume() { return currentVolume; }

    // Сеттер громкости (как в предыдущем решении)
    public void setCurrentVolume(int volume) {
        if (volume >= 0 && volume <= 100) {
            this.currentVolume = volume;
        }
    }

    // Сеттер станции
    public void setCurrentStation(int station) {
        if (station >= 0 && station <= 9) {
            this.currentStation = station;
        }
    }

    // Переключение на следующую станцию
    public void next() {
        this.currentStation = (this.currentStation + 1) % 10;
    }

    // Переключение на предыдущую станцию
    public void prev() {
        this.currentStation = (this.currentStation - 1 + 10) % 10;
    }

    // Методы увеличения/уменьшения громкости (как в предыдущем решении)
    public void increaseVolume() {
        if (currentVolume < 100) currentVolume++;
    }

    public void decreaseVolume() {
        if (currentVolume > 0) currentVolume--;
    }
}
