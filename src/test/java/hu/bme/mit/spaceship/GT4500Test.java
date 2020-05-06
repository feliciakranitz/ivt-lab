package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTS1;
  private TorpedoStore mockTS2;

  @BeforeEach
  public void init() {
    mockTS1 = mock(TorpedoStore.class);
    mockTS2 = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTS1, mockTS2);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(mockTS1.isEmpty()).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1, times(0)).fire(1);
    verify(mockTS2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  @DisplayName("Firing both of them, none of them is empty")

  public void fireTorpedo_All_Success() {
    // Arrange
    when(mockTS1.isEmpty()).thenReturn(false);
    when(mockTS2.isEmpty()).thenReturn(false);
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  @DisplayName("firing first time, first is not empty")
  public void fireTorpedo_Single_firstNotEmpty() {
    when(mockTS1.isEmpty()).thenReturn(false);
    when(mockTS1.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, times(0)).fire(1);
    assertEquals(true, result);
  }

  @Test
  @DisplayName("firing first time, first is empty")
  public void fireTorpedo_Single_firstIsEmpty() {
    when(mockTS1.isEmpty()).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockTS1, times(0)).fire(1);
    verify(mockTS2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  @DisplayName("both stores are empty")
  public void fireTorpedo_Single_bothAreEmpty() {
    when(mockTS1.isEmpty()).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockTS1, times(0)).fire(1);
    verify(mockTS2, times(0)).fire(1);
    assertEquals(false, result);
  }

  @Test
  @DisplayName("firing both, first is empty")
  public void fireTorpedo_All_firstEmpty() {
    when(mockTS1.isEmpty()).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockTS1, times(0)).fire(1);
    verify(mockTS2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  @DisplayName("firing both, second is empty")
  public void fireTorpedo_All_secondEmpty() {
    when(mockTS1.isEmpty()).thenReturn(false);
    when(mockTS2.isEmpty()).thenReturn(true);
    when(mockTS1.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, times(0)).fire(1);
    assertEquals(true, result);
  }

  @Nested
  @DisplayName("when firing second time")
  class SecondFire {

    @BeforeEach
    void fireFirst() {
      when(mockTS1.isEmpty()).thenReturn(false);
      when(mockTS1.fire(1)).thenReturn(true);
      ship.fireTorpedo(FiringMode.SINGLE);
      
      verify(mockTS1, times(1)).fire(1);
    }

    @Test
    @DisplayName("second Store is not empty")
    public void fireTorpedo_Single_secondTime_secondIsNotEmpty() {
      when(mockTS2.isEmpty()).thenReturn(false);
      when(mockTS2.fire(1)).thenReturn(true);

      boolean result = ship.fireTorpedo(FiringMode.SINGLE);

      verify(mockTS2, times(1)).fire(1);
      assertEquals(true, result);
    }

    @Test
    @DisplayName("second Store is empty, firing first again")
    public void fireTorpedo_Single_secondTime_secondIsEmpty() {
      when(mockTS2.isEmpty()).thenReturn(true);
      when(mockTS1.isEmpty()).thenReturn(false);
      when(mockTS1.fire(1)).thenReturn(true);

      boolean result = ship.fireTorpedo(FiringMode.SINGLE);

      verify(mockTS1, times(2)).fire(1);
      verify(mockTS2, times(0)).fire(1);
      assertEquals(true, result);
    }

    @Test
    @DisplayName("second Store is empty, first as well")
    public void fireTorpedo_Single_secondTime_bothAreEmpty() {
      when(mockTS1.isEmpty()).thenReturn(true);
      when(mockTS2.isEmpty()).thenReturn(true);

      boolean result = ship.fireTorpedo(FiringMode.SINGLE);

      verify(mockTS1, times(1)).fire(1);
      verify(mockTS2, times(0)).fire(1);
      assertEquals(false, result);
    }
  }

  @Test
  @DisplayName("fire laser")
  public void fireLaser() {
    boolean result = ship.fireLaser(FiringMode.SINGLE);
    assertEquals(false, result);
  }
}
