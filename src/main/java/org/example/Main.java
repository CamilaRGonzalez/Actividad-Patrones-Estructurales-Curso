package org.example;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Todos los elementos de la lista son ISmartDevices
        List<ISmartDevice> devices = new ArrayList<>();
        devices.add(new LampDecorator(new ModernLight())); //se envuelve a ModernLight en un decorador que agregue los logs en encendido y apagado
        devices.add(new VintageLampAdapter(new OldVintageLamp())); //No se agrega la lampara vintage, se agrega la interfaz del adaptador

        devices.forEach(ISmartDevice::turnOn); //se pueden prender todos de la misma manera al ser interfaces ISmartDevice

        System.out.println("\n");
        devices.forEach(ISmartDevice::turnOff);

        // Se prepara modo cine invocando 1 solo método del objeto fachada, el cual realiza todas las acciones necesarias
        ModoCineFacade modoCineFacade = new ModoCineFacade(new ModernLight(),new SmartTV(), new AudioSystem());
        modoCineFacade.prepararModoCine();
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

class VintageLampAdapter implements ISmartDevice{
    private final OldVintageLamp oldVintageLamp;

    public VintageLampAdapter(OldVintageLamp oldVintageLamp){
        this.oldVintageLamp = oldVintageLamp;
    }

    @Override
    public void turnOn() {
        oldVintageLamp.ignite();
    }

    @Override
    public void turnOff() {
        oldVintageLamp.extinguish();
    }
}

class ModoCineFacade{
    private final ModernLight light;
    private final SmartTV tv;
    private final AudioSystem audio;

    public ModoCineFacade(ModernLight modernLight, SmartTV smartTV, AudioSystem audioSystem){
        this.light = modernLight;
        this.audio = audioSystem;
        this.tv = smartTV;
    }

    public void prepararModoCine(){
        System.out.println("\nPreparando modo cine...");
        light.turnOff(); // Apagar luz
        tv.turnOn();
        tv.setChannel(101); // Netflix
        audio.turnOn();
        audio.setVolume(50);
        System.out.println("¡Disfruta la película!");
    }
}

abstract class DeviceDecorator implements ISmartDevice {
    protected ISmartDevice wrappedDevice;

    public DeviceDecorator(ISmartDevice device) {
        this.wrappedDevice = device;
    }
}

class LampDecorator extends DeviceDecorator{
    public LampDecorator(ISmartDevice device) {
        super(device);
    }

    @Override
    public void turnOn() {
        System.out.println("LOG: Registrando evento de encendido...");
        this.wrappedDevice.turnOn();
    }

    @Override
    public void turnOff() {
        System.out.println("LOG: Registrando evento de apagado...");
        this.wrappedDevice.turnOff();
    }
}


