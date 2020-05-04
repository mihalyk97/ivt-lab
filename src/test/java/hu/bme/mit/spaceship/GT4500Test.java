package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTS1;
  private TorpedoStore mockTS2;

  @BeforeEach
  public void init(){
    this.mockTS1=mock(TorpedoStore.class);
    this.mockTS2=mock(TorpedoStore.class);

    this.ship = new GT4500(mockTS1, mockTS2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockTS1.isEmpty()).thenReturn(false);
    when(mockTS1.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1, times(1)).isEmpty();
    verify(mockTS1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockTS1.isEmpty()).thenReturn(false);
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL); //Ez a teszt lett kijavítva a feladat során.  //Ezt implementáltuk a feladat során.

    // Assert
    verify(mockTS1, times(1)).isEmpty();
    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, times(1)).isEmpty();
    verify(mockTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_SINGLE_isPrimaryWasFirst(){
    // Arrange
    when(mockTS1.isEmpty()).thenReturn(false);
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1, times(1)).isEmpty();
    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, times(0)).isEmpty();
    verify(mockTS2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_SINGLE_isPrimaryWasFirstThenSecondaryWasSecond(){
    // Arrange
    when(mockTS1.isEmpty()).thenReturn(false);
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1, times(1)).isEmpty();
    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, never()).isEmpty();
    verify(mockTS2, never()).fire(1);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1, times(1)).isEmpty();
    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, times(1)).isEmpty();
    verify(mockTS2, times(1)).fire(1);
  }

   @Test
  public void fireTorpedo_ALL_PrimaryWasFiredThenSecondaryWasNot(){
    // Arrange
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_SINGLE_primaryIsNotEmptyButFailsToFireAndSecondaryWontFire(){
    // Arrange
    when(mockTS1.isEmpty()).thenReturn(false);
    when(mockTS1.fire(1)).thenReturn(false);
    when(mockTS2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockTS1, times(1)).isEmpty();
    verify(mockTS1, times(1)).fire(1);
    verify(mockTS2, never()).fire(1);
  }

  @Test
  public void fireTorpedo_SINGLE_primaryIsFiredThenSecondaryIsEmptyThenPrimaryFires(){
    // Arrange
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS2, times(1)).isEmpty();
    verify(mockTS1, times(2)).fire(1);
    verify(mockTS2, never()).fire(1);
  }

  //kód alapján
  @Test
  public void fireTorpedo_ALL_bothEmpty(){
    // Arrange
    when(mockTS1.isEmpty()).thenReturn(true);
    when(mockTS2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockTS1, times(1)).isEmpty();
    verify(mockTS2, times(1)).isEmpty();
    verify(mockTS1, never()).fire(1);
    verify(mockTS2, never()).fire(1);
  }

}
