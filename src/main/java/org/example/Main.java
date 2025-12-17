package org.example;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Problema de Adapter: Mezcla de interfaces
        List<Object> devices = new ArrayList<>();
        devices.add(new ModernLight());
        devices.add(new OldVintageLamp()); // Ups, esto no es un ISmartDevice

        System.out.println("--- Encendiendo todo (Código Sucio) ---");
        for (Object device : devices) {
            if (device instanceof ISmartDevice) {
                ((ISmartDevice) device).turnOn();
            } else if (device instanceof OldVintageLamp) {
                // Violación: El cliente tiene que saber los detalles de la clase vieja
                ((OldVintageLamp) device).ignite();
            }
        }

        // 2. Problema de Facade: El cliente hace micro-management
        System.out.println("\n--- Preparando Modo Cine (Muy complejo) ---");
        ModernLight light = new ModernLight();
        SmartTV tv = new SmartTV();
        AudioSystem audio = new AudioSystem();

        light.turnOff(); // Apagar luz
        tv.turnOn();
        tv.setChannel(101); // Netflix
        audio.turnOn();
        audio.setVolume(50);

        System.out.println("¡Disfruta la película!");
    }
}

// --- Interfaz Común ---
interface ISmartDevice {
    void turnOn();
    void turnOff();
}

// --- Dispositivo Moderno ---
class ModernLight implements ISmartDevice {
    @Override
    public void turnOn() {
        // Problema: Responsabilidad mezclada (Logging + Lógica) -> Candidato a Decorator
        System.out.println("LOG: Registrando evento de encendido...");
        System.out.println("Luz moderna: Encendida e iluminando.");
    }

    @Override
    public void turnOff() {
        System.out.println("Luz moderna: Apagada.");
    }
}

// --- Dispositivo Legacy (Incompatible) ---
// Nota: No implementa ISmartDevice y sus métodos se llaman diferente.
class OldVintageLamp {
    public void ignite() {
        System.out.println("Lámpara vieja: ¡Fuego! (Encendida con aceite)");
    }

    public void extinguish() {
        System.out.println("Lámpara vieja: Extinguida.");
    }
}

// --- Otros Dispositivos ---
class SmartTV implements ISmartDevice {
    public void turnOn() { System.out.println("TV: Encendida."); }
    public void turnOff() { System.out.println("TV: Apagada."); }
    public void setChannel(int channel) { System.out.println("TV: Canal puesto en " + channel); }
}

class AudioSystem implements ISmartDevice {
    public void turnOn() { System.out.println("Audio: Encendido."); }
    public void turnOff() { System.out.println("Audio: Apagado."); }
    public void setVolume(int vol) { System.out.println("Audio: Volumen al " + vol + "%"); }
}



