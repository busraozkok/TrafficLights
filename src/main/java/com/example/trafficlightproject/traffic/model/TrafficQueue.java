package com.example.trafficlightproject.traffic.model;

import com.example.trafficlightproject.traffic.core.interfaces.IVehicle;

import java.util.LinkedList;
import java.util.Queue;

public class TrafficQueue {

    // Araçları tutan FIFO (ilk giren ilk çıkar) yapısındaki kuyruk
    private final Queue<IVehicle> queue;

    // Yapıcı metod: boş bir araç kuyruğu oluşturur
    public TrafficQueue() {
        this.queue = new LinkedList<>();
    }

    // Kuyruğa bir araç ekler (yeni gelen araç)
    public void addVehicle(IVehicle vehicle) {
        queue.offer(vehicle); // offer = kuyruğun sonuna ekle
    }

    // Kuyruğun başındaki aracı çıkarır (araç geçer)
    public IVehicle pollVehicle() {
        return queue.poll(); // araç geçtikten sonra kuyruktan silinir
    }

    // Kuyruğun başındaki aracı gösterir (çıkarmaz)
    public IVehicle peekVehicle() {
        return queue.peek(); // sırada bekleyen ilk aracı gösterir
    }

    // Kuyruktaki toplam araç sayısını döndürür
    public int getSize() {
        return queue.size();
    }

    // Kuyruk boş mu diye kontrol eder
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Kuyruğu tamamen temizler
    public void clear() {
        queue.clear();
    }
}