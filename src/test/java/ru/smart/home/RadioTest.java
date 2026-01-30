package ru.smart.home;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class RadioTest {

    private Radio radio;

    @BeforeEach
    public void setUp() {
        radio = new Radio();
    }

    @Test
    public void testInitialState() {
        assertEquals(0, radio.getCurrentStation());
        assertEquals(50, radio.getCurrentVolume());
    }

    @Test
    public void testSetValidStation() {
        radio.setCurrentStation(5);
        assertEquals(5, radio.getCurrentStation());

        radio.setCurrentStation(0);
        assertEquals(0, radio.getCurrentStation());

        radio.setCurrentStation(9);
        assertEquals(9, radio.getCurrentStation());
    }

    @Test
    public void testSetInvalidStationNegative() {
        radio.setCurrentStation(5);
        radio.setCurrentStation(-1);
        assertEquals(5, radio.getCurrentStation()); // Должно остаться 5
    }

    @Test
    public void testSetInvalidStationGreaterThan9() {
        radio.setCurrentStation(5);
        radio.setCurrentStation(10);
        assertEquals(5, radio.getCurrentStation()); // Должно остаться 5
    }

    @Test
    public void testSetInvalidStationMaxInt() {
        radio.setCurrentStation(5);
        radio.setCurrentStation(Integer.MAX_VALUE);
        assertEquals(5, radio.getCurrentStation()); // Должно остаться 5
    }

    @Test
    public void testSetInvalidStationMinInt() {
        radio.setCurrentStation(5);
        radio.setCurrentStation(Integer.MIN_VALUE);
        assertEquals(5, radio.getCurrentStation()); // Должно остаться 5
    }

    @Test
    public void testNextFromMiddleStation() {
        radio.setCurrentStation(5);
        radio.next();
        assertEquals(6, radio.getCurrentStation());
    }

    @Test
    public void testNextFromStation8() {
        radio.setCurrentStation(8);
        radio.next();
        assertEquals(9, radio.getCurrentStation());
    }

    @Test
    public void testNextFromStation9() {
        radio.setCurrentStation(9);
        radio.next();
        assertEquals(0, radio.getCurrentStation());
    }

    @Test
    public void testPrevFromMiddleStation() {
        radio.setCurrentStation(5);
        radio.prev();
        assertEquals(4, radio.getCurrentStation());
    }

    @Test
    public void testPrevFromStation1() {
        radio.setCurrentStation(1);
        radio.prev();
        assertEquals(0, radio.getCurrentStation());
    }

    @Test
    public void testPrevFromStation0() {
        radio.setCurrentStation(0);
        radio.prev();
        assertEquals(9, radio.getCurrentStation());
    }

    @Test
    public void testIncreaseVolumeFromMiddle() {
        radio.setCurrentStation(0); // Чтобы не менять станцию
        int initialVolume = radio.getCurrentVolume();
        radio.increaseVolume();
        assertEquals(initialVolume + 1, radio.getCurrentVolume());
    }

    @Test
    public void testIncreaseVolumeFrom99() {
        // Устанавливаем громкость 99
        while (radio.getCurrentVolume() < 99) {
            radio.increaseVolume();
        }
        assertEquals(99, radio.getCurrentVolume());

        radio.increaseVolume();
        assertEquals(100, radio.getCurrentVolume());
    }

    @Test
    public void testIncreaseVolumeFrom100() {
        // Устанавливаем максимальную громкость
        while (radio.getCurrentVolume() < 100) {
            radio.increaseVolume();
        }
        assertEquals(100, radio.getCurrentVolume());

        // Попытка увеличить сверх максимума
        radio.increaseVolume();
        assertEquals(100, radio.getCurrentVolume()); // Должно остаться 100
    }

    @Test
    public void testDecreaseVolumeFromMiddle() {
        radio.setCurrentStation(0); // Чтобы не менять станцию
        int initialVolume = radio.getCurrentVolume();
        radio.decreaseVolume();
        assertEquals(initialVolume - 1, radio.getCurrentVolume());
    }

    @Test
    public void testDecreaseVolumeFrom1() {
        // Устанавливаем минимальную громкость кроме 0
        while (radio.getCurrentVolume() > 1) {
            radio.decreaseVolume();
        }
        assertEquals(1, radio.getCurrentVolume());

        radio.decreaseVolume();
        assertEquals(0, radio.getCurrentVolume());
    }

    @Test
    public void testDecreaseVolumeFrom0() {
        // Устанавливаем минимальную громкость
        while (radio.getCurrentVolume() > 0) {
            radio.decreaseVolume();
        }
        assertEquals(0, radio.getCurrentVolume());

        // Попытка уменьшить ниже минимума
        radio.decreaseVolume();
        assertEquals(0, radio.getCurrentVolume()); // Должно остаться 0
    }

    @Test
    public void testVolumeBoundaryConditions() {
        // Тест нижней границы
        while (radio.getCurrentVolume() > 0) {
            radio.decreaseVolume();
        }
        assertEquals(0, radio.getCurrentVolume());

        // Многократные попытки уменьшить ниже 0
        for (int i = 0; i < 10; i++) {
            radio.decreaseVolume();
        }
        assertEquals(0, radio.getCurrentVolume());

        // Тест верхней границы
        while (radio.getCurrentVolume() < 100) {
            radio.increaseVolume();
        }
        assertEquals(100, radio.getCurrentVolume());

        // Многократные попытки увеличить выше 100
        for (int i = 0; i < 10; i++) {
            radio.increaseVolume();
        }
        assertEquals(100, radio.getCurrentVolume());
    }

    @Test
    public void testStationBoundaryConditions() {
        // Многократное переключение вперед с зацикливанием
        for (int i = 0; i < 30; i++) {
            radio.next();
        }
        // После 30 переключений (3 полных цикла) должно быть на станции 0
        assertEquals(0, radio.getCurrentStation());

        // Многократное переключение назад с зацикливанием
        for (int i = 0; i < 25; i++) {
            radio.prev();
        }
        // После 25 переключений назад: 0→9→8→... (два полных цикла и еще 5 шагов)
        // 0→9(1), 9→8(2), 8→7(3), 7→6(4), 6→5(5)
        assertEquals(5, radio.getCurrentStation());
    }

    @Test
    public void testMultipleOperations() {
        // Комплексный тест: установка, переключение, изменение громкости
        radio.setCurrentStation(3);
        assertEquals(3, radio.getCurrentStation());

        radio.next();
        assertEquals(4, radio.getCurrentStation());

        radio.prev();
        assertEquals(3, radio.getCurrentStation());

        int initialVolume = radio.getCurrentVolume();
        radio.increaseVolume();
        assertEquals(initialVolume + 1, radio.getCurrentVolume());

        radio.decreaseVolume();
        assertEquals(initialVolume, radio.getCurrentVolume());

        radio.setCurrentStation(9);
        assertEquals(9, radio.getCurrentStation());

        radio.next();
        assertEquals(0, radio.getCurrentStation());
    }
}