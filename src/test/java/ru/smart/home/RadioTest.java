package ru.smart.home;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class RadioTest {

    @Test
    void testDefaultValues() {
        Radio radio = new Radio();
        assertEquals(0, radio.getCurrentStation());
        assertEquals(0, radio.getCurrentVolume());
    }

    @Test
    void testSetValidStation() {
        Radio radio = new Radio();
        radio.setCurrentStation(5);
        assertEquals(5, radio.getCurrentStation());
    }

    @Test
    void testSetInvalidStationLow() {
        Radio radio = new Radio();
        radio.setCurrentStation(-1);
        assertEquals(0, radio.getCurrentStation()); // Остаётся 0
    }

    @Test
    void testSetInvalidStationHigh() {
        Radio radio = new Radio();
        radio.setCurrentStation(10);
        assertEquals(0, radio.getCurrentStation()); // Остаётся 0
    }

    @Test
    void testNextStationNormal() {
        Radio radio = new Radio();
        radio.setCurrentStation(3);
        radio.next();
        assertEquals(4, radio.getCurrentStation());
    }

    @Test
    void testNextStationWrapAround() {
        Radio radio = new Radio();
        radio.setCurrentStation(9);
        radio.next();
        assertEquals(0, radio.getCurrentStation());
    }

    @Test
    void testPrevStationNormal() {
        Radio radio = new Radio();
        radio.setCurrentStation(7);
        radio.prev();
        assertEquals(6, radio.getCurrentStation());
    }

    @Test
    void testPrevStationWrapAround() {
        Radio radio = new Radio();
        radio.setCurrentStation(0);
        radio.prev();
        assertEquals(9, radio.getCurrentStation());
    }

    @Test
    void testIncreaseVolumeNormal() {
        Radio radio = new Radio();
        radio.increaseVolume();
        assertEquals(1, radio.getCurrentVolume());
    }


    @Test
    void testIncreaseVolumeMax() {
        Radio radio = new Radio();
        radio.setCurrentVolume(100);
        radio.increaseVolume();
        assertEquals(100, radio.getCurrentVolume());  // Громкость не должна измениться
    }

    @Test
    void testDecreaseVolumeNormal() {
        Radio radio = new Radio();
        radio.setCurrentVolume(50);
        radio.decreaseVolume();
        assertEquals(49, radio.getCurrentVolume());  // Уменьшение на 1
    }

    @Test
    void testDecreaseVolumeMin() {
        Radio radio = new Radio();
        radio.setCurrentVolume(0);
        radio.decreaseVolume();
        assertEquals(0, radio.getCurrentVolume());  // Громкость остаётся 0
    }
}

